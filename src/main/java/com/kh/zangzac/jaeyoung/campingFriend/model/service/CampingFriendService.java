package com.kh.zangzac.jaeyoung.campingFriend.model.service;

import java.util.ArrayList;

import com.kh.zangzac.common.model.vo.PageInfo;
import com.kh.zangzac.jaeyoung.campingFriend.model.vo.CampingFriend;

public interface CampingFriendService {

	int insertCf(CampingFriend cf);

	int listCount();

	ArrayList<CampingFriend> cfLimitList(PageInfo pi);

	CampingFriend selectCampingFriend(int boardNo);

	int updateCampingFriend(CampingFriend cf);

	int deleteCampingFriend(CampingFriend cf);

}
