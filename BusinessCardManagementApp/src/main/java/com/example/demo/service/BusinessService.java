package com.example.demo.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.BusinessRequest;
import com.example.demo.entity.BusinessCard;
import com.example.demo.repository.BusinessRepository;

@Service
@Transactional(rollbackFor = Exception.class)
public class BusinessService {
	
	/** ロール情報 */
	@Autowired
	private BusinessRepository businessRepository;
	
	/**
	 * 名刺一覧情報
	 * @return 検索
	 * */
	public List<BusinessCard> searchAll() {
		return businessRepository.findAll();
	}
	
	/** IDで名刺情報を検索する */
    public BusinessCard findById(Long id) {
        return businessRepository.findById(id);
    }
	
	/** ユーザー毎の情報 */
	public List<BusinessCard> getByEmail(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
		return businessRepository.findByUserEmail(userEmail);
	}
	
	/**
	 * ユーザー情報 新規登録
	 * @param user ユーザー情報
	 * ユーザーのリクエストを受け取りその値をそれぞれ格納してデータベースへ保存する処理
	 * @return 
	 * */
	public void userCreate(BusinessRequest businessRequest) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
		BusinessCard businesscard = new BusinessCard();
		businesscard.setUserEmail(userEmail);
		businesscard.setCompany(businessRequest.getCompany());
		businesscard.setBusinessName(businessRequest.getBusinessName());
		businesscard.setCardImage("5");
		businesscard.setBookmark(businessRequest.getBookmark());
		businessRepository.save(businesscard);
	}

    /** 名刺登録情報変更 */
    public void update(BusinessRequest businessRequest, Long updateId) {
    	BusinessCard businesscard = findById(updateId);
		businesscard.setCompany(businessRequest.getCompany());
		businesscard.setBusinessName(businessRequest.getBusinessName());
		businesscard.setBookmark(businessRequest.getBookmark());
    	businessRepository.save(businesscard);
    }

    //ポイント③
    public void delete(Integer id) {
    	businessRepository.deleteById(id);
    }
    
    
    /** アップロード機能 */
    public void upload(MultipartFile multipartFile, Model model, Long userId) {
		try {
			// ファイルをアップロードするディレクトリを指定する
			String uploadDir = "/Applications/Eclipse_2023-03.app/Contents/workspace/BusinessCardManagementApp/src/main/java/com/example/demo/dir";

			// ファイル名を取得する
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

			// ファイルをディスクに保存する
			Path path = Paths.get(uploadDir + "/" + fileName);
			Files.copy(multipartFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

			// データベースにファイルのパスを保存する
			BusinessCard filePath = findById(userId);
			filePath.setCardImage(path.toString());
			businessRepository.save(filePath);

			// 成功メッセージを設定する
			model.addAttribute("message", "ファイルが正常にアップロードされました。");
		} catch (IOException e) {
			// エラーメッセージを設定する
			model.addAttribute("message", "ファイルのアップロードに失敗しました。");
		}
    }
}

