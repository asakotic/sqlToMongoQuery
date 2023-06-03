package org.example.sql;

import java.util.ArrayList;
import java.util.List;

public class SQLValidator {

    private List<String> keywords = new ArrayList<>();

    public SQLValidator() {
        keywords.add("SELECT");
        keywords.add("FROM");
        keywords.add("JOIN");
        keywords.add("WHERE");
        keywords.add("GROUP BY");
        keywords.add("ORDER BY");
    }

    public String checkSQL(String s){

        int global = 0;

        List<String> arrSQL = List.of(s.split(" "));

        if(!s.toLowerCase().contains("from") || !s.toLowerCase().contains("select"))
            return "ERROR 1";

        for(int i = 0; i<arrSQL.size(); i++){
            if(keywords.contains(arrSQL.get(i).toUpperCase()))
            {
                int index = keywords.indexOf(arrSQL.get(i).toUpperCase());
                System.out.println(index+ "! \n");
                if(global>index) return "ERROR 2";
                global = index + 1;
            }
        }

        return "Nice";
    }

}
