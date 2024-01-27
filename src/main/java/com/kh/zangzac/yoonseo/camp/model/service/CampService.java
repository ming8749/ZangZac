package com.kh.zangzac.yoonseo.camp.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import com.kh.zangzac.common.Pagination;
import com.kh.zangzac.common.model.vo.Attachment;
import com.kh.zangzac.common.model.vo.PageInfo;
import com.kh.zangzac.common.photo.model.vo.Photo;
import com.kh.zangzac.yoonseo.camp.model.vo.CampingGround;

public interface CampService {
	int insertCamp(CampingGround camp);

	int insertInfoImg(ArrayList<Photo> infoList);

	int getListCount(int i);

	ArrayList<CampingGround> selectCampList(PageInfo pi, int i);

	ArrayList<CampingGround> selectMapList(int i);

	CampingGround selectCampingDetail(int no);

	ArrayList<Photo> selectPhoto(Integer no);

	ArrayList<Photo> selectInfoPhoto(int no);

	int getRecomendationCount(String recomendation);

	ArrayList<CampingGround> selectRecomendationList(PageInfo pi, String recomendation);

	ArrayList<Photo> selectOnePhoto(int i);

	int insertCampImg(ArrayList<Photo> campList);

	int getAllCount();

	ArrayList<CampingGround> selectAllList(PageInfo pi);

	int stateUpdate(Properties prop);

	int deleteFile(ArrayList<String> delRename);

	CampingGround selectAllCamping(int no);

	void updatePhotoLevel(int cgNo);

	int updateCamp(CampingGround camp);

	int insertPhoto(ArrayList<Photo> list);

	int searchCampCount(String keyword, String city, String type);

	ArrayList<CampingGround> searchCampList(PageInfo pi, String keyword, String city, String type);

	ArrayList<CampingGround> getMainList(String recomendation);

	ArrayList<CampingGround> selectMainPhoto(ArrayList<Integer> intArrayList);

	int updateCount(int no);


	
}
