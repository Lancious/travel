package cn.itcast.travel.web.servlet.temp;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/registUserServlet")
public class RegistUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)throws IOException{
        //验证校验
        String check = req.getParameter("check");
        //session中获取验证码
        HttpSession httpSession = req.getSession();
        String checkCode_server = (String) httpSession.getAttribute("CHECKCODE_SERVER");
        httpSession.removeAttribute("CHECKCODE_SERVER");
        if (checkCode_server==null||!check.equalsIgnoreCase(checkCode_server)){
            //验证失败
            ResultInfo resultInfo = new ResultInfo();
            //注册失败
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("验证码错误");
            //将info对象序列化为json
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(resultInfo);
            resp.setContentType("application/json;charset=utf-8");
            resp.getWriter().write(json);
            return;
        }
        //获取数据
        Map<String,String[]> map = req.getParameterMap();
        //封装User对象
        User user = new User();
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        UserService userService = new UserServiceImpl();
        boolean flag = userService.registUser(user);
        ResultInfo resultInfo = new ResultInfo();
        if (flag){
            resultInfo.setFlag(true);
        }else {
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("注册失败");
        }
        //将info对象序列化为json
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(resultInfo);
        resp.setContentType("application/json;charset=utf-8");
        resp.getWriter().write(json);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)throws IOException{
        this.doPost(req, resp);
    }
}
