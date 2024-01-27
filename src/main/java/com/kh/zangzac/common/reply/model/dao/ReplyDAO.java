package com.kh.zangzac.common.reply.model.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.kh.zangzac.common.model.vo.SelectCondition;
import com.kh.zangzac.common.reply.model.vo.Reply;

@Mapper
public interface ReplyDAO {
	ArrayList<Reply> selectReply(Reply sendReply);
	
	ArrayList<Reply> selectReply(SelectCondition b);

	int insertReply(Reply reply);

	int countReply(SelectCondition b);

	ArrayList<Reply> replyLimitList(SelectCondition b, RowBounds rowBounds);
	
	Reply selectReplyOne(Reply reply);

	int updateReply(Reply r);

	int deleteReply(Reply r);

}
