package cn.itcast.travel.web.servlet.temp;

import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/activeUserServlet")
public class ActiveUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)throws IOException{
        String code = req.getParameter("code");
        if (code!=null){
            UserService userService = new UserServiceImpl();
            boolean flag = userService.active(code);
            String msg = null;
            if (flag){
                msg = "激活成功,请往<a href='login.html'>登录</a>";
            }else {
                msg = "激活失败，请联系管理员";
            }
            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().write(msg);
        }
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)throws IOException{
        this.doPost(req, resp);
    }
}

