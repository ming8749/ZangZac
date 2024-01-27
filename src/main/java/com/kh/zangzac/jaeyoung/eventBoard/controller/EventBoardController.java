package com.kh.zangzac.jaeyoung.eventBoard.controller;

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
import org.springframework.web.multipart.MultipartFile;

import com.kh.zangzac.common.ImageStorage;
import com.kh.zangzac.common.Pagination;
import com.kh.zangzac.common.model.vo.PageInfo;
import com.kh.zangzac.common.model.vo.SelectCondition;
import com.kh.zangzac.common.photo.model.service.PhotoService;
import com.kh.zangzac.common.photo.model.vo.Photo;
import com.kh.zangzac.common.reply.model.vo.Reply;
import com.kh.zangzac.jaeyoung.eventBoard.model.service.EventBoardService;
import com.kh.zangzac.jaeyoung.eventBoard.model.vo.EventBoard;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class EventBoardController {
	
	@Autowired
	EventBoardService ebService;
	
	@Autowired
	PhotoService pService;
	
	private final ImageStorage imageStorage;
	
	@Autowired
    public EventBoardController(ImageStorage imageStorage) {
        this.imageStorage = imageStorage;
    }
	
	@GetMapping("eventBoardListView.jy")
	public String eventBoardListView(@RequestParam(value="page", defaultValue="1") int page,Model model,HttpServletRequest request) {
		
		int listCount = ebService.getListCount();
		
		int currentPage = page;
		PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 4);
		ArrayList<EventBoard> list = ebService.selectEventBoardList(pi);
		
		for(EventBoard eb : list) { // 사진 찾아 넣기
			SelectCondition sc = new SelectCondition();
		      sc.setBoardNo(eb.getEbNo());
		      sc.setBoardType(9);
		      ArrayList<Photo> pList = pService.selectBoardPhoto(sc);
		      eb.setPhoto(pList.get(0)); //썸네일 넣기
			
		}
		
		model.addAttribute("loc", request.getRequestURI());
		model.addAttribute("list", list);
		model.addAttribute("pi", pi);
		
		
		return "views/jaeyoung/champingEvent"; 
	}
	
	
	@GetMapping("eventBoardWriteView.jy")
	public String eventBoardWriteView() {
		
		return "views/jaeyoung/champingEventWrite"; 
	}
	
	
	@PostMapping("eventBoardWrite.jy")
	public String eventBoardWrite(@ModelAttribute EventBoard eb,@RequestParam("file") MultipartFile file) {
		
		int result = ebService.insertEventBoard(eb);
		
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
            a.setBoardNo(eb.getEbNo()); 
            a.setBoardType(9);
            list.add(a);
            //DB에 사진 저장
            pService.insertPhotoCampBoard(list);
        }
		
        return "redirect:eventBoardListView.jy";  
	}

	
	
	@GetMapping(value = "/selectEventBoard.jy", produces = "application/json")
	@ResponseBody
	public Map<String, Object> insertReply(@RequestParam("ebNo") int ebNo) {
		  
		  EventBoard eb = ebService.selectEventBoard(ebNo);
		  
		  SelectCondition sc = new SelectCondition();
	      sc.setBoardNo(eb.getEbNo());
	      sc.setBoardType(9);
	      ArrayList<Photo> pList = pService.selectBoardPhoto(sc);
	      eb.setPhoto(pList.get(0)); //썸네일 넣기
		  
	      Map<String, Object> map = new HashMap<>();
	      
	      map.put("ebNo", eb.getEbNo());
	      map.put("ebLocation", eb.getEbLocation());
	      map.put("ebName", eb.getEbName());
	      map.put("ebIntroduce", eb.getEbIntroduce());
	      map.put("ebPrice", eb.getEbPrice());
	      map.put("ebTime", eb.getEbTime());
	      map.put("ebDate", eb.getEbDate());
	      map.put("ebInformation", eb.getEbInformation());
	      map.put("ebScore", eb.getEbScore());
	      map.put("ebHost", eb.getEbHost());
	      map.put("ebPhoto", eb.getPhoto().getPhotoPath());
	      
	       return map;
	  }
	
	
	@GetMapping("eventBoardEditView.jy")
	public String eventBoardEditView(@RequestParam("ebNo") int ebNo,Model model) {
		
		 EventBoard eb = ebService.selectEventBoard(ebNo);
		  
		  SelectCondition sc = new SelectCondition();
	      sc.setBoardNo(eb.getEbNo());
	      sc.setBoardType(9);
	      ArrayList<Photo> pList = pService.selectBoardPhoto(sc);
	      eb.setPhoto(pList.get(0)); //썸네일 넣기
	      
	      System.out.println(eb);
	      model.addAttribute("eb", eb);
		
		return "views/jaeyoung/champingEventEdit"; 
	}
	
	@PostMapping("eventBoardEdit.jy")
	public String eventBoardEdit(@ModelAttribute EventBoard eb,@RequestParam("file") MultipartFile file,@RequestParam("preImage") String preImage) {
		
		int result = ebService.updateEventBoard(eb);
		String checkDelete =preImage.split("#")[1]; 
		String imgName=preImage.split("#")[0];
		int result2=0;
		
		//삭제된 경우
		if(checkDelete.equals("2")) {
			//사진 삭제 진행
			
			//삭제가 된경우
			//구글클라우드에서 삭제
			imageStorage.deleteImage(imgName, "jaeyoung");
			//DB에서 삭제
			result2= pService.deletePhotoName(imgName);
			
			
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
	            a.setBoardNo(eb.getEbNo()); 
	            a.setBoardType(9);
	            list.add(a);
	            //DB에 사진 저장
	            pService.insertPhotoCampBoard(list);
	        }
		}
		
		return "redirect:eventBoardListView.jy"; 
	}

	
	
	@GetMapping("eventBoardDelete.jy")
	public String eventBoardDelete(@RequestParam("ebNo") int ebNo) {
		
		 int result = ebService.deleteEventBoard(ebNo);
	     return "redirect:eventBoardListView.jy"; 
	}
	
	

}
