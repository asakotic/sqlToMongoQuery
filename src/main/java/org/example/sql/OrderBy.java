package org.example.sql;

import java.util.Iterator;
import java.util.List;

public class OrderBy extends Clause{

    @Override
    public boolean check() {


        if(params.isEmpty() || params == null)return false;
        if(params.get(0).equalsIgnoreCase("ASC") || params.get(0).equalsIgnoreCase("DESC"))
            return false;

        for(int i = 0;i<params.size();i++){
            if(!params.get(i).equalsIgnoreCase("ASC") && !params.get(i).equalsIgnoreCase("DESC")){
                if(i+1 == params.size()){
                    params.add("ASC");
                    break;
                }
                if(!params.get(i+1).equalsIgnoreCase("ASC") && !params.get(i+1).equalsIgnoreCase("DESC")){
                    shiftRight(params,i+1);
                }
            }
        }

        System.out.println(params);
        return true;
    }

    public void shiftRight(List<String> params, int index){
        params.add("");

        for(int i = params.size()-1;i>index;i--){
            params.set(i, params.get(i-1));
        }

        params.set(index, "ASC");
    }
}
