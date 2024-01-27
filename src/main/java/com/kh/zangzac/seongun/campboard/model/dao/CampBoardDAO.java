package com.kh.zangzac.seongun.campboard.model.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.kh.zangzac.common.photo.model.vo.Photo;
import com.kh.zangzac.seongun.campboard.model.vo.CampBoard;
import com.kh.zangzac.seongun.common.model.vo.SearchBoard;

@Mapper
public interface CampBoardDAO {

	int getListCount(CampBoard b);

	int insertCampBoard(CampBoard board);

	CampBoard selectBoard(int cbNo);

	int updateCount(int cbNo);

	int searchListCount(CampBoard b);
	
	ArrayList<CampBoard> searchBoardList(CampBoard b, RowBounds rowBounds);

	int deleteCampBoard(int cbNo);

	int updateCampBoard(CampBoard b);

	int listCount();

	ArrayList<CampBoard> popularList(RowBounds rowBounds);
}
