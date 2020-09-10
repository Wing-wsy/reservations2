package com.annet.config;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.annet.common.GetToken;
import com.annet.result.R;
import com.annet.result.ResultSupport;
import com.annet.service.impl.TbBespokeServiceImpl;
import com.annet.yml.Family;
import lombok.Data;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author ：xy
 * @date ：Created in 2020/4/14 11:17
 * @description：
 * @modified By：
 * @version: $
 */
@Aspect      //定义该类为AOP切面类
@Component  //交给spring容器管理
@Slf4j
public class SessionTimeOutAspect {
    //private static Logger logger = Logger.getLogger(SessionTimeOutAspect.class);

    @Autowired
    Family family;

    public SessionTimeOutAspect() {
    }
    @Pointcut("execution(public * com.annet.controller..*.*(..))")  //定义一个名称为controllerPointcut的切点
    public void controllerPointcut(){
    }
    @Pointcut("execution(public * com.annet.controller.TbUserController.login(..))")
    public void rootPointcut(){//登录登出功能不需要Session验证
    }
    @Pointcut("execution(public * com.annet.controller.TbUserController.pseudoLogin(..))")
    public void root2Pointcut(){//伪登录
    }

    @Pointcut("execution(public * com.annet.controller.TbBespokeController.getBespokeStatusAndQueueNo(..))")
    public void root3Pointcut(){
    }
    @Pointcut("execution(public * com.annet.controller.TbBespokeController.selectCancelBespoke(..))")
    public void root4Pointcut(){
    }

    @Pointcut("controllerPointcut()&&(!rootPointcut())&&(!root2Pointcut())&&(!root3Pointcut())&&(!root4Pointcut())")
    public void sessionTimeOutPointcut(){
    }

    @Around("sessionTimeOutPointcut()")
    public Object sessionTimeOutAdvice(ProceedingJoinPoint pjp) {
        Object result = null;
        String targetName = pjp.getTarget().getClass().getSimpleName();
        String methodName = pjp.getSignature().getName();
        System.out.print("类名："+targetName+" 方法名："+methodName);
        HttpServletResponse response = null;

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        //移动端的不进行拦截
        if(family.getFlage()!=null && family.getFlage().equals("1")){
            try {
                result = pjp.proceed();   //调用拦截的原有方法
            } catch (Throwable e) {
                e.printStackTrace();
            }
            return result;
        }
        //判断是否有合法token
//        String token = request.getHeader("token");
//        if(StringUtils.isEmpty(token)){
//            token = request.getParameter("token");
//        }
//        if(!StringUtils.isEmpty(token)){
//            //判断token是否合法
//            String id =  GetToken.sessionMap.get(token);
//            if(id!=null){
//                try {
//                    result = pjp.proceed();
//                } catch (Throwable e) {
//                    e.printStackTrace();
//                }
//                return result;
//            }
//        }


        for (Object param : pjp.getArgs()) {
            /**
            if (param instanceof HttpServletResponse) {
                response = (HttpServletResponse) param;
            }
             */
        }

        HttpSession session = request.getSession();
        if(session.getAttribute("user")!=null){
            try {
                result = pjp.proceed();
            } catch (Throwable e) {
                e.printStackTrace();
            }
            return result;
        } else{
            log.info(session.getId()+":登录用户信息已过期，请重新登陆！");
            return new R(1001,1001,"登录用户信息已过期，请重新登陆！");
        }
    }
}
