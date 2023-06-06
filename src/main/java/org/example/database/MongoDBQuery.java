package org.example.database;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.example.controller.MongoDBController;
import org.example.data.Row;
import org.example.gui.MainFrame;
import org.example.gui.table.TableModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

public class MongoDBQuery {

    private List<String> queryParameters = new ArrayList<>();
    private MongoClient connection;

    public MongoDBQuery(List<String> params){
        this.queryParameters = params;
        this.connection = MongoDBController.getConnection();
    }

    public void databaseConnection() {
        MongoDatabase database = connection.getDatabase("bp_tim10");
        System.out.println(queryParameters.get(6));
        System.out.println(queryParameters.get(7));

        List<Integer> counter = new ArrayList<>();

        if(!queryParameters.get(1).equals("")) counter.add(1);
        if(!queryParameters.get(2).equals("")) counter.add(2);
        if(!queryParameters.get(3).equals("")) counter.add(3);
        if(!queryParameters.get(4).equals("")) counter.add(4);
        if(queryParameters.get(6).equals("")) counter.add(5);
        else counter.add(6);
        if(!queryParameters.get(7).equals("")) counter.add(7);

        Document[] submit = new Document[counter.size()];

        for(int i = 0; i<counter.size();i++){
            submit[i] = Document.parse(queryParameters.get(counter.get(i)));
        }

        MongoCursor<Document> cursor = database.getCollection(queryParameters.get(0)).aggregate(
                Arrays.asList(
                        submit
                )
        ).iterator();

        List<Row> rows = new ArrayList<>();


        while (cursor.hasNext()){
            Row row = new Row();
            row.setName(queryParameters.get(0));
            Document d = cursor.next();
            Set<String> keys = d.keySet();
            Object[] keyP = keys.toArray();
            Collection<Object> values = d.values();
            Object[] valuesP = values.toArray();

            for(int i = 0; i < keys.size(); i++){
                row.addField(keyP[i].toString(), valuesP[i]);
                System.out.println(keyP[i].toString());
            }

            rows.add(row);
        }

        TableModel tableModel = new TableModel();

        tableModel.setRows(rows);
        MainFrame.getInstance().getJTable().setModel(tableModel);
        System.out.println(rows);
    }

}
