package cn.itcast.travel.web.servlet.temp;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/findUserServlet")
public class FindUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        Object user = req.getSession().getAttribute("user");
        ObjectMapper om = new ObjectMapper();
        resp.setContentType("application/json;charset=utf-8");
        om.writeValue(resp.getOutputStream(),user);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        this.doPost(req, resp);
    }
}
