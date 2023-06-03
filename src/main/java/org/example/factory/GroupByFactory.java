package org.example.factory;

import org.example.sql.Clause;
import org.example.sql.GroupBy;

public class GroupByFactory extends ClauseFactory{
    @Override
    public Clause createClause() {
        return new GroupBy();
    }
}
