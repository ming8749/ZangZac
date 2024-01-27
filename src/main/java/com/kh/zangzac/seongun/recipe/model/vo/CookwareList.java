package com.kh.zangzac.seongun.recipe.model.vo;


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
public class CookwareList {
	private int cwListNo;
	private int cwRecipeNo;
	private int cookCategoryNo;
	private String cookCategoryName;
}
