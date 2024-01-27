package com.kh.zangzac.common.reply.model.service;

import java.util.ArrayList;

import com.kh.zangzac.common.model.vo.PageInfo;
import com.kh.zangzac.common.model.vo.SelectCondition;
import com.kh.zangzac.common.reply.model.vo.Reply;

public interface ReplyService {
	int insertReply(Reply reply);
	
	ArrayList<Reply> selectReply(Reply reply);

	int countReply(SelectCondition b);

	ArrayList<Reply> selectReply(SelectCondition b);

	ArrayList<Reply> replyLimitList(SelectCondition b, PageInfo pi);
	
	Reply selectReplyOne(Reply reply);

	int updateReply(Reply r);

	int deleteReply(Reply r);

}
