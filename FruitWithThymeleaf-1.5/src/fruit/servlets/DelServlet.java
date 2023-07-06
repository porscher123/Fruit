package fruit.servlets;

import fruit.dao.FruitDao;
import fruit.dao.impl.FruitDaoImpl;
import myssm.myspringmvc.ViewBaseServlet;
import myssm.utils.StringUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;


@WebServlet("/del.do")
public class DelServlet extends ViewBaseServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)  {
        FruitDao fruitDao = new FruitDaoImpl();
        // 获取get请求携带的信息: id
        String idStr = request.getParameter("id");

        if (!StringUtils.isEmpty(idStr)) {
            int id = Integer.parseInt(idStr);
            try {
                int rows = fruitDao.deleteFruitById(id);
                System.out.println("删除成功, 删除了" + rows + "条数据");
                // 重定向到index
                response.sendRedirect("index");
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
