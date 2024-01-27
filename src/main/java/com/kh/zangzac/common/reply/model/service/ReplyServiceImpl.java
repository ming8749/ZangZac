package com.kh.zangzac.common.reply.model.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.zangzac.common.model.vo.PageInfo;
import com.kh.zangzac.common.model.vo.SelectCondition;
import com.kh.zangzac.common.reply.model.dao.ReplyDAO;
import com.kh.zangzac.common.reply.model.vo.Reply;

@Service
public class ReplyServiceImpl implements ReplyService {
	@Autowired
	private ReplyDAO rDAO;
	
	@Override
	public ArrayList<Reply> selectReply(Reply reply) {
		ArrayList<Reply> list = rDAO.selectReply(reply);
		
		for(Reply r : list) {
			format(r);
		}
		return list;
	}
	
	@Override
	public int insertReply(Reply reply) {
		return rDAO.insertReply(reply);
	}

	@Override
	public int countReply(SelectCondition b) {
		return rDAO.countReply(b);
	}

	@Override
	public ArrayList<Reply> selectReply(SelectCondition b) {
		
		ArrayList<Reply> list = rDAO.selectReply(b);
		
		for(Reply r : list) {
			format(r);
		}
		return list;
	}

	@Override
	public ArrayList<Reply> replyLimitList(SelectCondition b, PageInfo pi) {
		RowBounds rowBounds = new RowBounds((pi.getCurrentPage()-1 )* pi.getBoardLimit(), pi.getBoardLimit());
		ArrayList<Reply> list = rDAO.replyLimitList(b, rowBounds);
		for(Reply r : list) {
			format(r);
		}
		return list;
	}
	
	
	
    public void format(Reply r) {
    	String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";
    	String DATE = "yyyy-MM-dd HH:mm:ss";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
        DateTimeFormatter format = DateTimeFormatter.ofPattern(DATE);
        String create = r.getReplyCreateDate().format(formatter);
        String modify = r.getReplyModifyDate().format(formatter);
        
        if(r.getReplyCreateDate().format(format).equals(r.getReplyModifyDate().format(format))) {
        	r.setFormatDate(create);
        }else {
        	r.setFormatDate("(수정)"+modify);
        }
    }
	
	
	

	@Override
	public int updateReply(Reply r) {
		return rDAO.updateReply(r);
	}
	
	@Override
	public Reply selectReplyOne(Reply reply) {
		Reply r = rDAO.selectReplyOne(reply);
		format(r);
		return r;
	}

	@Override
	public int deleteReply(Reply r) {
		return rDAO.deleteReply(r);
	}
}
