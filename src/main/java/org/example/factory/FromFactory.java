package org.example.factory;

import org.example.sql.Clause;
import org.example.sql.From;

public class FromFactory extends ClauseFactory{
    @Override
    public Clause createClause() {
        return new From();
    }
}
