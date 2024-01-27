package com.kh.zangzac.jaeyoung.campingReview.model.vo;

import java.sql.Date;
import java.util.ArrayList;

import com.kh.zangzac.common.photo.model.vo.Photo;
import com.kh.zangzac.common.reply.model.vo.Reply;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class CampingReview {
	private int crNo;
	private String crTitle;
	private String crContent;
	private Date crCreatedate;
	private Date crModifydate;
	private int crCount;
	private String memberId;
	private String memberNickName;
	private int categoryNo;
	private String categoryName;
	private double crStar;
	private ArrayList<Reply> replys;
	private Photo thumnail;
	private ArrayList<Photo> photos;

}
