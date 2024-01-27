package com.kh.zangzac.seongun.recipe.model.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.zangzac.common.model.vo.PageInfo;
import com.kh.zangzac.seongun.recipe.model.dao.RecipeDAO;
import com.kh.zangzac.seongun.recipe.model.vo.CookwareList;
import com.kh.zangzac.seongun.recipe.model.vo.Recipe;

@Service
public class RecipeServiceImpl implements RecipeService {

	@Autowired
	private RecipeDAO rDAO;
	
	@Override
	public int insertRecipe(Recipe recipe) {
		return rDAO.insertRecipe(recipe);
	}

	@Override
	public int getListCount() {
		return rDAO.getListCount();
	}

	@Override
	public ArrayList<Recipe> recipeList(PageInfo pi) {
		ArrayList<Recipe> list = rDAO.recipeList(pi);
		int temp =list.size();
		for(int i = 0; i < temp; i++) {
			ArrayList<CookwareList> cl = rDAO.selectCookwareList(list.get(i).getRecipeNo());
			int temp2 = cl.size();
			String[] clNames = new String[temp2];
			Integer[] clNo = new Integer[temp2];
			
			for (int j = 0; j < temp2; j++) {
			    clNames[j] = cl.get(j).getCookCategoryName();
			    clNo[j] = cl.get(j).getCookCategoryNo();
			}
			
			list.get(i).setCookCategoryName(clNames);
			list.get(i).setCookCategoryNo(clNo);
		}
		return list;
	}

	@Override
	public int insertCookList(ArrayList<CookwareList> cookList) {
		return rDAO.insertCookList(cookList);
	}

	@Override
	public Recipe selectRecipe(int recipeNo) {
		Recipe r = rDAO.selectRecipe(recipeNo);
		ArrayList<CookwareList> cl = rDAO.selectCookwareList(recipeNo);
		int temp = cl.size();
		String[] clNames = new String[temp];
		Integer[] clNo = new Integer[temp];
		
		for (int i = 0; i < temp; i++) {
		    clNames[i] = cl.get(i).getCookCategoryName();
		    clNo[i] = cl.get(i).getCookCategoryNo();
		}

		r.setCookCategoryName(clNames);
		r.setCookCategoryNo(clNo);

		return r;
	}

	@Override
	public int deleteRecipe(int recipeNo) {
		return rDAO.deleteRecipe(recipeNo);
	}

	@Override
	public int updateRecipe(Recipe recipe) {
		return rDAO.updateRecipe(recipe);
	}

	@Override
	public int updataeCookList(ArrayList<CookwareList> cookList) {
		return rDAO.updataeCookList(cookList);
	}

	@Override
	public void deleteCookList(int recipeNo) {
		rDAO.deleteCookList(recipeNo);
	}

}
