package com.kh.zangzac.jaeyoung.eventBoard.model.service;

import java.util.ArrayList;

import com.kh.zangzac.common.model.vo.PageInfo;
import com.kh.zangzac.jaeyoung.eventBoard.model.vo.EventBoard;

public interface EventBoardService {

	int insertEventBoard(EventBoard eb);

	int getListCount();

	ArrayList<EventBoard> selectEventBoardList(PageInfo pi);

	EventBoard selectEventBoard(int ebNo);

	int updateEventBoard(EventBoard eb);

	int deleteEventBoard(int ebNo);

}
