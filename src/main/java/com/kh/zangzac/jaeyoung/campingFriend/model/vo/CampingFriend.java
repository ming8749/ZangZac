package com.kh.zangzac.jaeyoung.campingFriend.model.vo;

import java.util.ArrayList;

import com.kh.zangzac.common.reply.model.vo.Reply;

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
public class CampingFriend {
	private int cfNo;
	private String cfContent;
	private String cfTitle;
	private String memberId;
	private String categoryName;
	private int categoryNo;
	private int replyCount;
	private int likeCount;
	private String photoPath;
	private String PhotoRename;
	private ArrayList<Reply> replys;
	private String likeStatus;
	private String memberProfile;
}
