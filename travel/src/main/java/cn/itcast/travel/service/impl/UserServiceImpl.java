package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

import javax.jws.soap.SOAPBinding;

public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();

    /**
     * 注册用户
     * @param user
     * @return
     */
    @Override
    public boolean registUser(User user) {
        //根据用户名查询用户对象
        User u = userDao.findByUsername(user.getUsername());
        if (u!=null){
            //用户名已存在
            return false;
        }
        //设置激活码 唯一字符串
        user.setCode(UuidUtil.getUuid());
        //设置激活状态
        user.setStatus("N");
        userDao.save(user);
        //激活邮件发送
        String content = "<a href='http://localhost/travel/user/active?code="+user.getCode()+"'>点击激活【黑马旅游网】</a>";
        MailUtils.sendMail(user.getEmail(),content,"激活邮件");
        return true;
    }

    @Override
    public boolean active(String code) {
        User u = userDao.findByCode(code);
        if (u!=null){
            userDao.updateStatus(u);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public User login(User user) {
        return userDao.login(user.getUsername(),user.getPassword());
    }


}
