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
    public List<Fruit> getFruitList(int page) throws SQLException, NoSuchFieldException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        int offset = (page - 1) * 5;
        String sql = "SELECT * FROM t_fruit LIMIT 5 OFFSET ?";
        return super.executeQuery(Fruit.class,sql, offset);
    }

    @Override
    public Fruit getFruitById(int id) throws SQLException, NoSuchFieldException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        String sql = "select * from t_fruit where id = ?";
        return  super.executeQuery(Fruit.class, sql, id).get(0);
    }

    @Override
    public int updateFruit(Fruit fruit) throws SQLException {
        String sql = "update t_fruit set name = ?, " +
                "price = ?, amount = ?, commet = ? where id = ?";
        return super.executeUpdate(sql, fruit.getName(), fruit.getPrice(), fruit.getAmount(),
                fruit.getCommet(), fruit.getId());
    }

    @Override
    public int deleteFruitById(int id) throws SQLException {
        String sql = "delete from t_fruit where id = ?";
        return super.executeUpdate(sql, id);
    }


    @Override
    public int addFruit(Fruit fruit) throws SQLException {
        String sql = "INSERT INTO t_fruit(name, price, amount, commet) VALUES(?, ?, ?, ?);";
        System.out.println(sql);
        return super.executeUpdate(sql, fruit.getName(), fruit.getPrice(), fruit.getAmount(), fruit.getCommet());
    }


    @Override
    public int getFruitCount() throws Exception {
        String sql = "select count(1) from t_fruit;";
        return ((Long) super.executeComplexQuery(sql).get(0)).intValue();
    }
}
