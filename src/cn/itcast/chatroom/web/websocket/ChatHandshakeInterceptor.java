package cn.itcast.chatroom.web.websocket;

import java.util.Map;

import javax.servlet.http.HttpSession;

import cn.itcast.chatroom.dao.RecordsDao;
import cn.itcast.chatroom.dao.UserDao;
import cn.itcast.chatroom.domain.Records;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import cn.itcast.chatroom.domain.User;
/**
 * websocket的链接建立是基于http握手协议，我们可以添加一个拦截器处理握手之前和握手之后过程
 * @author BoBo
 *
 */
@Component
public class ChatHandshakeInterceptor implements HandshakeInterceptor{

	@Autowired
  	private RecordsDao recordsDao;
	
	/**
     * 握手之前，若返回false，则不建立链接
     */
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes) throws Exception {
		if (request instanceof ServletServerHttpRequest) {
			
			ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
			HttpSession session = servletRequest.getServletRequest().getSession(false);
			
			//如果用户已经登录，允许聊天
			if(session.getAttribute("loginUser")!=null){
				
				
				//获取登录的用户
				User loginUser=(User)session.getAttribute("loginUser");
				
				if(session.getAttribute("having")!=null){
					attributes.put("having", "has");
				}
				
				//将用户放入socket处理器的会话(WebSocketSession)中
				attributes.put("loginUser", loginUser);
				
				System.out.println("Websocket:用户[ID:" + (loginUser.getId() + ",Name:"+loginUser.getNickname()+"]要建立连接"));
			}else{
				
				//用户没有登录，拒绝聊天
				//握手失败！
				System.out.println("--------------握手已失败...");
				return false;
			}
		}
		
		System.out.println("--------------握手开始...");
		return true;
	}

	/**
     * 握手之后
     */
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception exception) {
		System.out.println("--------------握手成功啦...");
		//把登录信息存到数据库中，并开始记录聊天信息
		if (request instanceof ServletServerHttpRequest) {

			ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
			HttpSession session = servletRequest.getServletRequest().getSession(false);

			//获取登录的用户
			User loginUser=(User)session.getAttribute("loginUser");
			if(loginUser!=null){

				//插入记录
				Records records = new Records(loginUser.getUserId(),loginUser.getId(),loginUser.getNickname());
				recordsDao.insert(records);
			}

		}

		}

}
