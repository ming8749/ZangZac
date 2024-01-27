package com.kh.zangzac.seongun.campboard.controller;

import java.util.ArrayList;

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
import com.kh.zangzac.common.controller.BoardCondition;
import com.kh.zangzac.common.heart.model.service.HeartService;
import com.kh.zangzac.common.heart.model.vo.Heart;
import com.kh.zangzac.common.model.vo.PageInfo;
import com.kh.zangzac.common.model.vo.SelectCondition;
import com.kh.zangzac.common.photo.model.service.PhotoService;
import com.kh.zangzac.common.photo.model.vo.Photo;
import com.kh.zangzac.ming.member.model.vo.Member;
import com.kh.zangzac.seongun.campboard.model.service.CampBoardService;
import com.kh.zangzac.seongun.campboard.model.vo.CampBoard;
import com.kh.zangzac.seongun.common.WorkController;
import com.kh.zangzac.seongun.common.model.vo.SearchBoard;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@SessionAttributes("loginUser")
@Controller
public class CampBoardController {
	
	private final ImageStorage imageStorage;
	
	@Autowired
	public CampBoardController(ImageStorage imageStorage) {
		this.imageStorage = imageStorage;
	}
	
	
	@Autowired
	private CampBoardService cService;
	
	@Autowired 
	private PhotoService pService;
    
	@Autowired
	private WorkController sWork;
	
	@Autowired
	private BoardCondition boardSet;
	
	@Autowired
	private HeartService hService;
	
	@GetMapping("campBoard.su")
	public String campBoardListView(@RequestParam(value="page", defaultValue="1") int page, @ModelAttribute CampBoard b, Model model, HttpServletRequest request) {
	    String msg = null;
	    String text = b.getSearchText();
	    if(text != null && !text.trim().equals("")) {
	    	b.setSearchText("%" + b.getSearchText() + "%");
	    }
	    
	    int currentPage = page;
	    int listCount = cService.searchListCount(b);
	    PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 15);
	    
	    ArrayList<CampBoard> list = cService.searchBoardList(pi, b);
	    
