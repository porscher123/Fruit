package myssm.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 封装dao数据库重复代码
 * 抽象两种方法: DQL, 非DQL
 */
public abstract class BaseDao {
    /**
     *
     * @param sql sql结构
     * @param params 占位符取值
     * @return 影响行数
     */
    public int executeUpdate(String sql, Object... params) throws SQLException {
        Connection con = JdbcUtilsV2.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);

        // 可变长度参数, 当作数组用, 传入占位符
        for (int i = 1; i <= params.length; i++) {
            ps.setObject(i, params[i - 1]);
        }
        int rows = ps.executeUpdate();
        ps.close();
        // 是否回收连接考虑是否是事务
        // 开启事务了, 就不用关闭连接
        if (con.getAutoCommit()) {
            // 没有开启事务
            JdbcUtilsV2.freeConnection();
        }
        return  rows;
    }

    /**
     *
     * @param tClass 要给字段接受值的实体集合的模板对象
     * @param sql 查询结构, 列名或别名与实体类的字段名相同
     * @param params
     * @return
     * @param <T>
     * @throws SQLException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws NoSuchFieldException
     */
    public <T> List<T> executeQuery(Class<T> tClass, String sql, Object... params)
            throws SQLException, InstantiationException, IllegalAccessException, NoSuchFieldException, NoSuchMethodException, InvocationTargetException {


        Connection con = JdbcUtilsV2.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);

        for (int i = 1; i <= params.length; i++) {
            ps.setObject(i, params[i - 1]);
        }
        ResultSet rs  = ps.executeQuery();

        // 每行一个map, 列名为key, 值为value
        List<T> ts = new ArrayList<>();

        // 获取列的相关信息
        ResultSetMetaData rsMetaData = rs.getMetaData();

        int col = rsMetaData.getColumnCount();
        while (rs.next()) {
            // 通过类创建实例对象
            T t = tClass.getDeclaredConstructor().newInstance();
            for (int i = 1; i <= col; i++) {
                Object value = rs.getObject(i);
                String colLabel = rsMetaData.getColumnLabel(i);
                // 通过T的类获得属性
                Field declaredField = tClass.getDeclaredField(colLabel);
                // 设置字段可以访问
                declaredField.setAccessible(true);
                // 要赋值的对象, 属性的值
                declaredField.set(t, value);
            }
            ts.add(t);
        }
        rs.close();
        ps.close();
        if (con.getAutoCommit()) {
            con.close();
        }
        return ts;
    }
}
