package cn.itcast.chatroom.domain;

import java.io.Serializable;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

//真正的用户
@Document(collection = "users")
public class Users implements Serializable {
    /** 
     *  
     */  
    private static final long serialVersionUID = 1L;

    @Id
	private ObjectId _id;

    @Indexed(unique = true)
    private String id;  //用户的唯一标示

	@Indexed(unique = true)
    private String name; //真实的名字

    private int age;



	public Users(String id, String name, int age) {
		this.id = id;
		this.name = name;
		this.age = age;
	}

	public Users() {
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
    
}
