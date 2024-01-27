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

public class Qna {
	
	private int questionNo;
	private String questionContent;
	private Date questionDate;
	private String answerContent;
	private Date answerDate;
	private String qnaStatus;
	private String memberId;
	private int productNo;
	

}
