package com.kh.zangzac.jaeyoung.campingFriend.model.service;

import java.util.ArrayList;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.zangzac.common.model.vo.PageInfo;
import com.kh.zangzac.jaeyoung.campingFriend.model.dao.CampingFriendDAO;
import com.kh.zangzac.jaeyoung.campingFriend.model.vo.CampingFriend;

@Service
public class CampingFriendServiceImpl  implements CampingFriendService{

	
	@Autowired
	CampingFriendDAO cDAO;
	
	@Override
	public int insertCf(CampingFriend cf) {
		
		return cDAO.insertCf(cf);
	}

	@Override
	public int listCount() {
		return cDAO.listCount();
	}

	@Override
	public ArrayList<CampingFriend> cfLimitList(PageInfo pi) {
		
		RowBounds rowBounds = new RowBounds((pi.getCurrentPage()-1 )* pi.getBoardLimit(), pi.getBoardLimit());
		return cDAO.cfLimitList(rowBounds);
	}

	@Override
	public CampingFriend selectCampingFriend(int boardNo) {
		return cDAO.selectCampingFriend(boardNo);
	}

	@Override
	public int updateCampingFriend(CampingFriend cf) {
		return cDAO.updateCampingFriend(cf);
	}

	@Override
	public int deleteCampingFriend(CampingFriend cf) {
		return cDAO.deleteCampingFriend(cf);
	}

}
