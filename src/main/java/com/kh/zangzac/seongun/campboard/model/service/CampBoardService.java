package com.kh.zangzac.seongun.campboard.model.service;

import java.util.ArrayList;

import com.kh.zangzac.common.model.vo.PageInfo;
import com.kh.zangzac.common.model.vo.SelectCondition;
import com.kh.zangzac.common.photo.model.vo.Photo;
import com.kh.zangzac.common.reply.model.vo.Reply;
import com.kh.zangzac.seongun.campboard.model.vo.CampBoard;
import com.kh.zangzac.seongun.common.model.vo.SearchBoard;

public interface CampBoardService {

	int getListCount(CampBoard b);

	int insertCampBoard(CampBoard board);

	CampBoard selectBoard(int cbNo, String id);

	int searchListCount(CampBoard b);

	ArrayList<CampBoard> searchBoardList(PageInfo pi, CampBoard b);

	int deleteCampBoard(int cbNo);

	int updateCampBoard(CampBoard b);

	int listCount();

	ArrayList<CampBoard> popularList(PageInfo cbPi);
}
