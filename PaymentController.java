package com.kh.petsisters.payment.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.kh.petsisters.member.model.vo.Member;
import com.kh.petsisters.payment.model.service.PaymentService;
import com.kh.petsisters.payment.model.vo.Payment;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class PaymentController {

	@Autowired
	private PaymentService paymentService;
	
	
	
	
	@ResponseBody
	@RequestMapping(value="insertPay", produces = "application/json; charset=UTF-8")
	public String insertPay(String payDesc, int payPrice, int resNo) {
		
		System.out.println(payDesc);
		System.out.println(payPrice);
		System.out.println(resNo);
		
		int result = paymentService.insertPay(payDesc, payPrice, resNo);
		
		System.out.println(result);
		
		if(result > 0) {
			new Gson().toJson(result);
		}
		
		return null;
	}
	
	@RequestMapping(value="payInfo")
	public ModelAndView selectResPay(ModelAndView mv, 
								     HttpSession session, 
								     int resNo) {
		
		Payment p = paymentService.selectResPay(resNo);
		
		mv.addObject("p", p).setViewName("reservation/reservationEnrollForm");
		
		return mv;
	}
	
	
	
	// 결제완료화면 띄우기
	@RequestMapping("paySuccess")
	public String paySuccess() {
		
		return "reservation/reservationSuccess";
	}
	
}