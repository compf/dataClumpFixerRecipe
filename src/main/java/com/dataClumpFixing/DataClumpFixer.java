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
import org.openrewrite.java.tree.J.MethodDeclaration;
import org.openrewrite.marker.Range;

import com.google.gson.Gson;
@Value
@EqualsAndHashCode(callSuper = true)
public class DataClumpFixer extends Recipe {
    private static final MethodMatcher NEW_ARRAY_LIST = new MethodMatcher("com.google.common.collect.Lists newArrayList()");
    private static final MethodMatcher NEW_ARRAY_LIST_ITERABLE = new MethodMatcher("com.google.common.collect.Lists newArrayList(java.lang.Iterable)");
    private static final MethodMatcher NEW_ARRAY_LIST_CAPACITY = new MethodMatcher("com.google.common.collect.Lists newArrayListWithCapacity(int)");

    @Override
    public String getDisplayName() {
        //language=markdown
        return "Use `new ArrayList<>()` instead of Guava";
    }

    @Override
    public String getDescription() {
        //language=markdown
        return "Prefer the Java standard library over third-party usage of Guava in simple cases like this.";
    }



final String methodParameterDCTest =
    "{" +
    "    'type':'data_clump'," +
    "    'key':'parameters_to_parameters_data_clump-lib/src/main/java/org/example/MathStuff.java-org/example/MathStuff/method/printLength(int x, int y, int z)-org/example/MathStuff/method/printMax(int x, int y, int z)-xyz'," +
    "    'probability':1," +
    "    'from_file_path':'src/main/java/org/example/MathStuff.java'," +
    "    'from_class_or_interface_name':'MathStuff'," +
    "    'from_class_or_interface_key':'org.example.MathStuff'," +
    "    'from_method_name':'printLength'," +
    "    'from_method_key':'org.example.MathStuff/method/printLength(int x, int y, int z)'," +
    "    'to_file_path':'src/main/java/org/example/MathStuff.java'," +
    "    'to_class_or_interface_name':'MathStuff'," +
    "    'to_class_or_interface_key':'org.example.MathStuff'," +
    "    'to_method_name':'printMax'," +
    "    'to_method_key':'org.example.MathStuff/method/printMax(int x, int y, int z)'," +
    "    'data_clump_type':'parameters_to_parameters_data_clump'," +
    "    'data_clump_data':{" +
    "        'org/example/MathStuff/method/printLength(int x, int y, int z)/parameter/x':{" +
    "            'key':'org.example.MathStuff/method/printLength(int x, int y, int z)/parameter/x'," +
    "            'name':'x'," +
    "            'type':'int'," +
    "            'probability':1," +
    "            'modifiers':[]," +
    "            'to_variable':{" +
    "                'key':'org.example.MathStuff/method/printMax(int x, int y, int z)/parameter/x'," +
    "                'name':'x'," +
    "                'type':'int'," +
    "                'modifiers':[]," +
    "                'position':{" +
    "                    'startLine':13," +
    "                    'startColumn':30," +
    "                    'endLine':13," +
    "                    'endColumn':31" +
    "                }" +
    "            }," +
    "            'position':{" +
    "                'startLine':5," +
    "                'startColumn':33," +
    "                'endLine':5," +
    "                'endColumn':34" +
    "            }" +
    "        }," +
    "        'org/example/MathStuff/method/printLength(int x, int y, int z)/parameter/y':{" +
    "            'key':'org.example.MathStuff/method/printLength(int x, int y, int z)/parameter/y'," +
    "            'name':'y'," +
    "            'type':'int'," +
    "            'probability':1," +
    "            'modifiers':[]," +
    "            'to_variable':{" +
    "                'key':'org.example.MathStuff/method/printMax(int x, int y, int z)/parameter/y'," +
    "                'name':'y'," +
    "                'type':'int'," +
    "                'modifiers':[]," +
    "                'position':{" +
    "                    'startLine':13," +
    "                    'startColumn':37," +
    "                    'endLine':13," +
    "                    'endColumn':38" +
    "                }" +
    "            }," +
    "            'position':{" +
    "                'startLine':5," +
    "                'startColumn':40," +
    "                'endLine':5," +
    "                'endColumn':41" +
    "            }" +
    "        }," +
    "        'org/example/MathStuff/method/printLength(int x, int y, int z)/parameter/z':{" +
    "            'key':'org.example.MathStuff/method/printLength(int x, int y, int z)/parameter/z'," +
    "            'name':'z'," +
    "            'type':'int'," +
    "            'probability':1," +
    "            'modifiers':[]," +
    "            'to_variable':{" +
    "                'key':'org.example.MathStuff/method/printMax(int x, int y, int z)/parameter/z'," +
    "                'name':'z'," +
    "                'type':'int'," +
    "                'modifiers':[]," +
    "                'position':{" +
    "                    'startLine':13," +
    "                    'startColumn':44," +
    "                    'endLine':13," +
    "                    'endColumn':45" +
    "                }" +
    "            }," +
    "            'position':{" +
    "                'startLine':5," +
    "                'startColumn':47," +
    "                'endLine':5," +
    "                'endColumn':48" +
    "            }" +
    "        }" +
    "    }" +
    "}";

