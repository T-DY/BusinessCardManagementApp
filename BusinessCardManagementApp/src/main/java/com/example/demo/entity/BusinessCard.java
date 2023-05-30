package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

/**
 * ユーザー情報 Entity
 * */
@Entity
@Data
@Table(name = "businesscard_list")
public class BusinessCard {
	
	/**
	 * ID
	 * */
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY) /** プライマリキーカラムにユニークな値を自動で生成 */
	private Long id;
	
	/** 登録したユーザーメールアドレス */
	@Column(name = "userEmail", unique = false)
	private String userEmail;
	
	@Column(name = "company", columnDefinition = "VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci")
	private String company;

	@Column(name = "businessName", columnDefinition = "VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci")
	private String businessName;

	@Column(name = "cardImage", columnDefinition = "VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci")
	private String cardImage;

	
	/** ブックマーク */

	@Column(name = "bookmark")
	private boolean isBookmark;

	@Transient // データベースにはマッピングしない
	public Boolean getBookmark() {
		return isBookmark;
	}

	public void setBookmark(Boolean bookmark) {
		this.isBookmark = bookmark;
	}
}
