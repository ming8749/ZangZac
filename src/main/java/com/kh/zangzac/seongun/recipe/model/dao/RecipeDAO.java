package com.kh.zangzac.seongun.recipe.model.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.kh.zangzac.common.model.vo.PageInfo;
import com.kh.zangzac.seongun.recipe.model.vo.CookwareList;
import com.kh.zangzac.seongun.recipe.model.vo.Recipe;

@Mapper
public interface RecipeDAO {

	int insertRecipe(Recipe recipe);

	int getListCount();

	ArrayList<Recipe> recipeList(PageInfo pi);

	int insertCookList(ArrayList<CookwareList> cookList);

	Recipe selectRecipe(int recipeNo);

	ArrayList<CookwareList> selectCookwareList(int recipeNo);

	int deleteRecipe(int recipeNo);

	int updateRecipe(Recipe recipe);

	int updataeCookList(ArrayList<CookwareList> cookList);

	void deleteCookList(int recipeNo);


}
