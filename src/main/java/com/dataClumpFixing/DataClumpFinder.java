/*
 * Copyright 2021 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dataClumpFixing;

import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.val;

import java.util.HashSet;

import org.openrewrite.*;
import org.openrewrite.internal.lang.NonNull;
import org.openrewrite.java.*;
import org.openrewrite.java.search.UsesMethod;
import org.openrewrite.java.tree.J;
import org.openrewrite.java.tree.JavaCoordinates;
import org.openrewrite.java.tree.Statement;
import org.openrewrite.java.tree.J.Block;
import org.openrewrite.java.tree.J.ClassDeclaration;
import org.openrewrite.java.tree.J.MethodDeclaration;
import org.openrewrite.java.tree.J.VariableDeclarations;
import org.openrewrite.java.tree.J.VariableDeclarations.NamedVariable;
import org.openrewrite.marker.Range;
import java.util.ArrayList;
import java.util.HashMap;
import com.google.gson.Gson;

@Value
@EqualsAndHashCode(callSuper = true)
public class DataClumpFinder extends Recipe {
    private static final MethodMatcher NEW_ARRAY_LIST = new MethodMatcher(
            "com.google.common.collect.Lists newArrayList()");
    private static final MethodMatcher NEW_ARRAY_LIST_ITERABLE = new MethodMatcher(
            "com.google.common.collect.Lists newArrayList(java.lang.Iterable)");
    private static final MethodMatcher NEW_ARRAY_LIST_CAPACITY = new MethodMatcher(
            "com.google.common.collect.Lists newArrayListWithCapacity(int)");

    @Override
    public String getDisplayName() {
        // language=markdown
        return "Use `new ArrayList<>()` instead of Guava";
    }

    @Override
    public String getDescription() {
        // language=markdown
        return "Prefer the Java standard library over third-party usage of Guava in simple cases like this.";
    }

   
    private HashMap<String, VariablesHolder> variablesMap = new HashMap<>();

   
    public VariablesHolder getExistingClassVariableHolder(ClassDeclaration classDecl) {
        return variablesMap.get(classDecl.getType().getFullyQualifiedName());
    }

    public void checkDataClumps() {
        Object[] holdersObject =variablesMap.values().toArray();
        VariablesHolder[] holders = new VariablesHolder[holdersObject.length];
        for(int i=0;i<holdersObject.length;i++){
            holders[i]=(VariablesHolder)holdersObject[i];
        }
       for(int i=0;i<holders.length;i++){
        for(int j=i+1;j<holders.length;j++){
            if(VariablesHolder.checkDataClump(holders[i], holders[j])){
                System.out.println("############# Start data clump #############");
                System.out.println(holders[i].getClassReference().getType().getFullyQualifiedName() + ":::" +( holders[i].getMethodReference()!=null ?holders[i].getMethodReference().getSimpleName():" null"));
                System.out.println(holders[i].toString());
                System.out.println();
                System.out.println(holders[j].getClassReference().getType().getFullyQualifiedName() + ":::" + (holders[j].getMethodReference()!=null ?holders[j].getMethodReference().getSimpleName():" null"));
                System.out.println(holders[j].toString());
                System.out.println("############# End data clump #############");
                System.out.println();
            }
        }
       }
    }

    @Override
    public TreeVisitor<?, ExecutionContext> getVisitor() {
        JavaVisitor<ExecutionContext> visitor= new JavaVisitor<ExecutionContext>() {

            @Override
            public J visitClassDeclaration(ClassDeclaration cld, ExecutionContext p) {
                String qualified = cld.getType().getFullyQualifiedName();
                JavaVisitor<ExecutionContext> classVisitor = this;
                cld.accept(new JavaVisitor<ExecutionContext>() {
                    @Override
                    public J visitClassDeclaration(ClassDeclaration classDecl, ExecutionContext p) {
                        if (classDecl == cld)
                            return super.visitClassDeclaration(classDecl, p);
                        return classVisitor.visitClassDeclaration(classDecl, p);
                    }

                    @Override
                    public J visitMethodDeclaration(MethodDeclaration method, ExecutionContext p) {
                        String fullyQualified = qualified + "." + method.getSimpleName() ;
                        if(method.getMethodType().isConstructor()){
                            return null;
                        }
                        method.accept(new JavaVisitor<ExecutionContext>() {
                            @Override
                            public J visitBlock(Block block, ExecutionContext p) {
                                return null;
                            }
                        }, p);
                       final VariablesHolder methodHolder = variablesMap.get(fullyQualified)!=null? variablesMap.get(fullyQualified): new VariablesHolder(cld, method) ;
                        
                        variablesMap.put(fullyQualified, methodHolder);
                        
                        for (Statement param : method.getParameters()) {
                            param.accept(new JavaVisitor<ExecutionContext>() {
                                @Override
                                public J visitVariable(
                                        org.openrewrite.java.tree.J.VariableDeclarations.NamedVariable variable,
                                        ExecutionContext p) {
                                    methodHolder.addVariable(variable.getType().toString(), variable.getSimpleName());
                                    return super.visitVariable(variable, p);
                                };
                            }, p);

                        }

                        return null;
                    }

                    @Override
                    public J visitVariable(NamedVariable variable, ExecutionContext p) {
                        VariablesHolder classHolder = getExistingClassVariableHolder(cld);
                        if (classHolder == null) {
                            classHolder = new VariablesHolder(cld, null);
                            variablesMap.put(qualified, classHolder);
                        }
                        classHolder.addVariable(variable.getType().toString(), variable.getSimpleName());
                        //System.out.println("field " + variable.getType().toString() + " " + variable.getSimpleName());

                        return super.visitVariable(variable, p);
                    }

                }, p);

                return super.visitClassDeclaration(cld, p);

            };

        };
        checkDataClumps();
        return visitor;
    }
}
