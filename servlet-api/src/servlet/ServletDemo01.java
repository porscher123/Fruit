package servlet;


import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


@WebServlet(urlPatterns = {"/demo01"},
initParams = {
        @WebInitParam(name = "hello", value = "world")
}
)
public class ServletDemo01 extends HttpServlet {
    /**
     * 如果我们想在Servlet初始化时做一些准备工作,
     * 就可以重写init()方法
     * 比如可以获取初始化数据
     * @throws ServletException
     */
    @Override
    public void init() throws ServletException {
        // 获取config对象
        ServletConfig config = getServletConfig();
        // 获取初始化参数值
        String initParameter = config.getInitParameter("hello");
        System.out.println(initParameter);

        // 在init()中从ServletContext获取初始化参数
        ServletContext context = getServletContext();
        String parameter = context.getInitParameter("contextConfigLocation");
        System.out.println();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext servletContext = request.getServletContext();
        ServletContext servletContext1 = request.getSession().getServletContext();
    }
}
