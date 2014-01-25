package com.teradata.demo.dao.impl;

import com.teradata.demo.dao.AddressDao;
import com.teradata.demo.entity.Address;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 操作地址数据
 *
 * Created by zhu on 14-1-25.
 */
public class AddressDaoImpl extends ExcelDaoImpl implements AddressDao {
    private static final String ADD_ADDRESS_SQL = "INSERT INTO T_ADDRESS(NAME) VALUES(:name)";
    private static final String SEARCH_ADDRESS_SQL = "SELECT * FROM T_ADDRESS WHERE NAME LIKE '%'||?||'%'";

    @Override
    protected String getSQL() {
        return ADD_ADDRESS_SQL;
    }

    @Override
    public List<Address> list(String queryString) {
        return getJdbcTemplate().query(SEARCH_ADDRESS_SQL, new ParameterizedRowMapper<Address>() {
            @Override
            public Address mapRow(ResultSet rs, int rowNum) throws SQLException {
                Address address = new Address();
                address.setId(rs.getLong("id"));
                address.setName(rs.getString("name"));
                return address;
            }
        }, queryString);
    }
}
