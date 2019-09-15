package com.aop;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
@Slf4j
public class LogWebAspect {
    ThreadLocal<Date>  start = new ThreadLocal<>();

    //@Pointcut("execution(@com.aop.LogWebController * com.web.*.*(..))")
    @Pointcut("@annotation(com.aop.LogWebController)")
    public void log(){};

    @Pointcut("execution(public * com.rest..*.*(..))")
    public void webLog(){};

    @Before("log()")
    public void getRequestInfo(JoinPoint joinPoint){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest  request = attributes.getRequest();
        /*获取当前时间*/
        start.set(Calendar.getInstance().getTime());

        log.info("请求路劲："+ request.getRequestURL().toString());
        log.info("请求ip："+ request.getRemoteAddr());
        log.info("请求方法类型："+ request.getMethod());
        Map<String,Object> fieldsName = getFieldsName((ProceedingJoinPoint) joinPoint);
        for(Map.Entry<String,Object> entry : fieldsName.entrySet()){
            log.info("方法的参数是： "+ entry.getKey()+"----->"+entry.getValue());
        }
        log.info("目标方法名： "+joinPoint.getSignature().getName());
        log.info("目标方法的类名： "+joinPoint.getSignature().getDeclaringTypeName());
        log.info("被代理的对象:" + joinPoint.getTarget());
        /*
        if("GET".equals(request.getMethod())){
            Map<String,String[]> map = request.getParameterMap();
            log.info("map.length: "+map.size());
            for(Map.Entry<String,String[]> entry : map.entrySet()){
                log.info("get参数: 键--- "+entry.getKey()+", 值--- " + request.getParameter(entry.getKey()));
            }
        }else{
            String body = "";
            StringBuffer sb = new StringBuffer();
            try(
                    InputStream input =request.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(input));
                ){
                if(input !=null ){
                    char[] charBuffer = new char[128];
                    int bytesRead = -1;
                    while((bytesRead = br.read(charBuffer))>0){
                        sb.append(charBuffer,0,bytesRead);
                    }
                }else{
                    sb.append("");
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            log.info("post参数： "+sb.toString());
        }
        */
    }
    @AfterReturning(pointcut = "log()",returning="object")
    public void methodAfterReturning(Object object){
        log.info("===================返回内容==================");
        // JSONObject.fromObject(object).toString()
        log.info("Response内容： " + JSONObject.toJSON(object).toString());
        log.info("===================执行时间==================");
        log.info("请求处理时间： "+(Calendar.getInstance().getTime().getTime()-start.get().getTime()));
    }

    /**
     * 获取参数列表
     *
     * @param joinPoint
     * @return
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     */
    private static Map<String, Object> getFieldsName(ProceedingJoinPoint joinPoint) {
        // 参数值
        Object[] args = joinPoint.getArgs();
        ParameterNameDiscoverer pnd = new DefaultParameterNameDiscoverer();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        String[] parameterNames = pnd.getParameterNames(method);
        Map<String, Object> paramMap = new HashMap<>(32);
        for (int i = 0; i < parameterNames.length; i++) {
            paramMap.put(args[i].getClass()+" "+parameterNames[i], args[i]);
        }
        return paramMap;
    }
}
