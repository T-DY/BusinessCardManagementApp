package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
		businesscard.setCardImage(businessRequest.getCard_image());
		businesscard.setBookmark(businessRequest.getBookmark());
		businessRepository.save(businesscard);
	}

    /** 名刺登録情報変更 */
    public void update(BusinessRequest businessRequest, String updateId) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        List<BusinessCard> businesscardlist = businessRepository.findByUserEmail(userEmail);
    	BusinessCard businesscard = businesscardlist.get(0);
		businesscard.setCompany(businessRequest.getCompany());
		businesscard.setBusinessName(businessRequest.getBusinessName());
		businesscard.setCardImage(businessRequest.getCard_image());
		businesscard.setBookmark(businessRequest.getBookmark());
    	businessRepository.save(businesscard);
    }

    //ポイント③
    public void delete(Integer id) {
    	businessRepository.deleteById(id);
    }

//    //ポイント④
//    public Optional<BusinessCard> selectById(Integer id) {
//        return businessRepository.findById(id);
//    }
}
