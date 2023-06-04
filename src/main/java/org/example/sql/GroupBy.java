package org.example.sql;

public class GroupBy extends Clause{
    @Override
    public boolean check() {
        if(params == null || params.isEmpty()) return false;

        return true;
    }
}
