package com.kh.petsisters.reservation.model.service;

import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

import com.kh.petsisters.common.model.vo.PageInfo;
import com.kh.petsisters.payment.model.vo.Payment;
import com.kh.petsisters.reservation.model.vo.CareJournal;
import com.kh.petsisters.reservation.model.vo.Reservation;
import com.kh.petsisters.reservation.model.vo.Review;

public interface ReservationService {
	
	// 리스트 총 갯수 조회
	public abstract int selectListCount(int userNo);
	
	// 내 예약 펫시터 리스트 조회 (select)
	ArrayList<Reservation> selectPetsitterList(PageInfo pi, int userNo, Integer checkReview);

	// 예약정보 보내기
	int insertReservation(Reservation r);
	
	// 예약요청 조회
	Reservation selectReservation(int refPno);
	
	// 후기 작성시 날짜 조회용
	Reservation selectReview(int writeReviewNo);

	// 후기 작성
	int insertReview(Review r);

	// 후기 수정시 기본정보 조회용
	Review updateReview(int rNo);

	// 후기 수정 업데이트
	int updateForm(Review r);

	// 예약 리스트 삭제용
	int deleteReservation(int rNo);

	// 예약 상세페이지 조회
	Reservation reservationDetail(int rNo);

	// 돌봄일지 페이지네이션 용
	int selectCareCount(int userNo, String keyword);
	
	// 돌봄일지 목록 조회
	ArrayList<CareJournal> journalList(PageInfo pi, int userNo, String keyword, String options);
	
	// 돌봄일지 상세 조회
	ArrayList<CareJournal> careDetail(int jno);
	
	// 이번주 정산금 조회
	int weekPay(int userNo);
	
	// 총 정산금 조회
	int totalPay(int userNo);
	
	// 결제 내역 조회
	ArrayList<Payment> payList(int userNo);
	
	// 결제내역 ajax용
	ArrayList<Payment> searchPay(int userNo, String startDate, String endDate);
	
	
	// ======== 펫시터 관련 =========
	// 펫시터 예약 화면 조회용
	ArrayList<Reservation> petsitterRevList(int userNo, PageInfo pi, String keyword);

	// 펫시터 예약 화면 갯수 (페이지네이션)
	int selectListPetsitterRev(int userNo, String keyword);

	// 펫시터 예약 화면 날짜 조회
	ArrayList<Reservation> dateSelect(int userNo, String startDate, String endDate);

	// 펫시터 예약 상세화면 조회용
	Reservation petsitterRevDetail(int rNo);

	// 펫시터 돌봄일지 추가
	int insertJournal(int cNo, String careTitle, String careDesc);

	// 펫시터 돌봄일지에 파일 있을경우 파일 추가
	int insertJournalFile(ArrayList<CareJournal> list);

	// 펫시터 돌봄일지 관리 갯수 조회용
	int selectJournalCount(int userNo);

	// 펫시터 돌봄일지 관리 리스트 조회용
	ArrayList<CareJournal> careJournalManagement(PageInfo pi, int userNo, String options);

	// 펫시터 돌봄일지 수정시 초기 정보 조회용
	CareJournal updateJournal(int jno);

	// 펫시터 돌봄일지 제목,내용 수정용
	int updateCare(int jno, String careTitle, String careDesc);

	// 펫시터 돌봄일지 파일 업데이트용
	int updateCareFile(ArrayList<CareJournal> list);

	// 펫시터 돌봄일지 삭제
	int deleteJournal(int jno);








}
