package com.kh.zangzac.jaeyoung.chat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.kh.zangzac.jaeyoung.chat.model.service.ChatService;
import com.kh.zangzac.jaeyoung.chat.model.vo.ChatSession;
import com.kh.zangzac.jaeyoung.chat.model.vo.Chatter;


/*
 *chatType(1) : 단체채팅
 *chatType(2) : 1:1채팅
 */
@Component
public class SocketHandler extends TextWebSocketHandler {
   
   //HashMap<String, WebSocketSession> sessionMap = new HashMap<>(); //웹소켓 세션을 담아둘 맵
   List<HashMap<String, Object>> rls = new ArrayList<>(); //웹소켓 세션을 담아둘 리스트 ---roomListSessions
   
   //채팅자들 저장하는 변수
   private ArrayList<ArrayList<Chatter>> chatterList = new ArrayList<ArrayList<Chatter>>();
   
   private boolean first = true;
   
   @Autowired
   ChatFileManager cFileManager;
   
   //Service
   @Autowired
   ChatService cService;
   
   public SocketHandler() {

   }
   
   
  @SuppressWarnings("unchecked")
  @Override
   public void handleTextMessage(WebSocketSession session, TextMessage message) {
      //메시지 발송
      String msg = message.getPayload();
      JSONObject obj = jsonToObjectParser(msg);
      ArrayList<Chatter> nowChatter =new ArrayList<Chatter>();
      
      String rN = (String) obj.get("roomName");
      HashMap<String, Object> temp = new HashMap<String, Object>();
      if(rls.size() > 0) {
         for(int i=0; i<rls.size(); i++) {
            String roomNumber = (String) rls.get(i).get("roomName"); //세션리스트의 저장된 방번호를 가져와서
            if(roomNumber.equals(rN)) { //같은값의 방이 존재한다면
               temp = rls.get(i); //해당 방번호의 세션리스트의 존재하는 모든 object값을 가져온다.
               break;
            }
         }
         
         //카운트 쌓아주기
         if(obj.get("chatType").equals("1")) { // 단체 채팅 
        	   int chatNum = Integer.parseInt((String)obj.get("roomName"));
        	   //전체채팅리스트 - 현재채팅리스  
        	   obj.put("unReadChatter",chatterList.get(chatNum).size()-(temp.size()-1));
         }
         
         //해당 방의 세션들만 찾아서 메시지를 발송해준다.
         for(String k : temp.keySet()) { 
            if(k.equals("roomName")) { //다만 방번호일 경우에는 건너뛴다.
               continue;
            }
            //현재 채팅방 리스트 쌓아주기
            Chatter c= new Chatter();
            c.setMemberId( ((ChatSession)temp.get(k)).getId());
            nowChatter.add(c);
            
            //각 세션에 보내기
            WebSocketSession wss = (WebSocketSession)((ChatSession)(temp.get(k))).getSession();
            if(wss != null) {
               try {
                  wss.sendMessage(new TextMessage(obj.toJSONString()));
               } catch (IOException e) {
                  e.printStackTrace();
               }
            }
         }
         
      // 채팅타입에 따라 설정해주기
         if(obj.get("chatType").equals("1")) { // 단체 채팅 
      	   int chatNum = Integer.parseInt((String)obj.get("roomName"));
      	         	   
      	   //전체채팅리스트 - 현재채팅리스  
      	   obj.put("unReadChatter",unReadChatter(chatterList.get(chatNum), nowChatter));
      	   
          }
         cFileManager.saveChat(obj);
      }
   }
   
