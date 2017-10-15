package cn.itcast.chatroom.dao.Impl;

import cn.itcast.chatroom.dao.RecordsDao;
import cn.itcast.chatroom.domain.Message;
import cn.itcast.chatroom.domain.Records;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class RecordsDaoImpl implements RecordsDao {


    private final static String collectionName ="records";

    @Resource
    private MongoTemplate mongoTemplate;

    //插入一个集合的元素
    public void insert(Records object) {
        mongoTemplate.insert(object, collectionName);
    }

    public void createCollection(String name) {
        mongoTemplate.createCollection(name);
    }

    //得到全部
    public List<Records> findAll() {
        List<Records> user = mongoTemplate.findAll(Records.class,collectionName);
        return user;
    }

    //根据userId查询
    public Records getOne(String id) {
        Records user = mongoTemplate.findOne(new Query(Criteria.where("userId")
                .is(id)), Records.class,collectionName);
        return user;
    }

    //根据sessionId查询
    public Records getOneBySessionId(String id) {
        Records user = mongoTemplate.findOne(new Query(Criteria.where("sessionId")
                .is(id)), Records.class,collectionName);
        return user;
    }

    /**
     * 根据条件查询出来后 再去修改聊天记录 <br>
     * ------------------------------<br>
     *
     * @param criteriaUser
     *            查询条件
     * @param list
     *            修改的值对象
     * @return
     */
    public Records findAndModify(Records criteriaUser, List<Message> list) {
        Query query = getQuery(criteriaUser);
        Update update = Update.update("recordList", list);
        return mongoTemplate.findAndModify(query, update, Records.class,collectionName);
    }


    //根据sessionId删除
    public void removeOne(String id) {
        Criteria criteria = Criteria.where("sessionId").in(id);

        if (criteria != null) {
            Query query = new Query(criteria);
            if (query != null
                    && mongoTemplate.findOne(query, Records.class,collectionName) != null)
                mongoTemplate.remove(mongoTemplate.findOne(query, Records.class,collectionName),collectionName);
        }

    }

    //根据删除
    public void removeOneByUserId(String id) {
        Criteria criteria = Criteria.where("userId").in(id);

        if (criteria != null) {
            Query query = new Query(criteria);
            if (query != null
                    && mongoTemplate.findOne(query, Records.class,collectionName) != null)
                mongoTemplate.remove(mongoTemplate.findOne(query, Records.class,collectionName),collectionName);
        }

    }

    /**
     * 查询出来后 删除 <br>
     * ------------------------------<br>
     *
     * @param criteriaUser
     * @return
     */
    public Records findAndRemove(Records criteriaUser) {
        Query query = getQuery(criteriaUser);
        return mongoTemplate.findAndRemove(query, Records.class,collectionName);
    }

    /**
     * count <br>
     * ------------------------------<br>
     *
     * @param criteriaUser
     * @return
     */
    public long count(Records criteriaUser) {
        Query query = getQuery(criteriaUser);
        return mongoTemplate.count(query, Records.class,collectionName);
    }



    /**
     * 按条件查询, 分页 <br>
     * ------------------------------<br>
     *
     * @param criteriaUser
     * @param skip
     * @param limit
     * @return
     */
    public List<Records> find(Records criteriaUser, int skip, int limit) {
        Query query = getQuery(criteriaUser);
        query.skip(skip);
        query.limit(limit);
        return mongoTemplate.find(query, Records.class,collectionName);
    }

    /**
     *
     * <br>
     * ------------------------------<br>
     *
     * @param criteriaUser
     * @return
     */
    private Query getQuery(Records criteriaUser) {
        if (criteriaUser == null) {
            criteriaUser = new Records();
        }
        Query query = new Query();
        if (criteriaUser.getSessionId() != null) {
            Criteria criteria = Criteria.where("sessionId").is(criteriaUser.getSessionId());
            query.addCriteria(criteria);
        }
        if (criteriaUser.getUserId() !=null) {
            Criteria criteria = Criteria.where("userId").gt(criteriaUser.getUserId());
            query.addCriteria(criteria);
        }
        if (criteriaUser.getNickname() != null) {
            Criteria criteria = Criteria.where("nickname").regex(
                    "^" + criteriaUser.getNickname());
            query.addCriteria(criteria);
        }
        return query;
    }

}
