package cn.itcast.chatroom.dao;

import cn.itcast.chatroom.domain.Message;
import cn.itcast.chatroom.domain.Records;

import java.util.List;


public interface RecordsDao extends MongoBase<Records>{

    public Records getOne(String id);

    public Records getOneBySessionId(String id);

    public Records findAndModify(Records criteriaUser, List<Message> list);

    public void removeOneByUserId(String id);

}
