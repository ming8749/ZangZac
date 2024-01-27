package com.kh.zangzac.jaeyoung.campingFriend.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.kh.zangzac.common.ImageStorage;
import com.kh.zangzac.common.Pagination;
import com.kh.zangzac.common.heart.model.service.HeartService;
import com.kh.zangzac.common.heart.model.vo.Heart;
import com.kh.zangzac.common.model.vo.PageInfo;
import com.kh.zangzac.common.model.vo.SelectCondition;
import com.kh.zangzac.common.photo.model.service.PhotoService;
import com.kh.zangzac.common.photo.model.vo.Photo;
import com.kh.zangzac.common.reply.model.service.ReplyService;
import com.kh.zangzac.common.reply.model.vo.Reply;
import com.kh.zangzac.jaeyoung.campingFriend.model.service.CampingFriendService;
import com.kh.zangzac.jaeyoung.campingFriend.model.vo.CampingFriend;
import com.kh.zangzac.ming.member.model.service.MemberService;
import com.kh.zangzac.ming.member.model.vo.Member;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@SessionAttributes("loginUser")
@Controller
public class FriendController {
	
	private final ImageStorage imageStorage;
	
	@Autowired
	PhotoService pService;
	
	@Autowired
	CampingFriendService cService;
	
	@Autowired
	ReplyService rService;
	
	@Autowired
	HeartService hService;
	
	@Autowired
	MemberService mService;

    @Autowired
    public FriendController(ImageStorage imageStorage) {
        this.imageStorage = imageStorage;
    }

	@GetMapping("champingFriend.jy")
    public String champingFriendPage(Model model,HttpSession session) {
		
		//로그인 정보 가져오기
		Member loginUser = (Member)session.getAttribute("loginUser");
		
		 // 전체 숫자
	      int listCount = cService.listCount();
	      PageInfo pi = Pagination.getPageInfo(1, listCount, 3);
	      
	      //전체 리스트 가져오기~~
	      ArrayList<CampingFriend> list = cService.cfLimitList(pi);
	      

	      for(CampingFriend cf : list) {
	    	  
	    	  Member temp = new Member();
	    	  temp.setMemberId(cf.getMemberId());
	    	  //프로필사진 가져오기
	    	  cf.setMemberProfile(mService.login(temp).getMemberProfilePath());
	    	  
		      //전체 리스트의 댓글들 저장하기
		      SelectCondition sc = new SelectCondition();
		      sc.setBoardNo(cf.getCfNo());
		      sc.setBoardType(7);
		      ArrayList<Reply> replyList = rService.selectReply(sc); 
		      cf.setReplys(replyList);
		      cf.setReplyCount(replyList.size());
		      
		      Heart h = new Heart();
	    	  h.setBoardNo(cf.getCfNo());
	    	  h.setBoardType(7);
		      cf.setLikeCount(hService.countHeart(h));
		      
		      //하트 세팅하기
		      if(loginUser != null) {// 유저가 있을때만 셋팅
		    	  // 하트리스트 가져와서 셋팅..?
		    	  // 보드 번호도 같고 아이디도 같은경우에만 셋팅 해야함
		    	  h.setMemberId(loginUser.getMemberId());
		    	  // 좋아요 있나 없나 검사
		    	  Heart heart = hService.selectHeart(h);
		    	  if(heart == null) {// 좋아요가 없으면
		    		  cf.setLikeStatus("<img src=\"image/seongun/offheart.png\" alt=\"좋아요\" class=\"like-button\" onclick=\"toggleLike(event)\">");
		    	  }else { // 좋아요가 있으면
		    		  cf.setLikeStatus("<img src=\"image/seongun/onheart.png\" alt=\"좋아요\" class=\"like-button clicked\" onclick=\"toggleLike(event)\">");
		    	  }
		      }else {
		    	  cf.setLikeStatus("<img src=\"image/seongun/offheart.png\" alt=\"좋아요\" class=\"like-button\" onclick=\"toggleLike(event)\">");
		      }
		      
		      
	      }
	      

	      
	      
	      model.addAttribute("list", list);
	      model.addAttribute("maxPage", pi.getMaxPage());
	      
    	return "views/jaeyoung/champingFriendPage";
    }
	
	
   @GetMapping("cfList.jy")
   @ResponseBody
   public Map<String, Object> replyLimitList(@RequestParam(value="currentPage", defaultValue="1") int page, HttpServletRequest request,HttpSession session) {
	  // 전체 숫자
      int listCount = cService.listCount();
      PageInfo pi = Pagination.getPageInfo(page, listCount, 3);
      
      //로그인 정보 가져오기
       Member loginUser = (Member)session.getAttribute("loginUser");
       
      //전체 리스트 가져오기
      ArrayList<CampingFriend> list = cService.cfLimitList(pi);
      
      
      //전체 리스트의 댓글들 저장하기
      for(CampingFriend cf : list) {
    	  
    	  Member temp = new Member();
    	  temp.setMemberId(cf.getMemberId());
    	  //프로필사진 가져오기
    	  cf.setMemberProfile(mService.login(temp).getMemberProfilePath());
    	  
	      SelectCondition sc = new SelectCondition();
	      sc.setBoardNo(cf.getCfNo());
	      sc.setBoardType(7);
	      ArrayList<Reply> replyList = rService.selectReply(sc); 
	      cf.setReplys(replyList);
	      cf.setReplyCount(replyList.size());
	      
	      Heart h = new Heart();
    	  h.setBoardNo(cf.getCfNo());
    	  h.setBoardType(7);
	      cf.setLikeCount(hService.countHeart(h));
	      
	      //하트 세팅하기
	      if(loginUser != null) {// 유저가 있을때만 셋팅
	    	  // 하트리스트 가져와서 셋팅..?
	    	  // 보드 번호도 같고 아이디도 같은경우에만 셋팅 해야함
	    	  
	    	  h.setMemberId(loginUser.getMemberId());
	    	  // 좋아요 있나 없나 검사
	    	  Heart heart = hService.selectHeart(h);
	    	  if(heart == null) {// 좋아요가 없으면
	    		  cf.setLikeStatus("<img src=\"image/seongun/offheart.png\" alt=\"좋아요\" class=\"like-button\" onclick=\"toggleLike(event)\">");
	    	  }else { // 좋아요가 있으면
	    		  cf.setLikeStatus("<img src=\"image/seongun/onheart.png\" alt=\"좋아요\" class=\"like-button clicked\" onclick=\"toggleLike(event)\">");
	    	  }
	      }else {
	    	  cf.setLikeStatus("<img src=\"image/seongun/offheart.png\" alt=\"좋아요\" class=\"like-button\" onclick=\"toggleLike(event)\">");
	      }
	      
      }
      
      
      Map<String, Object> map = new HashMap<>();
      
      
       map.put("list", list);
       map.put("Pi", pi);
       map.put("loc", request.getRequestURI());
       
       return map;
   }
	
	
	
	
	
