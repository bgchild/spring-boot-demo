package com.lk.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author Ann00
 * @date 2020/8/14
 */
public class BaseDao {
    private static final Logger LOG = LoggerFactory.getLogger(BaseDao.class);
    @Autowired
    public JdbcTemplate jdbcTemplate;
}
