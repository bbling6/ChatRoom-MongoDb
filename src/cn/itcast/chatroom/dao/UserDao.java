package cn.itcast.chatroom.dao;

import cn.itcast.chatroom.domain.Users;

public interface UserDao extends MongoBase<Users>{

    public Users getOne(String id);

    public  Users findAndModify(Users criteriaUser, Users updateUser);

    //修改多条
    public void update(Users criteriaUser, Users user);

}  
