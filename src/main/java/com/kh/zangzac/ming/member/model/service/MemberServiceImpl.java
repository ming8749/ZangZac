package com.kh.zangzac.ming.member.model.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kh.zangzac.common.model.vo.PageInfo;
import com.kh.zangzac.common.photo.model.vo.Photo;
import com.kh.zangzac.ming.member.model.dao.MemberDAO;
import com.kh.zangzac.ming.member.model.vo.Member;
import com.kh.zangzac.seongun.campboard.model.vo.CampBoard;
import com.kh.zangzac.sohwa.product.model.vo.Product;
import com.kh.zangzac.sohwa.product.model.vo.Review;
import com.kh.zangzac.yoonahrim.spBoard.model.vo.secondHandProduct;


@Service
public class MemberServiceImpl implements MemberService{
	
	@Autowired
	private MemberDAO mDAO;
	
	//회원가입
	@Override
	public int insertMember(Member m) {
		return mDAO.insertMember(m);
	}
	
	//로그인
	@Override
	public Member login(Member m) {
		return mDAO.login(m);
	}
	
	//아이디 찾기
	@Override
	public ArrayList<Member> selectId(Member m) {
		return mDAO.selectId(m);
	}

	
	//비밀번호 랜덤코드 재설정
	@Override
	public int updateNewPwd(Member m) {
		return mDAO.updateNewPwd(m);
	}
	
	//아이디 중복체크
	@Override
	public int checkId(String memberId) {
		return mDAO.checkId(memberId);
	}
	
	//이메일 중복체크
	@Override
	public int checkEmail(String memberEmail) {
		return mDAO.checkEmail(memberEmail);
	}

	@Override
	public int deleteMember(String memberId) {
		return mDAO.deleteMember(memberId);
	}

	@Override
	public int changePwd(HashMap<String, String> map) {
		return mDAO.changePwd(map);
	}
	
	//마이페이지 수정
	@Override
	public int updateMemberName(HashMap<String, String> map) {
		return mDAO.updateMemberName(map);
	}

	@Override
	public int updateMemberNickname(HashMap<String, String> map) {
		return mDAO.updateMemberNickname(map);
	}

	@Override
	public int updatememberPhone(HashMap<String, String> map) {
		return mDAO.updatememberPhone(map);
	}

	@Override
	public int updatememberEmail(HashMap<String, String> map) {
		return mDAO.updatememberEmail(map);
	}

	@Override
	public int updatememberAddress(HashMap<String, String> map) {
		return mDAO.updatememberAddress(map);
	}

	@Override
	public int updateMemberProfile(Member m) {
		return mDAO.updateMemberProfile(m);
	}
	
	//관리자페이지

	@Override
	public int updateInfo(Properties prop) {
		return mDAO.updateInfo(prop);
	}

	@Override
	public int adminUpdateNickName(Member m) {
		return mDAO.adminUpdateNickName(m);
	}

	@Override
	public int adminUpdateName(Member m) {
		return mDAO.adminUpdateName(m);
	}

	@Override
	public int getListCount() {
		return mDAO.getListCount();
	}

	@Override
	public int searchList(HashMap<String, String> map) {
		return mDAO.searchList(map);
	}

	@Override
	public ArrayList<Member> searchtNoticeList(PageInfo pi, HashMap<String, String> map) {
		int offset = (pi.getCurrentPage() - 1)*pi.getBoardLimit();
		int limit = pi.getBoardLimit();
		
		RowBounds rowBounds = new RowBounds(offset, limit);
		return mDAO.searchtNoticeList(map,rowBounds);
	}

