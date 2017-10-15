package cn.itcast.chatroom.web.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import cn.itcast.chatroom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cn.itcast.chatroom.domain.User;



@Controller
@RequestMapping("/chat")
public class ChatController {

	@Autowired
	private UserService userService;
	
	// 跳转到登录页面
	@RequestMapping(value = "loginpage", method = RequestMethod.GET)
	public ModelAndView loginpage() {
		return new ModelAndView("login");
	}


	// 登录进入聊天主页面
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public ModelAndView login(User loginUser, HttpServletRequest request) {
		HttpSession session = request.getSession();
		// 登录操作

		//判断根据id是否一个合格的用户
		if(!(userService.checkUser(loginUser))){
			//跳回登录
			return new ModelAndView("login");
		}

		userService.newUserInSession(session,loginUser);

		// 将登录信息放入数据库，便于协查跟踪聊天者
		System.out.println("新用户诞生了：" + loginUser);
		return new ModelAndView("main");

	}
	
	
	
	// 跳转到聊天室页面
	@RequestMapping(value = "mainpage", method = RequestMethod.GET)
	public ModelAndView mainpage(HttpServletRequest request) {
		
		//判断，如果没有session，则跳到登录页面
		HttpSession session = request.getSession();
		if(null==session.getAttribute("loginUser")){
			return new ModelAndView("login");
		}else{
			System.out.println("*****竟然还过这里*********");
			session.setAttribute("having", "has");
			return new ModelAndView("main");
		}
	}

}
