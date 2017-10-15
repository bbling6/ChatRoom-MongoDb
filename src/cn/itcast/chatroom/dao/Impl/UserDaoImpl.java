package cn.itcast.chatroom.dao.Impl;

import java.util.List;

import javax.annotation.Resource;

import cn.itcast.chatroom.dao.UserDao;
import cn.itcast.chatroom.domain.Users;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {

    private final static String collectionName ="users";
      
    @Resource  
    private MongoTemplate mongoTemplate;  
  
    //插入一个集合的元素
    public void insert(Users object) {
        mongoTemplate.insert(object, collectionName);
    }

    public void createCollection(String name) {  
        mongoTemplate.createCollection(name);  
    }

    //得到全部
    public List<Users> findAll() {
        List<Users> user = mongoTemplate.findAll(Users.class,collectionName);
        return user;
    }

    public Users getOne(String id) {
        Users user = mongoTemplate.findOne(new Query(Criteria.where("id")
                .is(id)), Users.class,collectionName);
        return user;
    }

    /**
     * 根据条件查询出来后 再去修改 <br>
     * ------------------------------<br>
     * @param criteriaUser
     *            查询条件
     * @param updateUser
     *            修改的值对象
     * @return
     */
    public Users findAndModify(Users criteriaUser, Users updateUser) {
        Query query = getQuery(criteriaUser);
        Update update = Update.update("age", updateUser.getAge()).set("name",
                updateUser.getName());
        return mongoTemplate.findAndModify(query, update, Users.class,collectionName);
    }


    //根据id删除
    public void removeOne(String id) {
        Criteria criteria = Criteria.where("id").in(id);

        if (criteria != null) {
            Query query = new Query(criteria);
            if (query != null
                    && mongoTemplate.findOne(query, Users.class,collectionName) != null)
                mongoTemplate.remove(mongoTemplate.findOne(query, Users.class,collectionName),collectionName);
        }

    }

    /**
     * 查询出来后 删除 <br>
     * ------------------------------<br>
     * @param criteriaUser
     * @return
     */
    public Users findAndRemove(Users criteriaUser) {
        Query query = getQuery(criteriaUser);
        return mongoTemplate.findAndRemove(query, Users.class,collectionName);
    }

    /**
     * count <br>
     * @param criteriaUser
     * @return
     */
    public long count(Users criteriaUser) {
        Query query = getQuery(criteriaUser);
        return mongoTemplate.count(query, Users.class,collectionName);
    }


    /**
     * 修改多条 <br>
     * ------------------------------<br>
     * @param criteriaUser
     * @param user
     */
    public void update(Users criteriaUser, Users user) {
        Criteria criteria = Criteria.where("age").gt(criteriaUser.getAge());

        Query query = new Query(criteria);
        Update update = Update.update("name", user.getName()).set("age",
                user.getAge());
        mongoTemplate.updateMulti(query, update, Users.class,collectionName);
    }

    /**
     * 按条件查询, 分页 <br>
     * ------------------------------<br>
     * @param criteriaUser
     * @param skip
     * @param limit
     * @return
     */
    public List<Users> find(Users criteriaUser, int skip, int limit) {
        Query query = getQuery(criteriaUser);
        query.skip(skip);
        query.limit(limit);
        return mongoTemplate.find(query, Users.class,collectionName);
    }

    /**
     *
     * <br>
     * ------------------------------<br>
     * @param criteriaUser
     * @return
     */
    private Query getQuery(Users criteriaUser) {
        if (criteriaUser == null) {
            criteriaUser = new Users();
        }
        Query query = new Query();
        if (criteriaUser.getId() != null) {
            Criteria criteria = Criteria.where("userId").is(criteriaUser.getId());
            query.addCriteria(criteria);
        }
        if (criteriaUser.getAge() > 0) {
            Criteria criteria = Criteria.where("age").gt(criteriaUser.getAge());
            query.addCriteria(criteria);
        }
        if (criteriaUser.getName() != null) {
            Criteria criteria = Criteria.where("name").regex(
                    "^" + criteriaUser.getName());
            query.addCriteria(criteria);
        }
        return query;
    }

}

