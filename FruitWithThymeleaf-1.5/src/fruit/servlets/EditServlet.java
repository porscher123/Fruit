package fruit.servlets;

import fruit.dao.FruitDao;
import fruit.dao.impl.FruitDaoImpl;
import fruit.pojo.Fruit;
import myssm.myspringmvc.ViewBaseServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

// 导入静态方法
import static myssm.utils.StringUtils.isEmpty;


@WebServlet("/edit.do")
public class EditServlet extends ViewBaseServlet {
    private FruitDao fruitDao = new FruitDaoImpl();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
}
