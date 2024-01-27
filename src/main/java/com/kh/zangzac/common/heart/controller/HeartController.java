package com.kh.zangzac.common.heart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kh.zangzac.common.heart.model.service.HeartService;
import com.kh.zangzac.common.heart.model.vo.Heart;

@Controller
public class HeartController {
	@Autowired
	private HeartService hService;
	
	@PostMapping("selectHeart.like")
	@ResponseBody
	public Heart selectHeart(@ModelAttribute Heart h) {
		return hService.selectHeart(h);
	}
	
	@PostMapping("insertHeart.like")
	@ResponseBody
	public int insertHeart(@ModelAttribute Heart h) {
		return hService.insertHeart(h);
	}
	
	@PostMapping("deleteHeart.like")
	@ResponseBody
	public int deleteHeart(@ModelAttribute Heart h) {
		return hService.deleteHeart(h);
	}
	
	@PostMapping("countHeart.like")
	@ResponseBody
	public int countHeart(@ModelAttribute Heart h) {
		return hService.countHeart(h);
	}
}
