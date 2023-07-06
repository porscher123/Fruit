package demo;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 演示request保存作用域
 */
@WebServlet("/demo01")
public class ServletDemo01 extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 向ServletContext设置程序范围内的数据
        ServletContext context = request.getServletContext();
        context.setAttribute("username", "wxc");
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
        response.getWriter().print("application变量设置成功");
    }
}