   @SuppressWarnings("unchecked")
   @Override
   public void afterConnectionEstablished(WebSocketSession session) throws Exception {
	 //소켓 연결
	   if(first) {
		 //채팅방 개수 가져오기
		   //1번방 저장 // 2번방 3번방 저장
		   for(int i=0;i<4;i++) { // 
			   //방 번호에 따라 가져오기 
			   ArrayList<Chatter> temp = cService.selectChatterList(i);
			   chatterList.add(temp);
		   }
		   first=false;
	   }
	   
      //소켓 연결
      super.afterConnectionEstablished(session);
      boolean flag = false;
      String url = session.getUri().toString();
      
      //내아이디와 상대아이디를 저장한다.
      String myId = url.split("/chating/")[1].split("/")[0];
      String oppoentId = url.split("/chating/")[1].split("/")[1];
      int chatType = Integer.parseInt(url.split("/chating/")[1].split("/")[2]);
      
      //방 타입에 따라 방이름 만들기
      String roomName = "";
      if(chatType ==1) { //단체 채팅
    	roomName = oppoentId; // 받아온 값 룸네임 바로 넣기  
      }else if(chatType==2) { //1:1 채팅
    	  
    	  //두 숫자를 비교
    	  int result = myId.compareTo(oppoentId);
    	  
    	  //정렬에 따라 룸네임 정하기
          if (result < 0) {
              roomName = myId+"@"+oppoentId;
          } else if (result > 0) {
        	  roomName = oppoentId+"@"+myId;
          } 
    	  
      }
      boolean check =false;
      //DB, 파일 채팅 설정
      // 1. 단체 채팅인 경우 채팅방에 입장 했는지 DB에서 확인하기
      if(chatType == 1) {
    	  //현재 입장한 방에 내가 있는지 체크
    	  Chatter checkChatter = new Chatter();
    	  checkChatter.setMemberId(myId);
    	  checkChatter.setClNo(Integer.parseInt(roomName));
    	  int count = cService.checkCount(checkChatter);
    	  
    	  if(count>0) {
    		  // 채팅방에 이미 있는 상태라면
    		  
    		  check = cFileManager.updateUnreadChatter(roomName,myId);
    		  
    		  //입장한 채팅방에 메시지를 보내 갱신시키게 만들거.
    		  
    	      JSONObject obj = new JSONObject();
    	      
    	      HashMap<String, Object> temp = new HashMap<String, Object>();
    	      if(rls.size() > 0) {
    	         for(int i=0; i<rls.size(); i++) {
    	        	  String roomNumber = (String) rls.get(i).get("roomName"); //세션리스트의 저장된 방번호를 가져와서
    	        	 if(roomNumber.equals(roomName)) { //같은값의 방이 존재한다면
    	               temp = rls.get(i); //해당 방번호의 세션리스트의 존재하는 모든 object값을 가져온다.
    	               break;
    	            }
    	         }
    	       if(check) {  
	    	         //해당 방의 세션들만 찾아서 메시지를 발송해준다.
	    	         for(String k : temp.keySet()) { 
	    	            if(k.equals("roomName")) { //다만 방번호일 경우에는 건너뛴다.
	    	               continue;
	    	            }
	    	            
	    	            obj.put("type", "reset");
	    	            WebSocketSession wss = (WebSocketSession)((ChatSession)(temp.get(k))).getSession();
	    	            if(wss != null) {
	    	               try {
	    	                  wss.sendMessage(new TextMessage(obj.toJSONString()));
	    	               } catch (IOException e) {
	    	                  e.printStackTrace();
	    	               }
	    	            }
	    	         }
    	       	 }
    	      }
    		  
    	  }else {
    		 //DB에 정보 정보 추가하기
    		  int result = cService.insertChatRoom(checkChatter);
    		  
    		  
    		  //갱신된 리스트 다시 받아 저장하기
    		  ArrayList<Chatter> changeList = cService.selectChatterList(Integer.parseInt(roomName));
    		  chatterList.set(Integer.parseInt(roomName), changeList);
    	  }
      }else if(chatType == 2) {
    	  
      }
      
      
      //컨트롤러 세션 방 설정
      int idx = rls.size(); //방의 사이즈를 조사한다.
      //있는 방인지 찾기
      if(rls.size() > 0) {
         for(int i=0; i<rls.size(); i++) {
            String rN = (String) rls.get(i).get("roomName");
            if(rN.equals(oppoentId)) {
               flag = true;
               idx = i;
               break;
            }
         }
      }
      
      //아이디값 과 세션 정보를 가진 값들
      ChatSession temp = new ChatSession();
      temp.setId(myId);
      temp.setSession(session);
      
      if(flag) { //존재하는 방이라면 세션만 추가한다.
         HashMap<String, Object> map = rls.get(idx);
         map.put(session.getId(), temp);
      }else { //최초 생성하는 방이라면 방번호와 세션을 추가한다.
         HashMap<String, Object> map = new HashMap<String, Object>();
         map.put("roomName", roomName); // 방이름 혹은  채팅하는 아이디 값으로 저장한다.
         map.put(session.getId(), temp);
         rls.add(map);
      }
      
      //세션등록이 끝나면 발급받은 세션ID값의 메시지를 발송한다.
      JSONObject obj = new JSONObject();
      obj.put("type", "getId");
      obj.put("sessionId", session.getId());
      obj.put("roomName", roomName);
      obj.put("sub", check);
     
      
      session.sendMessage(new TextMessage(obj.toJSONString()));
   }
   
   @Override
   public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
      //소켓 종료
      if(rls.size() > 0) { //소켓이 종료되면 해당 세션값들을 찾아서 지운다.
         for(int i=0; i<rls.size(); i++) {
            rls.get(i).remove(session.getId());
         }
      }
      super.afterConnectionClosed(session, status);
   }
   
   private static JSONObject jsonToObjectParser(String jsonStr) {
      JSONParser parser = new JSONParser();
      JSONObject obj = null;
      try {
         obj = (JSONObject) parser.parse(jsonStr);
      } catch (ParseException e) {
         e.printStackTrace();
      }
      return obj;
   }
   
   
   public ArrayList<String> unReadChatter(ArrayList<Chatter> allChatter, ArrayList<Chatter> nowChatter) {
	    ArrayList<String> unReadChatterList = new ArrayList<>();

	    for (Chatter chatter : allChatter) {
	        // Assuming that getId() returns the id of the Chatter object
	        if (!containsId(nowChatter, chatter.getMemberId())) {
	            unReadChatterList.add(chatter.getMemberId());
	        }
	    }

	    return unReadChatterList;
	}

	public boolean containsId(ArrayList<Chatter> chatterList, String targetId) {
	    for (Chatter chatter : chatterList) {
	        // Assuming that getId() returns the id of the Chatter object
	        if (chatter.getMemberId().equals(targetId)) {
	            return true;
	        }
	    }
	    return false;
	}
}