	@Override
	   public String getAccessToken(String code) {
	      String access_Token = "";
	      String refresh_Token = "";
	      String reqURL = "https://kauth.kakao.com/oauth/token";

	      try {
	         URL url = new URL(reqURL);
	            
	         HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	         // POST 요청을 위해 기본값이 false인 setDoOutput을 true로
	            
	         conn.setRequestMethod("POST");
	         conn.setDoOutput(true);
	         // POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
	            
	         BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
	         StringBuilder sb = new StringBuilder();
	         sb.append("grant_type=authorization_code");
	            
	         sb.append("&client_id=18ae7b1bab696aadfa39200fae9ad11b"); //본인이 발급받은 key
	         sb.append("&redirect_uri=http://192.168.20.207:8080/kakaoLogin"); // 본인이 설정한 주소
	            
	         sb.append("&code=" + code);
	         System.out.println("code = "+code);
	         bw.write(sb.toString());
	         System.out.println(sb.toString());
	         bw.flush();
	            
	         // 결과 코드가 200이라면 성공
	         int responseCode = conn.getResponseCode();
	         System.out.println("responseCode:"+responseCode);
	         
	         // 요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
	         BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	         String line = "";
	         String result = "";
	            
	         while ((line = br.readLine()) != null) {
	            result += line;
	         }
	            
	         // Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
	         JsonElement element = JsonParser.parseString(result);
	            
	         access_Token = element.getAsJsonObject().get("access_token").getAsString();
	         refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();
	            
	         br.close();
	         bw.close();
	      } catch (IOException e) {
	         e.printStackTrace();
	      }
	      return access_Token;
	   }

