package com.kh.zangzac.seongun.campboard.model.service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.zangzac.common.model.vo.PageInfo;
import com.kh.zangzac.common.model.vo.SelectCondition;
import com.kh.zangzac.common.photo.model.vo.Photo;
import com.kh.zangzac.common.reply.model.dao.ReplyDAO;
import com.kh.zangzac.common.reply.model.vo.Reply;
import com.kh.zangzac.seongun.campboard.model.dao.CampBoardDAO;
import com.kh.zangzac.seongun.campboard.model.vo.CampBoard;
import com.kh.zangzac.seongun.common.model.vo.SearchBoard;

@Service
public class CampBoardServiceImpl implements CampBoardService{
	@Autowired
	private CampBoardDAO cDAO;
	
	@Override
	public int getListCount(CampBoard b) {
		return cDAO.getListCount(b);
	}

	@Override
	public int insertCampBoard(CampBoard board) {
		return cDAO.insertCampBoard(board);
	}

	@Override
	public CampBoard selectBoard(int cbNo, String id) {
		CampBoard b = cDAO.selectBoard(cbNo);
		format(b);
	      if(b != null) {
	    	  if(id != null && !b.getMemberId().equals(id)) {
	    		  int result = cDAO.updateCount(cbNo);
	    		  if(result > 0) {
	    			  b.setCbCount(b.getCbCount() + 1);
	    		  }
	         }
	      } 
		return b;
	}

	@Override
	public int searchListCount(CampBoard b) {
		return cDAO.searchListCount(b);
	}

	@Override
	public ArrayList<CampBoard> searchBoardList(PageInfo pi, CampBoard b){
		RowBounds rowBounds = new RowBounds((pi.getCurrentPage()-1 )* pi.getBoardLimit(), pi.getBoardLimit());
		ArrayList<CampBoard> list = cDAO.searchBoardList(b, rowBounds);
		for(CampBoard board : list) {
			listFormat(board);
		}
		return list;
	}

	@Override
	public int deleteCampBoard(int cbNo) {
		return cDAO.deleteCampBoard(cbNo);
	}
	
	@Override
	public int updateCampBoard(CampBoard b) {
		return cDAO.updateCampBoard(b);
	}
	
	public void format(CampBoard b) {
		String DATE_TIME_FORMAT = "YYYY MM-dd";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
        String create = b.getCbCreateDate().format(formatter);
        String modify = b.getCbModifyDate().format(formatter);
        
        if(create.equals(modify)) {
        	b.setFormatDate(create);
        }else {
        	b.setFormatDate("(수정)"+modify);
        }
    }
	
	public void listFormat(CampBoard b) {
		String DATE_TIME_FORMAT = "YYYY MM-dd";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
        String create = b.getCbCreateDate().format(formatter);
        String modify = b.getCbModifyDate().format(formatter);
        
        if(create.equals(modify)) {
        	b.setFormatDate(create);
        }else {
        	b.setFormatDate(create);
        }
    }

	@Override
	public int listCount() {
		return cDAO.listCount();
	}

	@Override
	public ArrayList<CampBoard> popularList(PageInfo pi) {
		RowBounds rowBounds = new RowBounds((pi.getCurrentPage()-1 )* pi.getBoardLimit(), pi.getBoardLimit());
		ArrayList<CampBoard> list = cDAO.popularList(rowBounds);
		return list;
	}
}
