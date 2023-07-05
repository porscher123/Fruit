package fruit.dao;

import fruit.pojo.Fruit;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

public interface FruitDao {
    List<Fruit> getFruitList(String keyword, int page) throws SQLException, NoSuchFieldException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException;


    Fruit getFruitById(int id) throws SQLException, NoSuchFieldException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException;

    int updateFruit(Fruit fruit) throws SQLException;

    int deleteFruitById(int id) throws SQLException;

    int addFruit(Fruit fruit) throws SQLException;


    int getFruitCount(String keyword) throws Exception;



}
