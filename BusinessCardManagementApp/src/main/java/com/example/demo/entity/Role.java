package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "user_role")
@Data
public class Role{

	/** ユーザー */
	@Column(name = "userId")
	private Long userId;
	
	/** ロールID */
	@Column(name = "roleId")
	private String roleId;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "number")
	private Integer number;

}
