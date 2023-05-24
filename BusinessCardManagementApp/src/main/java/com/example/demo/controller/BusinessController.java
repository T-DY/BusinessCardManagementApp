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
import org.springframework.web.multipart.MultipartFile;

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
	 * 名刺登録情報一覧画面を表示
	 * @param model Model
	 * @return 名刺登録情報一覧画面
	 * */
	@GetMapping(value = "/businesscard/upload")
	public String getUpload(@RequestParam("id") Long uploadId, @RequestParam("card-image") String cardImagePath,
			Model model) {
		model.addAttribute("uploadId", uploadId);
		model.addAttribute("cardImagePath", cardImagePath);
		List<BusinessCard> user = businessService.getByEmail();
		model.addAttribute("businesscardlist", user);
		return "businesscard/upload";
	}

	/**
	 * 名刺登録情報一覧画面を表示
	 * @param model Model
	 * @return 名刺登録情報一覧画面
	 * */
	@GetMapping(value = "/businesscard/viewList")
	public String getViewList(Model model) {
		List<BusinessCard> user = businessService.getByEmail();
		model.addAttribute("businesscardlist", user);
		return "businesscard/viewList";
	}

	/**
	 * ユーザー新規登録画面を表示
	 * @param model Model
	 * @return ユーザー情報一覧画面
	 * */
	@GetMapping(value = "/businesscard/create")
	public String displayAdd(Model model) {
		model.addAttribute("businessRequest", new BusinessRequest());
		return "businesscard/create";
	}

	/**
	 * ユーザー登録変更画面表示
	 * @param model Model
	 * @return ユーザー情報変更画面
	 * */
	@GetMapping(value = "/businesscard/update")
	public String displayUpdate(@RequestParam("id") Long updateId, @RequestParam("card-image") String cardImagePath,
			Model model) {
		model.addAttribute("businessRequest", new BusinessRequest());
		model.addAttribute("updateId", updateId);
		model.addAttribute("cardImagePath", cardImagePath);
		BusinessCard businessCard = businessService.findById(updateId);
		model.addAttribute("businessName", businessCard.getBusinessName());
		model.addAttribute("company", businessCard.getCompany());
		model.addAttribute("bookmark", businessCard.getBookmark());
		return "businesscard/update";
	}

	/**
	 * 名刺新規登録
	 * @param userRequest リクエストデータ
	 * @param model Model
	 * @return ユーザー情報一覧画面
	 * */
	@PostMapping("/businesscard/create")
	public String bisinessCardCreate(@Validated @ModelAttribute BusinessRequest businessRequest, @RequestParam("card_image") String cardImagePath, BindingResult result,
			Model model) {
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
		businessService.userCreate(businessRequest, cardImagePath); /** 登録処理 */
		return "redirect:/businesscard/viewList";
	}

	/**
	 * 名刺情報更新
	 * @param userRequest リクエストデータ
	 * @param model Model
	 * @return 名刺情報一覧画面
	 * */
	@PostMapping(value = "/businesscard/update", produces = "text/html")
	public String bisinessCardUpdate(@Validated @ModelAttribute("businessRequest") BusinessRequest businessRequest,
			@RequestParam("updateId") Long updateId, BindingResult result, Model model) {
		if (result.hasErrors()) {
			// 入力チェックエラーの場合
			List<String> errorList = new ArrayList<String>();
			for (ObjectError error : result.getAllErrors()) {
				errorList.add(error.getDefaultMessage());
			}
			model.addAttribute("validationError", errorList);

			return "businesscard/update";
		}

		String cardImage = businessService.findById(updateId).getCardImage();
		// 登録情報変更処理
		businessService.update(businessRequest, updateId, cardImage);
		 // モデル属性の追加を先頭に移動
	    model.addAttribute("updateId", updateId);
	    model.addAttribute("businessRequest", new BusinessRequest());
	    BusinessCard businessCard = businessService.findById(updateId);
	    model.addAttribute("businessName", businessCard.getBusinessName());
	    model.addAttribute("company", businessCard.getCompany());
	    model.addAttribute("bookmark", businessCard.getBookmark());

	    // 画像パスの追加
	    String cardImagePath = businessService.findById(updateId).getCardImage();
	    model.addAttribute("cardImagePath", cardImagePath);
		return "redirect:/businesscard/viewList";
	}

	/**
	 * 名刺情報削除
	 * @param model Model
	 * @return 名刺情報一覧画面
	 * */
	@GetMapping("/businesscard/delete")
	public String displayDelete(@RequestParam("id") Integer updateId) {
		businessService.delete(updateId);
		return "redirect:/businesscard/viewList";
	}

	/**
	 * 名刺画像アップロード
	 * @param model Model
	 * @return 名刺情報一覧画面
	 * */
	@PostMapping("/businesscard/createUpload")
	public String uploadFile(@RequestParam("file") MultipartFile multipartFile,
	        @ModelAttribute("businessRequest") BusinessRequest businessRequest,
	        Model model) {
	    String cardImagePath = businessService.createUpload(multipartFile, model);
	    businessRequest.setCard_image(cardImagePath);
	    model.addAttribute("cardImagePath", cardImagePath);
	    
	    return "businesscard/create";
	}


	/**
	 * 名刺画像更新画面からのアップロード
	 * @param model Model
	 * @return 名刺情報一覧画面
	 * */
	@PostMapping("/businesscard/updateUpload")
	public String updateUpload(@RequestParam("uploadId") Long uploadId,
	                           @RequestParam("file") MultipartFile multipartFile,
	                           Model model) {
	    // 画像のアップロード処理
	    businessService.updateUpload(multipartFile, model, uploadId);

	    // モデル属性の追加を先頭に移動
	    model.addAttribute("updateId", uploadId);
	    model.addAttribute("businessRequest", new BusinessRequest());
	    BusinessCard businessCard = businessService.findById(uploadId);
	    model.addAttribute("businessName", businessCard.getBusinessName());
	    model.addAttribute("company", businessCard.getCompany());
	    model.addAttribute("bookmark", businessCard.getBookmark());

	    // 画像パスの追加
	    String cardImagePath = businessService.findById(uploadId).getCardImage();
	    model.addAttribute("cardImagePath", cardImagePath);

	    return "businesscard/update";
	}
}
