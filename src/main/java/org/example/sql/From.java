package org.example.sql;

public class From extends Clause{

    @Override
    public boolean check() {
        if(params == null || params.isEmpty() || params.size()>1) return false;

        return true;
    }
}
