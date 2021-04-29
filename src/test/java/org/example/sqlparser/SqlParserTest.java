package org.example.sqlparser;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SqlParserTest {

    @Test
    public void testParse() {
        SqlParser sqlParser = new SqlParser();
        String sql = "select * from users WHERE id in (<foreach item=\"id\" index=\"index\" collection=\"idList\" separator=\",\" >#{id}</foreach>)";
        List<Long> idList = Arrays.asList(123L,234L,345L);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idList", idList);
        SqlData sqlData = sqlParser.parse(sql, params);
        Assert.assertEquals("select * from users WHERE id in (  ? , ? , ? )", sqlData.getSql());
        Assert.assertArrayEquals(idList.toArray(), sqlData.getParamValues().toArray());
    }
}
