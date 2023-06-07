package org.example.adapter;

import org.example.database.MongoDBQuery;

public interface ISQLAdapter {
    MongoDBQuery getQuery();
}
