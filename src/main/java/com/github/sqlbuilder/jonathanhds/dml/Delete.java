package com.github.sqlbuilder.jonathanhds.dml;

import com.github.sqlbuilder.jonathanhds.IllegalQueryException;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;


public class Delete implements Query {

	private String table;
    private final Context context;
	private final Collection<String> conditions;
    private boolean terminated = false;

    Delete(Context context){
        this.context = context;
        this.context.append("DELETE FROM ");
        conditions = new LinkedList<>();
    }

	Delete(Context context, String table) {
        this(context);
		this.table = table;
	}

	public Delete where(String condition) {
		conditions.add(condition);
		return this;
	}

    public Delete and(String condition){
        conditions.add(condition);
        return this;
    }

    private void terminate(){
		if (table == null || "".equals(table)) throw new IllegalQueryException("No table specified!");
        
        if(!terminated){
            context.append(table);

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
        return context.getSql();
	}
}