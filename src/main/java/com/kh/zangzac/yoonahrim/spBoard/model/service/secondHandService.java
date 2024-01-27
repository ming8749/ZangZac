package com.kh.zangzac.yoonahrim.spBoard.model.service;

import java.util.ArrayList;
import java.util.HashMap;

import com.kh.zangzac.common.model.vo.Attachment;
import com.kh.zangzac.common.model.vo.PageInfo;
import com.kh.zangzac.common.photo.model.vo.Photo;
import com.kh.zangzac.common.reply.model.vo.Reply;
import com.kh.zangzac.jaeyoung.chat.model.vo.Chatter;
import com.kh.zangzac.yoonahrim.spBoard.model.vo.secondHandProduct;

public interface secondHandService {

	int insertSecondHand(secondHandProduct sp);

	int updateSecondHand(secondHandProduct sp);

	int insertAttmSecondHand(ArrayList<Photo> detailList);

	ArrayList<secondHandProduct> selectMyList(secondHandProduct sp);

	ArrayList<Photo> selectAttachmentList(Integer spNo);

	int deleteAttmSecondHand(int spNo);

	int updateBooking(int spNo);

	int updateBookingundo(int spNo);

	int soldout(int spNo);

	int markDelete(int spNo);

	ArrayList<secondHandProduct> selectSeconHand(secondHandProduct sp);

	ArrayList<Photo> selectPhotoSeconHand(Integer spNo);

	int updateAttmSecondHand(ArrayList<Photo> list);

	int deleteAttm(ArrayList<String> delRename);

	int deleteAttmForN(int spNo);

	void updatePhotoLevel(int spNo);

	ArrayList<secondHandProduct> selectAdminList(secondHandProduct sp);

	int getListCount();

	ArrayList<secondHandProduct> selectBoardList(PageInfo pi, int i);

	int updateAdminInfo(secondHandProduct sp);

	ArrayList<secondHandProduct> selectSeconHand(PageInfo pi, int i);

	int searchSpCount(HashMap<String, String> map);

	ArrayList<secondHandProduct> searchSpList(HashMap<String, String> map);

	int searchAdminList(HashMap<String, String> map);

	ArrayList<secondHandProduct> searchtAdminList(PageInfo pi, HashMap<String, String> map);

	int updateCount(int spNo);

	ArrayList<Chatter> chatterList(String roomName);

	ArrayList<secondHandProduct> getSpList(String recomendation);

	ArrayList<secondHandProduct> selectSpPhoto(ArrayList<Integer> spArrayList);




	
	

}
