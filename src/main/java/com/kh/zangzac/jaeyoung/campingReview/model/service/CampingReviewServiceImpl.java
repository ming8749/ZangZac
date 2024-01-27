package com.kh.zangzac.jaeyoung.campingReview.model.service;

import java.util.ArrayList;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.zangzac.common.model.vo.PageInfo;
import com.kh.zangzac.jaeyoung.campingReview.model.dao.CampingReviewDAO;
import com.kh.zangzac.jaeyoung.campingReview.model.vo.CampingReview;

@Service
public class CampingReviewServiceImpl implements CampingReviewService{

	@Autowired
	CampingReviewDAO crDAO;
	
	@Override
	public int insertCampingReview(CampingReview cr) {
		return crDAO.insertCampingReview(cr);
	}

	@Override
	public ArrayList<CampingReview> selectList() {
		return crDAO.selectList();
	}

	@Override
	public int getListCount(String searchText) {
		return crDAO.getListCount(searchText);
	}

	@Override
	public ArrayList<CampingReview> selectBoardList(PageInfo pi, String searchText) {
		RowBounds rowBounds = new RowBounds((pi.getCurrentPage()-1 )* pi.getBoardLimit(), pi.getBoardLimit());
		return  crDAO.selectBoardList(rowBounds,searchText);
	}

	@Override
	public CampingReview selectDetailCr(int crNo) {
		CampingReview cr = crDAO.selectDetailCr(crNo);
		crDAO.updateCount(crNo);
		
		cr.setCrCount(cr.getCrCount()+1);
		
		return cr;
	}

	@Override
	public int updateCampingReview(CampingReview cr) {
		return crDAO.updateCampingReview(cr);
	}

	@Override
	public int campingReviewDelete(int crNo) {
		return crDAO.campingReviewDelete(crNo);
	}

}
