package com.kh.zangzac.ming.member.model.vo;



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
public class Member {
	
	private String memberId;
	private String memberPwd;
	private String memberName;
	private String memberEmail;
	private String memberNickName;
	private Date memberBirth;
	private String memberAddress;
	private String memberPhone;
	private Date memberEnrollDate;
	private String memberIsAdmin;
	private String memberStatus;
	private String memberProfileRename;
	private String memberProfilePath;
	private int memberLoginType; 
}
