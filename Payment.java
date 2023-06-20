package com.kh.petsisters.payment.model.vo;

import java.sql.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 2023-06-14 조승호 객체 추가
 * @author dkarl
 *
 */
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Payment {
	
	private int payNo;			// 결제번호
	private Date payDate;		// 결제일시
	private String payDesc; 	// 결제내용
	private int payPrice;		// 결제금액
	private String status;		// 결제상태
	private int refResno; 		// 참조예약번호 (예약테이블)
	
	// 2023-06-14 조승호 추가
	private int weekendPay;		// 주별 정산금
	private int totalPay;		// 누적 정산금

}
