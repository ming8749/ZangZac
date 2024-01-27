package com.kh.zangzac.seongun.common.model.vo;

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
public class SearchBoard {
	private int searchCategory;
	private String searchText;
	private int searchCategoryNo;
}
