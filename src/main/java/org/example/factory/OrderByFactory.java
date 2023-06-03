package org.example.factory;

import org.example.sql.Clause;
import org.example.sql.OrderBy;

public class OrderByFactory extends ClauseFactory{
    @Override
    public Clause createClause() {
        return new OrderBy();
    }
}
