package org.example.factory;

import org.example.sql.Clause;

public abstract class ClauseFactory {

    public Clause getClause(){

        Clause clause = createClause();
        return clause;
    }
    public abstract Clause createClause();
}
