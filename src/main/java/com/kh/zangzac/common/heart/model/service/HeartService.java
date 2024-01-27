package com.kh.zangzac.common.heart.model.service;

import com.kh.zangzac.common.heart.model.vo.Heart;

public interface HeartService {

	Heart selectHeart(Heart h);
	
	int insertHeart(Heart h);

	int deleteHeart(Heart h);

	int countHeart(Heart h);
}
