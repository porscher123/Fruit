package fruit.servlets;

import fruit.dao.FruitDao;
import fruit.dao.impl.FruitDaoImpl;
import fruit.pojo.Fruit;
import myssm.myspringmvc.ViewBaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;


@WebServlet("/update.do")
public class UpdateServlet extends ViewBaseServlet {

    private FruitDao fruitDao = new FruitDaoImpl();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");

        // 获取request参数
        String idStr = request.getParameter("id");
        int id = Integer.parseInt(idStr);
        String name = request.getParameter("name");
        String priceStr = request.getParameter("price");
        int price = Integer.parseInt(priceStr);
        String amountStr = request.getParameter("amount");
        int amount = Integer.parseInt(amountStr);
        String commet = request.getParameter("commet");



        // 调用dao实例, 进行更新
        try {
            fruitDao.updateFruit(new Fruit(id, name, price, amount, commet));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // 资源跳转
        // super.processTemplate("index", request, response);
        // 重新给IndexServlet发请求,
        // 使得在IndexServlet中重新获取Fruits并设置到session中
        response.sendRedirect("index");
    }
}
