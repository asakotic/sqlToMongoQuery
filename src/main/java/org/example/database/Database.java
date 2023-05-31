package org.example.database;

import org.example.data.Row;

import java.util.List;

public interface Database {
    List<Row> getDataFromTable(String from);
}
