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
public class ChatRoom {
	private int clNo;
	private int clLimitPerson;
	private String clName;
	private String categoryNo;
	private String categoryName;
	private int clNowPerson;
}
