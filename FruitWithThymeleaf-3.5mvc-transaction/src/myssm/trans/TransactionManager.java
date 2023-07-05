package myssm.trans;

import myssm.utils.JdbcUtilsV2;
import java.sql.SQLException;


/**
 * 提供管理事务的方法:
 * 开启事务, 提交, 回滚
 * 直接调用工具类的静态方法获取连接
 */
public class TransactionManager {

    public static void beginTrans() throws SQLException {
        JdbcUtilsV2.getConnection().setAutoCommit(false);
    }

    public static void commit() throws SQLException {
        JdbcUtilsV2.getConnection().commit();
        JdbcUtilsV2.freeConnection();
    }

    public static void rollback() throws SQLException {
        JdbcUtilsV2.getConnection().rollback();
        JdbcUtilsV2.freeConnection();
    }
}
