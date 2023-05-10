package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.UserRequest;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.RoleRepository;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserService extends AppInitializer{
	/**
	 * ユーザー情報　Repository
	 * */
	@Autowired
	private CustomerRepository customerRepository;

	/** ロール情報 */
	@Autowired
	private RoleRepository roleRepository;

	/**
	 * ユーザー情報
	 * @return 検索
	 * */
	public List<User> searchAll() {
		return customerRepository.findAll();
	}

	/**
	 * ユーザー情報 新規登録
	 * @param user ユーザー情報
	 * ユーザーのリクエストを受け取りその値をそれぞれ格納してデータベースへ保存する処理
	 * @return 
	 * */
	public void userCreate(UserRequest userRequest) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		User user = new User();
		user.setName(userRequest.getName());
		user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
		user.setEmail(userRequest.getEmail());
		customerRepository.save(user);
	}

	/** 登録する際にロール情報も付与　*/
	public void addRole(UserRequest userRequest) {
		Role role = new Role();
		User user = customerRepository.findByEmail(userRequest.getEmail());
		role.setUserId(user.getId());
		role.setRoleId(userRequest.getRoleId());
		roleRepository.save(role);
		
		/** アドミンで登録された場合はジェネラルも自動で追加登録する */
		if ("2".equals(userRequest.getRoleId())) {
			Role role2 = new Role();
			role2.setUserId(user.getId());
			role2.setRoleId("1");
			roleRepository.save(role2);
		}
	}

	/** メールアドレスの重複チェック後にメッセージを渡す処理 */
	public String addUser(UserRequest userRequest) {
		if (customerRepository.findByEmail(userRequest.getEmail()) != null) {
			return "メールアドレスが既に使用されています。別のメールアドレスを使用してください。";
		}
		return null;
	}
}
