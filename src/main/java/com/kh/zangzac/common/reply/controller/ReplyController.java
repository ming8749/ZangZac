package com.kh.zangzac.common.reply.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.kh.zangzac.common.Pagination;
import com.kh.zangzac.common.model.vo.PageInfo;
import com.kh.zangzac.common.model.vo.SelectCondition;
import com.kh.zangzac.common.reply.model.service.ReplyService;
import com.kh.zangzac.common.reply.model.vo.Reply;
import com.kh.zangzac.ming.member.model.vo.Member;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@SessionAttributes("loginUser")
@Controller
public class ReplyController {
   @Autowired
   private ReplyService rService;
   
   @Autowired
   private ReplyWork work;
   @PostMapping("insertReply.rep")
   @ResponseBody
   public Map<String, Object> insertReply(@ModelAttribute("Reply") Reply reply,@RequestParam(value="page", defaultValue="1") int page, HttpSession session) {
      Member loginUser = (Member)session.getAttribute("loginUser");
      int maxPage = -1;
      
      if(loginUser != null) {
          reply.setMemberId(loginUser.getMemberId()); 
      }
       
      int result = rService.insertReply(reply);
      ArrayList<Reply> list = rService.selectReply(reply);
       Map<String, Object> map = new HashMap<>();
       
      if(result > 0) {
         maxPage = maxPage(reply);
      }
      
      map.put("list", list);
      map.put("maxPage", maxPage);
       return map;
   }

   public int countReply(SelectCondition b) {
      return rService.countReply(b);
   }
   
   private int maxPage(Reply r) {
      return Pagination.getReplyPageInfo(1, countReply(work.changeReply(r)), 10).getMaxPage();
   }
   
   @GetMapping("replyLimitList.rep")
   @ResponseBody
   public Map<String, Object> replyLimitList(@ModelAttribute("SelectCondition") SelectCondition b,@RequestParam(value="page", defaultValue="1") int page, HttpServletRequest request ) {
      int listCount = countReply(b);
      PageInfo pi = Pagination.getReplyPageInfo(page, listCount, 10);
      
      ArrayList<Reply> list = rService.replyLimitList(b, pi);

      Map<String, Object> map = new HashMap<>();
      map.put("list", list);
       map.put("rPi", pi);
       map.put("loc", request.getRequestURI());
       return map;
   }
   
   @PostMapping("updateReply.rep")
   @ResponseBody
   public int updateReply(@ModelAttribute Reply r){
      int result = rService.updateReply(r);
      int maxPage = -1;
      if(result > 0) {
         maxPage = maxPage(r);
      }
      
      return maxPage;
   }

   @PostMapping("deleteReply.rep")
   @ResponseBody
   public int deleteReply(@ModelAttribute Reply r) {
      int result = rService.deleteReply(r);
      int maxPage = -1;
      if(result > 0) {
         maxPage = maxPage(r);
      }
      return maxPage;
   }
}