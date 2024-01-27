package com.kh.zangzac.jaeyoung.campingReview.model.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.kh.zangzac.jaeyoung.campingReview.model.vo.CampingReview;

@Mapper
public interface CampingReviewDAO {

	int insertCampingReview(CampingReview cr);

	ArrayList<CampingReview> selectList();

	int getListCount(String searchText);

	ArrayList<CampingReview> selectBoardList(RowBounds rowBounds, String searchText);

	CampingReview selectDetailCr(int crNo);

	int updateCampingReview(CampingReview cr);

	int campingReviewDelete(int crNo);

	void updateCount(int crNo);

}
