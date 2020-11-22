package com.lk.mapper;

import com.lk.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author Ann00
 * @date 2020/8/12
 */
@Repository
public class UserDao extends BaseDao {
    private static final String INSERT_INFO = "INSERT INTO `lk`.`user`(`user_name`, `password`, `name`, `age`, `sex`, `birthday`, `created`, `updated`, `note`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public int batchSave(List<User> users) {

        jdbcTemplate.batchUpdate(INSERT_INFO, new BatchPreparedStatementSetter() {
            @Override
            public int getBatchSize() {
                return users.size();
            }

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, users.get(i).getUserName());
                ps.setString(2, users.get(i).getPassword());
                ps.setString(3, users.get(i).getName());
                ps.setLong(4, users.get(i).getAge());
                ps.setLong(5, users.get(i).getSex());
                ps.setString(6, users.get(i).getBirthday());
                ps.setString(7, users.get(i).getCreated());
                ps.setString(8, users.get(i).getUpdated());
                ps.setString(9, users.get(i).getNote());
            }
        });
        return 1;
    }
}