	@GetMapping("campingFriendWriteView.jy")
	public String campingFriendWriteView() {
		
		return "views/jaeyoung/champingFriendWrite";
		
	}
	
	@PostMapping("champingFriendWrite.jy")
	public String champingFriendWrite(@ModelAttribute CampingFriend cf,@RequestParam("file")MultipartFile file) {
		
		
		//파일 넣기 
		int result = cService.insertCf(cf);
		
		//사진 넣기
        String name = "jaeyoung";
        String[] returnArr = imageStorage.saveImage(file, name);
        Photo a = new Photo();
        ArrayList<Photo> list = new ArrayList<Photo>();
        
        //구글 클라우드에 사진 저장
        if (returnArr != null) {
        	a.setPhotoRename(returnArr[0]);
            a.setPhotoPath(returnArr[1]);
            a.setPhotoLevel(0);
            a.setBoardNo(cf.getCfNo()); 
            a.setBoardType(7);
            list.add(a);
            //DB에 사진 저장
            pService.insertPhotoCampBoard(list);
        }
        
	    
		return "redirect:champingFriend.jy";
		
	}
	
	
	   @GetMapping(value = "/insertReply.jy", produces = "application/json")
	   @ResponseBody
	   public Map<String, Object> insertReply(@ModelAttribute Reply reply) {
		  
		  reply.setBoardType(7);
		  int result = rService.insertReply(reply);
		  
		  Reply r = rService.selectReplyOne(reply);
		  
	      Map<String, Object> map = new HashMap<>();
	      
	      if(result >0) {
	    	  map.put("data", r);
	      }else {
	    	  map.put("data", "no");
	      }
	      
	       return map;
	   }
	   
