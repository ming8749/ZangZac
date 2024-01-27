package com.kh.zangzac.sohwa.product.model.vo;



import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Review {
	
	private int reviewNo;
	private String reviewContent;
	private Date reviewUploadDate;
	private int reviewScore;
	private Date reviewModifyDate;
	private String reviewStatus;
	private int productNo;
	private String memberId;

}
