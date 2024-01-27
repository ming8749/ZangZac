package com.kh.zangzac.jaeyoung.chat.model.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.kh.zangzac.jaeyoung.chat.model.vo.ChatRoom;
import com.kh.zangzac.jaeyoung.chat.model.vo.Chatter;

@Mapper
public interface ChatDAO {

	int checkCount(Chatter checkChatter);

	int insertChatRoom(Chatter c);

	ArrayList<Chatter> selectChatterList(int i);

	int updateChatRoomCount(int clNo);

	ArrayList<ChatRoom> chatRoomList();

	ArrayList<Chatter> chatterList(String roomName);

	int insertChat(ChatRoom cr);

	void deleteChat(String no);

}
