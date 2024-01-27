package com.kh.zangzac.common.heart.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.zangzac.common.heart.model.dao.HeartDAO;
import com.kh.zangzac.common.heart.model.vo.Heart;

@Service
public class HeartServiceImpl implements HeartService{
	
	@Autowired
	private HeartDAO hDAO;
	
	@Override
	public Heart selectHeart(Heart h) {
		return hDAO.selectHeart(h);
	}
	
	@Override
	public int insertHeart(Heart h) {
		return hDAO.insertHeart(h);
	}

	@Override
	public int deleteHeart(Heart h) {
		return hDAO.deleteHeart(h);
	}

	@Override
	public int countHeart(Heart h) {
		return hDAO.countHeart(h);
	}
}