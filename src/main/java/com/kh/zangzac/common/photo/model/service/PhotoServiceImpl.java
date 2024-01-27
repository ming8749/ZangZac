package com.kh.zangzac.common.photo.model.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.zangzac.common.model.vo.SelectCondition;
import com.kh.zangzac.common.photo.model.dao.PhotoDAO;
import com.kh.zangzac.common.photo.model.vo.Photo;

@Service
public class PhotoServiceImpl implements PhotoService{
	
	@Autowired
	PhotoDAO pDAO;

	@Override
	public ArrayList<Photo> selectBoardPhoto(SelectCondition b) {
		return pDAO.selectBoardPhoto(b);
	}

	@Override
	public int insertPhotoCampBoard(ArrayList<Photo> fileList) {
		return pDAO.insertPhotoCampBoard(fileList);
	}

	@Override
	public int deletePhoto(Photo temp) {
		return pDAO.deletePhoto(temp);
	}

	@Override
	public int deletePhotoName(String deletePhotoName) {
		return pDAO.deletePhotoName(deletePhotoName);
	}

	@Override
	public int updatePhoto(int photoNo) {
		return pDAO.updatePhoto(photoNo);
	}
	
	

}