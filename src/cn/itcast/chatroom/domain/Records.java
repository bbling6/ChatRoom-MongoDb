package cn.itcast.chatroom.domain;



import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


import java.util.List;


@Document(collection = "records")
public class Records {


    private String userId;  //用户id

    @Id
    private String sessionId;  //session的id


    private String nickname;  //昵称

    @DBRef
    private List<Message> recordList;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public List<Message> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<Message> recordList) {
        this.recordList = recordList;
    }

    @PersistenceConstructor
    public Records(String userId, String sessionId, String nickname) {
        this.userId = userId;
        this.sessionId = sessionId;
        this.nickname = nickname;
    }

    public Records() {
    }
}

