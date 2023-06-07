package org.example.sql;

import org.example.adapter.SQLAdapter;
import org.example.database.MongoDBQuery;
import org.example.gui.MainFrame;

import java.util.List;

public class SQLToMongo {

    public static MongoDBQuery convertSQL(String text){
        SQLParser parser = new SQLParser(text);
        SQLValidator validator = new SQLValidator(text);

        if(!validator.checkSQL1()) {
            MainFrame.getInstance().getLblMessage().setText("Syntax error");
            return null;
        }

        List<Clause> clauses = parser.solve();

        if(!validator.checkSQL2(clauses)){
            MainFrame.getInstance().getLblMessage().setText("Syntax error: Group by");
            return null;
        }

        if(!validator.checkSQL3(clauses)){
            MainFrame.getInstance().getLblMessage().setText("Syntax error: Where");
            return null;
        }

        if(!validator.checkSQL4(clauses)){
            MainFrame.getInstance().getLblMessage().setText("Syntax error: Join");
            return null;
        }

        if(!validator.checkSQL5(clauses)){
            MainFrame.getInstance().getLblMessage().setText("Syntax error: Clause error");
            return null;
        }

        SQLQuery sqlQuery = new SQLQuery(clauses);
        SQLAdapter sqlAdapter = new SQLAdapter(sqlQuery);
        return sqlAdapter.getQuery();
    }

}
