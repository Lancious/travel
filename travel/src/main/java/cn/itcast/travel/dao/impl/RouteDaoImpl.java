package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import com.alibaba.druid.util.JdbcUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RouteDaoImpl implements RouteDao {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public List<Route> findByPage(int cid, int start, int pageSize,String rname) {
//        String sql = "select * from tab_route where cid=? limit ?,?";
        String sql = "select * from tab_route where 1=1 ";
        StringBuilder sb = new StringBuilder(sql);
        List parmas = new ArrayList();  //条件
        if (cid!=0){
            sb.append(" and cid=? ");
            parmas.add(cid);    //添加?对应值
        }
        if (rname!=null&&rname.length()>0){
            sb.append(" and rname like ?");
            parmas.add("%"+rname+"%");
        }
        sb.append(" limit ?,? ");
        sql = sb.toString();
        parmas.add(start);
        parmas.add(pageSize);
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Route.class),parmas.toArray());
    }

    @Override
    public int findTotalCount(int cid,String rname) {
//        String sql = "select count(*) from tab_route where cid=?";
        //自定义sql模板
        String sql = "select count(*) from tab_route where 1=1 ";
        StringBuilder sb = new StringBuilder(sql);
        List parmas = new ArrayList();  //条件
        if (cid!=0){
            sb.append(" and cid=? ");
            parmas.add(cid);    //添加?对应值
        }
        if (rname!=null&&rname.length()>0){
            sb.append(" and rname like ? ");
            parmas.add("%"+rname+"%");
        }
        sql = sb.toString();
        return jdbcTemplate.queryForObject(sql,Integer.class,parmas.toArray());
    }

    @Override
    public Route findOne(int rid){
        String sql = "select * from tab_route where rid = ? ";
        return jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<>(Route.class),rid);
    }
}
