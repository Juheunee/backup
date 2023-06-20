package com.kh.petsisters.reservation.model.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.kh.petsisters.common.model.vo.PageInfo;
import com.kh.petsisters.payment.model.vo.Payment;
import com.kh.petsisters.reservation.model.vo.CareJournal;
import com.kh.petsisters.reservation.model.vo.Reservation;
import com.kh.petsisters.reservation.model.vo.Review;

@Repository
public class ReservationDao {

	public int selectListCount(SqlSessionTemplate sqlSession, int userNo) {
		return sqlSession.selectOne("reservationMapper.selectListCount", userNo);
	}

	public ArrayList<Reservation> selectPetsitterList(SqlSessionTemplate sqlSession, PageInfo pi
			, int userNo, Integer checkReview) {
		
		Map<String, Object> parameters = new HashMap<>();
        parameters.put("userNo", userNo);
        parameters.put("checkReview", checkReview);
		int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();	// 건너뛸 숫자
		int limit = pi.getBoardLimit();		// 조회할 갯수
		
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		return (ArrayList)sqlSession.selectList("reservationMapper.selectPetsitterList", parameters, rowBounds);
	}
	
	public int insertReservation(SqlSessionTemplate sqlSession, Reservation r) {
		return sqlSession.insert("reservationMapper.insertReservation", r);
	}
	
	public Reservation selectReservation(SqlSessionTemplate sqlSession, int refPno) {
		return sqlSession.selectOne("reservationMapper.selectReservation", refPno);
	}

	public Reservation selectReview(SqlSessionTemplate sqlSession, int writeReviewNo) {
		return sqlSession.selectOne("reservationMapper.selectReview", writeReviewNo);
	}


	public int insertReview(SqlSessionTemplate sqlSession, Review r) {
		return sqlSession.insert("reservationMapper.insertReview", r);
	}

	public Review updateReview(SqlSessionTemplate sqlSession, int rNo) {
		return sqlSession.selectOne("reservationMapper.updateReview", rNo);
	}

	public int updateForm(SqlSessionTemplate sqlSession, Review r) {
		return sqlSession.update("reservationMapper.updateForm", r);
	}

	public int deleteReservation(SqlSessionTemplate sqlSession, int rNo) {
		return sqlSession.update("reservationMapper.deleteReservation", rNo);
	}

	public Reservation reservationDetail(SqlSessionTemplate sqlSession, int rNo) {
		return sqlSession.selectOne("reservationMapper.reservationDetail", rNo);
	}

	public ArrayList<Reservation> petsitterRevList(SqlSessionTemplate sqlSession, int userNo, PageInfo pi, String keyword) {
		
		int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();	// 건너뛸 숫자
		int limit = pi.getBoardLimit();		// 조회할 갯수
		
		Map<String, Object> parameters = new HashMap<>();
        parameters.put("userNo", userNo);
        parameters.put("keyword", keyword);
		
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		return (ArrayList)sqlSession.selectList("reservationMapper.petsitterRevList", parameters, rowBounds);
	}


	public int selectListPetsitterRev(SqlSessionTemplate sqlSession, int userNo, String keyword) {
		
		Map<String, Object> parameter = new HashMap<>();
		parameter.put("userNo", userNo);
		parameter.put("keyword", keyword);
		
		return sqlSession.selectOne("reservationMapper.selectListPetsitterRev", parameter);
	}

	public ArrayList<Reservation> dateSelect(SqlSessionTemplate sqlSession, int userNo, String startDate,
			String endDate) {
		
		Map<String, Object> parameter = new HashMap<>();
		parameter.put("userNo", userNo);
		parameter.put("startDate", startDate);
		parameter.put("endDate", endDate);
		
		return (ArrayList)sqlSession.selectList("reservationMapper.dateSelect", parameter);
	}

	public Reservation petsitterRevDetail(SqlSessionTemplate sqlSession, int rNo) {
		return sqlSession.selectOne("reservationMapper.petsitterRevDetail", rNo);
	}

