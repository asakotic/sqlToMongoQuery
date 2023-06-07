package org.example.adapter;

import org.example.database.MongoDBQuery;
import org.example.sql.*;

import java.util.ArrayList;
import java.util.List;

public class SQLAdapter implements ISQLAdapter{

    String select = "";
    String from = "";
    String orderBy = "";
    String groupBy = "";
    String where = "";
    String join = "";
    String join2 = "";
    String join3 = "";

    String unwind1 = "{$unwind: { path: \"$merged1\", preserveNullAndEmptyArrays: true }}";
    String unwind2 = "{$unwind: { path: \"$merged2\", preserveNullAndEmptyArrays: true }}";
    String unwind3 = "{$unwind: { path: \"$merged3\", preserveNullAndEmptyArrays: true }}";

    private SQLQuery sqlQuery;

    public SQLAdapter(SQLQuery sqlQuery){
        this.sqlQuery = sqlQuery;
    }

    @Override
    public MongoDBQuery getQuery() {
        return convertSQLQueryToMongoDBQuery(sqlQuery);
    }


    private MongoDBQuery convertSQLQueryToMongoDBQuery(SQLQuery SQLQuery) {

        select = selectConversion(sqlQuery.getSelect());
        from = fromConversion(sqlQuery.getFrom());

        if(sqlQuery.getOrderBy() != null)
            orderBy = orderByConversion(sqlQuery.getOrderBy());

        if(sqlQuery.getGroupBy() != null)
            groupBy = groupByConversion(sqlQuery.getGroupBy(), sqlQuery.getSelect());

        if(sqlQuery.getWhere() != null)
            where = whereConversion(sqlQuery.getWhere());

        if(sqlQuery.getJoin() != null)
            join = joinConversion(sqlQuery.getJoin());

        if(sqlQuery.getJoin2() != null)
            join2 = join2Conversion(sqlQuery.getJoin2());

        if(sqlQuery.getJoin3() != null)
            join3 = join3Conversion(sqlQuery.getJoin3());

        List<String> params = new ArrayList<>();

        params.add(from);
        params.add(where);
        params.add(join);
        params.add(unwind1);
        params.add(join2);
        params.add(unwind2);
        params.add(join3);
        params.add(unwind3);
        params.add(select);
        params.add(groupBy);
        params.add(orderBy);

        //MONGOQUER = MAPPER(PARAMS)
        MongoDBQuery mdb = mapper(params);

        return mdb;
    }

    private MongoDBQuery mapper(List<String> param) {
        MongoDBQuery mdbQ = new MongoDBQuery(param);
        return mdbQ;
    }

    private String joinConversion(Join join){

        StringBuilder sb = new StringBuilder();

        sb.append("{$lookup:{ from: ");
        sb.append("'" + join.getParams().get(0) + "', ");
        sb.append("localField: ");
        if(join.getParams().get(1).equalsIgnoreCase("USING")){
            sb.append("'" + join.getParams().get(3) + "', ");
            sb.append("foreignField: ");
            sb.append("'" + join.getParams().get(3) + "', ");
            sb.append("as: 'merged1" + "'}}");
        }else{
            if(join.getParams().get(2).equals("(")){

            }else {
                sb.append("'" + join.getParams().get(2) + "', ");
                sb.append("foreignField: ");
                sb.append("'" + join.getParams().get(4) + "', ");
                sb.append("as: 'merged1" + "'}}");
            }
        }

        sb = new StringBuilder(sb.toString().replace("'", "\""));
        //sb.append("}}, {$unwind: 'merged'}");
        return sb.toString();
    }

