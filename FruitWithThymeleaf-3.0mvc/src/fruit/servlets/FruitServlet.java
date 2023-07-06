package fruit.servlets;

import fruit.dao.FruitDao;
import fruit.dao.impl.FruitDaoImpl;
import fruit.pojo.Fruit;
import myssm.myspringmvc.ViewBaseServlet;
import myssm.utils.StringUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

import static myssm.utils.StringUtils.isEmpty;


@WebServlet("/fruit.do")
public class FruitServlet extends ViewBaseServlet {
    private FruitDao fruitDao = new FruitDaoImpl();
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");

        String operate = request.getParameter("operate");
        if (StringUtils.isEmpty(operate)) {
            operate = "index";
        }

        switch (operate) {
            case "index":
                index(request, response);
                break;
            case "add" :
                add(request, response);
                break;
            case "delete" :
                delete(request, response);
                break;
            case "edit" :
                edit(request, response);
                break;
            case "update" :
                update(request, response);
                break;
            default:
                throw new RuntimeException("operate值非法");
        }

    }
    // helper methods
    private void index(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        HttpSession session = req.getSession();
        String oper = req.getParameter("oper");

        int curPage = 1;

        String keyword = null;
        if (!StringUtils.isEmpty(oper) && oper.equals("query")) {
            curPage = 1;
            keyword = req.getParameter("keyword");
            if (StringUtils.isEmpty(keyword)) {
                keyword = "";
            }
            session.setAttribute("keyword", keyword);
        } else  {
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
            session.setAttribute("fruitList", fruitList);
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

    private void add(HttpServletRequest request, HttpServletResponse response) throws IOException {


        String name = request.getParameter("name");
        String priceStr = request.getParameter("price");
        int price = Integer.parseInt(priceStr);
        String amountStr = request.getParameter("amount");
        int amount = Integer.parseInt(amountStr);
        String commet = request.getParameter("commet");


        try {
            fruitDao.addFruit(new Fruit(0, name, price, amount, commet));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // 重定向到fruit.do 默认按index处理
        response.sendRedirect("fruit.do");
    }

    private void delete(HttpServletRequest request, HttpServletResponse response)  {

        // 获取get请求携带的信息: id
        String idStr = request.getParameter("id");

        if (!StringUtils.isEmpty(idStr)) {
            int id = Integer.parseInt(idStr);
            try {
                int rows = fruitDao.deleteFruitById(id);
                System.out.println("删除成功, 删除了" + rows + "条数据");
                // 重定向到index
                response.sendRedirect("fruit.do");
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void edit(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idStr = request.getParameter("id");
        if (!isEmpty(idStr)) {
            int id = Integer.parseInt(idStr);
            try {
                // 根据请求所携带的水果的id, 获取水果对象
                Fruit fruit  = fruitDao.getFruitById(id);
                // 添加到request保存域中
                request.setAttribute("fruit", fruit);

                // 调用父类方法, 处理edit页面
                super.processTemplate("edit", request, response);
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

    private void update(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String idStr = request.getParameter("id");
        int id = Integer.parseInt(idStr);
        String name = request.getParameter("name");
        String priceStr = request.getParameter("price");
        int price = Integer.parseInt(priceStr);
        String amountStr = request.getParameter("amount");
        int amount = Integer.parseInt(amountStr);
        String commet = request.getParameter("commet");

        try {
            fruitDao.updateFruit(new Fruit(id, name, price, amount, commet));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        response.sendRedirect("fruit.do");
    }
}
