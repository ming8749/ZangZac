package com.kh.zangzac.ming.member.controller;

import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;

@Controller
public class MessageController {
	
final DefaultMessageService messageService;
	
	public MessageController() {
        // 반드시 계정 내 등록된 유효한 API 키, API Secret Key를 입력해주셔야 합니다!
        this.messageService = NurigoApp.INSTANCE.initialize("NCSNP3PM6OCZLCNA", "QNUF6LIQCBDLLYTYFYUVJUEKIIXPEFMN", "https://api.coolsms.co.kr");
    }
	
	@GetMapping("/send-one")
	@ResponseBody
    public String sendOne(@RequestParam("phone")String phone) {
	    Random r = new Random();
	    int checkNum = r.nextInt(888888) + 111111;
        Message message = new Message();
        // 발신번호 및 수신번호는 반드시 01012345678 형태로 입력되어야 합니다.
        message.setFrom("01057410416");
        message.setTo(phone);
        String msg="[ZangZac]본인확인을 위해 인증번호 ["+checkNum + "]를 입력해주세요.";
        
        message.setText(msg);

        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));

        return checkNum+"";
    }
	
}
