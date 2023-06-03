package org.example.factory;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FactoryUtils {

    static FromFactory fromFactory = new FromFactory();
    static GroupByFactory groupByFactory = new GroupByFactory();
    static SelectFactory selectFactory = new SelectFactory();
    static OrderByFactory orderByFactory = new OrderByFactory();
    static JoinFactory joinFactory = new JoinFactory();

    public static ClauseFactory getFactory(String clauseFactory){

        if(clauseFactory.equalsIgnoreCase("FROM"))
            return fromFactory;
        else if(clauseFactory.equalsIgnoreCase("SELECT"))
            return selectFactory;
        else if(clauseFactory.equalsIgnoreCase("GROUP"))
            return groupByFactory;
        else if(clauseFactory.equalsIgnoreCase("ORDER"))
            return orderByFactory;

        return null;
    }
}
