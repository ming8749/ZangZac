package com.kh.zangzac.jaeyoung.campingReview.model.service;

import java.util.ArrayList;

import com.kh.zangzac.common.model.vo.PageInfo;
import com.kh.zangzac.jaeyoung.campingReview.model.vo.CampingReview;
import com.kh.zangzac.seongun.campboard.model.vo.CampBoard;

public interface CampingReviewService {

	int insertCampingReview(CampingReview cr);

	ArrayList<CampingReview> selectList();

	int getListCount(String searchText);

	ArrayList<CampingReview> selectBoardList(PageInfo pi, String searchText);

	CampingReview selectDetailCr(int crNo);

	int updateCampingReview(CampingReview cr);

	int campingReviewDelete(int crNo);

}
