package cn.itcast.travel.web.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BaseServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        //完成方法分发
        //获取请求路径
        String uri = req.getRequestURI();
        System.out.println("请求uri:"+uri);
        //获取方法名称
        String methodName = uri.substring(uri.lastIndexOf('/')+1);
        System.out.println("方法名称:"+methodName);
        //获取方法对象
        System.out.println(this);
        try {
            //获取方法
            Method method = this.getClass().getMethod(methodName,HttpServletRequest.class,HttpServletResponse.class);
            //暴力反射
            method.invoke(this,req,resp);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将传入的对象序列化为json并写回客户端
     * @param obj
     * @param resp
     * @throws IOException
     */
    public void writeValue(Object obj,HttpServletResponse resp) throws IOException {
        ObjectMapper om = new ObjectMapper();
        resp.setContentType("application/json;charset=utf-8");
        om.writeValue(resp.getOutputStream(),obj);
    }

    /**
     * 将传入的对象序列化为json，返回
     * @param obj
     * @return
     * @throws JsonProcessingException
     */
    public String writeValueAsString(Object obj) throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();
        return om.writeValueAsString(obj);
    }

}
