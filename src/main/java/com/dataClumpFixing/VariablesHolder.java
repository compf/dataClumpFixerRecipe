package com.dataClumpFixing;

import org.openrewrite.java.tree.J.ClassDeclaration;
import org.openrewrite.java.tree.J.MethodDeclaration;
import java.util.ArrayList;
import java.util.HashSet;
import org.openrewrite.java.tree.TypeUtils;
public class VariablesHolder {
    private ClassDeclaration classReference;
    private org.openrewrite.java.tree.J.MethodDeclaration methodReference;
    public static class NameTypeTuple {
        public final String name;
        public final String type;

        public NameTypeTuple(String name, String type) {
            this.name = name;
            this.type = type;
        }
    }
    private  ArrayList<NameTypeTuple> variables = new ArrayList<>();
    public VariablesHolder(ClassDeclaration classReference, MethodDeclaration methodReference) {
        this.classReference = classReference;
        this.methodReference = methodReference;
    }
    public void addVariable( String type,String name) {
        variables.add(new NameTypeTuple(name, type));
    }
    public ArrayList<NameTypeTuple> getVariables() {
        return variables;
    }
    public ClassDeclaration getClassReference() {
        return classReference;
    }
    public MethodDeclaration getMethodReference() {
        return methodReference;
    }
    @Override
    public String toString() {

       return String.join(",", variables.stream().map(variable -> variable.type + " " + variable.name).toArray(String[]::new));
    }
    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return toString().equals(obj.toString());
    }
    public static final int MIN_NUMBER_OF_VARIABLES = 3;
    public static boolean checkDataClump(VariablesHolder holder1, VariablesHolder holder2) {
        if(holder1.variables.size()<MIN_NUMBER_OF_VARIABLES || holder2.variables.size()<MIN_NUMBER_OF_VARIABLES){
            return false;
        }
        else if(holder1.methodReference!=null &&  holder2.methodReference!=null){
            if(TypeUtils.isOverride(holder1.methodReference.getMethodType()) || TypeUtils.isOverride( holder2.methodReference.getMethodType())){
                return false;
            }
        }
       
        HashSet<String> variables1 = new HashSet<>();
        HashSet<String> variables2 = new HashSet<>();
        for (NameTypeTuple variable : holder1.getVariables()) {
            variables1.add( variable.type+" "+variable.name);
        }
        for (NameTypeTuple variable : holder2.getVariables()) {
            variables2.add( variable.type+" "+variable.name);
        }
        variables1.retainAll(variables2);
        if(variables1.size()>=MIN_NUMBER_OF_VARIABLES){
            return true;
        }
        return false;
    }
}
