package filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;

import java.io.IOException;

/**
 * 实现Filter接口的类
 * 就可以称之为Filter, isn't it?
 */
//@WebFilter("/demo01.do")
public class FilterDemo01 implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("请求已拦截");

        // 放行
        filterChain.doFilter(servletRequest, servletResponse);

        System.out.println("响应已拦截");
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
