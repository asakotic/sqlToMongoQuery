package org.example.adapter;

import org.example.sql.*;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SQLAdapter implements ISQLAdapter{

    private SQLQuery sqlQuery;

    public SQLAdapter(SQLQuery sqlQuery){
        this.sqlQuery = sqlQuery;
    }

    @Override
    public String getQuery() {
        return convertSQLQueryToMongoDBQuery(sqlQuery);
    }


    private String convertSQLQueryToMongoDBQuery(SQLQuery SQLQuery){

        String select = selectConversion(sqlQuery.getSelect());
        String from = fromConversion(sqlQuery.getFrom());
        String orderBy = "";
        String groupBy = "";
        String where = "";
        String join = "";

        if(sqlQuery.getOrderBy() != null)
            orderBy = orderByConversion(sqlQuery.getOrderBy());

        if(sqlQuery.getGroupBy() != null)
            groupBy = groupByConversion(sqlQuery.getGroupBy(), sqlQuery.getSelect());

        sqlQuery.getWhere().getPostfix().add("=");
        if(sqlQuery.getWhere() != null)
            where = whereConversion(sqlQuery.getWhere());

        System.out.println(select+"\n"+from+"\n"+orderBy+"\n"+groupBy+"\n"+where);
        if(sqlQuery.getJoin() != null)
            join = joinConversion(sqlQuery.getJoin());

        System.out.println(select+"\n"+from+"\n"+orderBy+"\n"+groupBy);
        System.out.println(join);
        return "";
    }
    private String joinConversion(Join join){
        StringBuilder sb = new StringBuilder();
        sb.append("{$lookup:{ from: ");
        sb.append("'" + join.getParams().get(0) + "', ");
        sb.append("localField: ");
        if(join.getParams().get(1).equalsIgnoreCase("USING")){
            sb.append("'" + join.getParams().get(3) + "', ");
            sb.append("foreignField: ");
            sb.append("'" + join.getParams().get(3) + "', ");
            sb.append("as: 'merged'");
        }else{
            if(join.getParams().get(2).equals("(")){

            }else {
                sb.append("'" + join.getParams().get(2) + "', ");
                sb.append("foreignField: ");
                sb.append("'" + join.getParams().get(4) + "', ");
                sb.append("as: 'merged'");
            }
        }
        sb.append("}}, {$unwind: 'merged'}");
        return sb.toString();
    }

    private String whereConversion(Where where){
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < where.getPostfix().size(); i++){
            if((!where.isKeyword(where.getPostfix().get(i)) && !where.isOperation(where.getPostfix().get(i)))){
                System.out.println(where.getPostfix() +" "+i);
                continue;
            }else{
                if(i-2<0){
                    where.getPostfix().remove(where.getPostfix().size()-1);
                    break;
                }

                String first = where.getPostfix().get(i-2);
                String second = where.getPostfix().get(i-1);
                String finalS = "";

                if(!where.isOperation(where.getPostfix().get(i))){
                    finalS ="{" + first + where.getPostfix().get(i) + second +"},";
                    finalS = finalS.replace("=", ":");
                }
                else
                    finalS = "{\"$" + where.getPostfix().get(i) + "\":[" + first + second + "]},";

                where.getPostfix().set(i-2, finalS);

                for(int k = 0; k<2; k++){
                    for(int p = i-1; p<where.getPostfix().size()-1;p++){
                        where.getPostfix().set(p, where.getPostfix().get(p+1));
                        if(p==where.getPostfix().size()-2){
                            where.getPostfix().remove(where.getPostfix().size()-1);
                        }
                    }
                }

                i = i-2;


            }

            System.out.println(where.getPostfix() +" "+i);
        }


        return where.getPostfix().get(0);
    }


    private String groupByConversion(GroupBy groupBy, Select select){

        StringBuilder sb = new StringBuilder();
        sb.append("{\"$group\" : {_id: {");

        for(int i = 0; i < groupBy.getParams().size(); i++){
            if(i==groupBy.getParams().size()-1){
                sb.append(groupBy.getParams().get(i) + ": \"$" + groupBy.getParams().get(i) + "\"}}, ");
            }else{
                sb.append(groupBy.getParams().get(i) + ": \"$" + groupBy.getParams().get(i) + "\",");
            }
        }

        for(String s : select.getParams()){
            String agg = "";
            agg = checkAggregation(s);
            if(!agg.equals("")){
                String pom = s.substring(s.indexOf("(")+1, s.length()-1);
                sb.append("\""+agg+"\": {\"$"+agg+":" + pom + "\"}, ");
            }
        }

        sb.replace(sb.length()-2, sb.length()-1, "}");

        return sb.toString();
    }

    private String checkAggregation(String s){
        List<String> aggFun = new ArrayList<>();
        aggFun.add("AVG");
        aggFun.add("COUNT");
        aggFun.add("SUM");
        aggFun.add("MIN");
        aggFun.add("MAX");

        for(String pom : aggFun){
            if(s.toLowerCase().contains(pom.toLowerCase()) ) {
                return pom.toLowerCase();
            }
        }

        return "";
    }

    private String orderByConversion(OrderBy orderBy){
        StringBuilder sb = new StringBuilder(".sort({");

        for(int i = 0; i < orderBy.getParams().size(); i+=2){
            int value = 1;
            if(orderBy.getParams().get(i+1).equalsIgnoreCase("ASC")) value = 1;
            else value = -1;

            if(i== orderBy.getParams().size()-2)
                sb.append(orderBy.getParams().get(i)+":"+value);
            else
                sb.append(orderBy.getParams().get(i)+":"+value+", ");
        }

        sb.append("})");

        return sb.toString();
    }

    private String selectConversion(Select select){
        if(select.getParams().size()==1 && select.getParams().get(0).equals("*")){
            return "";
        }

        StringBuilder sbR = new StringBuilder(".projection({");

        for(String s : select.getParams()){
            sbR.append("\"" + s+"\":1, ");
        }

        sbR.append("\"_id\":0})");

        return sbR.toString();
    }

    private String fromConversion(From from){
        return ("." + from.getParams().get(0));
    }

}
