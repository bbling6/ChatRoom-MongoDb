package test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test1 {

    public static void main(String[] args) {
//
//        MongoOperations mongoOps = new MongoTemplate(new SimpleMongoDbFactory(new Mongo(),"test"));
//        //MongoOperations mongoOps = new MongoTemplate(new Mongo(), "rui");
////        mongoOps.insert(new User(1, "Joe", 34));
//
//        System.out.println(mongoOps.findOne(new Query(new Criteria("name").is("hong")),
//                Users.class));

        ApplicationContext app = new ClassPathXmlApplicationContext("classpath:resources/applicationContext.xml");



    }
}
