package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.BusinessCard;

public interface BusinessRepository extends JpaRepository<BusinessCard, Integer>{
	BusinessCard findById(Long id);
	List<BusinessCard> findByUserEmail(String email);
}
