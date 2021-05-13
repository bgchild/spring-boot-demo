package com.lk.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.Properties;
import java.util.concurrent.Executor;

@Intercepts(@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class,
        ResultHandler.class, CacheKey.class, BoundSql.class}))
@Slf4j
public class MybatisInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        log.info("MybatisInterceptor");

        //原始sql
        MappedStatement ms = (MappedStatement) invocation.getArgs()[0];
        Object param = invocation.getArgs()[1];

        BoundSql boundSql = ms.getBoundSql(param);
        String oldSql = boundSql.getSql();
        log.info("oldSql = {}", oldSql);

        //新sql
        BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), oldSql + " where id = 1", boundSql.getParameterMappings(), boundSql.getParameterObject());
        //MappedStatement不需要替换，可能版本原因吧
        //MappedStatement newMappedStatement = copyMappedStatement(ms, new MySqlSource(newBoundSql));
        //invocation.getArgs()[0] = newMappedStatement;
        //这是关键
        invocation.getArgs()[5] = newBoundSql;

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
