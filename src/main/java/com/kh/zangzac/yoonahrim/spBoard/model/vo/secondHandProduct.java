package com.kh.zangzac.yoonahrim.spBoard.model.vo;


import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class secondHandProduct {
	private int spNo;
	private String spTitle;
	private int spPrice;
	private String spContent;
	private String spAddress;
	private String spIsSell;
	private String spStatus;
	private String memberId;
	private int categoryNo;
	private String spIsBook;
	private Date spCreateDate;
	private Date spModifyDate;
	
	private String categoryName;
	private String memberName;
	private String memberNickName;
	private String memberProfilePath;
	private int replyCount;
	private int spCount;
	private String formatDate;
}
