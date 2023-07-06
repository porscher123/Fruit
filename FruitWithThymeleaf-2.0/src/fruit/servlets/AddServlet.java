package fruit.servlets;

import fruit.dao.FruitDao;
import fruit.dao.impl.FruitDaoImpl;
import fruit.pojo.Fruit;
import myssm.myspringmvc.ViewBaseServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;




@WebServlet("/add.do")
public class AddServlet extends ViewBaseServlet {
    private FruitDao fruitDao = new FruitDaoImpl();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");

        // 获取request参数
        String name = request.getParameter("name");
        String priceStr = request.getParameter("price");
        int price = Integer.parseInt(priceStr);
        String amountStr = request.getParameter("amount");
        int amount = Integer.parseInt(amountStr);
        String commet = request.getParameter("commet");


        try {
            // 调用dao实例, 进行更新
            fruitDao.addFruit(new Fruit(0, name, price, amount, commet));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        response.sendRedirect("index");
    }
}
