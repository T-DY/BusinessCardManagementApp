package com.example.demo.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 名刺情報　リクエストデータ
 * */
@Data
public class BusinessRequest {
	/** Id */
	private Integer id;
	
	/**
	 * 社名
	 * */
	@NotEmpty(message = "社名を入力してください")
	@Size(max = 100, message = "社名は100桁以内で入力してください")
	private String company;
	
	/** 名刺の名前 */
	private String businessName;
	
	/**
	 * 名刺画像情報
	 * */
	private String card_image;
	
	/**
	 * ブックマーク機能
	 * */
	private String bookmark;
}
