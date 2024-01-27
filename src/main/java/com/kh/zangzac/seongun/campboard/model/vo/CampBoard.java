package com.kh.zangzac.seongun.campboard.model.vo;

import java.time.LocalDateTime;

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
public class CampBoard {
	private int cbNo;
	private String cbTitle;
	private String cbContent;
	private int cbCount;
	private LocalDateTime cbCreateDate;
	private LocalDateTime cbModifyDate;
	private String cbStatus;
	private String memberId;
	private String memberProfilePath;
	private int categoryNo;
	private String memberName;
	private String memberNickname;
	private String categoryName;
	private int heartCount;
	private boolean heartCheck;
	private String photoPath;
	private String photoRename;
	private int replyCount;
	private String formatDate;
	private String searchText;
	private int searchCategory;
	
}
