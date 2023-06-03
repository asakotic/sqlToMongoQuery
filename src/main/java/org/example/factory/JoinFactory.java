package org.example.factory;

import org.example.sql.Clause;
import org.example.sql.Join;

public class JoinFactory extends ClauseFactory{
    @Override
    public Clause createClause() {
        return new Join();
    }
}
