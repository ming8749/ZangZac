package com.kh.zangzac.jaeyoung.chat.model.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Chatter {
	private int chatterNo;
	private int clNo;
	private String memberId;
	private String memberNickName;

}
