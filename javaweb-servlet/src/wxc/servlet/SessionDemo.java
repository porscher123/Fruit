package wxc.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class SessionDemo extends HttpServlet {

    @Override
    public void service(HttpServletRequest request, HttpServletResponse res) throws ServletException, IOException {
        // 获取session
        HttpSession session = request.getSession();
        System.out.println("session id = " + session.getId());


    }

}
