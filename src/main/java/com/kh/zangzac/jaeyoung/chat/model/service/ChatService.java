package com.kh.zangzac.jaeyoung.chat.model.service;

import java.util.ArrayList;

import com.kh.zangzac.jaeyoung.chat.model.vo.ChatRoom;
import com.kh.zangzac.jaeyoung.chat.model.vo.Chatter;

public interface ChatService {

	int checkCount(Chatter checkChatter);

	int insertChatRoom(Chatter c);

	ArrayList<Chatter> selectChatterList(int i);

	ArrayList<ChatRoom> chatRoomList();

	ArrayList<Chatter> chatterList(String roomName);

	int insertChat(ChatRoom cr);

	void deleteChat(String no);


}
