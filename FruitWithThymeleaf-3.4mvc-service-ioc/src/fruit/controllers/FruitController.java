package fruit.controllers;

import fruit.service.FruitService;
import fruit.service.impl.FruitServiceImpl;
import fruit.pojo.Fruit;
import myssm.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;




/**
 * 使用业务对象BO:FruitService
 * 替代DAO: FruitDAO
 */
public class FruitController {

    private FruitService fruitService = null;
    
    // helper methods
    private String index(HttpServletRequest request, Integer curPage, String oper, String keyword)  {
        if (curPage == null) curPage = 1;
        HttpSession session = request.getSession();
        if (!StringUtils.isEmpty(oper) && oper.equals("query")) {
            curPage = 1;
            if (StringUtils.isEmpty(keyword)) {
                keyword = "";
            }
            session.setAttribute("keyword", keyword);
        } else  {
            Object keywordObj = session.getAttribute("keyword");
            if (keywordObj != null) {
                keyword = (String) keywordObj;
            } else {
                keyword = "";
            }
        }
        session.setAttribute("curPage", curPage);


        try {
            int pages = fruitService.getPageCount(keyword);
            session.setAttribute("pages", pages);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            List<Fruit> fruitList = fruitService.getFruitList(keyword, curPage);
            session.setAttribute("fruitList", fruitList);
            return "index";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private String add(String name, Integer price, Integer amount, String comment)  {

        try {
            fruitService.addFruit(new Fruit(0, name, price, amount, comment));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return "redirect:fruit.do";
    }

    private String delete(Integer id) throws SQLException {

        if (id != null) {
            int rows = fruitService.delFruit(id);
            return "redirect:fruit.do";
        }
        return null;
    }

    private String edit(HttpServletRequest request, Integer id) throws SQLException, NoSuchFieldException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        if (id != null) {
            Fruit fruit = fruitService.getFruitById(id);
            request.setAttribute("fruit", fruit);
            return "edit";
        }
        return null;
    }

    private String update(Integer id, String name, Integer price, Integer amount, String comment) {
        try {
            fruitService.updateFruit(new Fruit(id, name, price, amount, comment));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return "redirect:fruit.do";
    }
}
