package org.example.sql;


import org.example.factory.FactoryUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SQLParser {

    static List<String> keywords = new ArrayList<>();
    private List<Clause> clauses = new ArrayList<>();
    private String text;

    public SQLParser(String text) {
        this.text = text;
        keywords.add("SELECT");
        keywords.add("FROM");
        keywords.add("JOIN");
        keywords.add("WHERE");
        keywords.add("GROUP BY");
        keywords.add("ORDER BY");
    }

    public String solve(){

        String[] arrSQL = text.split(" ");
        List<String > param = new ArrayList<>();
        Clause clause = null;
        for(int i = 0; i< arrSQL.length; i++){
            if(keywords.contains(arrSQL[i].toUpperCase())){
                if(!param.isEmpty()){
                    clause.params.addAll(param);
                }
                clause= FactoryUtils.getFactory(arrSQL[i]).getClause();
                clauses.add(clause);
                param.clear();
            }else{
                param.add(arrSQL[i]);
            }
        }
        if (clause != null) {
            clause.params.addAll(param);
        }
        for(Clause c: clauses){
            System.out.println(c.params);
        }

        return "";
    }

}
