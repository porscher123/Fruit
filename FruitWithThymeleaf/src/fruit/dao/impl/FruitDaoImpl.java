package fruit.dao.impl;



import fruit.pojo.Fruit;
import myssm.utils.BaseDao;
import fruit.dao.FruitDao;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;


public class FruitDaoImpl extends BaseDao implements FruitDao {

    @Override
    public List<Fruit> getFruitList() throws SQLException, NoSuchFieldException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        return super.executeQuery(Fruit.class,"select * from t_fruit;");
    }
}
