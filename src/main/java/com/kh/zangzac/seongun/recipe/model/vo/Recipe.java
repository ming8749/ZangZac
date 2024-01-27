package com.kh.zangzac.seongun.recipe.model.vo;

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
public class Recipe {
	private int recipeNo;
	private String recipeName;
	private String  recipeTaste;
	private String recipeIngredients;
	private String recipeContent;
	private Date recipeCreateDate;
	private Date recipeModifyDate;
	private String recipeStatus;
	private String[] cookCategoryName;
	private Integer[] cookCategoryNo;
	private String photoPath;
	private String photoRename;
}