	   @GetMapping(value = "/updateReply.jy", produces = "application/json")
	   @ResponseBody
	   public Map<String, Object> updateReply(@ModelAttribute Reply reply) {
		  
		  int result = rService.updateReply(reply);
		  
		  Reply r = rService.selectReplyOne(reply);
		  
		  Map<String, Object> map = new HashMap<>();
	      
	      if(result >0) {
	    	  map.put("data", r);
	      }else {
	    	  map.put("data", "no");
	      }
	      
	       return map;
	   }
	   
	   @GetMapping(value = "/deleteReply.jy")
	   @ResponseBody
	   public String deleteReply(@ModelAttribute Reply reply) {
		  
		  int result = rService.deleteReply(reply);
		  
		  String answer ="";
		  
		  if(result>0) {
			  answer="yes";
		  }else {
			  answer="no";
		  }
	       return answer;
	   }
	   
	   @GetMapping(value = "/updateLike.jy")
	   @ResponseBody
	   public String updateLike(@ModelAttribute Heart heart,@RequestParam("changeLike") int changeLike) {
		  
		   
		   int result = 1;
		   heart.setBoardType(7);
		   if(changeLike == 1) { //좋아요 등록
			  result= hService.insertHeart(heart);
		   }else if(changeLike==2){ //좋아요 취소
			  result= hService.deleteHeart(heart);
		   }
		  String answer ="";
		  
		  if(result>0) {
			  answer="yes";
		  }else {
			  answer="no";
		  }
	       return answer;
	   }
	   
	   
	   @GetMapping("campingFriendEditView.jy")
		public String campingFriendEditView(@RequestParam("boardNo") int boardNo,Model model) {
		   
		   //보드 정보 가져와서 넘기기
		   CampingFriend cf = cService.selectCampingFriend(boardNo);
		   
		   model.addAttribute("cf",cf);
		   
		   return "views/jaeyoung/champingFriendEdit";
			
		}
	   
	   @PostMapping("campingFriendEdit.jy")
	   public String campingFriendEdit(@ModelAttribute CampingFriend cf,@RequestParam("file")MultipartFile file,@RequestParam("preImage")String preImage) {
		   
		   int result =0;
		   //전 사진이 있는 경우만 
		   if(preImage !=null) {
			   String[] segments = preImage.split("/");
			   String imagePath = segments[segments.length - 1];
			   String imageName = imagePath.split("@")[0];
			   String checkDelete = imagePath.split("@")[1];
			   
			   
			   //삭제 한다면
			   if(checkDelete.equals("2")) {
				   
				   //구글에서 파일 삭제
				   imageStorage.deleteImage(cf.getPhotoRename(), "jaeyoung");
				   
				   //DB에서 삭제
				   Photo temp = new Photo();
				   temp.setBoardNo(cf.getCfNo());
				   temp.setBoardType(7);
				   
				   result = pService.deletePhoto(temp);
				   
			   }
		   }
		   
		   //사진 넣기
	        String name = "jaeyoung";
	        String[] returnArr = imageStorage.saveImage(file, name);
	        Photo a = new Photo();
	        ArrayList<Photo> list = new ArrayList<Photo>();
	        
	        //구글 클라우드에 사진 저장
	        if (returnArr != null) {
	        	a.setPhotoRename(returnArr[0]);
	            a.setPhotoPath(returnArr[1]);
	            a.setPhotoLevel(0);
	            a.setBoardNo(cf.getCfNo()); 
	            a.setBoardType(7);
	            list.add(a);
	            //DB에 사진 저장
	            pService.insertPhotoCampBoard(list);
	        }
	        
	        //글 내용 수정
	        int result2 = cService.updateCampingFriend(cf);
		   
		   
		   if(result +result2 > 0) {
			   return "redirect:champingFriend.jy";
		   }
		   
		   
		   return "redirect:home.do";
	   }
	   
	   
	   @GetMapping("campingFriendDelete.jy")
	   public String campingFriendDelete(@ModelAttribute CampingFriend cf) {
		   int result = cService.deleteCampingFriend(cf);
		   
		   if(result  > 0) {
			   return "redirect:champingFriend.jy";
		   }
		   
		   return "redirect:home.do";
	   }
	
}
