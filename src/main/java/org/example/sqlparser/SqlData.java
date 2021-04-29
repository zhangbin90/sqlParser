package org.example.sqlparser;

import java.util.List;

public class SqlData {

    private String sql;

    private List<Object> paramValues;

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public List<Object> getParamValues() {
        return paramValues;
    }

    public void setParamValues(List<Object> paramValues) {
        this.paramValues = paramValues;
    }
}
