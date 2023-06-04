package org.example.factory;

import org.example.sql.Clause;
import org.example.sql.Select;
import org.example.sql.Where;

public class WhereFactory extends ClauseFactory{
    @Override
    public Clause createClause() {
        return new Where();
    }
}