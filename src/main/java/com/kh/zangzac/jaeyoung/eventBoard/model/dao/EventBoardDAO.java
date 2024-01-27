package com.kh.zangzac.jaeyoung.eventBoard.model.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.kh.zangzac.jaeyoung.eventBoard.model.vo.EventBoard;

@Mapper
public interface EventBoardDAO {

	int insertEventBoard(EventBoard eb);

	int getListCount();

	ArrayList<EventBoard> selectEventBoardList(RowBounds rowBounds);

	EventBoard selectEventBoard(int ebNo);

	int updateEventBoard(EventBoard eb);

	int deleteEventBoard(int ebNo);

}
