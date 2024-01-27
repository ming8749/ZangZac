package com.kh.zangzac.ming.member.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.kh.zangzac.common.model.vo.PageInfo;
import com.kh.zangzac.common.photo.model.vo.Photo;
import com.kh.zangzac.ming.member.model.vo.Member;
import com.kh.zangzac.seongun.campboard.model.vo.CampBoard;
import com.kh.zangzac.sohwa.product.model.vo.Product;
import com.kh.zangzac.sohwa.product.model.vo.Review;
import com.kh.zangzac.yoonahrim.spBoard.model.vo.secondHandProduct;

public interface MemberService {

	int insertMember(Member m);

	Member login(Member m);

	ArrayList<Member> selectId(Member m);

	int updateNewPwd(Member m);

	int checkId(String memberId);

	int checkEmail(String memberEmail);

	int deleteMember(String memberId);

	int changePwd(HashMap<String, String> map);
	
	//마이페이지
	int updateMemberName(HashMap<String, String> map);

	int updateMemberNickname(HashMap<String, String> map);

	int updatememberPhone(HashMap<String, String> map);

	int updatememberEmail(HashMap<String, String> map);

	int updatememberAddress(HashMap<String, String> map);

	int updateMemberProfile(Member m);
	
	//관리자페이지

	int updateInfo(Properties prop);

	int adminUpdateNickName(Member m);

	int adminUpdateName(Member m);

	int getListCount();

	int searchList(HashMap<String, String> map);

	ArrayList<Member> searchtNoticeList(PageInfo pi, HashMap<String, String> map);

	String getAccessToken(String code);

	HashMap<String, Object> getUserInfo(String access_Token);

	int kakaoLogin(Member kakaoMemberInfo);

	boolean isEmailDuplicate(String memberEmail);

	int getReviewListCount(int i);

	ArrayList<Review> selectReview(String memberId, PageInfo pi);

	ArrayList<Product> selectAllProduct();

	ArrayList<Photo> selectAllPotoProduct();

	int deleteReview(int reviewNo);

	String deleteSelectReview(int reviewNo);

	//int getmySecondHandProductListCount(Map<String, Object> paramMap);

	//ArrayList<secondHandProduct> selectsecondHandProduct(String memberId, PageInfo pi);

	ArrayList<CampBoard> selectCampBoard(String memberId, PageInfo pi);

	ArrayList<CampBoard> searchCbList(PageInfo pi, HashMap<String, Object> map);

	int searchSPListCount(HashMap<String, Object> map);

	int searchCbListCount(HashMap<String, Object> map);

	int getmyBoardListCount(Map<String, Object> paramMap);

	ArrayList<secondHandProduct> searchSpList(PageInfo pi, HashMap<String, Object> map);

	Member getMemberLoginType(String memberEmail);




	

	
	

}