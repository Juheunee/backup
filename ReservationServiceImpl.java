package com.kh.petsisters.reservation.model.service;

import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.petsisters.common.model.vo.PageInfo;
import com.kh.petsisters.payment.model.vo.Payment;
import com.kh.petsisters.reservation.model.dao.ReservationDao;
import com.kh.petsisters.reservation.model.vo.CareJournal;
import com.kh.petsisters.reservation.model.vo.Reservation;
import com.kh.petsisters.reservation.model.vo.Review;

@Service
public class ReservationServiceImpl implements ReservationService{
	
	@Autowired
	private ReservationDao reservationDao;
	
	@Autowired
	private SqlSessionTemplate sqlSession;

	@Override
	public int selectListCount(int userNo) {
		return reservationDao.selectListCount(sqlSession, userNo);
	}

	@Override
	public ArrayList<Reservation> selectPetsitterList(PageInfo pi, int userNo, Integer checkReview) {
		return reservationDao.selectPetsitterList(sqlSession, pi, userNo, checkReview);
	}
	
	@Override
	public int insertReservation(Reservation r) {
		return reservationDao.insertReservation(sqlSession, r);
	}
	
	@Override
	public Reservation selectReservation(int refPno) {
		return reservationDao.selectReservation(sqlSession, refPno);
	}

	@Override
	public Reservation selectReview(int writeReviewNo) {
		return reservationDao.selectReview(sqlSession, writeReviewNo);
	}

	@Override
	public int insertReview(Review r) {
		return reservationDao.insertReview(sqlSession, r);
	}

	@Override
	public Review updateReview(int rNo) {
		return reservationDao.updateReview(sqlSession, rNo);
	}

	@Override
	public int updateForm(Review r) {
		return reservationDao.updateForm(sqlSession, r);
	}

	@Override
	public int deleteReservation(int rNo) {
		return reservationDao.deleteReservation(sqlSession, rNo);
	}

	@Override
	public Reservation reservationDetail(int rNo) {
		return reservationDao.reservationDetail(sqlSession, rNo);
	}

	@Override
	public int selectListPetsitterRev(int userNo, String keyword) {
		return reservationDao.selectListPetsitterRev(sqlSession, userNo, keyword);
	}

	@Override
	public ArrayList<Reservation> petsitterRevList(int userNo, PageInfo pi, String keyword) {
		return reservationDao.petsitterRevList(sqlSession, userNo, pi, keyword);
	}

	@Override
	public ArrayList<Reservation> dateSelect(int userNo, String startDate, String endDate) {
		return reservationDao.dateSelect(sqlSession, userNo, startDate, endDate);
	}

	@Override
	public Reservation petsitterRevDetail(int rNo) {
		return reservationDao.petsitterRevDetail(sqlSession, rNo);
	}

	@Override
	public int insertJournal(int cNo, String careTitle, String careDesc) {
		return reservationDao.insertJournal(sqlSession, cNo, careTitle, careDesc);
	}

	@Override
	public int insertJournalFile(ArrayList<CareJournal> list) {
		return reservationDao.insertJournalFile(sqlSession, list);
	}

	@Override
	public int selectCareCount(int userNo, String keyword) {
		return reservationDao.selectCareCount(sqlSession, userNo, keyword);
	}
	
	@Override
	public ArrayList<CareJournal> journalList(PageInfo pi, int userNo, String keyword, String options) {
		return reservationDao.journalList(sqlSession, pi, userNo, keyword, options);
	}

	@Override
	public ArrayList<CareJournal> careDetail(int jno) {
		return reservationDao.careDetail(sqlSession, jno);
	}

	@Override
	public int weekPay(int userNo) {
		return reservationDao.weekPay(sqlSession, userNo);
	}

	@Override
	public int totalPay(int userNo) {
		return reservationDao.totalPay(sqlSession, userNo);
	}

	@Override
	public ArrayList<Payment> payList(int userNo) {
		return reservationDao.payList(sqlSession, userNo);
	}

	@Override
	public ArrayList<Payment> searchPay(int userNo, String startDate, String endDate) {
		return reservationDao.searchPay(sqlSession, userNo, startDate, endDate);
	}

	@Override
	public int selectJournalCount(int userNo) {
		return reservationDao.selectJournalCount(sqlSession, userNo);
	}

	@Override
	public ArrayList<CareJournal> careJournalManagement(PageInfo pi, int userNo, String options) {
		return reservationDao.careJournalManagement(sqlSession, pi, userNo, options);
	}

	@Override
	public CareJournal updateJournal(int jno) {
		return reservationDao.updateJournal(sqlSession, jno);
	}

	@Override
	public int updateCare(int jno, String careTitle, String careDesc) {
		return reservationDao.updateCare(sqlSession, jno, careTitle, careDesc);
	}

	@Override
	public int updateCareFile(ArrayList<CareJournal> list) {
		return reservationDao.updateCareFile(sqlSession, list);
	}

	@Override
	public int deleteJournal(int jno) {
		return reservationDao.deleteJournal(sqlSession, jno);
	}

}
