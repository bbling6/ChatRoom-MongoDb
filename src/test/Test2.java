package test;

import java.util.Map;
import java.util.Set;



import com.mongodb.BasicDBList;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class Test2 {
	

	public static void main(String[] args) {
		try{
            // 连接到 mongodb 服务
             Mongo mongo = new Mongo("127.0.0.1", 27017);
            //根据mongodb数据库的名称获取mongodb对象 ,
             DB db = mongo.getDB( "test" );
             Set<String> collectionNames = db.getCollectionNames();

               // 打印出test中的集合
              for (String name : collectionNames) {
                    System.out.println("collectionName==="+name);
              }

              DBObject doc2 = null;

              doc2 = db.getCollection("user").findOne();

              System.out.println(doc2);
          }catch(Exception e){
             e.printStackTrace();
          }
	}

}
