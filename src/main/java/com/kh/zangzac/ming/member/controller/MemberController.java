package com.kh.zangzac.ming.member.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import com.kh.zangzac.common.ImageStorage;
import com.kh.zangzac.common.Pagination;
import com.kh.zangzac.common.model.vo.PageInfo;
import com.kh.zangzac.common.photo.model.vo.Photo;
import com.kh.zangzac.ming.member.model.exception.MemberException;
import com.kh.zangzac.ming.member.model.service.MemberService;
import com.kh.zangzac.ming.member.model.vo.Member;
import com.kh.zangzac.seongun.campboard.model.vo.CampBoard;
import com.kh.zangzac.sohwa.product.model.vo.Product;
import com.kh.zangzac.sohwa.product.model.vo.Review;
import com.kh.zangzac.yoonahrim.spBoard.model.vo.secondHandProduct;

import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@SessionAttributes("loginUser")
@Controller
public class MemberController {
	
	private final ImageStorage imageStorage;

	@Autowired
	public MemberController(ImageStorage imageStorage) {
		this.imageStorage = imageStorage;
    }
	
	@Autowired
	private MemberService mService;
	
	//암호화
	@Autowired
	private BCryptPasswordEncoder bcrypt;
	
	private Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	//메일 인증
	@Autowired
	private JavaMailSender mailSender;
	
	//로그인 화면으로 넘어가기
	@GetMapping("signUp.me")
	public String sign() {
		return "views/ming/member/sign";
	}
	
	
	//심리테스트 메인화면
	@GetMapping("psychologicalTestMain.me")
	public String psychologicalTest() {
		return "views/ming/psychologicalTest/psychologicalTestMain";
	}
	
	//회원가입
	@PostMapping("insertMember.me")
	public String insertMember(@ModelAttribute Member m, @RequestParam("sample6_postcode") String sample6_postcode,
								@RequestParam("sample6_address") String sample6_address,@RequestParam("sample6_detailAddress") String sample6_detailAddress,
								@RequestParam("sample6_extraAddress") String sample6_extraAddress, @RequestParam("existingNickname") String existingNickname, Model model) {
		
		String address = null;
		if(!sample6_postcode.trim().equals("")) {
			 address = sample6_postcode + "@" + sample6_address + "@" + sample6_detailAddress + "@" + sample6_extraAddress;
		}
		
		m.setMemberAddress(address);
		
		m.setMemberNickName(existingNickname + "#" + generateRandomNumbers()); // 랜덤닉네임
		m.setMemberPwd(bcrypt.encode(m.getMemberPwd()));
		m.setMemberLoginType(1); // 일반 회원가입
		m.getMemberId();
		
		
		int result = mService.insertMember(m);
		if(result > 0) {
		   return "redirect:/";
	    } else {
	    	model.addAttribute("msg", "회원가입에 실패하였습니다.");
			return "views/ming/member/sign";
	    }
	}
	
	
	//아이디 중복체크
	@RequestMapping(value = "checkId.me")
	@ResponseBody
	public String checkId(@RequestParam("memberId") String memberId) {
		int count = mService.checkId(memberId);
		String result = count ==  0 ? "yes" : "no";
		
		return result;
	}
	
	
	//이메일 중복체크
	@RequestMapping(value = "checkEmail.me")
	@ResponseBody
	public String checkEmail(@RequestParam("memberEmail") String memberEmail) {
	    int count = mService.checkEmail(memberEmail);

	    if (count == 0) {
	        return "yes";
	    } else {
	        Member result = mService.getMemberLoginType(memberEmail);
	        
	        if (result != null) {
	            System.out.println("kakao");
	            return "kakao";
	        } else {
	            System.out.println("no");
	            return "no";
	        }
	    }
	}
	
	
	private String generateRandomNumbers() {
	    // 4자리 랜덤 숫자 생성
	    Random random = new Random();
	    int randomNum = random.nextInt(9000) + 1000; // 1000 이상 9999 이하의 숫자
	    return String.valueOf(randomNum);
	}
	
	//개인정보 동의
	@GetMapping("agreement.me")
	public String agreement() {
		return "views/ming/member/agreement";
	}
	
