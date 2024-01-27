package com.kh.zangzac.ming.member.model.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;
import org.springframework.web.multipart.MultipartFile;

import com.kh.zangzac.common.model.vo.PageInfo;
import com.kh.zangzac.common.photo.model.vo.Photo;
import com.kh.zangzac.ming.member.model.vo.Member;
import com.kh.zangzac.seongun.campboard.model.vo.CampBoard;
import com.kh.zangzac.sohwa.product.model.vo.Product;
import com.kh.zangzac.sohwa.product.model.vo.Review;
import com.kh.zangzac.yoonahrim.spBoard.model.vo.secondHandProduct;


@Mapper
public interface MemberDAO {

	// 회원가입
	int insertMember(Member m);
	
	// 로그인
	Member login(Member m);
	
	//아이디 찾기
	ArrayList<Member> selectId(Member m);

	//비밀번호 찾기
	int updateNewPwd(Member m);
	
	//아이디 중복체크
	int checkId(String memberId);
	
	//이메일 중복체크
	int checkEmail(String memberEmail);

	//회원탈퇴
	int deleteMember(String memberId);

	int changePwd(HashMap<String, String> map);

	//마이페이지
	int updateMemberName(HashMap<String, String> map);

	int updateMemberNickname(HashMap<String, String> map);

	int updatememberPhone(HashMap<String, String> map);

	int updatememberEmail(HashMap<String, String> map);

	int updatememberAddress(HashMap<String, String> map);

	int updateMemberProfile(Member m);

	int updateInfo(Properties prop);

	int adminUpdateNickName(Member m);

	int adminUpdateName(Member m);

	int getListCount();

	int searchList(HashMap<String, String> map);

	ArrayList<Member> searchtNoticeList(HashMap<String, String> map, RowBounds rowBounds);

	int kakaoLogin(Member kakaoMemberInfo);

	boolean isEmailDuplicate(String memberEmail);

	int getReviewListCount(int i);

	ArrayList<Product> selectAllProduct();

	ArrayList<Photo> selectAllPotoProduct();

	ArrayList<Review> selectReview(String memberId, RowBounds rowBounds);

	int deleteRerview(int reviewNo);

	String deleteSelectReview(int reviewNo);

	int deleteReview(int reviewNo);

	int getmySecondHandProductListCount(Map<String, Object> paramMap);

	ArrayList<secondHandProduct> selectsecondHandProduct(String memberId, RowBounds rowBounds);

	ArrayList<CampBoard> selectCampBoard(String memberId, RowBounds rowBounds);

	ArrayList<secondHandProduct> searchSpList(RowBounds rowBounds, HashMap<String, Object> map);

	int searchSPListCount(HashMap<String, Object> map);

	int searchCbListCount(HashMap<String, Object> map);

	int getmyBoardListCount(Map<String, Object> paramMap);

	ArrayList<CampBoard> searchCbList(RowBounds rowBounds, HashMap<String, Object> map);

	Member getMemberLoginType(String memberEmail);


}