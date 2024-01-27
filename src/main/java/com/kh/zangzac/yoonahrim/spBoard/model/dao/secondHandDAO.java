package com.kh.zangzac.yoonahrim.spBoard.model.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.kh.zangzac.common.photo.model.vo.Photo;
import com.kh.zangzac.common.reply.model.vo.Reply;
import com.kh.zangzac.jaeyoung.chat.model.vo.Chatter;
import com.kh.zangzac.yoonahrim.spBoard.model.vo.secondHandProduct;

@Mapper
public interface secondHandDAO {

	int insertSecondHand(secondHandProduct sp);

	int updateSecondHand(secondHandProduct sp);

	int insertAttmSecondHand(ArrayList<Photo> list);

	ArrayList<secondHandProduct> selectMyList(secondHandProduct sp);

	int deleteAttmSecondHand(int spNo);

	ArrayList<Photo> selectAttachmentList(Integer spNo);

	int updateBooking(int spNo);

	int updateBookingundo(int spNo);

	int soldout(int spNo);

	int markDelete(int spNo);

	ArrayList<secondHandProduct> selectSeconHand(secondHandProduct sp);

	ArrayList<Photo> selectPhotoSeconHand(Integer spNo);

	int updateAttmSecondHand(ArrayList<Photo> detailList);

	int deleteAttm(ArrayList<String> delRename);

	int deleteAttmForN(int spNo);

	void updatePhotoLevel(int spNo);

	ArrayList<secondHandProduct> selectAdminList(secondHandProduct sp);

	int getListCount();

	ArrayList<secondHandProduct> selectBoardList(int i, RowBounds rowBounds);

	int updateAdminInfo(secondHandProduct sp);

	ArrayList<secondHandProduct> selectSeconHand(int i, RowBounds rowBounds);

	int searchSpCount(HashMap<String, String> map);

	ArrayList<secondHandProduct> searchSpList(HashMap<String, String> map);

	int searchAdminList(HashMap<String, String> map);

	ArrayList<secondHandProduct> searchtAdminList(HashMap<String, String> map, RowBounds rowBounds);

	int updateCount(int spNo);

	ArrayList<Chatter> chatterList(String roomName);

	ArrayList<secondHandProduct> getSpList(String recomendation);

	ArrayList<secondHandProduct> selectSpPhoto(ArrayList<Integer> spArrayList);


	




	
	

}
