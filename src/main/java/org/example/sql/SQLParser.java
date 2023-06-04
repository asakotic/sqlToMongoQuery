package org.example.sql;


import lombok.Getter;
import org.example.factory.FactoryUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
@Getter
public class SQLParser {

    static List<String> keywords = new ArrayList<>();
    private List<String> joinkw = new ArrayList<>();
    private List<Clause> clauses = new ArrayList<>();
    private String text;

    public SQLParser(String text) {
        this.text = text;
        keywords.add("SELECT");
        keywords.add("FROM");
        keywords.add("JOIN");
        keywords.add("WHERE");
        keywords.add("GROUP");
        keywords.add("ORDER");

        joinkw.add("LEFT");
        joinkw.add("RIGHT");
        joinkw.add("FULL");
    }

    public List<Clause> solve(){

        String[] arrSQL = text.split(" ");
        List<String > param = new ArrayList<>();
        Clause clause = null;
        String kw = null;

        for(int i = 0; i< arrSQL.length; i++){
            if(keywords.contains(arrSQL[i].toUpperCase())){
                if(!param.isEmpty()) clause.params.addAll(param);
                clause = FactoryUtils.getFactory(arrSQL[i]).getClause();
                clauses.add(clause);
                param.clear();

                if(arrSQL[i].equalsIgnoreCase("GROUP") ||
                        arrSQL[i].equalsIgnoreCase("ORDER")){
                    if(arrSQL[i+1].equalsIgnoreCase("by")){
                        i++;
                        continue;
                    }
                }
                if(arrSQL[i].equalsIgnoreCase("JOIN")){
                    if(kw!= null){
                        ((Join)clause).setKw(kw);
                        kw = null;
                    }
                }

            }else if(joinkw.contains(arrSQL[i].toUpperCase())){
                kw = arrSQL[i];
            }
            else param.add(arrSQL[i]);
        }

        if (clause != null) {//edge case za poslednji keyword test
            clause.params.addAll(param);
        }
        for(Clause c: clauses){
            c.clear();
        }
        return clauses;
    }
}