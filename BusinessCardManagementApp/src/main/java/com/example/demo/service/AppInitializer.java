package com.example.demo.service;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import jakarta.servlet.Filter;

public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	  /**
     * リクエスト、レスポンス共にUTF8で統一する。
     */
    @Override
	public Filter[] getServletFilters() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        return new Filter[] {characterEncodingFilter};
    }    


	@Override
	protected String[] getServletMappings() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}


	@Override
	protected Class<?>[] getRootConfigClasses() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}


	@Override
	protected Class<?>[] getServletConfigClasses() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

}
