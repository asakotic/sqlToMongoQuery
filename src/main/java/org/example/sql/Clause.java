package org.example.sql;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public abstract class Clause {

    List<String> params = new ArrayList<>();

    public abstract boolean check();

    public void clear(){
        for(int i = 0;i< params.size(); i++){
            String s = params.get(i);
            if(s.contains(",")){
                String tmp = s;
                tmp = tmp.substring(0,tmp.length()-1);
                params.set(i,tmp);
            }
        }
    }

}
