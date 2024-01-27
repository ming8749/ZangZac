package com.kh.zangzac.jaeyoung.chat.model.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.zangzac.jaeyoung.chat.model.dao.ChatDAO;
import com.kh.zangzac.jaeyoung.chat.model.vo.ChatRoom;
import com.kh.zangzac.jaeyoung.chat.model.vo.Chatter;

@Service
public class ChatServiceImpl implements ChatService{

	@Autowired
	ChatDAO cDAO;
	
	@Override
	public int checkCount(Chatter checkChatter) {
		
		return cDAO.checkCount(checkChatter);
	}

	@Override
	public int insertChatRoom(Chatter c) {
		int result =cDAO.insertChatRoom(c);
		int result2 =cDAO.updateChatRoomCount(c.getClNo());
		return result; 
	}

	@Override
	public ArrayList<Chatter> selectChatterList(int i) {
		return cDAO.selectChatterList(i);
	}

	@Override
	public ArrayList<ChatRoom> chatRoomList() {
		return cDAO.chatRoomList();
	}

	@Override
	public ArrayList<Chatter> chatterList(String roomName) {
		return cDAO.chatterList(roomName);
	}

	@Override
	public int insertChat(ChatRoom cr) {
		return cDAO.insertChat(cr);
	}

	@Override
	public void deleteChat(String no) {
		cDAO.deleteChat(no);
	}


}
