package com.kh.zangzac.yoonseo.camp.model.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;


import com.kh.zangzac.common.photo.model.vo.Photo;
import com.kh.zangzac.yoonseo.camp.model.vo.CampingGround;

@Mapper
public interface CampDAO {

	int insertCamp(CampingGround camp);

	int insertCampImg(ArrayList<Photo> campList);

	int insertInfoImg(ArrayList<Photo> infoList);

	int getListCount(int i);

	ArrayList<CampingGround> selectCampList(int i, RowBounds row);

	ArrayList<CampingGround> selectMapList(int i);

	CampingGround selectCampingDetail(int no);

	ArrayList<Photo> selectPhoto(Integer no);

	ArrayList<Photo> selectInfoPhoto(int no);

	int getRecomendationCount(String recomendation);

	ArrayList<CampingGround> selectRecomendationList(RowBounds row, String recomendation);

	ArrayList<Photo> selectOnePhoto(int i);

	int getAllCount();

	ArrayList<CampingGround> selectAllList(RowBounds row);

	int stateUpdate(Properties prop);

	int deleteFile(ArrayList<String> delRename);

	CampingGround selectAllCamping(int no);

	int updateCamp(CampingGround camp);

	int insertPhoto(ArrayList<Photo> list);

	void updatePhotoLevel(int cgNo);

	int searchCampCount(HashMap<String, String> map);

	ArrayList<CampingGround> searchCampList(RowBounds row, HashMap<String, String> map);

	ArrayList<CampingGround> getMainList(String recomendation);

	ArrayList<Photo> selectMainPhoto(int[] cgNo);

	ArrayList<CampingGround> selectMainPhoto(ArrayList<Integer> intArrayList);

	int updateCount(int no);

	int updateLike(HashMap<String, Object> like, String check);
}
