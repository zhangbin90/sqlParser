package org.example.sqlparser;


import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.parsing.XPathParser;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.session.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SqlParser {

    private Configuration configuration;

    public SqlParser() {
        configuration = new Configuration();
    }

    public SqlData parse(String sql, Map<String, Object> params) {
        String xml = "<select>" + sql + "</select>";

        XPathParser parser = new XPathParser(xml);

        List<XNode> xNodes = parser.evalNodes("select|insert|update|delete");

        if (xNodes == null || xNodes.size() <= 0) {
            throw new RuntimeException("找不到sql语句");
        }
        if (xNodes.size() > 1) {
            throw new RuntimeException("只能解析一句sql");
        }

        LanguageDriver langDriver =  configuration.getLanguageDriver(null);

        XNode node = xNodes.get(0);
        SqlSource sqlSource = langDriver.createSqlSource(configuration, node, null);
        BoundSql boundSql = sqlSource.getBoundSql(params);

        SqlData sqlData = new SqlData();
        sqlData.setSql(boundSql.getSql());

        List<Object> paramValues = new ArrayList<>();
        for (ParameterMapping parameterMapping : boundSql.getParameterMappings()) {
            paramValues.add(boundSql.getAdditionalParameter(parameterMapping.getProperty()));
        }
        sqlData.setParamValues(paramValues);
        return sqlData;
    }
}
