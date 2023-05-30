package com.example.demo.dto;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * ユーザー情報　リクエストデータ
 * */
@Data
public class UserRequest implements Serializable {
	/**
	 * 名前
	 * */
	@NotEmpty(message = "名前を入力してください")
	@NotBlank
	@Length(max=32)
	private String name;
	
	/**
	 * パスワード
	 * */
	//バリデーション追加予定
	@NotEmpty(message = "パスワードを入力してください")
	@NotBlank
	@Size(max = 255, message = "パスワードは255桁以内で入力してください")
	private String password;
	
	/**
	 * パスワード確認用
	 * */
	//バリデーション追加予定
	@Size(max = 255, message = "パスワードは255桁以内で入力してください")
	private String passwordConfirmation;
	
    @AssertTrue(message = "パスワードとパスワード確認用は同一にしてください。")
    public boolean isPasswordValid() {
        if (password == null || password.isEmpty()) {
            return true;
        }

        return password.equals(passwordConfirmation);
    } 
	
	/**
	 * メールアドレス
	 * */
    @Email
	 @NotNull(message = "メールアドレスを入力してください")
	 @Pattern(regexp = "[\\w\\-._]+@[\\w\\-._]+\\.[A-Za-z]+", message = "正しい形式で入力して下さい")
	private String email;
	
	/**
	 * ロールID
	 * */
	@Range(min = 1, max = 2, message = "１か２で入力してください。  １がGeneral ２がadminになります。")
	private String roleId;

}
