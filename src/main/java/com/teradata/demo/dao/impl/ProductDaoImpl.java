package com.teradata.demo.dao.impl;

import com.teradata.demo.dao.ProductDao;
import com.teradata.demo.entity.Product;
import com.teradata.demo.entity.StatisticProducts;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.ParameterizedSingleColumnRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * 商品数据操作
 * <p/>
 * Created by zhu on 14-1-20.
 */
public class ProductDaoImpl extends ExcelDaoImpl implements ProductDao {
    private static final String INSERT_SQL = "INSERT INTO T_PRODUCT VALUES(:businessNo, :name, :type, :sum, :unit)";

    private static final String FIND_PRODUCT_SQL = "SELECT P.name, P.unit, P.businessno, " +
            "count(S.userbusinessno) as peoples, " +                    // 人数
            "sum(S.sum * S.count) as totalsum, " +                      // 销售额
            "sum(S.sum * S.count - P.sum * S.count) as totalprofit " +  // 利润
            "FROM T_PRODUCT P, T_SALE S " +
            "WHERE P.businessno=S.productbusinessno GROUP BY P.businessno LIMIT ?,?";

    private static final String STATISTIC_MONTH = "SELECT MAX(SALEDATE) " +
            "FROM T_SALE S, T_USER U WHERE S.userbusinessno=U.businessno AND U.address=?";

    private static final String STATISTIC_SQL = "SELECT P.name, P.businessno, " +
            "sum(S.sum * S.count - P.sum * S.count) totalprofit " +
            "FROM T_SALE S, T_PRODUCT P, T_USER U " +
            "WHERE S.SALEDATE LIKE ?||'%' and S.productbusinessno=P.businessno AND S.userbusinessno=U.businessno " +
            "AND U.address=? " +
            "GROUP BY P.businessno order by totalprofit desc limit ?,?";

    private static final String STATISTIC_HISTORY_SQL = "SELECT P.name, P.businessno, " +
            "sum(S.sum * S.count - P.sum * S.count) totalprofit " +
            "FROM T_SALE S, T_PRODUCT P, T_USER U " +
            "WHERE S.SALEDATE LIKE ?||'%' and S.productbusinessno=P.businessno AND S.userbusinessno=U.businessno " +
            "AND U.address=? " +
            "GROUP BY P.businessno ";

    @Override
    protected String getSQL() {
        return INSERT_SQL;
    }


    @Override
    public List<Product> findProducts(int from, int rows) {
        return getJdbcTemplate().query(FIND_PRODUCT_SQL, new ParameterizedRowMapper<Product>() {
            @Override
            public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
                Product product = new Product();
                product.setUnit(rs.getString("unit"));
                product.setName(rs.getString("name"));
                product.setBusinessNo(rs.getString("businessno"));
                product.setPeoples(rs.getInt("peoples"));
                product.setTotalSum(rs.getDouble("totalsum"));
                product.setTotalProfit(Math.round(rs.getDouble("totalprofit") * 100) / 100.00);
                return product;
            }
        }, from, rows);
    }

    @Override
    public List<Product> findTopProductsByAddress(String address, int from, int rows, String month) {
        return getJdbcTemplate().query(STATISTIC_SQL, new ParameterizedRowMapper<Product>() {
            @Override
            public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
                Product product = new Product();
                product.setName(rs.getString("name"));
                product.setBusinessNo(rs.getString("businessno"));
                product.setTotalProfit(Math.round(rs.getDouble("totalprofit") * 100) / 100.00);
                return product;
            }
        }, month, address, from, rows);
    }

    @Override
    public DateTime getMaxDay(String address) {
        String maxDay = getJdbcTemplate().queryForObject(STATISTIC_MONTH,
                new ParameterizedSingleColumnRowMapper<String>(), address);
        if (StringUtils.isBlank(maxDay)) {
            return null;
        }
        return DateTime.parse(maxDay);
    }

    @Override
    public List<Product> findPreMonthProductsByAddress(String address, String preMonth, List<Product> products) {
        String[] replacements = new String[products.size()];
        for (int i = 0; i < replacements.length; i++) {
            replacements[i] = "?";
        }
        Object[] parameters = new Object[products.size() + 2];
        parameters[0] = preMonth;
        parameters[1] = address;
        int j = 2;
        for (Product product : products) {
            parameters[j++] = product.getBusinessNo();
        }

        String replaceString = StringUtils.join(replacements, ",");
        return getJdbcTemplate().query(STATISTIC_HISTORY_SQL + "having P.businessno in (" + replaceString + ")",
                new ParameterizedRowMapper<Product>() {
                    @Override
                    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Product product = new Product();
                        product.setName(rs.getString("name"));
                        product.setBusinessNo(rs.getString("businessno"));
                        product.setTotalProfit(Math.round(rs.getDouble("totalprofit") * 100) / 100.00);
                        return product;
                    }
                }, parameters);
    }

}




/*P1822	上海	58553.9163	2013-03-04
        P1514	上海	50170.3064	2013-03-06
        P1338	上海	49835.7144	2013-03-03
        P1107	上海	47223.6435	2013-03-09
        P1807	上海	44915.2158	2013-03-03
        P1755	上海	43009.134	2013-03-04
        P1856	上海	37441.212	2013-03-07
        P1658	上海	35923.5177	2013-03-06
        P1623	上海	34820.4417	2013-03-09
        P1617	上海*/

