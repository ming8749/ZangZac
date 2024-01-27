package com.kh.zangzac.yoonseo.camp.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.TreeMap;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.zangzac.common.ImageStorage;
import com.kh.zangzac.common.Pagination;
import com.kh.zangzac.common.model.vo.PageInfo;
import com.kh.zangzac.common.photo.model.vo.Photo;
import com.kh.zangzac.yoonseo.camp.model.exception.CampException;
import com.kh.zangzac.yoonseo.camp.model.service.CampService;
import com.kh.zangzac.yoonseo.camp.model.vo.CampingGround;

import jakarta.servlet.http.HttpServletRequest;

@SessionAttributes("loginUser")
@Controller
public class CampController {
	
	@Autowired
	private CampService cService;
	
	private final ImageStorage imageStorage;

    @Autowired
    public CampController(ImageStorage imageStorage) {
        this.imageStorage = imageStorage;
    }
	
	
	@GetMapping("campSearch.ys")
	public String campSearch(@RequestParam(value="page", defaultValue="1") int page,
			                  Model model, HttpServletRequest request) {
		
		
		int listCount = cService.getListCount(3); //내 보드타입은 3 번이니까
		int currentPage = page;
		PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 7); //7개씩 보이게 할거야
		ArrayList<CampingGround> cList = cService.selectCampList(pi,3);
		ArrayList<CampingGround> mapList = cService.selectMapList(3);
		
		
		if(cList != null) {
			model.addAttribute("loc", request.getRequestURI());
			model.addAttribute("cList", cList);
			model.addAttribute("pi", pi);
			model.addAttribute("mapList", mapList);
			model.addAttribute("listCount", listCount);
			
			return "views/yoonseo/campSearch";
		}else {
			return "redirect:/";
		}
	}
	
	@GetMapping("campList.ys")
	public String campList(@RequestParam(value="page", defaultValue="1") int page,
			               Model model, HttpServletRequest request) {
		
		String recomendation = "Y";
		int listCount = cService.getRecomendationCount(recomendation);
		
		
		int currentPage = page;
		PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 6);
		ArrayList<CampingGround> list = cService.selectRecomendationList(pi,recomendation);
		
	    ArrayList<Photo> photo = cService.selectOnePhoto(0);
	         
	    for(CampingGround cg : list) {
	    	cg.calculateCgPoint();
	    }
	    
		if(list != null) {
			model.addAttribute("list", list);
			model.addAttribute("loc", request.getRequestURI());
			model.addAttribute("pi", pi);
			model.addAttribute("photo", photo);
			
			return "views/yoonseo/campList";
			
		}else {
			return "redirect:/";
		}
				
	}
	
	@GetMapping("campDetail.ys")
	public String selectCampDetail(@RequestParam("no") int no,
			                       @RequestParam("page") int page,
			                       Model model) {
		
	
		CampingGround camp = cService.selectCampingDetail(no);
		int count = cService.updateCount(no);
		if(count > 0) {
			camp.setCgCount(camp.getCgCount() + 1);
		}
		
		ArrayList<Photo> campList = cService.selectPhoto(no); //캠핑장 사진
		
		System.out.println(camp.getCgImgInfo());
	    String info = camp.getCgImgInfo();
	    String[] infoArray = info.split(",");
	  
	    double point = camp.getCgStarPoint()*100/5;//별점에 적용시킬 width % 계산식.
	    
		if(camp != null) {
		   model.addAttribute("camp", camp);
		   model.addAttribute("list", campList);
		   model.addAttribute("page", page);
		   model.addAttribute("infoArray", infoArray);
		   model.addAttribute("point", point);
		   
		   return"views/yoonseo/campDetail";   
		}else {
			throw new CampException("캠핑장 등록에 실패하였습니다");
		}
	}
	
	@GetMapping("detailWrite.ys")
	public String detailWriter() {
		return "views/yoonseo/detailWrite";
	}
	
	@PostMapping("campInsert.ys")
	public String campInsert(@ModelAttribute CampingGround camp,
			                 @RequestParam("campImg") ArrayList<MultipartFile> campFiles,
			                 @RequestParam("nowURL") String nowURL,
			                 Model model
			                
			                 ) {
		
		System.out.println(camp.getCgAddress());
		System.out.println(camp.getCgImgInfo());
		
		ArrayList<Photo> campList = new ArrayList<>();
		
		String name = "yoonseo";
		
		int result1 = 0;
		int result2 = 0;
		
		
		for(int i = 0; i < campFiles.size(); i++) {
			MultipartFile campUpload = campFiles.get(i);
			String[] returnArr = imageStorage.saveImage(campUpload, name);
			
			if(returnArr != null) {
				Photo a = new Photo();
				if(i == 0) {
					a.setPhotoLevel(0);
					
					a.setPhotoRename(returnArr[0]);
					a.setPhotoPath(returnArr[1]);
					
				
				}else {
					a.setPhotoLevel(1);
					
					a.setPhotoRename(returnArr[0]);
					a.setPhotoPath(returnArr[1]);
					
				}
				
				campList.add(a);
			}
		}
		
		
		
		result1 = cService.insertCamp(camp);
		
		//캠프상세사진 저장
		for(Photo a : campList ) {
			a.setBoardNo(camp.getCgNo());
		}
		result2 = cService.insertCampImg(campList);
				
		if(result1 + result2  == campList.size()+ 1) {
			return "redirect:campUpdate.ys";
			
		}else {
			model.addAttribute("msg", "캠핑장 등록에 실패하였습니다.");
			return "views/yoonseo/detailWrite";
		}
	}
	
	@GetMapping("campUpdate.ys")
	public String campUpdate(@RequestParam(value="page", defaultValue="1") int currentPage,
			                 Model model, HttpServletRequest request) {
		
		int listCount = cService.getAllCount();
		
		PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 10);
		ArrayList<CampingGround> list = cService.selectAllList(pi);
		
		if(list != null) {
			model.addAttribute("list", list);
			model.addAttribute("pi", pi);
			model.addAttribute("loc", request.getRequestURI());
			
			return "views/yoonseo/campUpdate";
		}else {
			
			model.addAttribute("msg", "캠핑장 목록 조회에 실패하였습니다.");
			return "views/yoonseo/campUpdate";
		}
		
	}
	
	@GetMapping("stateUpdate.ys")
	@ResponseBody
	public String stateUpdate(@RequestParam("column") String column,
			                  @RequestParam("state") String state,
			                  @RequestParam("no") String no) {
		
		
		Properties prop = new Properties();//map형식으로 key와 value로 이루어져있음.문자열만 받아줌.
		prop.setProperty("column",column);
		prop.setProperty("state", state);
		prop.setProperty("no", no);
		
		int result = cService.stateUpdate(prop);
		
		return result == 1 ? "success" : "fail";
	}
	
	@GetMapping("selectUpdate.ys")
	public String selectUpdate(@RequestParam("no") int no,
			                   @RequestParam("page") int page,
			                   Model model) {
		
		CampingGround campList = cService.selectAllCamping(no);
		ArrayList<Photo> photoList = cService.selectPhoto(no);
		
		String info = campList.getCgImgInfo();
	    String recomendation = campList.getCgRecomendation();
	    
		
		if(campList != null) {
			model.addAttribute("campList", campList);
			model.addAttribute("photoList", photoList);
			model.addAttribute("page", page);
			model.addAttribute("info", info);
			model.addAttribute("recomendation", recomendation);
			
			return "views/yoonseo/campEdit";
		}else {
			return "redirect:/";
		}
	}
	
	@PostMapping("campEdit.ys")
	public String campEdit(@ModelAttribute CampingGround camp,
			               @RequestParam("page") int page,
			               @RequestParam("deleteFile") String[] deleteFile,
			               @RequestParam("file") ArrayList<MultipartFile> files,
			               HttpServletRequest request,
			               RedirectAttributes redirectAttributes) {
		
		String name = "yoonseo";
		
		ArrayList<Photo> list = new ArrayList<>();
		//files 새로 추가한 파일들이 들어가 있는곳
		for(int i = 0; i < files.size(); i++) {
			MultipartFile upload = files.get(i);
			
			if(!upload.getOriginalFilename().equals("")) {
				//파일이 들어왔으면.
				String[] returnArr = imageStorage.saveImage(upload, name);
				
				if(returnArr != null) {
					Photo a = new Photo();
					
					a.setPhotoRename(returnArr[0]);
					a.setPhotoPath(returnArr[1]);
					
					list.add(a);
				}
			}
		}
		
		ArrayList<String> delRename = new ArrayList<>();
		ArrayList<Integer> delLevel = new ArrayList<>();
		
		for(int i =0; i < deleteFile.length; i++) { //삭제할 파일들을
			String rename = deleteFile[i]; //rename에 담음
			if(!rename.equals("none")) { //none이 아니면
				String[] split = rename.split("/");// 슬래시로 잘라서
				delRename.add(split[0]); //reanme은 여기 담고
				delLevel.add(Integer.parseInt(split[1]));//level은 여기 담음.
			}
		}
		
		System.out.println(delLevel);
		
		int deleteFileResult = 0;
		int updateCampResult = 0;
		boolean existBeforeAttm = true;
		
		if(!delRename.isEmpty()) {//담긴 파일명이 있으면.
			deleteFileResult = cService.deleteFile(delRename);
			if(deleteFileResult > 0) { //db에서 삭제가 됐으면.
				for(String rename: delRename) { 
					imageStorage.deleteImage(rename,name);//삭제 메소드
				}
			}
			if(delRename.size() == deleteFile.length) {
				existBeforeAttm = false;
			}else {
				for(int level : delLevel) { 
					if(level == 0) { //지운 사진의 레벨이 0이면 
						cService.updatePhotoLevel(camp.getCgNo());//사진중 가장적은 사진번호에 level 0 주기
						break;
					}
				}
			}
		}
		
		for(int i = 0; i <list.size(); i++) {
			Photo p = list.get(i);
			p.setBoardNo(camp.getCgNo()); //추가된 사진에 캠핑장 번호 부여 해줌
			if(existBeforeAttm) {
				p.setPhotoLevel(1);
			}else {
				if(i == 0) {
					p.setPhotoLevel(0);
				}else {
					p.setPhotoLevel(1);
				}
			}
			
		}
		updateCampResult = cService.updateCamp(camp); //camp정보 update
		int updateFileResult = 0;
		if(!list.isEmpty()) { //새로 추가한 파일이 있으면
			updateFileResult = cService.insertCampImg(list); //camp사진 insert
		}
		
		if(updateCampResult +  updateFileResult == list.size()+1) { 
			//camp정보 update된 결과(행 갯수)와 캠프사진 insert한 결과(행 갯수)를 합한 것과
			//추가한 사진 길이에 +1 한 것이 같으면.
			redirectAttributes.addAttribute("no", camp.getCgNo());
			redirectAttributes.addAttribute("page", page);
			return "redirect:campUpdate.ys";
		}else {
			return "redirect:/";
		}
		
	}
	
	@GetMapping("campSearchList.ys")
	public String campSerchList(@RequestParam("keyword") String keyword,
			                    @RequestParam("city") String city,
			                    @RequestParam("type") String type,
			                    @RequestParam(value="page", defaultValue="1") int page,
			                    Model model) {
		
		
		int result = cService.searchCampCount(keyword,city,type);
		
		int currentPage = page;
		PageInfo pi = Pagination.getPageInfo(currentPage,result,6);
		ArrayList<CampingGround> campList = cService.searchCampList(pi,keyword,city,type);
		
		if(campList != null) {
			model.addAttribute("result", result);
			model.addAttribute("campList", campList);
			model.addAttribute("type", type);
			model.addAttribute("keyword", keyword);
			model.addAttribute("city", city);
			model.addAttribute("pi", pi);
			
			return "views/yoonseo/campSearchList";
		}else {
			return "redirect:/";
		}
		
	}
	

	
	

	
}