	public int insertJournal(SqlSessionTemplate sqlSession, int cNo, String careTitle, String careDesc) {
		
		Map<String, Object> parameter = new HashMap<>();
		parameter.put("cNo", cNo);
		parameter.put("careTitle", careTitle);
		parameter.put("careDesc", careDesc);
		
		return sqlSession.insert("reservationMapper.insertJournal", parameter);
	}

	public int insertJournalFile(SqlSessionTemplate sqlSession, ArrayList<CareJournal> list) {
		
	    int result = 0;
	    for (CareJournal item : list) {
	        result += sqlSession.insert("reservationMapper.insertJournalFile", item);
	    }
	    return result;
	}

	public int selectCareCount(SqlSessionTemplate sqlSession, int userNo, String keyword) {
		
		Map<String, Object> parameter = new HashMap<>();
		parameter.put("userNo", userNo);
		parameter.put("keyword", keyword);
		
		return sqlSession.selectOne("reservationMapper.selectCareCount", parameter);
	}

	public ArrayList<CareJournal> journalList(SqlSessionTemplate sqlSession, PageInfo pi, int userNo
			, String keyword, String options) {
		
		Map<String, Object> parameter = new HashMap<>();
		parameter.put("userNo", userNo);
		parameter.put("keyword", keyword);
		parameter.put("options", options);
		
		int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
		int limit = pi.getBoardLimit();
		
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		return (ArrayList)sqlSession.selectList("reservationMapper.journalList", parameter, rowBounds);
	}

	public ArrayList<CareJournal> careDetail(SqlSessionTemplate sqlSession, int jno) {
		return (ArrayList)sqlSession.selectList("reservationMapper.careDetail", jno);
	}

	public int weekPay(SqlSessionTemplate sqlSession, int userNo) {
		return sqlSession.selectOne("reservationMapper.weekPay", userNo);
	}

	public int totalPay(SqlSessionTemplate sqlSession, int userNo) {
		return sqlSession.selectOne("reservationMapper.totalPay", userNo);
	}

	public ArrayList<Payment> payList(SqlSessionTemplate sqlSession, int userNo) {
		return (ArrayList)sqlSession.selectList("reservationMapper.payList", userNo);
	}

	public ArrayList<Payment> searchPay(SqlSessionTemplate sqlSession, int userNo, String startDate, String endDate) {
		
		Map<String, Object> parameter = new HashMap<>();
		parameter.put("userNo", userNo);
		parameter.put("startDate", startDate);
		parameter.put("endDate", endDate);
		
		return (ArrayList)sqlSession.selectList("reservationMapper.searchPay", parameter);
	}

	public int selectJournalCount(SqlSessionTemplate sqlSession, int userNo) {
		return sqlSession.selectOne("reservationMapper.selectJournalCount", userNo);
	}

	public ArrayList<CareJournal> careJournalManagement(SqlSessionTemplate sqlSession, PageInfo pi, int userNo,
			String options) {
		
		Map<String, Object> parameter = new HashMap<>();
		parameter.put("userNo", userNo);
		parameter.put("options", options);
		
		int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
		int limit = pi.getBoardLimit();
		
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		return (ArrayList)sqlSession.selectList("reservationMapper.careJournalManagement", parameter, rowBounds);
	}

	public CareJournal updateJournal(SqlSessionTemplate sqlSession, int jno) {
		return sqlSession.selectOne("reservationMapper.updateJournal", jno);
	}

	public int updateCare(SqlSessionTemplate sqlSession, int jno, String careTitle, String careDesc) {
		
		Map<String, Object> parameter = new HashMap<>();
		parameter.put("jno", jno);
		parameter.put("careTitle", careTitle);
		parameter.put("careDesc", careDesc);
		
		return sqlSession.update("reservationMapper.updateCare", parameter);
	}

	public int updateCareFile(SqlSessionTemplate sqlSession, ArrayList<CareJournal> list) {
		
	    int result = 0;
	    for (CareJournal item : list) {
	        result += sqlSession.update("reservationMapper.updateCareFile", item);
	    }
	    return result;
	}

	public int deleteJournal(SqlSessionTemplate sqlSession, int jno) {
		return sqlSession.update("reservationMapper.deleteJournal", jno);
	}



}
