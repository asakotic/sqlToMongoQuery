package org.example.sql;

import java.util.ArrayList;
import java.util.List;

public abstract class Clause {

    List<String> params = new ArrayList<>();

    public abstract String collect(String text);
}
