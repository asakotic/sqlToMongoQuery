package org.example.sql;

public class From extends Clause{

    @Override
    public boolean check() {
        if(params.isEmpty() || params == null || params.size()>1) return false;

        return true;
    }
}
