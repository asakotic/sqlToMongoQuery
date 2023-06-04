package org.example.sql;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Join extends Clause{

    private String kw = null;

    @Override
    public boolean check() {
        if(params == null || params.isEmpty() || params.size()<3) return false;
        if(!params.get(1).equalsIgnoreCase("ON") &&  !params.get(1).equalsIgnoreCase("USING"))
            return false;

        return true;
    }
}