	//로그인
	@PostMapping("login.me")
	public String loginUser(@ModelAttribute Member m , Model model, @RequestParam("beforeURL") String beforeURL) {
		Member loginUser = mService.login(m);
		
		if(loginUser != null) {
			if(bcrypt.matches(m.getMemberPwd(), loginUser.getMemberPwd())) {
				model.addAttribute("loginUser",loginUser);
				
				if (beforeURL != null && (beforeURL.equals("http://localhost:8080/logout.me") || beforeURL.equals("http://localhost:8080/signUp.me") ||
										beforeURL.equals("http://192.168.20.207:8080/logout.me") || beforeURL.equals("http://192.168.20.207:8080/signUp.me"))){
					return "redirect:" + beforeURL;
				}else if(beforeURL.equals(beforeURL.equals("http://192.168.20.207:8080/login.me") || beforeURL.equals("http://localhost:8080/login.me")) ) {
					return "redirect:/";
				}else {
					return "redirect:/";
				}
			}else {
				model.addAttribute("msg", "로그인에 실패하였습니다.\n아이디와 비밀번호를 다시 확인해주세요.");
				model.addAttribute("searchUrl","views/ming/member/sign");
				return "views/ming/member/sign";
				
			}
			
		}else {
			model.addAttribute("msg", "로그인에 실패하였습니다.\n아이디와 비밀번호를 다시 확인해주세요.");
			model.addAttribute("searchUrl","views/ming/member/sign");
			return "views/ming/member/sign";
		}
	}
	
	//로그아웃
	@GetMapping("logout.me")
	public String logout(SessionStatus status) {
		status.setComplete();
		return "redirect:/";
	}
	
	//아이디 / 비밀번호 화면
	@GetMapping("signIn.me")
	public String find() {
		return "views/ming/member/find";
	}
	
	// 아이디 찾기
	@PostMapping(value ="selectId.me",produces = "aplication/json; charset=UTF-8")
	@ResponseBody
	public String selectId(@ModelAttribute Member m, Model model) {
		
		ArrayList<Member> list = mService.selectId(m);
		JSONArray jArr = new JSONArray();
		
		 for(Member member : list) {
	         JSONObject json = new JSONObject();
	         json.put("memberId", member.getMemberId());
	         
	         jArr.put(json);
	      }
	      return jArr.toString();
	  }
	
	
	//이메일 인증
	@RequestMapping(value ="mailCheck.me", method = RequestMethod.GET ,produces = "aplication/json; charset=UTF-8")
	@ResponseBody
	public String email(@RequestParam("memberEmail") String to)throws Exception {
		   Random r = new Random();
	       int checkNum = r.nextInt(888888) + 111111;
	         
	       String subject = "[ZangZac]인증코드";                   // 제목
	       String content = "인증코드 ["+checkNum+"] 입니다.";    // 내용
	       String from = "park718513@naver.com";
	       
	       try {
	    	   MimeMessage mail = mailSender.createMimeMessage();
	           MimeMessageHelper mailHelper = new MimeMessageHelper(mail,true,"UTF-8");
	           
	           mailHelper.setFrom(from);                // 보낼사람    
	           mailHelper.setTo(to);                   // 받을사람
	           mailHelper.setSubject(subject);          // 제목
	           mailHelper.setText(content, true);          // 내용
	               
	               
	           mailSender.send(mail);
	               
	       } catch(Exception e) {
	          e.printStackTrace();
	       }
	          return checkNum+"";
	     }
	
	
	// 비밀번호 재설정
	@GetMapping(value ="pwdReset.me")
	@ResponseBody
	public String pwdReset(@RequestParam("memberPwdCode") String to, @ModelAttribute Member m)throws Exception {
		
		//임시 비밀번호 랜덤 코드
		char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

        String str = "";
        int idx = 0;
        for (int i = 0; i < 10; i++) {
            idx = (int) (charSet.length * Math.random());
            str += charSet[idx];
        }
        String encPwd = bcrypt.encode(str);
        m.setMemberId(m.getMemberId());
        m.setMemberPwd(encPwd);
        //System.out.println(encPwd);
        //System.out.println(m);
        
        int result = mService.updateNewPwd(m);
        
        String subject = "[ZangZac]임시 비밀번호";		// 제목
		String content = "임시비밀번호는 [ "+str+" ] 입니다.";    // 내용
		String from = "park718513@naver.com";
		 
		 try {
	    	   MimeMessage mail = mailSender.createMimeMessage();
	           MimeMessageHelper mailHelper = new MimeMessageHelper(mail,true,"UTF-8");
	           
	           mailHelper.setFrom(from);                // 보낼사람    
	           mailHelper.setTo(to);                   // 받을사람
	           mailHelper.setSubject(subject);          // 제목
	           mailHelper.setText(content, true);          // 내용
	               
	               
	           mailSender.send(mail);
	               
	       } catch(Exception e) {
	          e.printStackTrace();
	       }

        
        
        if (result > 0) {
        } else {
        }
         
	  return str+"";
		 
	}
	
