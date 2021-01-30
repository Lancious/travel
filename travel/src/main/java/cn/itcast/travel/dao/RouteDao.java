package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;

import java.sql.SQLException;
import java.util.List;

public interface RouteDao {
    List<Route> findByPage(int cid,int start,int pageSize,String rname);

    int findTotalCount(int cid,String rname);

    Route findOne(int rid);
}
