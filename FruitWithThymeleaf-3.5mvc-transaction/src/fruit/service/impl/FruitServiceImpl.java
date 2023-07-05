package fruit.service.impl;

import fruit.dao.FruitDao;
import fruit.pojo.Fruit;
import fruit.service.FruitService;
import myssm.utils.JdbcUtilsV2;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

/**
 * 实现FruitService接口
 * 具体实现Fruit业务
 */
public class FruitServiceImpl implements FruitService {
    // DAO对象
    private FruitDao fruitDao = null;

    @Override
    public void addFruit(Fruit fruit) throws SQLException, NoSuchFieldException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        fruitDao.addFruit(fruit);
//        System.out.println("查找id为1002");
//        fruitDao.getFruitById(1002);
    }

    @Override
    public List<Fruit> getFruitList(String keyword, Integer curPage) throws SQLException, NoSuchFieldException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        System.out.println("getFruitList : " +JdbcUtilsV2.getConnection());
        return fruitDao.getFruitList(keyword, curPage);
    }

    @Override
    public Fruit getFruitById(Integer id) throws SQLException, NoSuchFieldException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        return fruitDao.getFruitById(id);
    }

    @Override
    public int delFruit(Integer id) throws SQLException {
        fruitDao.deleteFruitById(id);
        return 0;
    }

    @Override
    public Integer getPageCount(String keyword) throws Exception {

        System.out.println("getPageCount : " +JdbcUtilsV2.getConnection());
        int fruitCount = fruitDao.getFruitCount(keyword);
        int pages = (int) Math.ceil(fruitCount / 5.0);
        return pages;
    }

    @Override
    public void updateFruit(Fruit fruit) throws SQLException {
        fruitDao.updateFruit(fruit);
    }
}
