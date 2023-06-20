package com.kh.petsisters.payment.model.service;

import com.kh.petsisters.payment.model.vo.Payment;

public interface PaymentService {

	int processPayment(Payment p);

	int insertPay(String payDesc, int payPrice, int resNo);

	Payment selectResPay(int resNo);
	
}
