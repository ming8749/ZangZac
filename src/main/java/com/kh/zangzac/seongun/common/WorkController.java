package com.kh.zangzac.seongun.common;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.kh.zangzac.common.Pagination;
import com.kh.zangzac.common.heart.model.vo.Heart;
import com.kh.zangzac.common.model.vo.PageInfo;
import com.kh.zangzac.common.model.vo.SelectCondition;
import com.kh.zangzac.common.photo.model.vo.Photo;
import com.kh.zangzac.common.reply.controller.ReplyController;
import com.kh.zangzac.seongun.campboard.model.vo.CampBoard;
import com.kh.zangzac.seongun.common.model.vo.SearchBoard;
import com.kh.zangzac.seongun.recipe.model.vo.CookwareList;
import com.kh.zangzac.seongun.recipe.model.vo.Recipe;

@Controller
public class WorkController {
	@Autowired
	private ReplyController rController;
	
	public void addListModel(Model model, PageInfo pi, ArrayList<CampBoard> list, String msg,int category, String loc) {
		model.addAttribute("pi", pi);
		model.addAttribute("list", list);
		model.addAttribute("msg", msg);
		model.addAttribute("category", category);
	    model.addAttribute("loc", loc);
	}
	
	public void searchModel(Model model, PageInfo pi, ArrayList<CampBoard> list, String msg,int category, SearchBoard search, String loc) {
		model.addAttribute("pi", pi);
		model.addAttribute("list", list);
		model.addAttribute("msg", msg);
		model.addAttribute("category", category);
		if(search.getSearchText() != null) {
			model.addAttribute("search", search);
		}
		model.addAttribute("loc", loc);
	}

	public Photo haventMain(String[] returnArr, int i, int j) {
		Photo a = new Photo();
		if(i==0) {
			a.setPhotoRename(returnArr[0]);
			a.setPhotoPath(returnArr[1]);
			a.setBoardType(j);
			a.setPhotoLevel(0);
		}else {
			a.setPhotoRename(returnArr[0]);
			a.setPhotoPath(returnArr[1]);
			a.setBoardType(j);
			a.setPhotoLevel(1);
		}
		
		return a;
	}
	
	//썸네일 삭제 X
	public Photo haveMain (String[] returnArr, int i) {
		Photo a = new Photo();
		a.setPhotoRename(returnArr[0]);
		a.setPhotoPath(returnArr[1]);
		a.setBoardType(i);
		a.setPhotoLevel(1);
		
		return a;
	}

	public SelectCondition selectBoard(int boardNo, int i) {
		SelectCondition b = new SelectCondition();
		b.setBoardNo(boardNo);
		b.setBoardType(i);
		return b;
	}

	public void BoardDetail(Model model,CampBoard bList, int maxPage, ArrayList<Photo> pList, int page) {
		if(bList != null) {
			model.addAttribute("bList", bList);
			model.addAttribute("pList", pList);
			model.addAttribute("page", page);
			model.addAttribute("maxPage", maxPage);
		}
		
	}

	public int countReply(int cbNo, int i) {
		SelectCondition b = selectBoard(cbNo, i);
		
		int listCount = rController.countReply(b);
		
		return Pagination.getReplyPageInfo(1, listCount, 10).getMaxPage();
	}

	public Heart addHeart(int cbNo, String memberId, int i) {
		Heart h = new Heart();
		h.setBoardNo(cbNo);
		h.setMemberId(memberId);
		h.setBoardType(i);
		return h;
	}

	public Photo setPhoto(int num, int i) {
		Photo p = new Photo();
		p.setBoardNo(num);
		p.setBoardType(i);
		return p;
	}

	public void deleteModel(Model model, String msg) {
		model.addAttribute("msg", msg);
	}

	public void editModel(CampBoard bList, ArrayList<Photo> pList, Model model) {
		model.addAttribute("bList", bList);
		model.addAttribute("pList", pList);
	}

	public CookwareList setCook(int set, int i) {
		CookwareList c = new CookwareList();
		c.setCwRecipeNo(set);
		c.setCookCategoryNo(i);
		return c;
	}

	public void addRec(ArrayList<Recipe> list, String msg, PageInfo pi, Model model, String loc) {
		model.addAttribute("pi", pi);
		model.addAttribute("list", list);
		model.addAttribute("msg", msg);
	    model.addAttribute("loc", loc);
	}

	public void recipeDetatil(Model model, Recipe r, ArrayList<Photo> pList) {
		model.addAttribute("r", r);
		model.addAttribute("pList", pList);
	}

	public void editRecipe(Recipe r, ArrayList<Photo> pList, Model model) {
		model.addAttribute("r", r);
		model.addAttribute("pList", pList);
	}

	public void searchModel(Model model, PageInfo pi, ArrayList<CampBoard> list, String msg, CampBoard b, String requestURI) {
		model.addAttribute("pi", pi);
		model.addAttribute("list", list);
		model.addAttribute("msg", msg);
		model.addAttribute("loc", requestURI);
		model.addAttribute("b",b);
	}
}
