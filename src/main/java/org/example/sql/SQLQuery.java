package org.example.sql;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SQLQuery {
    private Select select = null;
    private From from = null;
    private Join join = null;
    private Join join2 = null;
    private Join join3 = null;
    private Where where = null;
    private GroupBy groupBy = null;
    private OrderBy orderBy = null;


    public SQLQuery(List<Clause> clauses){
        for(Clause c : clauses){
            if(c instanceof Select)
                this.select = (Select) c;
            if(c instanceof From)
                this.from = (From) c;
            if(c instanceof Join){
                if(this.join == null)
                    this.join = (Join) c;
                else if(this.join2 == null)
                    this.join2 = (Join) c;
                else
                    this.join3 = (Join) c;
            }
            if(c instanceof Where)
                this.where = (Where) c;
            if(c instanceof GroupBy)
                this.groupBy = (GroupBy) c;
            if(c instanceof OrderBy)
                this.orderBy = (OrderBy) c;
        }
    }

    public String getQuery(){
        String query;

        query = "";

        return query;
    }

}
