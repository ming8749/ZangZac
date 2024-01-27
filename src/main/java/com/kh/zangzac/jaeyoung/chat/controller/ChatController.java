package com.kh.zangzac.jaeyoung.chat.controller;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.kh.zangzac.common.ImageStorage;
import com.kh.zangzac.jaeyoung.chat.ChatFileManager;
import com.kh.zangzac.jaeyoung.chat.model.service.ChatService;
import com.kh.zangzac.jaeyoung.chat.model.vo.ChatRoom;
import com.kh.zangzac.jaeyoung.chat.model.vo.Chatter;
import com.kh.zangzac.ming.member.model.vo.Member;

import jakarta.servlet.http.HttpSession;

@SessionAttributes("loginUser")
@Controller
public class ChatController {
	
	
	private final ImageStorage imageStorage;
	
	
	@Autowired
	ChatService cService;

    @Autowired
    public ChatController(ImageStorage imageStorage) {
        this.imageStorage = imageStorage;
    }
    
    @Autowired
    ChatFileManager cFileManager;
    
    
    @GetMapping("chatListView.jy")
    public String chatListView(Model model) {
    	// 채팅방 정보들 가져와서 보내기
    	ArrayList<ChatRoom> list = cService.chatRoomList();
    	model.addAttribute("list", list);
    	return "views/jaeyoung/chatRoomList";
    	
    }
    
    @GetMapping("/enterChatRoom.jy")
    public String chatRoom(@RequestParam("chatRoomId") String roomName,Model model,HttpSession session) {
    	
    	//채팅참가자 불러오기
    	ArrayList<Chatter> list = cService.chatterList(roomName);
    	
    	//채팅내역 불러오기
    	//예시: "1" 방의 채팅 로그 읽어오기
    	//unReadChatterCount
    	Member loginUser = (Member)session.getAttribute("loginUser");
        JSONArray chatLogs = cFileManager.readChatLog(roomName);
        String chatName="";
        
        if(roomName.equals("1")) {
        	chatName="백패킹";
        }else if(roomName.equals("2")) {
        	chatName="오토캠핑";
        }else if(roomName.equals("3")) {
        	chatName="장박";
        }else if(roomName.equals("4")) {
        	chatName="글램핑";
        }else if(roomName.equals("5")) {
        	chatName="야영장";
        }
        
        
        model.addAttribute("chatName", chatName);
    	model.addAttribute("roomName", roomName);
    	model.addAttribute("list", list);
    	model.addAttribute("chatLogs", chatLogs);

    	
        //chatRoomId
        return "views/jaeyoung/chatRoomPage";
    }
    
    @GetMapping("adminChatroom.jy")
    public String adminChatroom(Model model) {
    	
    	ArrayList<ChatRoom> list = cService.chatRoomList();
    	
    	model.addAttribute("list", list);
    	return "views/jaeyoung/adminChatroom";
    }
    
    @PostMapping("insertChatRoom.jy")
    public String insertChatRoom(@ModelAttribute ChatRoom cr,@RequestParam("categoryName")String categoryName) {
    	
    	
    	if(categoryName.equals("백패킹")) {
    		cr.setCategoryNo("1");
    	}else if (categoryName.equals("오토캠핑")) {
    		cr.setCategoryNo("2");
    	}else if (categoryName.equals("장박")) {
    		cr.setCategoryNo("3");
    	}else if (categoryName.equals("글램핑")) {
    		cr.setCategoryNo("4");
    	}else if (categoryName.equals("야영장")) {
    		cr.setCategoryNo("5");
    	}else{
    		cr.setCategoryNo("");
    	}
    	int result =0;
    	
    	if(!cr.getCategoryNo().equals("")) {
    		result = cService.insertChat(cr);
    	}
    	
    	return "redirect:adminChatroom.jy";
    }
    
    @GetMapping("deleteChat.jy")
    public String deleteChat(@RequestParam("no") String no) {
    	
    	cService.deleteChat(no);
    	
    	return "redirect:adminChatroom.jy";
    }

}