    final String fieldDCTest =
    "{\n" +
    "    'type':'data_clump',\n" +
    "    'key':'parameters_to_parameters_data_clump-lib/src/main/java/org/example/MathStuff.java-org/example/MathStuff/method/printLength(int x, int y, int z)-org/example/MathStuff/method/printMax(int x, int y, int z)-xyz',\n" +
    "    'probability':1,\n" +
    "    'from_file_path':'src/main/java/org/example/MathStuff.java',\n" +
    "    'from_class_or_interface_name':'MathStuff',\n" +
    "    'from_class_or_interface_key':'org.example.MathStuff',\n" +
    "    'to_file_path':'src/main/java/org/example/Library.java',\n" +
    "    'to_class_or_interface_name':'Library',\n" +
    "    'to_class_or_interface_key':'org.example.Library',\n" +
    "    'data_clump_type':'fields_to_fields_data_clump',\n" +
    "    'data_clump_data':{\n" +
    "        'org/example/MathStuff/method/printLength(int x, int y, int z)/parameter/x':{\n" +
    "            'key':'org.example.MathStuff/method/printLength(int x, int y, int z)/parameter/x',\n" +
    "            'name':'sign',\n" +
    "            'type':'boolean',\n" +
    "            'probability':1,\n" +
    "            'modifiers':[],\n" +
    "            'to_variable':{\n" +
    "                'key':'org.example.MathStuff/method/printMax(int x, int y, int z)/parameter/x',\n" +
    "                'name':'sign',\n" +
    "                'type':'boolean',\n" +
    "                'modifiers':[],\n" +
    "                'position':{\n" +
    "                    'startLine':13,\n" +
    "                    'startColumn':30,\n" +
    "                    'endLine':13,\n" +
    "                    'endColumn':31\n" +
    "                }\n" +
    "            },\n" +
    "            'position':{\n" +
    "                'startLine':5,\n" +
    "                'startColumn':33,\n" +
    "                'endLine':5,\n" +
    "                'endColumn':34\n" +
    "            }\n" +
    "        },\n" +
    "        'org/example/MathStuff/method/printLength(int x, int y, int z)/parameter/y':{\n" +
    "            'key':'org.example.MathStuff/method/printLength(int x, int y, int z)/parameter/y',\n" +
    "            'name':'mantissa',\n" +
    "            'type':'double',\n" +
    "            'probability':1,\n" +
    "            'modifiers':[],\n" +
    "            'to_variable':{\n" +
    "                'key':'org.example.MathStuff/method/printMax(int x, int y, int z)/parameter/y',\n" +
    "                'name':'mantissa',\n" +
    "                'type':'double',\n" +
    "                'modifiers':[],\n" +
    "                'position':{\n" +
    "                    'startLine':13,\n" +
    "                    'startColumn':37,\n" +
    "                    'endLine':13,\n" +
    "                    'endColumn':38\n" +
    "                }\n" +
    "            },\n" +
    "            'position':{\n" +
    "                'startLine':5,\n" +
    "                'startColumn':40,\n" +
    "                'endLine':5,\n" +
    "                'endColumn':41\n" +
    "            }\n" +
    "        },\n" +
    "        'org/example/MathStuff/method/printLength(int x, int y, int z)/parameter/z':{\n" +
    "            'key':'org.example.MathStuff/method/printLength(int x, int y, int z)/parameter/z',\n" +
    "            'name':'exponent',\n" +
    "            'type':'int',\n" +
    "            'probability':1,\n" +
    "            'modifiers':[],\n" +
    "            'to_variable':{\n" +
    "                'key':'org.example.MathStuff/method/printMax(int x, int y, int z)/parameter/z',\n" +
    "                'name':'exponent',\n" +
    "                'type':'int',\n" +
    "                'modifiers':[],\n" +
    "                'position':{\n" +
    "                    'startLine':13,\n" +
    "                    'startColumn':44,\n" +
    "                    'endLine':13,\n" +
    "                    'endColumn':45\n" +
    "                }\n" +
    "            },\n" +
    "            'position':{\n" +
    "                'startLine':5,\n" +
    "                'startColumn':47,\n" +
    "                'endLine':5,\n" +
    "                'endColumn':48\n" +
    "            }\n" +
    "        }\n" +
    "    }\n" +
    "}";

    private HashSet<String> dataClumpMethods=null;
    private void init(){
        DataClumps.DataClumpTypeContext context=new Gson().fromJson(methodParameterDCTest, DataClumps.DataClumpTypeContext.class);
        
    }
    @Override
    public TreeVisitor<?, ExecutionContext> getVisitor() {
        return new JavaVisitor<ExecutionContext>(){
            @Override
            public J visitMethodDeclaration(MethodDeclaration method, ExecutionContext p) {
                Object m=method.getMarkers().findFirst(Range.class);
                System.out.println("method "+method.getSimpleName()+m );
                return super.visitMethodDeclaration(method, p);
            }
        };
       
    
    }
}
