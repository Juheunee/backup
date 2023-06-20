package com.kh.petsisters.reservation.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Reservation {
	
	private int rowNum;
	private int resNo;  			// 예약번호 	RES_NO	NUMBER		
	private Date startDate;			// 시작일시	START_DATE	DATE
	private Date endDate;			// 종료일시 	END_DATE	DATE
	private String registerDate;	// 등록일시	REGISTER_DATE	DATE
	private String content;			// 특이사항	CONTENT	VARCHAR2(2000 BYTE)
	private String status;			// 최초예약시,예약취소시 N / 펫시터승인시 Y	STATUS	VARCHAR2(1 BYTE)
	private int userNo;				// 회원번호	&  상세페이지에서도 사용
	private int refPno;				// 참조펫시터프로필번호     &  상세페이지에서도 사용
	private String caStatus;		// 자격증 여부 (Y 면 프로펫시터)
	private String ptitle;			// 펫시터 프로필 제목
 	private String pcareList;		// 펫시터 돌봄 환경
	private int pno;				// 펫시터 고유 번호
	private String petFile;			// 펫시터 대표 프로필 사진 & 상세페이지에서 펫시터 대표사진
	private String address;			// 펫시터 주소
	private String originName;		// 펫시터 원본 대표사진명  & 상세페이지에서 펫시터 대표사진
	private int reviewCount;		// 리뷰 갯수
	private int checkReview;		// 리뷰 작성 여부
	
	// 예약 조회시 필요한 정보
	/*
	 * 1. 예약 기간					<- 예약 테이블 (예약 시작일 - 종료일)
	 * 2. 펫시터 주소				<- 회원 테이블 (회원번호로 펫시터 프로필이랑 엮어서 조회 해야함)
	 * 3. 프로펫시터 여부 (자격증 Y 면)	<- 회원 테이블 (CA_STATUS = 'Y')
	 * 4. 펫시터 타이틀				<- 펫시터프로필 테이블 (펫시터 프로필 제목)
	 * 5. 해시태그					<- 펫시터프로필 테이블 (돌봄 환경)
	 * 6. 펫시터 고유 번호				<- 펫시터프로필 테이블 (고유 번호)
	 * 7. 펫시터 프로필 사진			<- 펫시터 첨부파일테이블 (파일레벨1번 골라와서 대표사진으로)
	 */
	
	// 예약 상세 페이지를 위한 추가 변수
	private String dfilePath;	//	강아지 파일 경로
	private String dogGender;	//  강아지 성별
	private String dogName;		//  강아지 이름
	private String dogBreed;	//  강아지 견종
	private String userName;	//  펫시터 이름
	private String userFile;	//  유저 프로필 사진(처음 회원가입 할 때 사람 프로필임)	
	
	// ============ 펫시터 관련 =============
	
	// 펫시터 예약 목록 조회시 필요한 정보
	private int payPrice;		//  예약 고객의 결제 금액
	
	// 반려견 수 조회시 필요한 정보
	private int petAmount; // 반려견 수
	
	// 돌봄일지 작성을 위한 년월일시 날짜 정보
	private String careTime;	//  돌봄일지 작성 시간
	
	
	
	
	
}
