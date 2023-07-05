package fruit.dao.impl;



import fruit.pojo.Fruit;
import myssm.utils.BaseDao;
import fruit.dao.FruitDao;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;


public class FruitDaoImpl extends BaseDao implements FruitDao {


    // 显示page页, 每页显示5条
    @Override
    public List<Fruit> getFruitList(String keyword, int page) throws SQLException, NoSuchFieldException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        int offset = (page - 1) * 5;
        String sql = "SELECT * FROM t_fruit \n" +
                "WHERE name LIKE ? OR comment LIKE ?\n" +
                "LIMIT 5 OFFSET ?";
        return super.executeQuery(Fruit.class,sql,
                "%"+keyword+"%", "%"+keyword+"%",
                offset);
    }

    @Override
    public Fruit getFruitById(int id) throws SQLException, NoSuchFieldException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        String sql = "select * from t_fruit where id = ?";
        return  super.executeQuery(Fruit.class, sql, id).get(0);
    }

    @Override
    public int updateFruit(Fruit fruit) throws SQLException {
        String sql = "update t_fruit set name = ?, " +
                "price = ?, amount = ?, comment = ? where id = ?";
        return super.executeUpdate(sql, fruit.getName(), fruit.getPrice(), fruit.getAmount(),
                fruit.getComment(), fruit.getId());
    }

    @Override
    public int deleteFruitById(int id) throws SQLException {
        String sql = "delete from t_fruit where id = ?";
        return super.executeUpdate(sql, id);
    }


    @Override
    public int addFruit(Fruit fruit) throws SQLException {
        String sql = "INSERT INTO t_fruit(name, price, amount, comment) VALUES(?, ?, ?, ?);";
        return super.executeUpdate(sql, fruit.getName(), fruit.getPrice(), fruit.getAmount(), fruit.getComment());
    }


    @Override
    public int getFruitCount(String keyword) throws Exception {
        String sql = "select count(1) from t_fruit " +
                "WHERE name LIKE ? OR comment LIKE ?;";
        return ((Long) super.executeComplexQuery(sql, "%"+keyword+"%", "%"+keyword+"%").get(0)).intValue();
    }
}
