package org.example.adapter;

import org.example.sql.*;

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

        if(sqlQuery.getOrderBy() != null)
            orderBy = orderByConversion(sqlQuery.getOrderBy());


        System.out.println(select+"\n"+from+"\n"+orderBy);
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
