package myssm.filters;

import jakarta.servlet.*;
import myssm.utils.StringUtils;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;



//@WebFilter(
//        urlPatterns = {"*.do"},
//        initParams = {@WebInitParam(
//                name = "encoding",
//                value = "utf-8"
//        )}
//)
public class CharacterEncodingFilter  implements Filter {

    private String encoding = "utf-8";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        String initParameter = filterConfig.getInitParameter("encoding");
        if (!StringUtils.isEmpty(initParameter)) {
            this.encoding = initParameter;
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 设置编码
        ((HttpServletRequest)servletRequest).setCharacterEncoding(this.encoding);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
