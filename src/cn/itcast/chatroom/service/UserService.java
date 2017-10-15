package cn.itcast.chatroom.service;

import cn.itcast.chatroom.dao.UserDao;
import cn.itcast.chatroom.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;


    public boolean checkUser(User loginUser) {
        try {

            if (userDao.getOne(loginUser.getUserId()) == null) { return false; }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    //判断重复，并把登录用户放进session
    public void newUserInSession(HttpSession session,User loginUser){

        // 判断是否是一个已经登录的用户，没有则登录
        if (null != session.getAttribute("loginUser")) {
            // 清除旧的用户
            session.removeAttribute("loginUser");
        }

        // 新登录，需要构建一个用户
        // 随机生成一个用户
        String id = UUID.randomUUID().toString();
        loginUser.setId(id);

        // 将用户放入session
        session.setAttribute("loginUser", loginUser);
        //用来判断重复登录
        session.setAttribute("first", "first");

    }


}
