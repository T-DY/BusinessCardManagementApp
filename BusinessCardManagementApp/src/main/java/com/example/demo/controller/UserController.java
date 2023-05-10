package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.dto.UserRequest;
import com.example.demo.entity.User;
import com.example.demo.service.AppInitializer;
import com.example.demo.service.UserService;

@Controller
public class UserController extends AppInitializer{
	/**
	 * ユーザー登録 Service
	 * */
	@Autowired
	private UserService userService;
	
	/**
	 * ユーザー情報一覧画面を表示
	 * @param model Model
	 * @return ユーザー情報一覧画面
	 * */
	@GetMapping(value = "/user/list")
	public String displayList(Model model) {
		List<User> userlist = userService.searchAll();
		model.addAttribute("userlist", userlist);
		return "user/list";
	}

	/**
	 * ユーザー新規登録画面を表示
	 * @param model Model
	 * @return ユーザー情報一覧画面
	 * */
	@GetMapping(value = "/user/create")
	public String displayAdd(Model model) {
		model.addAttribute("userRequest", new UserRequest());
		return "user/create";
	}

	/**
	 * ユーザー新規登録
	 * @param userRequest リクエストデータ
	 * @param model Model
	 * @return ユーザー情報一覧画面
	 * */
	@PostMapping("/user/create")
	public String create(@RequestBody @Validated @ModelAttribute UserRequest userRequest, BindingResult result, Model model)
			throws DuplicateKeyException {
		if (result.hasErrors()) {
			// 入力チェックエラーの場合
			List<String> errorList = new ArrayList<String>();
			for (ObjectError error : result.getAllErrors()) {
				errorList.add(error.getDefaultMessage());
			}
			model.addAttribute("validationError", errorList);
			return "user/create";
		}
		/** メールアドレス重複チェックを入れる。 */
		else if (userService.addUser(userRequest) != null) {
			String errorList = userService.addUser(userRequest);
			model.addAttribute("validationError", errorList);
			return "user/create";
		}
		getServletFilters();
		// ユーザー登録情報
		userService.userCreate(userRequest); /** 登録処理 */
		userService.addRole(userRequest);/** ロール付与処理 adminの場合同時に２個ロール付与 */
		return "redirect:/user/list";
	}

	/**
	 * ユーザー情報詳細画面を表示
	 * @param id 表示するユーザーID
	 * @param model Model
	 * @return ユーザー情報詳細画面
	 * */
	@GetMapping("/user/{id}")
	public String displayView(@PathVariable Long id, Model model) {
		return "user/view";
	}
}