	    if(text != null && !text.trim().equals("")) {
	    	msg = list.isEmpty() ? "검색된 게시판이 없습니다!" : null;
	    	if(b.getSearchText().charAt(0) == '%' && text.trim().equals("")) {
	    		b.setSearchText("");
	    	}
	    }
	    sWork.searchModel(model, pi, list, msg, b, request.getRequestURI());
	    return "views/seongun/campboard/listBoard";
	}

	
	@GetMapping("cardBoard.su")
	public String campBoardCardView(@RequestParam(value="page", defaultValue="1") int page, @ModelAttribute CampBoard b, Model model, HttpServletRequest request) {
	    String msg = null;
	    String text = b.getSearchText();
	    if(text != null && !text.trim().equals("")) {
	    	b.setSearchText("%" + b.getSearchText() + "%");
	    }
	    
	    int currentPage = page;
	    int listCount = cService.searchListCount(b);
	    PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 10);
	    

	    ArrayList<CampBoard> list = cService.searchBoardList(pi, b);
	    
	    if(text != null && !text.trim().equals("")) {
	    	msg = list.isEmpty() ? "검색된 게시판이 없습니다!" : null;
	    	if(b.getSearchText().charAt(0) == '%' && text.trim().equals("")) {
	    		b.setSearchText("");
	    	}
	    }
	    sWork.searchModel(model, pi, list, msg, b, request.getRequestURI());
	    return "views/seongun/campboard/cardBoard";
	}
	
	@GetMapping("albumBoard.su")
	public String campBoardAlbumView(@RequestParam(value="page", defaultValue="1") int page, @ModelAttribute CampBoard b, Model model, HttpServletRequest request) {
	    String msg = null;
	    String text = b.getSearchText();
	    if(text != null && !text.trim().equals("")) {
	    	b.setSearchText("%" + b.getSearchText() + "%");
	    }
	    
	    int currentPage = page;
	    int listCount = cService.searchListCount(b);
	    PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 20);
	    
	    ArrayList<CampBoard> list = cService.searchBoardList(pi, b);
	    
	    if(text != null && !text.trim().equals("")) {
	    	msg = list.isEmpty() ? "검색된 게시판이 없습니다!" : null;
	    	if(b.getSearchText().charAt(0) == '%' && text.trim().equals("")) {
	    		b.setSearchText("");
	    	}
	    }
	    sWork.searchModel(model, pi, list, msg, b, request.getRequestURI());
	    return "views/seongun/campboard/albumBoard";
	}
	
	//캠핑 게시판 조회
	@GetMapping("selectBoard.su")
	public String campBoardView(@RequestParam("cbNo") int cbNo, @RequestParam(value="page", defaultValue="1") int page, HttpSession session,Model model) {
		Member loginUser = (Member)session.getAttribute("loginUser");
		SelectCondition b = boardSet.selectBoard(cbNo, 1);
		String id = null;
		if(loginUser != null) {
			id = loginUser.getMemberId(); 
		}
		
	    CampBoard bList = cService.selectBoard(cbNo, id);
	    
	    if(id != null) {
		    Heart heart = hService.selectHeart(sWork.addHeart(cbNo, id, 1));
		    if(heart != null) {
		    	bList.setHeartCheck(true);
		    }
	    }
	    
	    ArrayList<Photo> pList = pService.selectBoardPhoto(b);
	    int maxPage = sWork.countReply(cbNo, 1);
	    
	    if(bList != null) {
			sWork.BoardDetail(model,bList, maxPage, pList, page);
			return "views/seongun/campboard/boardDetail";
		}else {
			return "redirect:/campBoard.su";
		}
	}
	
	@PostMapping("insertCampBoard.su")
	public String insertCampBoard(@ModelAttribute CampBoard board, @RequestParam("file") ArrayList<MultipartFile> files,HttpServletRequest request, Model model) {
		board.setMemberId(((Member)request.getSession().getAttribute("loginUser")).getMemberId());
		int resultB = 0;
		int resultA = 0;
		ArrayList<Photo> fileList = new ArrayList<>();
		
		for(int i=0; i<files.size(); i++) {
			MultipartFile upload = files.get(i); //파일 하나씩 뽑아오기.
			String[] returnArr = imageStorage.saveImage(upload, "seongun");
			
			if(returnArr != null) {
				Photo a = sWork.haventMain(returnArr, i, 1);
				fileList.add(a);
			}
			
		}
		if(fileList.isEmpty()) {
			resultB = cService.insertCampBoard(board);
		}else {
			resultB = cService.insertCampBoard(board);
			for(Photo a : fileList) {
				a.setBoardNo(board.getCbNo());
			}
			resultA = pService.insertPhotoCampBoard(fileList);
		}
		
		if(fileList.isEmpty()) {
			if(resultB > 0) {
				return "redirect:/campBoard.su";
			}else {
				model.addAttribute("msg", "게시글 작성에 실패했습니다.재작성 부탁드립니다.");
				return "views/seongun/campboard/writeBoard";
			}
		}else {
			if(resultA > 0 && resultB > 0) {
				return "redirect:/campBoard.su";
			}else if(resultA > 0 && resultB <= 0){
				model.addAttribute("msg", "이미지 업로드를 실패했습니다. 이미지 없이 게시판이 작성되었습니다!");
				return "redirect:/campBoard.su";
			}else {
				model.addAttribute("msg", "게시글 작성에 실패했습니다.재작성 부탁드립니다..");
				return "views/seongun/campboard/writeBoard";
			}
		}
	}
	
	@GetMapping("writeCampBoard.su")
	public String writeCampBoard() {
		return "views/seongun/campboard/writeBoard";
	}
	
	@PostMapping("deleteCampBoard.su")
	@ResponseBody
	public String deleteBoard(@RequestParam("cbNo") int cbNo,Model model) {
		int result = cService.deleteCampBoard(cbNo);
		
		String msg = result > 0 ? "게시판을 삭제했습니다." : null;
		
		sWork.deleteModel(model, msg);
		return msg;
	}
	
	@GetMapping("updateBoardPage.su")
	public String updateBoardPage(@RequestParam("cbNo") int cbNo,Model model) {
		CampBoard bList = cService.selectBoard(cbNo, null);
		ArrayList<Photo> pList = pService.selectBoardPhoto(sWork.selectBoard(cbNo, 1));
		
		sWork.editModel(bList, pList, model);
		return "views/seongun/campboard/editBoard";
	}
	
	@PostMapping("updateCampBoard.su")
	public String updateCbBoard(@RequestParam("deletePhoto")String[] deleteFile, @ModelAttribute CampBoard b, @RequestParam("file") ArrayList<MultipartFile> files,HttpServletRequest request, Model model) {
		int resultB = 0;
		int resultA = 0;
		int x = 0;
		ArrayList<Photo> fileList = new ArrayList<>();
		
		//썸네일이 변경되었을때
		if((deleteFile[0].split("#")[1]).equals("isdel")) {
			for(int i=0; i < deleteFile.length; i++) {
				if(deleteFile[i].split("#")[1].equals("isdel")) {
					imageStorage.deleteImage(deleteFile[i].split("#")[0], "seongun");
					pService.deletePhotoName(deleteFile[i].split("#")[0]);
				}
			}
			
			//파일 추가
			for(int i=0; i<files.size(); i++) {
				MultipartFile upload = files.get(i); //파일 하나씩 뽑아오기.
				String[] returnArr = imageStorage.saveImage(upload, "seongun");
				
				if(returnArr != null) {
					Photo a = sWork.haventMain(returnArr, i, 1);
					fileList.add(a);
				}
			}
		}else {
			for(int i=0; i < deleteFile.length; i++) {
				if(deleteFile[i].split("#")[1].equals("isdel")) {
					imageStorage.deleteImage(deleteFile[i].split("#")[0], "seongun");
					pService.deletePhotoName(deleteFile[i].split("#")[0]);
				}
			}
			//파일 추가
			for(int i=0; i<files.size(); i++) {
				MultipartFile upload = files.get(i); //파일 하나씩 뽑아오기.
				String[] returnArr = imageStorage.saveImage(upload, "seongun");
				
				if(returnArr != null) {
					Photo a = sWork.haveMain(returnArr,1);
					fileList.add(a);
				}
			}
		}
		
		if(fileList.isEmpty()) {
			resultB = cService.updateCampBoard(b);
		}else {
			resultB = cService.updateCampBoard(b);
			for(Photo a : fileList) {
				a.setBoardNo(b.getCbNo());
			}
			resultA = pService.insertPhotoCampBoard(fileList);
		}
		
		ArrayList<Photo> pList = pService.selectBoardPhoto(sWork.selectBoard(b.getCbNo(), 1));
		
		if (!pList.isEmpty()) {
		    for (Photo p : pList) {
		        if (p.getPhotoLevel() == 0) {
		            x++;
		        }
		    }
		    if (x < 1) {
		        int test = pService.updatePhoto(pList.get(0).getPhotoNo());
		    }
		}
		
		if(fileList.isEmpty()) {
			if(resultB > 0) {
				return "redirect:/campBoard.su";
			}else {
				model.addAttribute("msg", "게시글 수정에 실패했습니다.재작성 부탁드립니다.");
				return "redirect:/campBoard.su";
			}
		}else {
			if(resultA > 0 && resultB > 0) {
				return "redirect:/campBoard.su";
			}else if(resultA > 0 && resultB <= 0){
				model.addAttribute("msg", "이미지 수정을 실패했습니다. 이미지 수정 없이 게시판이 작성되었습니다!");
				return "redirect:/campBoard.su";
			}else {
				model.addAttribute("msg", "게시글 수정에 실패했습니다.재작성 부탁드립니다.");
				return "redirect:/campBoard.su";
			}
		}
	}
}