package com.example.demo.service;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
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
	public List<BusinessCard> getByEmail() {
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
	public void userCreate(BusinessRequest businessRequest, String imagePath) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userEmail = authentication.getName();
		BusinessCard businesscard = new BusinessCard();
		businesscard.setUserEmail(userEmail);
		businesscard.setCompany(businessRequest.getCompany());
		businesscard.setBusinessName(businessRequest.getBusinessName());
		businesscard.setCardImage(businessRequest.getCard_image());
		businesscard.setBookmark(businessRequest.getBookmark());
		businessRepository.save(businesscard);
	}

	/** 名刺登録情報変更 */
	public void update(BusinessRequest businessRequest, Long updateId, String cardImage) {
		BusinessCard businesscard = findById(updateId);
		businesscard.setCompany(businessRequest.getCompany());
		businesscard.setBusinessName(businessRequest.getBusinessName());
		businesscard.setBookmark(businessRequest.getBookmark());
		businesscard.setCardImage(cardImage);
		businessRepository.save(businesscard);
	}

	//ポイント③
	public void delete(Integer id) {
		businessRepository.deleteById(id);
	}

	/** アップロード機能 
	 * @param multipartFile 拡張子の決まっていないファイルを受け取る
	 * @param model メッセージに正常にファイルがアップロードされたかを入れてる
	 * @param userId 画像のパスをどの情報に紐づけるのかを受け取る
	 * */
	public void updateUpload(MultipartFile multipartFile, Model model, Long userId) {
		try { 
	        // ファイルをアップロードするディレクトリを指定する
	        String uploadDir = "src/main/resources/static/upload";

	        // ファイル名を取得する
	        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

	        // ファイルをディスクに保存する
	        Path path = Paths.get(uploadDir, fileName);
	        Files.copy(multipartFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

	        /** 画像アップロード後に元々の画像を削除する */
	        imageDelete(userId, model);

	        // データベースにファイルのパスを保存する
	        BusinessCard filePath = findById(userId);
	        String imagePath = "/static/upload/" + fileName;
	        filePath.setCardImage(imagePath);
	        businessRepository.save(filePath);

	        // 成功メッセージを設定する
	        model.addAttribute("message", "ファイルが正常にアップロードされました。");
	    } catch (IOException e) {
	        // エラーメッセージを設定する
	        model.addAttribute("message", "ファイルのアップロードに失敗しました。");
	    }
	}

	/** 画像ファイルを削除する機能 
	 * @param deleteId 削除する画像パスを持つID
	 * @param model 削除の成功または失敗をメッセージに入れてる。あとでもしかしたら使うかも
	 * */
	public void imageDelete(Long deleteId, Model model) {
		BusinessCard businesscard = findById(deleteId);
		String imagePath = businesscard.getCardImage();
		Path currentDirectory = Paths.get("").toAbsolutePath(); // ここで絶対パスの読み込み
		Path imageFilePath = currentDirectory.resolve(imagePath);
		File imageFile = imageFilePath.toFile();

		if (imageFile.exists()) {
			if (imageFile.delete()) {
				model.addAttribute("message", System.out.printf("画像名%sが削除されました。", imagePath));
			} else {
				model.addAttribute("message", System.out.printf("画像名%sが削除に失敗しました。", imagePath));
			}
		} else {
			model.addAttribute("message", System.out.printf("画像名%sが存在しません。", imagePath));
		}

	}
	
	public String createUpload(MultipartFile multipartFile, Model model) {
		String imagePath = null;
		try { 
	        // ファイルをアップロードするディレクトリを指定する
	        String uploadDir = "src/main/resources/static/upload";

	        // ファイル名を取得する
	        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

	        // ファイルをディスクに保存用のパスを生成
	        Path path = Paths.get(uploadDir, fileName);
	        Files.copy(multipartFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
	        imagePath = "/static/upload/" + fileName;
	        
	        // 成功メッセージを設定する
	        model.addAttribute("message", "ファイルが正常にアップロードされました。");
	    } catch (IOException e) {
	        // エラーメッセージを設定する
	        model.addAttribute("message", "ファイルのアップロードに失敗しました。");
	    }
		return imagePath;
	}
	
	/** ブックマークのオンオフを処理 */
	public void bookmarkCheck(BusinessCard businessCard, Boolean checked) {
		
		businessCard.setBookmark(checked);
		businessRepository.save(businessCard);
		
	}
	
	/** ブックマークのみのリストを返す */
	public List<BusinessCard> bookmarkList(List<BusinessCard> list){
		List<BusinessCard> bookmarkedCards = new ArrayList<>();
		for(BusinessCard businessCard : list) {
			if(businessCard.getBookmark()) {
				bookmarkedCards.add(businessCard);
			}
			
		}
		return bookmarkedCards;
	}
	
	/** リクエスト内の文字をUTF-8に変換 */
	public void CharacterEncoding(BusinessRequest businessRequest) {
		try {
		    // businessRequestから値を取得
		    String businessNameISO88591 = businessRequest.getBusinessName(); // ISO-8859-1でエンコードされたBusinessName
		    String companyISO88591 = businessRequest.getCompany(); // ISO-8859-1でエンコードされたcompany

		    // ISO-8859-1からUTF-8に変換
		    String businessNameUTF8 = new String(businessNameISO88591.getBytes("ISO-8859-1"), "UTF-8");
		    String companyUTF8 = new String(companyISO88591.getBytes("ISO-8859-1"), "UTF-8");

		    // 変換後の値を設定
		    businessRequest.setBusinessName(businessNameUTF8);
		    businessRequest.setCompany(companyUTF8);

		    // 変換後の値を使用
		    System.out.println(businessRequest.getBusinessName());
		    System.out.println(businessRequest.getCompany());

		    // または、必要な処理を実行
		    // ...

		} catch (UnsupportedEncodingException e) {
		    // エンコーディングの変換に失敗した場合のエラーハンドリング
		    e.printStackTrace();
		}

	}
	
}
