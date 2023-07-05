package fruit.controllers;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import fruit.dao.FruitDao;
import fruit.dao.impl.FruitDaoImpl;
import fruit.pojo.Fruit;
import myssm.utils.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

public class FruitController {
    private FruitDao fruitDao = new FruitDaoImpl();

    private String index(HttpServletRequest request, String oper, String keyword , Integer curPage) throws Exception {
        HttpSession session = request.getSession() ;

        if(curPage == null) curPage = 1;

        if(!StringUtils.isEmpty(oper) && oper.equals("query")){
            curPage = 1 ;
            if(StringUtils.isEmpty(keyword)) {
                keyword = "" ;
            }
            session.setAttribute("keyword",keyword);
        }else{
            Object keywordObj = session.getAttribute("keyword");
            if(keywordObj != null){
                keyword = (String)keywordObj ;
            }else{
                keyword = "" ;
            }
        }

        // 重新更新当前页的值
        session.setAttribute("curPage",curPage);


        List<Fruit> fruitList = fruitDao.getFruitList(keyword , curPage);
        session.setAttribute("fruitList", fruitList);

        int fruitCount = fruitDao.getFruitCount(keyword);
        int pages = (int) Math.ceil(fruitCount / 5.0);
        session.setAttribute("pages",pages);
        return "index" ;
    }


    private String update(Integer id, String name, Integer price, Integer amount, String comment) throws SQLException {
        fruitDao.updateFruit(new Fruit(0, name, price ,amount, comment));
        return "redirect:fruit.do";
    }

    private String edit(HttpServletRequest request, Integer id) throws SQLException, NoSuchFieldException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        if (id != null) {
            Fruit fruit = fruitDao.getFruitById(id);
            request.setAttribute("fruit", fruit);

            System.out.println("controller : edit");
            return "edit";
        }
        return null;
    }

    private String delete(Integer id) throws SQLException {
        if (id != null) {
            fruitDao.deleteFruitById(id);
            return "redirect:fruit.do";
        }
        return null;
    }

    private String add(String name, Integer price, Integer amount, String comment) throws SQLException {
        Fruit fruit = new Fruit(0, name , price , amount , comment) ;
        fruitDao.addFruit(fruit);
        return "redirect:fruit.do";
    }


}
