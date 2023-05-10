package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dto.BusinessRequest;
import com.example.demo.entity.BusinessCard;
import com.example.demo.service.BusinessService;

@Controller
public class BusinessController {

	/**
	 * 名刺登録 Service
	 * */
	@Autowired
	private BusinessService businessService;
	
	
	/**
	 * 名刺情報一覧画面を表示
	 * @param model Model
	 * @return 名刺情報一覧画面
	 * */
	@GetMapping(value = "/businesscard/list")
	public String getUserByEmail(Model model) {
		List<BusinessCard> user =  businessService.getByEmail();
		model.addAttribute("businesscardlist", user);
	    return "businesscard/list";
	}

	/**
	 * ユーザー新規登録画面を表示
	 * @param model Model
	 * @return ユーザー情報一覧画面
	 * */
	@GetMapping(value = "/businesscard/create")
	public String displayAdd(Model model) {
		model.addAttribute("businesscardRequest", new BusinessRequest());
		return "businesscard/create";
	}

	/**
	 * ユーザー登録変更画面
	 * @param model Model
	 * @return ユーザー情報変更画面
	 * */
	@GetMapping(value = "/businesscard/update")
	public String displayUpdate(@RequestParam("id") String updateId, Model model) {
		model.addAttribute("updateId", updateId);
		model.addAttribute("businesscardRequest", new BusinessRequest());
		return "businesscard/update";
	}
	
	/**
	 * 名刺新規登録
	 * @param userRequest リクエストデータ
	 * @param model Model
	 * @return ユーザー情報一覧画面
	 * */
	@PostMapping("/businesscard/create")
	public String bisinessCardCreate(@Validated @ModelAttribute BusinessRequest businessRequest, BindingResult result, Model model){
		if (result.hasErrors()) {
			// 入力チェックエラーの場合
			List<String> errorList = new ArrayList<String>();
			for (ObjectError error : result.getAllErrors()) {
				errorList.add(error.getDefaultMessage());
			}
			model.addAttribute("validationError", errorList);
			
			return "businesscard/create";
		}

		// ユーザー登録情報
		businessService.userCreate(businessRequest); /** 登録処理 */
		return "redirect:/businesscard/list";
	}
	
	/**
	 * 名刺新規登録
	 * @param userRequest リクエストデータ
	 * @param model Model
	 * @return ユーザー情報一覧画面
	 * */
	@PostMapping(value = "/businesscard/update", produces = "user/create;charset=UTF-8")
	public String bisinessCardUpdate(@Validated @ModelAttribute @RequestParam("id") String updateId,BusinessRequest businessRequest, BindingResult result, Model model){
		if (result.hasErrors()) {
			// 入力チェックエラーの場合
			List<String> errorList = new ArrayList<String>();
			for (ObjectError error : result.getAllErrors()) {
				errorList.add(error.getDefaultMessage());
			}
			model.addAttribute("validationError", errorList);
			
			return "businesscard/update";
		}

		// ユーザー登録情報
		businessService.update(businessRequest, updateId); /** 変更処理 */
		return "redirect:/businesscard/list";
	}
}