	// 마이페이지로 가기
	@GetMapping("myPage.me")
	public String myPage() {
		return "views/ming/member/myPage";
	}
	
	@GetMapping("deleteMember.me")
	   public String deleteMember(Model model) {
	      Member m = (Member)model.getAttribute("loginUser");
	      
	      String memberId = m.getMemberId();
	      int result = mService.deleteMember(memberId);
	      
	      if(result > 0) {
	         return "redirect:logout.me";
	      }else {
	         model.addAttribute("msg","회원탈퇴실패");
	         return "views/ming/member/myPage";
	      }
	      
	   }
	
	
	//비밀번호 변경
	@GetMapping("updatePwd.me")
	public String updatePwdView() {
		return "views/ming/member/updatePwd";
	}
	
	@PostMapping("changePwd.me")
	public String changePwd(@RequestParam("currentPwd")String currentPwd, @RequestParam("newMemberPwd")String newMemberPwd, Model model) {
		
		Member m = (Member)model.getAttribute("loginUser");
		
		if(bcrypt.matches(currentPwd, m.getMemberPwd())) {
			 HashMap<String, String> map = new HashMap<String, String>();
			 
			 map.put("memberId", m.getMemberId());
			 map.put("memberPwd", bcrypt.encode(newMemberPwd));
			 
			 
			 int result = mService.changePwd(map);
			 
			 if(result > 0) {
				 model.addAttribute("loginUser", mService.login(m));
				 return "redirect:myPage.me";
			 } else {
				 model.addAttribute("msg", "비밀번호 수정에 실패하였습니다.\n비밀번호를 다시 확인해주세요.");
				 model.addAttribute("searchUrl","views/ming/member/updatePwd");
				 return "views/ming/member/updatePwd";
			 }
		 }else {
			 model.addAttribute("msg", "로그인에 실패하였습니다.\n아이디와 비밀번호를 다시 확인해주세요.");
			 model.addAttribute("searchUrl","views/ming/member/updatePwd");
			 return "views/ming/member/updatePwd";
		 }
		 
	}
	
	// 마이페이지 수정
	@PostMapping("updateMemberName.me")
	public String updateName(@RequestParam("memberName")String memberName, Model model) {
		Member m = (Member)model.getAttribute("loginUser");
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("memberId", m.getMemberId());
		map.put("memberName", memberName);
		
		int result = mService.updateMemberName(map);
		
		if(result > 0) {
			model.addAttribute("loginUser", mService.login(m));
			return "redirect:myPage.me";
		}else {
			model.addAttribute("msg", "비밀번호 수정에 실패하였습니다.\n비밀번호를 다시 확인해주세요.");
			model.addAttribute("searchUrl","views/ming/member/updatePwd");
			return "redirect:myPage.me";
		}
		
	}
	
	@PostMapping("updateMemberNickName.me")
	public String updateNickName(@RequestParam("existingNickname")String existingNickname, Model model) {
		Member m = (Member)model.getAttribute("loginUser");
		String memberNickName = null;
		memberNickName = existingNickname + "#" + generateRandomNumbers();
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("memberId", m.getMemberId());
		map.put("memberNickname", memberNickName);
		
		int result = mService.updateMemberNickname(map);
		
		if(result > 0) {
			model.addAttribute("loginUser", mService.login(m));
			return "redirect:myPage.me";
		}else {
			model.addAttribute("msg", "비밀번호 수정에 실패하였습니다.\n비밀번호를 다시 확인해주세요.");
			model.addAttribute("searchUrl","views/ming/member/updatePwd");
			return "redirect:myPage.me";
		}
		
	}
	
	@PostMapping("updateMemberPhone.me")
	public String updatePhone(@RequestParam("memberPhone")String memberPhone, Model model) {
		Member m = (Member)model.getAttribute("loginUser");
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("memberId", m.getMemberId());
		map.put("memberPhone", memberPhone);
		
		int result = mService.updatememberPhone(map);
		if(result > 0) {
			model.addAttribute("loginUser", mService.login(m));
			return "redirect:myPage.me";
		}else {
			model.addAttribute("msg", "비밀번호 수정에 실패하였습니다.\n비밀번호를 다시 확인해주세요.");
			model.addAttribute("searchUrl","views/ming/member/updatePwd");
			return "redirect:myPage.me";
		}
	}
	
