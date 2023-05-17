package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
	
	/** 社名 */
	@Column(name = "company")
	private String company;
	
	/** 名刺の名前 */
	@Column(name = "businessName")
	private String businessName;
	
	/** 画像イメージ情報 */
	@Column(name = "cardImage")
	private String cardImage;
	
	/** ブックマーク */
	@Column(name = "bookmark")
	private String bookmark;
}
