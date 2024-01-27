package com.kh.zangzac.yoonahrim.spBoard.model.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.zangzac.common.model.vo.Attachment;
import com.kh.zangzac.common.model.vo.PageInfo;
import com.kh.zangzac.common.photo.model.vo.Photo;
import com.kh.zangzac.common.reply.model.vo.Reply;
import com.kh.zangzac.jaeyoung.chat.model.vo.Chatter;
import com.kh.zangzac.yoonahrim.spBoard.model.dao.secondHandDAO;
import com.kh.zangzac.yoonahrim.spBoard.model.vo.secondHandProduct;

@Service
public class secondHandServiceImpl implements secondHandService{
	
	@Autowired
	private secondHandDAO spDAO;
	
	@Override
	public int insertSecondHand(secondHandProduct sp) {
		return spDAO.insertSecondHand(sp);
	}

	@Override
	public int updateSecondHand(secondHandProduct sp) {
		return spDAO.updateSecondHand(sp);
	}

	@Override
	public ArrayList<secondHandProduct> selectMyList(secondHandProduct sp) {
		return (ArrayList)spDAO.selectMyList(sp);
	}

	@Override
	public ArrayList<Photo> selectAttachmentList(Integer spNo) {
		return (ArrayList)spDAO.selectAttachmentList(spNo);
	}

	@Override
	public int deleteAttmSecondHand(int spNo) {
		return spDAO.deleteAttmSecondHand(spNo);
	}

	@Override
	public int updateBooking(int spNo) {
		return spDAO.updateBooking(spNo);
	}

	@Override
	public int updateBookingundo(int spNo) {
		return spDAO.updateBookingundo(spNo);
	}

	@Override
	public int soldout(int spNo) {
		return spDAO.soldout(spNo);
	}

	@Override
	public int markDelete(int spNo) {
		return spDAO.markDelete(spNo);
	}

	@Override
	public int insertAttmSecondHand(ArrayList<Photo> list) {
		return spDAO.insertAttmSecondHand(list);
	}

	@Override
	public ArrayList<secondHandProduct> selectSeconHand(secondHandProduct sp) {
		return spDAO.selectSeconHand(sp);
	}

	@Override
	public ArrayList<Photo> selectPhotoSeconHand(Integer spNo) {
		return spDAO.selectPhotoSeconHand(spNo);
	}

	@Override
	public int updateAttmSecondHand(ArrayList<Photo> detailList) {
		return spDAO.updateAttmSecondHand(detailList);
	}

	@Override
	public int deleteAttm(ArrayList<String> delRename) {
		return spDAO.deleteAttm(delRename);
	}

	@Override
	public int deleteAttmForN(int spNo) {
		return spDAO.deleteAttmForN(spNo);
	}

	@Override
	public void updatePhotoLevel(int spNo) {
		spDAO.updatePhotoLevel(spNo);
	}

	@Override
	public ArrayList<secondHandProduct> selectAdminList(secondHandProduct sp) {
		return spDAO.selectAdminList(sp);
	}

	@Override
	public int getListCount() {
		return spDAO.getListCount();
	}

	@Override
	public ArrayList<secondHandProduct> selectBoardList(PageInfo pi, int i) {
		RowBounds rowBounds = new RowBounds((pi.getCurrentPage()-1 )* pi.getBoardLimit(), pi.getBoardLimit());
		return spDAO.selectBoardList(i, rowBounds);
	}

	@Override
	public int updateAdminInfo(secondHandProduct sp) {
		return spDAO.updateAdminInfo(sp);
	}

	@Override
	public ArrayList<secondHandProduct> selectSeconHand(PageInfo pi, int i) {
		RowBounds rowBounds = new RowBounds((pi.getCurrentPage()-1 )* pi.getBoardLimit(), pi.getBoardLimit());
		return spDAO.selectSeconHand(i, rowBounds);
	}

	@Override
	public int searchSpCount(HashMap<String, String> map) {
		return spDAO.searchSpCount(map);
	}

	@Override
	public ArrayList<secondHandProduct> searchSpList(HashMap<String, String> map) {
		return spDAO.searchSpList(map);
	}

	@Override
	public int searchAdminList(HashMap<String, String> map) {
		return spDAO.searchAdminList(map);
	}

	@Override
	public ArrayList<secondHandProduct> searchtAdminList(PageInfo pi, HashMap<String, String> map) {
		RowBounds rowBounds = new RowBounds((pi.getCurrentPage()-1 )* pi.getBoardLimit(), pi.getBoardLimit());
		return spDAO.searchtAdminList(map, rowBounds);
	}

	@Override
	public int updateCount(int spNo) {
		return spDAO.updateCount(spNo);

	}

	@Override
	public ArrayList<Chatter> chatterList(String roomName) {
		return spDAO.chatterList(roomName);
	}

	@Override
	public ArrayList<secondHandProduct> getSpList(String recomendation) {
		return spDAO.getSpList(recomendation);
	}

	@Override
	public ArrayList<secondHandProduct> selectSpPhoto(ArrayList<Integer> spArrayList) {
		return spDAO.selectSpPhoto(spArrayList);
	}

	




	


}
