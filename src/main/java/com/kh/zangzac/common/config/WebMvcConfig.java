package com.kh.zangzac.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.kh.zangzac.common.interceptor.CheckLoginInterceptor;


@Configuration // 설정파일 이노테이션
public class WebMvcConfig implements WebMvcConfigurer {
	
	@Override // interceptor 등록
	public void addInterceptors(InterceptorRegistry registry) {
		// 일반 계정
		registry.addInterceptor(new CheckLoginInterceptor()) // 매개변수 : HandleInterceptor 상속/ 다형성 적용

				.addPathPatterns("/adminPage.me", "/selectMemberList.me", "/review.me", "/updatePassword.me", "/deleteMember.me","/adminChatroom.jy",
						"/adminProductEnroll.so", "/adminProductUpdate.so", "/adminQnaListView.so", "/adminProductList.so", "/insertAnswer.so","/deleteQna.s",
						"/myOrderPageView.so", "/deleteOrder.so", "/detailOrderView.so", "/insertPayment.so", "/adminOrderListView.so","/adminOrderDetail.so",
						"/productUpdate.so", "/updateDeliveryStatus.so", "/detailWrite.ys", "/campInsert.ys", "/campUpdate.ys","/stateUpdate.ys",
						"/selectUpdate.ys","/selectUpdate.ys");		
	}
	
}
