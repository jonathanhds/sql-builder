package com.github.sqlbuilder.jonathanhds.dml;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;


import com.github.sqlbuilder.jonathanhds.IllegalQueryException;
import java.util.Iterator;

public class Update implements Query {

    private Context context;

    private boolean terminated = false;

	private String table;

	private Map<String, String> assignments;

	private Collection<String> conditions;

    Update(Context context){
        this.context = context;
        this.context.append("UPDATE ");
        this.assignments = new LinkedHashMap<>();
        this.conditions = new LinkedList<>();
    }

	Update(Context context, String table) {
        this(context);
		this.table = table;
	}

    public Update table(String table){
        this.table = table;
        return this;
    }

	public Update set(String column, String value) {
		assignments.put(column, value);
		return this;
	}

	public Update where(String condition) {
		conditions.add(condition);
		return this;
	}

    public Update and(String condition){
        conditions.add(condition);
        return this;
    }

    private void terminate(){
		if (assignments.isEmpty()) throw new IllegalQueryException("Not contains SET statements!");
        
        if(!terminated){
            context.append(table).appendLine(" SET");

            Iterator<Entry<String, String>> iter = assignments.entrySet().iterator();

            while(iter.hasNext()){
                Entry<String, String> assignment = iter.next();
                context.append(assignment.getKey())
                        .append(" = ")
                        .append("'")
                        .append(assignment.getValue())
                        .append("'");

                if(iter.hasNext()){
                    context.append(",").newLine();
                }
            }

            if(!conditions.isEmpty()){
                context.newLine().append("WHERE ");

                Iterator<String> conditionIter = conditions.iterator();

                while(conditionIter.hasNext()){
                    String condition = conditionIter.next();
                    context.append(condition);

                    if(conditionIter.hasNext()){
                        context.newLine().append("AND ");
                    }
                }
            }

            terminated = true;
        }
    }

	@Override
	public String toSqlString() {
        terminate();
		return context.toString();
	}

}
