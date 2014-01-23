package com.teradata.demo.dao.impl;

import com.teradata.demo.dao.SaleDao;
import com.teradata.demo.entity.Product;
import com.teradata.demo.entity.Sale;
import com.teradata.demo.entity.User;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 销售数据
 *
 * Created by zhu on 14-1-20.
 */
public class SaleDaoImpl extends ExcelDaoImpl implements SaleDao {
    private static final String INSERT_SQL = "INSERT INTO T_SALE(userBusinessNo, productBusinessNo, saleDate, sum, count) " +
            "VALUES(:userBusinessNo, :productBusinessNo, :saleDate, :sum, :count)";

    private static final String FIND_GOODS_SQL = "SELECT S.saledate, S.sum, S.count, P.name, P.type, P.unit " +
            "FROM T_SALE S, T_PRODUCT P WHERE S.userbusinessno = ? and S.productbusinessno=P.businessno ORDER BY P.businessno LIMIT ?,?";

    private static final String FIND_DETAIL_SQL = "SELECT U.name, S.saledate, S.count, S.sum * S.count as totalsum " +
            "FROM T_SALE S, T_USER U WHERE S.userbusinessno=U.businessno and S.productbusinessno=? ORDER BY U.businessno LIMIT ?,?";

    @Override
    protected String getSQL() {
        return INSERT_SQL;
    }

    @Override
    public List<Sale> findGoodsByUser(String userId, int from, int rows) {
        return getJdbcTemplate().query(FIND_GOODS_SQL, new ParameterizedRowMapper<Sale>() {
            @Override
            public Sale mapRow(ResultSet rs, int rowNum) throws SQLException {
                Sale sale = new Sale();
                sale.setSaleDate(rs.getString("saledate"));
                sale.setSum(rs.getDouble("sum"));
                sale.setCount(rs.getDouble("count"));

                Product product = new Product();
                product.setName(rs.getString("name"));
                product.setType(rs.getString("type"));
                product.setUnit(rs.getString("unit"));
                sale.setProduct(product);

                return sale;
            }
        }, userId, from, rows);
    }

    @Override
    public List<Sale> findDetailsByProduct(String productId, int from, int rows) {
        return getJdbcTemplate().query(FIND_DETAIL_SQL, new ParameterizedRowMapper<Sale>() {
            @Override
            public Sale mapRow(ResultSet rs, int rowNum) throws SQLException {
                Sale sale = new Sale();
                sale.setSaleDate(rs.getString("saledate"));
                sale.setCount(rs.getDouble("count"));
                sale.setTotalSum(Math.round(rs.getDouble("totalSum") * 100) / 100.00);

                User user = new User();
                user.setName(rs.getString("name"));
                sale.setUser(user);

                return sale;
            }
        }, productId, from, rows);
    }
}