	@PostMapping("updateMemberEmail.me")
	public String updateEmail(@RequestParam("memberEmail") String memberEmail, Model model) {
		Member m = (Member)model.getAttribute("loginUser");
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("memberId", m.getMemberId());
		map.put("memberEmail", memberEmail);
		
		int result = mService.updatememberEmail(map);
		if(result > 0) {
			model.addAttribute("loginUser", mService.login(m));
			return "redirect:myPage.me";
		}else {
			model.addAttribute("msg", "비밀번호 수정에 실패하였습니다.\n비밀번호를 다시 확인해주세요.");
			model.addAttribute("searchUrl","views/ming/member/updatePwd");
			return "redirect:myPage.me";
		}
	}
	
	@PostMapping("updateMemberAddress.me")
	public String updateAddress(@RequestParam("sample6_postcode") String sample6_postcode, Model model,
								@RequestParam("sample6_address") String sample6_address,@RequestParam("sample6_detailAddress") String sample6_detailAddress,
								@RequestParam("sample6_extraAddress") String sample6_extraAddress) {
		
		Member m = (Member)model.getAttribute("loginUser");
		String address = null;
		if(!sample6_postcode.trim().equals("")) {
			 address = sample6_postcode + "@" + sample6_address + "@" + sample6_detailAddress + "@" + sample6_extraAddress;
		}
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("memberId", m.getMemberId());
		map.put("memberAddress", address);
		
		int result = mService.updatememberAddress(map);
		if(result > 0) {
			model.addAttribute("loginUser", mService.login(m));
			return "redirect:myPage.me";
		}else {
			model.addAttribute("msg", "비밀번호 수정에 실패하였습니다.\n비밀번호를 다시 확인해주세요.");
			model.addAttribute("searchUrl","views/ming/member/updatePwd");
			return "redirect:myPage.me";
		}
		
	}
	
	//프로필 등록
	@PostMapping("updateProfile.me")
	public String updateProfile(@RequestParam("memberProfile") MultipartFile file, @ModelAttribute Member m, Model model) {
		Member user = ((Member)model.getAttribute("loginUser"));
		String defaultProfile = "https://storage.googleapis.com/zangzac/image/ming/BasicProfile.png";
		String[] profileResult = null;
		int result = 0;
		
		profileResult = imageStorage.saveImage(file, "ming");
		m.setMemberProfileRename(profileResult[0]);
		m.setMemberProfilePath(profileResult[1]);
		m.setMemberId(user.getMemberId());
		
		
		if (profileResult[1] != defaultProfile) {
		    imageStorage.deleteImage(user.getMemberProfilePath(),"ming");
		}
		
		result = mService.updateMemberProfile(m);
		
		if(result > 0) {
			model.addAttribute("loginUser", mService.login(m));
			return "redirect:myPage.me";
		}else {
			model.addAttribute("msg", "비밀번호 수정에 실패하였습니다.\n비밀번호를 다시 확인해주세요.");
			model.addAttribute("searchUrl","views/ming/member/updatePwd");
			return "redirect:myPage.me";
		}

	}
	
	// 프로필 삭제
	@GetMapping("deleteProfile.me")
	public String deleteProfile(Model model) {
		
		
		Member user = ((Member)model.getAttribute("loginUser"));
		String defaultProfile = "https://storage.googleapis.com/zangzac/image/ming/BasicProfile.png";
		
		user.setMemberProfileRename("");
		user.setMemberProfilePath(defaultProfile);
		
		int result = mService.updateMemberProfile(user);
		
		return "redirect:myPage.me";
	}
	
	//관리자 페이지
	@GetMapping("adminPage.me")
	public String adminPage(Model model, HttpServletRequest request, @RequestParam(value="page", defaultValue="1") int page,
							@RequestParam(value = "searchType", defaultValue = "") String searchType,
							@RequestParam(value = "keyword", defaultValue = "")String keyword,@ModelAttribute Member m) {
		
		HashMap<String, String>map = new HashMap<>();
		map.put("keyword", keyword);
		map.put("searchType", searchType);

		int currentPage = page;
		int listCount = mService.searchList(map);
		PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 5);
		ArrayList<Member> list = mService.searchtNoticeList(pi, map);
		