    private String join2Conversion(Join join){

        StringBuilder sb = new StringBuilder();

        sb.append("{$lookup:{ from: ");
        sb.append("'" + join.getParams().get(0) + "', ");
        sb.append("localField: \"merged1.");
        if(join.getParams().get(1).equalsIgnoreCase("USING")){
            sb.append(join.getParams().get(3) + "', ");
            sb.append("foreignField: ");
            sb.append("'" + join.getParams().get(3) + "', ");
            sb.append("as: 'merged2" + "'}}");
        }else{
            if(join.getParams().get(2).equals("(")){

            }else {
                sb.append(join.getParams().get(2) + "', ");
                sb.append("foreignField: ");
                sb.append("'" + join.getParams().get(4) + "', ");
                sb.append("as: 'merged2" + "'}}");
            }
        }

        sb = new StringBuilder(sb.toString().replace("'", "\""));
        //sb.append("}}, {$unwind: 'merged'}");
        return sb.toString();
    }

    private String join3Conversion(Join join){

        StringBuilder sb = new StringBuilder();

        sb.append("{$lookup:{ from: ");
        sb.append("'" + join.getParams().get(0) + "', ");
        sb.append("localField: \"merged2.");
        if(join.getParams().get(1).equalsIgnoreCase("USING")){
            sb.append(join.getParams().get(3) + "', ");
            sb.append("foreignField: ");
            sb.append("'" + join.getParams().get(3) + "', ");
            sb.append("as: 'merged3" + "'}}");
        }else{
            if(join.getParams().get(2).equals("(")){

            }else {
                sb.append(join.getParams().get(2) + "', ");
                sb.append("foreignField: ");
                sb.append("'" + join.getParams().get(4) + "', ");
                sb.append("as: 'merged3" + "'}}");
            }
        }

        sb = new StringBuilder(sb.toString().replace("'", "\""));
        //sb.append("}}, {$unwind: 'merged'}");
        return sb.toString();
    }

    private String whereConversion(Where where){
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < where.getPostfix().size(); i++){
            if((!where.isKeyword(where.getPostfix().get(i)) && !where.isOperation(where.getPostfix().get(i)))){
                continue;
            }else{
                if(i-2<0){
                    where.getPostfix().remove(where.getPostfix().size()-1);
                    break;
                }
                String first = where.getPostfix().get(i-2);
                String second = where.getPostfix().get(i-1);
                String finalS = "";

                if(!where.isOperation(where.getPostfix().get(i))){
                    switch (where.getPostfix().get(i).toLowerCase()){
                        case "=":
                            finalS ="{" + first + where.getPostfix().get(i) + second +"},";
                            finalS = finalS.replace("=", ":");
                            break;
                        case ">":
                            finalS = "{" + first + ": {$gt:" + second + "} },";
                            break;
                        case "<":
                            finalS = "{" + first + ": {$lt:" + second + "} },";
                            break;
                        case "<=":
                            finalS = "{" + first + ": {$lte:" + second + "} },";
                            break;
                        case ">=":
                            finalS = "{" + first + ": {$gte:" + second + "} },";
                            break;
                        case "like":
                            System.out.println(second);
                            String q = "";
                            int countP = 0;
                            boolean end = false;
                            for(int t = 0; t < second.length(); t++){
                                if(second.charAt(t) != '%' && second.charAt(t) != '"'){
                                    q = q + second.charAt(t);
                                }
                                if(second.charAt(t) == '%'){
                                    countP++;
                                    if(second.charAt(t+1) == '"')
                                        end = true;
                                }

                            }

                            if(countP==2)
                                finalS = "{" + first + ": /" + q + "/ },";
                            else{
                                if(end)
                                    finalS = "{" + first + ": /^" + q + "/ },";
                                else
                                    finalS = "{" + first + ": /" + q + "$/ },";
                            }

                            break;
                        //case "in":
                        //    finalS = "{" + first + ": { $in: [" + second + "]}}";
                        //    break;
                        default:
                            break;
                    }
                }
                else
                    finalS = "{$" + where.getPostfix().get(i) + ":[" + first + second + "]},";

                where.getPostfix().set(i-2, finalS);

                for(int k = 0; k<2; k++){
                    for(int p = i-1; p<where.getPostfix().size()-1;p++){
                        where.getPostfix().set(p, where.getPostfix().get(p+1));
                        if(p==where.getPostfix().size()-2){
                            where.getPostfix().remove(where.getPostfix().size()-1);
                        }
                    }
                }

                i = i-2;

            }

        }
        String pom = where.getPostfix().get(0);

