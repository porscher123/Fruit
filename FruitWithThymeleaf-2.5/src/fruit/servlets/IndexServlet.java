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


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("utf-8");
        HttpSession session = req.getSession();
        String oper = req.getParameter("oper");

        int curPage = 1;

        String keyword = null;
        // oper为query, 说明是点击查询过来的
        if (!StringUtils.isEmpty(oper) && oper.equals("query")) {
            // 此时curPage应该为1, keyword应该从请求参数中获取
            curPage = 1;
            keyword = req.getParameter("keyword");
            if (StringUtils.isEmpty(keyword)) {
                keyword = "";
            }
            session.setAttribute("keyword", keyword);
        } else  {
            // 不是点击查询访问的index, 可能是点击换页按钮
            // 不同换页之间会多次请求index, 但是都是同一个session
            // 所以从session中获取keyword判断是否进行关键字查询
            String curPageStr = req.getParameter("curPage");
            if (!StringUtils.isEmpty(curPageStr)) {
                curPage = Integer.parseInt(curPageStr);
            }
            Object keywordObj = session.getAttribute("keyword");
            if (keywordObj != null) {
                keyword = (String) keywordObj;
            } else {
                keyword = "";
            }
        }







        session.setAttribute("curPage", curPage);
        try {
            int fruitCount = fruitDao.getFruitCount(keyword);
            int pages = (int) Math.ceil(fruitCount / 5.0);
            session.setAttribute("pages", pages);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            List<Fruit> fruitList = fruitDao.getFruitList(keyword, curPage);
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