		if(list != null) {
			model.addAttribute("searchType",searchType);
			model.addAttribute("keyword",keyword);
			model.addAttribute("pi", pi);
			model.addAttribute("list", list);
			model.addAttribute("loc", request.getRequestURI()); // url 다 가져옴 / uri 뒤에만 가져옴
			return "views/ming/admin/allMemberList";
		} else {
			throw new MemberException("게시글 목록 조회에 실패하였습니다.");
		}
	}
	
	
	@GetMapping("selectMemberList.me")
	@ResponseBody
	public HashMap<String, Object> selectMemberList(@RequestParam("memberId")String memberId, Model model) {
		
		Member m = new Member();
		m.setMemberId(memberId);
		Member member = mService.login(m);
		
		HashMap<String, Object> map = new HashMap<>();
		map.put("m", member);
		
		return map;
	}
	
	@GetMapping("updateInfo.me")
	@ResponseBody
	public String updateInfo(@RequestParam("column") String column, @RequestParam("data") String data, @RequestParam("id") String id) {
		Properties prop = new Properties();
		
		prop.setProperty("column", column);
		prop.setProperty("data", data);
		prop.setProperty("id", id);
		
		int result = mService.updateInfo(prop);
		return result == 1? "success" : "fail";
	}
	
	@GetMapping("adminUpdateNickName.me")
	@ResponseBody
	public String adminUpdateNickName(@ModelAttribute Member m) {
		String memberNickName = null;
		memberNickName = m.getMemberNickName() + "#" + generateRandomNumbers();
		
		
		m.setMemberNickName(memberNickName);
		
		int result = mService.adminUpdateNickName(m);
		
		return result == 1? "success" : "fail";
	}
	
	@GetMapping("adminUpdateName.me")
	@ResponseBody
	public String adminUpdateName(@ModelAttribute Member m) {
		String memberName = m.getMemberName();
		
		int result = mService.adminUpdateName(m);
		return result == 1? "success" : "fail";
	}
	
	
	//카카오로그인
	@GetMapping("kakaoLogin")
	public String kakaoLogin(
	        @RequestParam(value = "code", required = false) String code,
	        HttpSession session,
	        Model model,
	        @RequestParam(value = "beforeURL", required = false) String beforeURL) throws Exception {
	    // 카카오에서 받은 코드를 이용하여 access token을 얻어옴
	    String access_Token = mService.getAccessToken(code);
	    HashMap<String, Object> userInfo = mService.getUserInfo(access_Token);
	    Member kakaoMemberInfo = (Member) userInfo.get("userInfo");
	    // System.out.println("kakaoMemberInfo: " + kakaoMemberInfo);
	    
	    // 이메일 중복 체크
	    boolean isEmailDuplicate = mService.isEmailDuplicate(kakaoMemberInfo.getMemberEmail());
	    Member loginUser = mService.login(kakaoMemberInfo);
	    
	    if (isEmailDuplicate) {
	        // 중복된 이메일이 있는 경우
	    	session.setAttribute("loginUser", loginUser);
	    	session.setAttribute("msg", "중복되는 이메일이 있습니다.");
	            return "redirect:/";
	        // 여기서 로그인 처리 등을 수행
	        // 로그인만 가능하게끔 수정 http://192.168.20.207:8080/
	    } else {
	        // 중복된 이메일이 없는 경우
	        int result = mService.kakaoLogin(kakaoMemberInfo);
	        if (result > 0) {
	            // 로그인 성공
	            session.setAttribute("loginUser", kakaoMemberInfo);
	            if (beforeURL != null && (beforeURL.equals("http://localhost:8080/logout.me") || beforeURL.equals("http://localhost:8080/signUp.me") ||
											beforeURL.equals("http://192.168.20.207:8080/logout.me") || beforeURL.equals("http://192.168.20.207:8080/signUp.me"))) {
	                return "redirect:" + beforeURL;
	            } else {

	            	return "redirect:/";
	            }
	        } else {
	        	System.out.println("로그인 실패");
	            // 회원가입에 실패한 경우에 대한 처리
	            session.setAttribute("msg", "회원가입에 실패했습니다.");
	        }
	    }

	    return "views/ming/member/sign";
	}
	
	
	//리뷰 불러오기
	@GetMapping("review.me")
	public String review(@RequestParam(value = "page", defaultValue = "1") int page, Model model,
										@ModelAttribute Review r,@ModelAttribute Product p, HttpServletRequest request) {
		Member loginUser = (Member) model.getAttribute("loginUser");
		String memberId = loginUser.getMemberId();
		int listCount = mService.getReviewListCount(6);
		PageInfo pi = Pagination.getPageInfo(page, listCount, 3);
		ArrayList<Review>rList = mService.selectReview(memberId,pi);
		ArrayList<Product>pList = mService.selectAllProduct();
		ArrayList<Photo>phList = mService.selectAllPotoProduct();
		r.setMemberId(memberId);
		
		if(rList != null) {
			model.addAttribute("pList",pList);
			model.addAttribute("rList",rList);
			model.addAttribute("phList",phList);
			model.addAttribute("pi",pi);
			model.addAttribute("memberId", memberId);
			model.addAttribute("loc", request.getRequestURI());
			return "views/ming/member/review";
		}else {
			throw new MemberException("게시글 목록 조회에 실패하였습니다.");
		}
		
	}
	
	//리뷰삭제
	   @GetMapping("deleteReview.me")
	   @ResponseBody
	   public String deleteReview(@RequestParam("reviewNo") int reviewNo) {
	      
	      String name="sohwa";
	      int result = mService.deleteReview(reviewNo);
	      String deleteRename = mService.deleteSelectReview(reviewNo);
	      
	      if(deleteRename != null) {
	    	  Boolean sf = imageStorage.deleteImage(deleteRename, name);
	    	  
	      }
	      
	      if(result > 0) {
	         return "redirect:review.me";
	      }else {
	         throw new MemberException("리뷰 삭제 실패");
	      }
	   }
	   
	   @GetMapping("myBoardList.me")
	   public String searchSpList(@RequestParam(value = "page", defaultValue = "1") int page,
									@RequestParam(value = "searchType", defaultValue = "") String searchType,
									@RequestParam(value = "keyword", defaultValue = "")String keyword, Model model,
									HttpServletRequest request, @ModelAttribute CampBoard cb){
		   Member loginUser = (Member) model.getAttribute("loginUser");
		   String memberId = loginUser.getMemberId();
		   
		   HashMap<String, Object>map = new HashMap<>();
			map.put("keyword", keyword);
			map.put("searchType", searchType);
			map.put("memberId", memberId);
			map.put("1", 1);
			
			int currentPage = page;
			int listCount = mService.searchCbListCount(map);
			
			PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 5);
			ArrayList<CampBoard> cbList = mService.searchCbList(pi, map);
			
			if(cbList != null) {
				model.addAttribute("pi", pi);
				model.addAttribute("cbList", cbList);
				model.addAttribute("loc", request.getRequestURI());// url 다 가져옴 / uri 뒤에만 가져옴
				model.addAttribute("searchType",searchType);
				model.addAttribute("keyword",keyword);
				model.addAttribute("memberId", memberId);
				return "views/ming/member/myBoardList";
			} else {
				throw new MemberException("게시글 목록 조회에 실패하였습니다.");
			}
		}
	   

	   //중고 검색
	   @GetMapping("mySecondHandProductList.me")
	   public String searchSpList(@RequestParam(value = "page", defaultValue = "1") int page,
									@RequestParam(value = "searchType", defaultValue = "") String searchType,
									@RequestParam(value = "keyword", defaultValue = "")String keyword, Model model,
									HttpServletRequest request,@ModelAttribute secondHandProduct sp){
		   
		   Member loginUser = (Member) model.getAttribute("loginUser");
		   String memberId = loginUser.getMemberId();
		   
		   HashMap<String, Object>map = new HashMap<>();
			map.put("keyword", keyword);
			map.put("searchType", searchType);
			map.put("memberId", memberId);
			map.put("4", 4);
			
			int currentPage = page;
			int listCount = mService.searchSPListCount(map);
			
			PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 5);
			ArrayList<secondHandProduct> spList = mService.searchSpList(pi, map);
			
			
			if(spList != null) {
				model.addAttribute("pi", pi);
				model.addAttribute("spList", spList);
				model.addAttribute("loc", request.getRequestURI());// url 다 가져옴 / uri 뒤에만 가져옴
				model.addAttribute("searchType",searchType);
				model.addAttribute("keyword",keyword);
				model.addAttribute("memberId", memberId);
				return "views/ming/member/mySecondHandProductList";
			} else {
				throw new MemberException("게시글 목록 조회에 실패하였습니다.");
			}
		}
	   
	
   }
