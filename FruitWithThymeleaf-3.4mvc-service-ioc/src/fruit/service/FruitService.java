package fruit.service;


import fruit.pojo.Fruit;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

/**
 * 业务功能接口
 */
public interface FruitService {

    // 根据关键字获取指定页面列表信息
    List<Fruit> getFruitList(String keyword, Integer curPage) throws SQLException, NoSuchFieldException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException;

    // 添加库存
    void  addFruit(Fruit fruit) throws SQLException;

    // 通过id查询库存
    Fruit getFruitById(Integer id) throws SQLException, NoSuchFieldException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException;

    // 删除库存
    int delFruit(Integer id) throws SQLException;

    // 获取总页数
    Integer getPageCount(String keyword) throws Exception;

    // 修改
    void updateFruit(Fruit fruit) throws SQLException;
}
