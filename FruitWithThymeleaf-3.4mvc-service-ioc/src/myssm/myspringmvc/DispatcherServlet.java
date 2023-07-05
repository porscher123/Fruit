package myssm.myspringmvc;


import myssm.io.BeanFactory;
import myssm.io.ClassPathXmlApplicationContext;
import myssm.utils.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;


/**
 * 核心控制Servlet
 * 拦截所有以.do结尾的请求
 */
@WebServlet("*.do")
public class DispatcherServlet extends ViewBaseServlet {

    // 通过id获取对应的类的实例对象
    private BeanFactory beanFactory;

    /**
     * 构造函数解析applicationContext.xml配置文件
     * 将文件中的所有bean标签加载到map中
     */
    public void init() throws ServletException {
        super.init();
        beanFactory = new ClassPathXmlApplicationContext();
    }
    public DispatcherServlet() {
    }
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取controller
        request.setCharacterEncoding("utf-8");
        // 通过 /hello.do -> hello -> HelloController

        // 假设url= http://localhost:8080/hello.do
        // 则通过getServletPath获得的servletPath为: /hello.do

        String servletPath = request.getServletPath();
        // 字符串处理servletPath, 去掉'/'
        int lastDotIndex = servletPath.lastIndexOf(".do");
        String servletName = servletPath.substring(1, lastDotIndex);
        System.out.println(servletName);
        // 通过字servletPath获取对应的controller对象
        Object controllerBeanObj = beanFactory.getBean(servletName);



        // 获取操作
        String operate = request.getParameter("operate");
        if (StringUtils.isEmpty(operate)) {
            operate = "index";
        }

        try {
            // 获取当前类中有所类的方法
            Method[] methods = controllerBeanObj.getClass().getDeclaredMethods();
            // 遍历所有方法
            for (Method method : methods) {
                // 获取方法名
                String methodName = method.getName();
                // 找到和operate一样的方法名
                if (operate.equals(methodName)) {
                    // 1. 统一获取请求参数
                    // 根据要调用的方法的参数列表来获取参数
                    Parameter[] parameters = method.getParameters();
                    // 存放parameters每个参数的值
                    Object[] parameterValues = new Object[parameters.length];
                    for (int i = 0 ; i < parameters.length; i++) {
                        Parameter parameter = parameters[i];
                        String parameterName = parameter.getName();

                        if (parameterName.equals("request")) {
                            parameterValues[i] = request;
                        } else if (parameterName.equals("response")) {
                            parameterValues[i] = response;
                        } else if (parameterName.equals("session")) {
                            parameterValues[i] = request.getSession();
                        } else {
                            // 获取的参数都是字符串
                            String typeName = parameter.getType().getName();

                            if (typeName.equals("java.lang.Integer")) {
                                if (request.getParameter(parameterName) != null) {
                                    parameterValues[i] =  Integer.parseInt(request.getParameter(parameterName));
                                }
                            } else {
                                parameterValues[i] = request.getParameter(parameterName);
                            }
                        }
                    }

                    // 获取方法形参名称

                    // 2. controller组件方法调用
                    // 设置方法为可用
                    method.setAccessible(true);
                    // 传入参数值数组进行反射调用
                    System.out.println(methodName);
                    Object returnObj =  method.invoke(controllerBeanObj, parameterValues);
                    String methodReturnStr = (String) returnObj;
                    // redirect:fruit.do
                    // 截取字符串, 完成重定向
                    if (methodReturnStr.startsWith("redirect:")) {
                        String redirectStr = methodReturnStr.substring("redirect:".length());
                        response.sendRedirect(redirectStr);
                    } else {
                        // 3. 视图处理
                        super.processTemplate(methodReturnStr, request, response);
                    }
                }
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }





    }
}
