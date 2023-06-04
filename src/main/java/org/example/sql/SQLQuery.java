package org.example.sql;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SQLQuery {
    private Select select;
    private From from;
    private Join join;
    private GroupBy groupBy;
    private OrderBy orderBy;
    private Where where;

    public SQLQuery(Select select, From from, Join join, GroupBy groupBy, OrderBy orderBy, Where where){
        this.select = select;
        this.from = from;
        this.join = join;
        this.groupBy = groupBy;
        this.orderBy = orderBy;
        this.where = where;
    }

    public String getQuery(){
        String query;

        query = "";

        return query;
    }

}
