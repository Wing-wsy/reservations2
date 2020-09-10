package com.annet.config;

import com.annet.common.MyException;
import com.annet.entity.TbUser;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ：xy
 * @date ：Created in 2019/6/21 9:28
 * @description：
 * @modified By：
 * @version: $
 */
public class Interceptor implements HandlerInterceptor {
    long start = System.currentTimeMillis();

    /**
     * 请求执行前执行
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        System.out.println("sessionID="+request.getSession().getId());
        TbUser user = (TbUser) request.getSession().getAttribute("user");
        String url = request.getRequestURI();
        System.out.println(url);
        // XMLHttpRequest
        String type = request.getHeader("X-Requested-With");
        if (user != null) {
            //判断权限
            String RuleList = user.getRuleList();
            return true;
        } else {
            // 重定向
            String path = request.getContextPath();
            String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
            // 转发
            if ("XMLHttpRequest".equals(type)) {
                // ajax请求
                response.setHeader("SESSIONSTATUS", "TIMEOUT");
                response.setHeader("CONTEXTPATH", basePath + "index.jsp");

                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return false;
            }
            //这里可以写登录超时返回给页面的错误信息。或者跳转页面
            throw new MyException("登陆超时");
        }
    }

    /**
     * 请求结束执行,只有preHandle方法返回true的时候才会执行
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param modelAndView
     */
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) {
        System.out.println("Interceptor cost="+(System.currentTimeMillis()-start));
    }

    /**
     * 视图渲染完成后才执行，同样需要preHandle返回true，该方法通常用于清理资源等工作
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param e
     */
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
    }
}
