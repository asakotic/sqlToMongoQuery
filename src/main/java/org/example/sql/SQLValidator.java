package org.example.sql;

import java.util.ArrayList;
import java.util.List;

public class SQLValidator {

    private List<String> keywords = new ArrayList<>();
    private String text;

    public SQLValidator(String text) {
        keywords.add("SELECT");
        keywords.add("FROM");
        keywords.add("JOIN");
        keywords.add("WHERE");
        keywords.add("GROUP");
        keywords.add("ORDER");
        this.text = text;
    }

    public boolean checkSQL1(){

        int global = 0;

        List<String> arrSQL = List.of(text.split(" "));

        if(!text.toLowerCase().contains("from") || !text.toLowerCase().contains("select"))
            return false;
        int br = 0;
        for(String s: arrSQL){
            if(s.equalsIgnoreCase("JOIN")){
                if(++br>3) return false;
            }
        }

        for(int i = 0; i<arrSQL.size(); i++){
            if(keywords.contains(arrSQL.get(i).toUpperCase()))
            {
                int index = keywords.indexOf(arrSQL.get(i).toUpperCase());
                if(global>index) return false;
                if(index == 2){
                    global = index;
                    continue;
                }
                global = index + 1;
            }
        }

        return true;
    }


    public boolean checkSQL2(List<Clause> clauses) {
        Clause sel = null;
        Clause gb = null;

        for(Clause c: clauses){
            if(c instanceof Select)
                sel = c;
            if(c instanceof GroupBy)
                gb = c;
        }

        List<String> checkAgg = checkAggregate(sel);

        if(checkAgg == null && gb!=null)
            return false;

        if(checkAgg!=null && gb==null)
            return false;

        if(checkAgg==null && gb==null)
            return true;

        if(checkAgg.size() != gb.params.size())
            return false;

        for (String s : checkAgg){
            if(!gb.params.contains(s))
                return false;
        }

        return true;
    }

    public boolean checkSQL3(List<Clause> clauses) {

        Clause where = null;

        for(Clause c: clauses){
            if(c instanceof Where)
                where = c;
        }

        if(where==null)
            return true;

        for(int i = 0; i < where.params.size(); i++){
            if(where.params.get(i).contains("(") && where.params.get(i).contains(")"))
                return false;
        }

        return true;
    }

    public boolean checkSQL4(List<Clause> clauses) {

        Clause join = null;

        for(Clause c: clauses){
            if(c instanceof Join)
                join = c;
        }

        if(join==null)
            return true;

        //USING(DEPARTMENT_ID);

        for(int i = 0; i<join.params.size(); i++){
            if(join.params.get(i).equalsIgnoreCase("using")){
                if(i+1==join.params.size())
                    return false;

                if(!join.params.get(i+1).contains("("))
                    return false;

                break;
            }
            if(join.params.get(i).equalsIgnoreCase("on")){
                if(i+1==join.params.size())
                    return false;
            }
        }



        return true;
    }
    public boolean checkSQL5(List<Clause> clauses){

        for(Clause c: clauses){
            if(!c.check()) return false;
        }
        return true;
    }

    public List<String> checkAggregate(Clause pom){

        int flag = 0;
        List<String> pomList = new ArrayList<>();

        for(int i = 0; i < pom.params.size(); i++){
            if(pom.params.get(i).contains("(") && pom.params.get(i).contains(")"))
                flag = 1;
            else{
                pomList.add(pom.params.get(i));
            }
        }

        if(flag==0)
            return null;
        else
            return pomList;
    }

}
