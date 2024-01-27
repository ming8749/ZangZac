package com.kh.zangzac.common.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.kh.zangzac.common.Pagination;
import com.kh.zangzac.common.model.vo.PageInfo;
import com.kh.zangzac.common.model.vo.SelectCondition;
import com.kh.zangzac.common.photo.model.service.PhotoService;
import com.kh.zangzac.common.photo.model.vo.Photo;
import com.kh.zangzac.jaeyoung.campingReview.model.service.CampingReviewService;
import com.kh.zangzac.jaeyoung.campingReview.model.vo.CampingReview;
import com.kh.zangzac.jaeyoung.eventBoard.model.service.EventBoardService;
import com.kh.zangzac.jaeyoung.eventBoard.model.vo.EventBoard;
import com.kh.zangzac.seongun.campboard.model.service.CampBoardService;
import com.kh.zangzac.seongun.campboard.model.vo.CampBoard;
import com.kh.zangzac.yoonahrim.spBoard.model.service.secondHandService;
import com.kh.zangzac.yoonseo.camp.model.service.CampService;
import com.kh.zangzac.yoonseo.camp.model.vo.CampingGround;

@Controller
public class BoardCondition {
	
	@Autowired
	private CampService cService;
	
	@Autowired
	private secondHandService spService;
	
	@Autowired
	private EventBoardService ebService;
	
	@Autowired
	private PhotoService pService;
	
	@Autowired
	private CampingReviewService crService;
	
	@Autowired
	private CampBoardService cbService;


	public SelectCondition selectBoard(int x, int y) {
		SelectCondition b = new SelectCondition();
		b.setBoardNo(x);
		b.setBoardType(y);
		return b;
	}
	
	@GetMapping("/")
	public String main(Model model) {
		//yoonseo
		String recomendation = "Y";
		ArrayList<CampingGround> list = cService.getMainList(recomendation);
		
		ArrayList<Integer> intArrayList = new ArrayList<>();

		for (CampingGround cg : list) {
		    intArrayList.add(cg.getCgNo());
		}

		
 		ArrayList<CampingGround> photoList = cService.selectMainPhoto(intArrayList);
 		
 		
 		//jaeyoung
 		
 		int listCount = ebService.getListCount();
		int currentPage = 1;
		PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 2);
		ArrayList<EventBoard> eblist = ebService.selectEventBoardList(pi);
		
		
		for(EventBoard eb : eblist) { // 사진 찾아 넣기
			SelectCondition sc = new SelectCondition();
		      sc.setBoardNo(eb.getEbNo());
		      sc.setBoardType(9);
		      ArrayList<Photo> pList = pService.selectBoardPhoto(sc);
		      eb.setPhoto(pList.get(0)); //썸네일 넣기
			
		}
		
		
	    int listCount2 = crService.getListCount("");
		
		PageInfo pi2 = Pagination.getPageInfo(1, listCount2, 2);
		ArrayList<CampingReview> crlist = crService.selectBoardList(pi2,"");
		
		//사진들 가져오기
		for(CampingReview cr : crlist) {
			SelectCondition sc = new SelectCondition();
		      sc.setBoardNo(cr.getCrNo());
		      sc.setBoardType(8);
		      ArrayList<Photo> pList = pService.selectBoardPhoto(sc);
		      cr.setPhotos(pList);
		      cr.setThumnail(pList.get(0)); //썸네일 넣기
		}
		
		
		
		
 		
		
		//seongun
		
		int cbCount = cbService.listCount();
		PageInfo cbPi = Pagination.getPageInfo(currentPage, cbCount, 5);
		ArrayList<CampBoard> cbList = cbService.popularList(cbPi);
		
		if(list != null) {
			model.addAttribute("photoList", photoList);

			model.addAttribute("ebList", eblist);

			model.addAttribute("cbList", cbList);

			return"index";
		}else {
			return"index";
		}

	}
	
}
