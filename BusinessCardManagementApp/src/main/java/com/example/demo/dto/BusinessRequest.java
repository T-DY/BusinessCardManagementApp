package com.example.demo.dto;

import java.io.Serializable;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 名刺情報　リクエストデータ
 * */
@Data
public class BusinessRequest implements Serializable{
	/** Id */
	private Integer id;

	/**
	 * 社名
	 * */
	@NotEmpty(message = "社名を入力してください")
	@Size(max = 138, message = "社名は138文字以内で入力してください")
	private String company;

	/** 名刺の名前 */
	@NotEmpty(message = "社名を入力してください")
	@Size(max = 20, message = "名前は20文字以内で入力してください")
	private String businessName;

	/**
	 * 名刺画像情報
	 * */
	@NotEmpty(message = "画像を入れてください")
	private String card_image;

	/**
	 * ブックマーク機能
	 * */
	private Boolean bookmark;

}
