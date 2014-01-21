package com.teradata.demo.dao.impl;

import com.teradata.demo.dao.UserDao;
import com.teradata.demo.entity.User;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 用户
 *
 * Created by zhu on 14-1-19.
 */
public class UserDaoImpl extends ExcelDaoImpl implements UserDao {
    private static final String INSERT_SQL = "INSERT INTO T_USER VALUES(:businessNo, :name, :address, :birthday, :sex)";
    private static final String FIND_SQL = "SELECT * FROM T_USER LIMIT ?,?";

    @Override
    protected String getSQL() {
        return INSERT_SQL;
    }

    @Override
    public List<User> findUsers(int from, int rows) {
        return getJdbcTemplate().query(FIND_SQL, new ParameterizedRowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user = new User();
                user.setBusinessNo(rs.getString("businessno"));
                user.setName(rs.getString("name"));
                user.setAddress(rs.getString("address"));
                user.setBirthday(rs.getString("birthday"));
                user.setSex(rs.getString("sex"));
                return user;
            }
        }, from, rows);
    }
}
