package filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;

import java.io.IOException;

/**
 * 实现Filter接口的类
 * 就可以称之为Filter, isn't it?
 */
@WebFilter("*.do")
public class Filter3 implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("A3");

        // 放行
        filterChain.doFilter(servletRequest, servletResponse);

        System.out.println("B3");
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
