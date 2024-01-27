package com.kh.zangzac.jaeyoung.campingFriend.model.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.kh.zangzac.jaeyoung.campingFriend.model.vo.CampingFriend;

@Mapper
public interface CampingFriendDAO {

	public int insertCf(CampingFriend cf);

	public int listCount();

	public ArrayList<CampingFriend> cfLimitList(RowBounds rowBounds);

	public CampingFriend selectCampingFriend(int boardNo);

	public int updateCampingFriend(CampingFriend cf);

	public int deleteCampingFriend(CampingFriend cf);

}