        for(int i = 0 ; i<pom.length()-1; i++){
            if(pom.charAt(i) == ','){
                if(pom.charAt(i+1)==']' || pom.charAt(i+1)=='}')
                    pom = pom.substring(0, i) + "" + pom.substring(i+1);
            }
        }

        pom = pom.substring(0,pom.length()-1);

        pom = "{ $match:" + pom + "}";
        pom = pom.replace("'", "\"");

        return pom;
    }

    private String groupByConversion(GroupBy groupBy, Select select){

        StringBuilder sb = new StringBuilder();
        sb.append("{ $group: { _id: {");

        for(int i = 0; i < groupBy.getParams().size(); i++){
            if(i==groupBy.getParams().size()-1){
                sb.append(groupBy.getParams().get(i) + ": \"$" + groupBy.getParams().get(i) + "\" }, ");
            }else{
                sb.append(groupBy.getParams().get(i) + ": \"$" + groupBy.getParams().get(i) + "\",");
            }
        }

        for(String s : select.getParams()){
            String agg = "";
            agg = checkAggregation(s);
            if(!agg.equals("")){
                String pom = s.substring(s.indexOf("(")+1, s.length()-1);
                sb.append(agg+": {$"+agg+": \"$" + pom + "\"}, ");
            }
        }

        sb.replace(sb.length()-2, sb.length()-1, "}}");

        return sb.toString();
    }

    private String checkAggregation(String s){
        List<String> aggFun = new ArrayList<>();
        aggFun.add("AVG");
        aggFun.add("COUNT");
        aggFun.add("SUM");
        aggFun.add("MIN");
        aggFun.add("MAX");

        for(String pom : aggFun){
            if(s.toLowerCase().contains(pom.toLowerCase()) ) {
                return pom.toLowerCase();
            }
        }

        return "";
    }

    private String orderByConversion(OrderBy orderBy){
        StringBuilder sb = new StringBuilder("{ $sort : { ");

        for(int i = 0; i < orderBy.getParams().size(); i+=2){
            int value = 1;
            if(orderBy.getParams().get(i+1).equalsIgnoreCase("ASC")) value = 1;
            else value = -1;

            if(i== orderBy.getParams().size()-2) {
                if(sqlQuery.getGroupBy()==null)
                    sb.append("\"" + orderBy.getParams().get(i) + "\":" + value + " ");
                else{
                    if(sqlQuery.getGroupBy().getParams().contains(orderBy.getParams().get(i)))
                        sb.append("\"_id." + orderBy.getParams().get(i) + "\":" + value + " ");
                    else
                        sb.append("\"" + orderBy.getParams().get(i) + "\":" + value + " ");
                }
            }
            else{
                if(sqlQuery.getGroupBy()==null)
                    sb.append("\"" + orderBy.getParams().get(i) + "\":" + value + ", ");
                else {
                    if (sqlQuery.getGroupBy().getParams().contains(orderBy.getParams().get(i)))
                        sb.append("\"_id." + orderBy.getParams().get(i) + "\":" + value + ", ");
                    else
                        sb.append("\"" + orderBy.getParams().get(i) + "\":" + value + ", ");
                }
            }
        }

        sb.append("}}");

        return sb.toString();
    }

    private String selectConversion(Select select){
        if(select.getParams().size()==1 && select.getParams().get(0).equals("*")){
            return "";
        }

        StringBuilder sbR = new StringBuilder("{ $project: {");

        for(String s : select.getParams()){
            sbR.append("\"" + s+"\":1, ");
        }

        sbR.append("\"_id\":0}}");

        return sbR.toString();
    }

    private String fromConversion(From from){
        return (from.getParams().get(0));
    }
}
