package org.example.sql;

import lombok.Getter;

@Getter
public class Select extends Clause{
    @Override
    public boolean check() {
        if(params == null || params.isEmpty()) return false;

        return true;
    }
}
