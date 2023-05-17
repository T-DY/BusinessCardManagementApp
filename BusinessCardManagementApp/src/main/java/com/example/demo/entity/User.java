package com.example.demo.entity;

import java.io.Serializable;

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
@Table(name = "login_user")
public class User implements Serializable{
	/**
	 * ID
	 * */
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY) /** プライマリキーカラムにユニークな値を自動で生成 */
	private Long id;
	
	/** 名前 */
	@Column(name = "name")
	private String name;
	
	/** パスワード */
	@Column(name = "password")
	private String password;
	
	/** メールアドレス */
	@Column(name = "email", unique = true)
	private String email;
}
