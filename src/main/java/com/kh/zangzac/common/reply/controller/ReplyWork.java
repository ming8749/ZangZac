package com.kh.zangzac.common.reply.controller;

import org.springframework.stereotype.Controller;

import com.kh.zangzac.common.model.vo.SelectCondition;
import com.kh.zangzac.common.reply.model.vo.Reply;

@Controller
public class ReplyWork {
	
	public SelectCondition changeReply(Reply r) {
		SelectCondition b = new SelectCondition();
		b.setBoardNo(r.getBoardNo());
		b.setBoardType(r.getBoardType());
		
		return b;
	}
	
}
