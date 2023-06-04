package org.example.adapter;

import org.example.sql.SQLQuery;

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

        return "";
    }
}
