package org.example.sql;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Where extends Clause {

    private String[] brackets = new String[]{"(", ")"}; // jacina: 3
    private String[] operations = new String[]{"=", ">", "<", "<=", ">=", "LIKE", "IN"}; // jacina: 2
    private String[] keywords = new String[]{"AND", "OR"}; // jacina: 1
    private List<String> stack = new ArrayList<>();
    private List<String> postfix = new ArrayList<>();
    private List<String> query = new ArrayList<>();

    @Override
    public boolean check() {
        if(params == null || params.isEmpty()) return false;

        //WHERE customer_id = 6000 OR ( customer_id > 7000 AND last_name = 'Johnson' );

        for(int i = 0; i<params.size(); i++){

            if(!isKeyword(params.get(i)) && !isOperation(params.get(i)) && !isBracket(params.get(i)))
            {
                postfix.add(params.get(i));
                continue;
            }

            if(params.get(i).equals("("))
            {
                stack.add(params.get(i));
                continue;
            }

            if(params.get(i).equals(")")){
                for(int k = stack.size()-1; k>=0; k--){
                    if(stack.get(k).equals("(")){
                        stack.remove(k);
                        break;
                    }else{
                        postfix.add(stack.get(k));
                        stack.remove(k);
                    }
                }

            }

            stack.add(params.get(i));

            for(int k = stack.size()-1; k>0; k--){

                if(stack.get(k-1).equals("(")) break;

                int status = compareTwo(stack.get(k), stack.get(k-1));
                if(status==1){
                    break;
                }else if(status==0){
                    postfix.add((stack.get(k-1)));
                    stack.set(k-1, stack.get(k));
                    stack.remove(k);
                }else{
                    postfix.add((stack.get(k-1)));
                    stack.set(k-1, stack.get(k));
                    stack.remove(k);
                }
            }

        }

        for(int i = stack.size() - 1; i >= 0; i--){
            postfix.add(stack.get(i));
        }


        return true;
    }

    private int compareTwo(String s, String s1) {
        if(isOperation(s) && isKeyword(s1))
            return -1;
        else if(isKeyword(s) && isOperation(s1))
            return 1;
        else
            return 0;
    }

    public boolean isOperation(String s) {
        for(int i = 0; i< keywords.length; i++){
            if(keywords[i].equalsIgnoreCase(s))
                return true;
        }
        return  false;
    }

    public boolean isKeyword(String s){
        for(int i = 0; i< operations.length; i++){
            if(operations[i].equalsIgnoreCase(s))
                return true;
        }
        return  false;
    }

    public boolean isBracket(String s){
        for(int i = 0; i< brackets.length; i++){
            if(brackets[i].equalsIgnoreCase(s))
                return true;
        }
        return  false;
    }

}