	@Override
    public HashMap<String, Object> getUserInfo(String access_Token) {
        HashMap<String, Object> userInfo = new HashMap<String, Object>();
        String reqURL = "https://kapi.kakao.com/v2/user/me";
        String addressURL = "https://kapi.kakao.com/v1/user/shipping_address";
        try {
           
         //배송지
            URL url = new URL(addressURL);
             HttpURLConnection conn = (HttpURLConnection) url.openConnection();
             conn.setRequestMethod("GET");

             conn.setRequestProperty("Authorization", "Bearer " + access_Token);

             int responseCode = conn.getResponseCode();

             BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

             String line;
             StringBuilder result = new StringBuilder();

             while ((line = br.readLine()) != null) {
                 result.append(line);
             }
             br.close(); // BufferedReader 닫기
             
             
             JsonElement element = JsonParser.parseString(result.toString());
             JsonElement objElement = element.getAsJsonObject().get("shipping_addresses");
             JsonArray addresses = objElement.getAsJsonArray();
             String baseAddress="";
             String detailAddress="";

             if (addresses.size() > 0) {
                 JsonObject firstAddress = addresses.get(0).getAsJsonObject();

                 baseAddress = firstAddress.get("base_address").getAsString();
                 detailAddress = firstAddress.get("detail_address").getAsString();
                
             }
           
           
            url = new URL(reqURL);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            conn.setRequestProperty("Authorization", "Bearer " + access_Token);

            responseCode = conn.getResponseCode();

            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

           String line2 ="";
           result = new StringBuilder();

            while ((line2 = br.readLine()) != null) {
                result.append(line2);
            }
            br.close(); // BufferedReader 닫기
            
            
            
            element = JsonParser.parseString(result.toString());
            
            JsonObject kakaoAccount = element.getAsJsonObject().get("kakao_account").getAsJsonObject();
            JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
            
            String nickname = properties.getAsJsonObject().get("nickname").getAsString();
            String profileImg = properties.getAsJsonObject().get("profile_image").getAsString();
            String name = kakaoAccount.getAsJsonObject().get("name").getAsString();
            String email = kakaoAccount.getAsJsonObject().get("email").getAsString();
            String phone = kakaoAccount.getAsJsonObject().get("phone_number").getAsString();
            String birthyear = kakaoAccount.getAsJsonObject().get("birthyear").getAsString();
            String birth = kakaoAccount.getAsJsonObject().get("birthday").getAsString();
            String id = element.getAsJsonObject().get("id").getAsString();
           
            
//            System.out.println("kakaoAccount: " + kakaoAccount);
//            System.out.println("element: " + element);
//            System.out.println("properties: " + properties);
            
            
            detailAddress = baseAddress + detailAddress;
            phone = phone.replace(phone.substring(0, 4), "0").replaceAll("-", "");
            String formattedBirthday = birth.substring(0, 2) + "-" + birth.substring(2);
            birth = birthyear + "-" + formattedBirthday;

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(birth, formatter);
            java.sql.Date sqlDate = java.sql.Date.valueOf(localDate);
            
//            System.out.println("element: "+element);
//            System.out.println("properties: "+properties);
            //정보 저장 구간
            // nickname profile 등등 필요한거
            // Member 객체에 저장하고 올리기 나머진 알아서..
            
            Random random = new Random();
    	    int randomNum = random.nextInt(9000) + 1000;
    	    
            Member m = new Member();
            m.setMemberName(name);
            m.setMemberNickName(nickname + "#" + randomNum);
            m.setMemberProfilePath(profileImg);
            m.setMemberEmail(email);
            m.setMemberPhone(phone);
            m.setMemberBirth(sqlDate);
            m.setMemberId(id);
            m.setMemberLoginType(2);
            m.setMemberAddress(baseAddress);
            m.setMemberPwd("kakao");
            
            
            userInfo.put("userInfo", m);
            
            System.out.println("userInfo:" + userInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userInfo;
    }

	@Override
	public int kakaoLogin(Member kakaoMemberInfo) {
		return mDAO.kakaoLogin(kakaoMemberInfo);
	}

	@Override
	public boolean isEmailDuplicate(String memberEmail) {
		return mDAO.isEmailDuplicate(memberEmail);
	}

	@Override
	public int getReviewListCount(int i) {
		return mDAO.getReviewListCount(i);
	}

	@Override
	public ArrayList<Review> selectReview(String memberId,PageInfo pi) {
		int offset = (pi.getCurrentPage() - 1)*pi.getBoardLimit();
		int limit = pi.getBoardLimit();
		
		RowBounds rowBounds = new RowBounds(offset, limit);
		return mDAO.selectReview(memberId, rowBounds);
	}

	@Override
	public ArrayList<Product> selectAllProduct() {
		return mDAO.selectAllProduct();
	}

	@Override
	public ArrayList<Photo> selectAllPotoProduct() {
		return mDAO.selectAllPotoProduct();
	}

	@Override
	public int deleteReview(int reviewNo) {
		return mDAO.deleteReview(reviewNo);
	}

	@Override
	public String deleteSelectReview(int reviewNo) {
		return mDAO.deleteSelectReview(reviewNo);
	}

//	@Override
//	public int getmyBoardListCount(String memberId,int i) {
//		return mDAO.getmyBoardListCount(memberId,i);
//	}
//
//	@Override
//	public int getmySecondHandProductListCount(Map<String, Object> paramMap) {
//		return mDAO.getmySecondHandProductListCount(paramMap);
//	}

//	@Override
//	public ArrayList<secondHandProduct> selectsecondHandProduct(String memberId, PageInfo pi) {
//		int offset = (pi.getCurrentPage() - 1)*pi.getBoardLimit();
//		int limit = pi.getBoardLimit();
//		
//		RowBounds rowBounds = new RowBounds(offset, limit);
//		return mDAO.selectsecondHandProduct(memberId,rowBounds);
//	}

	@Override
	public ArrayList<CampBoard> selectCampBoard(String memberId, PageInfo pi) {
		int offset = (pi.getCurrentPage() - 1)*pi.getBoardLimit();
		int limit = pi.getBoardLimit();
		
		RowBounds rowBounds = new RowBounds(offset, limit);
		return mDAO.selectCampBoard(memberId,rowBounds);
	}

	@Override
	public ArrayList<secondHandProduct> searchSpList(PageInfo pi, HashMap<String, Object> map) {
		int offset = (pi.getCurrentPage() - 1)*pi.getBoardLimit();
		int limit = pi.getBoardLimit();
		
		RowBounds rowBounds = new RowBounds(offset, limit);
		return mDAO.searchSpList(rowBounds,map);
	}

	@Override
	public int searchSPListCount(HashMap<String, Object> map) {
		return mDAO.searchSPListCount(map);
	}

	@Override
	public ArrayList<CampBoard> searchCbList(PageInfo pi, HashMap<String, Object> map) {
		int offset = (pi.getCurrentPage() - 1)*pi.getBoardLimit();
		int limit = pi.getBoardLimit();
		
		RowBounds rowBounds = new RowBounds(offset, limit);
		return mDAO.searchCbList(rowBounds,map);
	}

	@Override
	public int searchCbListCount(HashMap<String, Object> map) {
		return mDAO.searchCbListCount(map);
	}

	@Override
	public int getmyBoardListCount(Map<String, Object> paramMap) {
		return mDAO.getmyBoardListCount(paramMap);
	}

	@Override
	public Member getMemberLoginType(String memberEmail) {
		return mDAO.getMemberLoginType(memberEmail);
	}



	
}

	