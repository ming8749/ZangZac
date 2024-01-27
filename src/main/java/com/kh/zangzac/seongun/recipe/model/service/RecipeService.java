package com.kh.zangzac.seongun.recipe.model.service;

import java.util.ArrayList;

import com.kh.zangzac.common.model.vo.PageInfo;
import com.kh.zangzac.seongun.recipe.model.vo.CookwareList;
import com.kh.zangzac.seongun.recipe.model.vo.Recipe;

public interface RecipeService {

	int insertRecipe(Recipe recipe);

	int getListCount();

	ArrayList<Recipe> recipeList(PageInfo pi);

	int insertCookList(ArrayList<CookwareList> cookList);

	Recipe selectRecipe(int recipeNo);

	int deleteRecipe(int recipeNo);

	int updateRecipe(Recipe recipe);

	int updataeCookList(ArrayList<CookwareList> cookList);

	void deleteCookList(int recipeNo);


}
