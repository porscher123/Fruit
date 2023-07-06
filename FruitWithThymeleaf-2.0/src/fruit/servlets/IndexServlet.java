package fruit.servlets;

import fruit.dao.impl.FruitDaoImpl;
import fruit.pojo.Fruit;
import fruit.dao.FruitDao;
import myssm.myspringmvc.ViewBaseServlet;
import myssm.utils.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;



// Servlet从3.0开始支持注解方式注册
@WebServlet("/index")
public class IndexServlet extends ViewBaseServlet {

    private FruitDao fruitDao = new FruitDaoImpl();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        int curPage = 1;
        String curPageStr = req.getParameter("curPage");
        if (!StringUtils.isEmpty(curPageStr)) {
            curPage = Integer.parseInt(curPageStr);
        }
        session.setAttribute("curPage", curPage);
        try {
            long fruitCount = fruitDao.getFruitCount();
            int pages = (int) Math.ceil(fruitCount / 5.0);
            session.setAttribute("pages", pages);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            List<Fruit> fruitList = fruitDao.getFruitList(curPage);
            // index页面显示的数据是从session中获取的
            // 当数据库修改后, 任然是从session中获取的
            session.setAttribute("fruitList", fruitList);
//             逻辑视图名称: index
//             物理视图名称: view-prefix + 逻辑视图名称 + view-suffix
//             因为配置了 view-prefix = /    view-suffix = .html
//             所以真实的视图(传入的)名称是: /index.html
            super.processTemplate("index", req, resp);
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
}
