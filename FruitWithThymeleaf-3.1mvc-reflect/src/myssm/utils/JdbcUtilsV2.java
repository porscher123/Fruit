package myssm.utils;


//import com.alibaba.druid.pool.DruidDataSourceFactory;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * TODO:
 *     利用线程本地变量, 存储连接信息
 *     确保一个线程的多个方法可以获得同一个连接
 *     不用给dao方法, 传递参数
 */
public class JdbcUtilsV2 {
    // 静态的连接池对象
    private static DataSource dataSource = null;

    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();
    // 静态代码块实例化静态对象
    static {
        Properties properties = new Properties();

        InputStream ips = JdbcUtilsV2.class.getClassLoader().
                getResourceAsStream("myssm/druid.properties");
        try {
            properties.load(ips);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    /**
     * 对外提供获取连接的方法
     * @return
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        // 先看线程本地变量是否有连接
        Connection con = threadLocal.get();
        if (con == null) {
            // 如果没有, 就从连接池获取一个
            con = dataSource.getConnection();
            // 并且, 将该连接, 加入到线程本地变量
            threadLocal.set(con);
        }
        // 此时一定有连接, 直接返回
        return  con;
    }

    /**
     * 释放传入的连接
     * @throws SQLException
     */
    public static void freeConnection() throws SQLException {
        Connection con = threadLocal.get();
        if (con != null) {
            // 移出线程本地变量中的变量
            threadLocal.remove();
            // 事务状态回归, 回归到默认状态
            con.setAutoCommit(true);
            con.close(); // 放回线程本地变量
        }
    }
}
