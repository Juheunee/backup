package com.kh.petsisters.payment.model.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.petsisters.payment.model.vo.Payment;

@Repository
public class PaymentDao {

	@Autowired
	private SqlSession sqlSession;

	public int processPayment(SqlSessionTemplate sqlSession, Payment p) {
		return sqlSession.insert("paymentMapper.processPayment", p);
    }

	public int insertPay(SqlSessionTemplate sqlSession, String payDesc, int payPrice, int resNo) {
		
		Map<String, Object> parameter = new HashMap<>();
		parameter.put("payDesc", payDesc);
		parameter.put("payPrice", payPrice);
		parameter.put("resNo", resNo);
		
		return sqlSession.insert("paymentMapper.insertPay", parameter);
	}

	public Payment selectResPay(SqlSessionTemplate sqlSession, int resNo) {
		return sqlSession.selectOne("paymentMapper.payInfo", resNo);
	}
	
}
