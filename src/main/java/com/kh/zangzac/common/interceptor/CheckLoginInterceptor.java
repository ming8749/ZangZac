package com.kh.zangzac.common.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.kh.zangzac.ming.member.model.vo.Member;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
											// interceptor 역할한다고 알려주기
public class CheckLoginInterceptor implements HandlerInterceptor {
	// select하기 전에 로그인이 되어있는지 확인하기위해 preHandle 사용
	// 컨트롤러에 넘어갈건지 말건지 처리하기 때문에 boolean 반환
	// postHandle을 사용하면 굳이 안해도될 처리를 할 수 있기 때문에 X
	@Override // 선언부가 부모 메소드와 같아야함
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		HttpSession session = request.getSession();
		Member loginUser = (Member)session.getAttribute("loginUser");
		
		// 비회원으로 상세보기 이동 막기
		if(loginUser == null) {
			String url = request.getRequestURI(); // uri 뽑아오기
			String msg = null;
			if(url.contains("adminPage.me") || url.contains("deleteMember.me")) { // ~를 가지고 있다면
				msg = "로그인 후 이용하세요.";
			} else {
				msg = "로그인 세션이 만료되어 로그인 화면으로 넘깁니다";
			}
			response.setContentType("text/html; charset=UTF-8");

			response.getWriter().write("<script>alert('"+msg+"'); location.href='/';</script>");

			
			return false; // 못 넘어가게 막기
		}
		
		return HandlerInterceptor.super.preHandle(request, response, handler); // 무조건 true 반환
	}
	
}
