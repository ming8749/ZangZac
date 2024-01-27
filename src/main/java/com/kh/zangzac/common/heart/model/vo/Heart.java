package com.kh.zangzac.common.heart.model.vo;

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
public class Heart {
	private int heartNo;
	private int boardNo;
	private int boardType;
	private String memberId;
}
