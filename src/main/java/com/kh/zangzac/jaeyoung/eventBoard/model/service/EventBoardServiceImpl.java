package com.kh.zangzac.jaeyoung.eventBoard.model.service;

import java.util.ArrayList;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.zangzac.common.model.vo.PageInfo;
import com.kh.zangzac.jaeyoung.eventBoard.model.dao.EventBoardDAO;
import com.kh.zangzac.jaeyoung.eventBoard.model.vo.EventBoard;

@Service
public class EventBoardServiceImpl implements EventBoardService {
	
	@Autowired
	EventBoardDAO ebDAO;

	@Override
	public int insertEventBoard(EventBoard eb) {
		return ebDAO.insertEventBoard(eb);
	}

	@Override
	public int getListCount() {
		return ebDAO.getListCount();
	}

	@Override
	public ArrayList<EventBoard> selectEventBoardList(PageInfo pi) {
		RowBounds rowBounds = new RowBounds((pi.getCurrentPage()-1 )* pi.getBoardLimit(), pi.getBoardLimit());
		return ebDAO.selectEventBoardList(rowBounds);
	}

	@Override
	public EventBoard selectEventBoard(int ebNo) {
		return ebDAO.selectEventBoard(ebNo);
	}

	@Override
	public int updateEventBoard(EventBoard eb) {
		return ebDAO.updateEventBoard(eb);
	}

	@Override
	public int deleteEventBoard(int ebNo) {
		return ebDAO.deleteEventBoard(ebNo);
	}

}
