package org.example.sql;

public class GroupBy extends Clause{
    @Override
    public boolean check() {
        if(params.isEmpty() || params == null)return false;

        return true;
    }
}
