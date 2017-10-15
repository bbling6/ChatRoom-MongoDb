package cn.itcast.chatroom.dao;

import java.util.List;


public interface MongoBase<T> {

    public void insert(T object);

    public List<T> findAll();

    public void removeOne(String id);

    public T findAndRemove(T criteriaUser);

    public long count(T criteriaUser);

    public List<T> find(T criteriaUser, int skip, int limit);

    //创建集合  
    public void createCollection(String collectionName);  

      
}
