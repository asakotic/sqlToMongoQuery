package org.example.factory;

import org.example.sql.Clause;
import org.example.sql.Select;

public class SelectFactory extends ClauseFactory{
    @Override
    public Clause createClause() {
        return new Select();
    }
}
