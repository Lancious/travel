package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

public interface UserDao {
    User findByUsername(String username);

    void save(User user);

    //根据激活码查询用户对象
    User findByCode(String code);

    //修改指定用户激活状态
    void updateStatus(User user);

    User login(String username,String password);
}
