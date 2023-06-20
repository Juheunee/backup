package com.kh.petsisters.reservation.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.kh.petsisters.common.model.vo.PageInfo;
import com.kh.petsisters.common.template.Pagination;
import com.kh.petsisters.member.model.vo.Member;
import com.kh.petsisters.payment.model.vo.Payment;
import com.kh.petsisters.petsitter.model.vo.PetSitter;
import com.kh.petsisters.reservation.model.service.ReservationService;
import com.kh.petsisters.reservation.model.vo.CareJournal;
import com.kh.petsisters.reservation.model.vo.Reservation;
import com.kh.petsisters.reservation.model.vo.Review;

@Controller
public class ReservationController {
	
	@Autowired
	private ReservationService reservationService;
	
	/**
	 * 예약 리스트 조회
	 * @param currentPage
	 * @param checkReview
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value="reservationList")
	public String selectPetsitterList(@RequestParam(value="rPage", defaultValue="1") 
									  int currentPage,
									  @RequestParam(value="checkReview", required=false) Integer checkReview,
									  Model model, HttpSession session) {
		
		
		int userNo = ((Member)(session.getAttribute("loginUser"))).getUserNo();
		
		int listCount = reservationService.selectListCount(userNo);
		
		int pageLimit = 10;
		int boardLimit = 6;
		
		PageInfo pi = Pagination.getPageInfo(listCount, currentPage, pageLimit, boardLimit);
		
		ArrayList<Reservation> list = reservationService.selectPetsitterList(pi, userNo, checkReview);

		model.addAttribute("pi", pi);
		model.addAttribute("list", list);
		
		return "reservation/reservationList";
	}
	
	@RequestMapping(value="insertReserv", produces="text/html; charset=UTF-8")
	public ModelAndView selectList(@RequestParam(value="startDate", defaultValue="") String startDate,
								   @RequestParam(value="endDate", defaultValue="") String endDate,
								   ModelAndView mv) {
		
		Reservation r = new Reservation();
		r.setStartDate(startDate);
		r.setEndDate(endDate);
		
		// 펫시터 리스트 전체 조회
		List<Reservation> list = reservationService.selectList(r);
		
		mv.addObject("pi", pi)
		  .addObject("list", list)
		  .setViewName("petsitter/petSitterListView");
		
		return mv;
	}
	
	/**
	 * 후기 작성 시 기본 정보 불러오기
	 * @param model
	 * @param writeReviewNo
	 * @return
	 */
	@RequestMapping(value="getReviewDate")
	public String selectReview(Model model, @RequestParam(value="rNo") int writeReviewNo) {
		
		Reservation rev = reservationService.selectReview(writeReviewNo);
		
		model.addAttribute("rev", rev);
		
		return "reservation/review";
	}
	
	
	/**
	 * 후기 작성
	 * @param upfile
	 * @param session
	 * @param model
	 * @param r
	 * @return
	 */
	@RequestMapping("reviewInsert")
	public String insertReview(MultipartFile upfile, HttpSession session, Model model, Review r) {
		
		if(!upfile.getOriginalFilename().equals("")) {
			
			String changeName = saveFile(upfile, session);
			
			r.setFilePath(upfile.getOriginalFilename());
			r.setChangeName("resources/upFiles/review_upfiles/" + changeName);
			
		}
		
		int result = reservationService.insertReview(r);
		if(result > 0) {
			
			session.setAttribute("writeMsg", "등록되었습니다!");
			
			return "redirect:/reservationList";
		} else {
			
			//TODO 에러 페이지만들면 포워딩 바꿔줄 것
			return null;
		}
	}
	
	
	/**
	 * 후기 수정시 기본 정보 불러오기
	 * @param model
	 * @param rNo
	 * @return
	 */
	@RequestMapping("reviewUpdate")
	public String updateReview(Model model, @RequestParam(value = "rNo") int rNo) {
		
		Review r = reservationService.updateReview(rNo);
		
		model.addAttribute("r", r);
		
		return "reservation/reviewUpdate";
	}
	
	
	/**
	 * 후기 수정
	 * @param model
	 * @param reupfile
	 * @param session
	 * @param r
	 * @return
	 */
	@RequestMapping("updateReviewForm")
	public String updateForm(Model model, MultipartFile reupfile,
							HttpSession session, Review r) {
		
		if(!reupfile.getOriginalFilename().contentEquals("")) {
			
			String changeName = saveFile(reupfile, session);
			
			r.setFilePath(reupfile.getOriginalFilename());
			r.setChangeName("resources/upFiles/review_upfiles/" + changeName);
		}
		
		int result = reservationService.updateForm(r);
		
		if(result > 0) {
			
			session.setAttribute("updateMsg", "수정되었습니다!");
			
			return "redirect:/reservationList";
		} else {
			
			//TODO 에러페이지 완성시 포워딩 변경할 것
			return null;
		}
		
	}
	
	
	/**
	 * 예약 리스트 삭제
	 * @param model
	 * @param session
	 * @param rNo
	 * @return
	 */
	@RequestMapping("deleteReservation")
	public String deleteReservation(Model model, HttpSession session, @RequestParam(value = "rNo") int rNo) {
		
		
		int result = reservationService.deleteReservation(rNo);
		
		if(result > 0) {
			
			session.setAttribute("deleteMsg", "삭제되었습니다");
			
			return "redirect:/reservationList";
		} else {
			
			//TODO 에러페이지 완성 시 포워딩 바꿔줄 것
			return null;
		}
	}
	
	/**
	 * 예약페이지 상세 보기
	 * @param model
	 * @param rNo
	 * @return
	 */
	@RequestMapping("reservationDetail")
	public String reservationDetail(Model model, 
			@RequestParam(value = "rNo") int rNo) {
		
		// System.out.println(rNo);
		Reservation rev = reservationService.reservationDetail(rNo);
		
		model.addAttribute("rev", rev);
		
		return "reservation/reservationDetail";
	}
	
	
	/**
	 * 돌봄일지 목록 조회
	 * @param model
	 * @param session
	 * @param currentPage
	 * @param keyword
	 * @param options
	 * @return
	 */
	@RequestMapping("journalList")
	public String journalList(Model model, HttpSession session,
			@RequestParam(value="cPage", defaultValue="1") int currentPage,
			@RequestParam(value="keyword", required=false) String keyword,
			@RequestParam(value="options", required=false) String options) {
		
		int userNo = ((Member)(session.getAttribute("loginUser"))).getUserNo();
		
		int listCount = reservationService.selectCareCount(userNo, keyword);
		
		int pageLimit = 10;
		int boardLimit = 6;
		
		PageInfo pi = Pagination.getPageInfo(listCount, currentPage, pageLimit, boardLimit);
		
		ArrayList<CareJournal> list = reservationService.journalList(pi, userNo, keyword, options);
		
		model.addAttribute("list", list);
		model.addAttribute("pi", pi);
		model.addAttribute("options", options);
		model.addAttribute("keyword", keyword);
		
		return "reservation/careJournalList";
	}
	
	
	/**
	 * 돌봄일지 상세보기
	 * @param jno
	 * @param model
	 * @return
	 */
	@RequestMapping("careDetail")
	public String careDetail(@RequestParam(value="jno") int jno, Model model) {
		
		ArrayList<CareJournal> list = reservationService.careDetail(jno);
		
		model.addAttribute("list", list);
		
		return "reservation/careJournalDetail";
	}
	
	
	/**
	 * 정산조회 목록
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("payList")
	public String patList(HttpSession session, Model model) {
		
		int userNo = ((Member)(session.getAttribute("loginUser"))).getUserNo();
		
		int weekPay = reservationService.weekPay(userNo);
		
		int totalPay = reservationService.totalPay(userNo);
		
		ArrayList<Payment> list = reservationService.payList(userNo);
		
		model.addAttribute("weekPay", weekPay);
		model.addAttribute("totalPay", totalPay);
		model.addAttribute("list", list);
		
		/*
		// 월별로 묶을 Map 생성
		Map<String, List<Payment>> monthlyPayments = new HashMap<>();
		
		// 데이터를 월별로 묶기
		for (Payment payment : list) {
		    // Payment 데이터의 payDate에서 월(Month) 정보 추출
		    String month = new SimpleDateFormat("MM").format(payment.getPayDate());
		    
		    // 해당 월의 Payment 리스트 가져오기
		    List<Payment> monthlyPaymentList = monthlyPayments.get(month);
		    
		    // 리스트가 없으면 새로 생성하여 맵에 추가
		    if (monthlyPaymentList == null) {
		        monthlyPaymentList = new ArrayList<>();
		        monthlyPayments.put(month, monthlyPaymentList);
		    }
		    
		    // Payment 데이터를 해당 월의 리스트에 추가
		    monthlyPaymentList.add(payment);
		}
		
		
		// 월별로 묶인 데이터 출력 예시
		for (Map.Entry<String, List<Payment>> entry : monthlyPayments.entrySet()) {
		    String month = entry.getKey();
		    List<Payment> monthlyPaymentList = entry.getValue();
		    
		    System.out.println("월: " + month);
		    
		    for (Payment payment : monthlyPaymentList) {
		        // 일자별로 세부적인 처리를 수행하여 사용하면 됩니다.
		        // payment.getPayDate()로 일자 정보를 가져올 수 있습니다.
		        // payment.getPayDesc(), payment.getPayPrice() 등의 필요한 정보를 활용합니다.
		        // 여기서는 간단히 일자 정보만 출력하도록 예시를 작성하였습니다.
		        System.out.println("일자: " + new SimpleDateFormat("dd").format(payment.getPayDate()));
		    }
		    
		    System.out.println("===================");
		}
		
		*/
		
			
		return "notice/settlementinquiry";
	}
	
	
	/**
	 * 정산조회 ajax 요청 처리
	 * @param session
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="searchPay", produces = "application/json; charset=UTF-8")
	public String searchPay(HttpSession session, String startDate, String endDate) {
		
		int userNo = ((Member)(session.getAttribute("loginUser"))).getUserNo();
		
		ArrayList<Payment> list = reservationService.searchPay(userNo, startDate, endDate);
		
		return new Gson().toJson(list);
		
	}
	
	
	
	
	
	
	
	
	
	// 여기서 부터 펫시터 관련 컨트롤
	/**
	 * 펫시터 예약 리스트 조회
	 * @param session
	 * @param model
	 * @param currentPage
	 * @param keyword
	 * @return
	 */
	@RequestMapping(value="petsitterRev", produces = "application/json; charset=UTF-8")
	public String petsitterRevList(HttpSession session, Model model,
			@RequestParam(value="pPage", defaultValue="1") int currentPage,
			@RequestParam(value="keyword", required=false) String keyword
			) {
		
		int userNo = ((Member)(session.getAttribute("loginUser"))).getUserNo();
		
		int listCount = reservationService.selectListPetsitterRev(userNo,keyword);
		
		int pageLimit = 10;
		int boardLimit = 10;
		
		PageInfo pi = Pagination.getPageInfo(listCount, currentPage, pageLimit, boardLimit);
		
		ArrayList<Reservation> rev = reservationService.petsitterRevList(userNo, pi, keyword);
		
		
		model.addAttribute("rev", rev);
		model.addAttribute("pi", pi);
		model.addAttribute("keyword", keyword);
		
		return "reservation/reservationListPetsiter";
	}


	/**
	 * 펫시터 예약 리스트 날짜 조회
	 * @param startDate
	 * @param endDate
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="dateFilter", produces = "application/json; charset=UTF-8")
	public String dateFilter(String startDate, String endDate, HttpSession session) {
		
		int userNo = ((Member)(session.getAttribute("loginUser"))).getUserNo();
		
		ArrayList<Reservation> rev = reservationService.dateSelect(userNo, startDate, endDate);
		
		return new Gson().toJson(rev);
	}

	
	/**
	 * 펫시터 예약 상세페이지
	 * @param model
	 * @param rNo
	 * @return
	 */
	@RequestMapping("petsitterRevDetail")
	public String petsitterRevDetail(Model model, int rNo) {
		
		Reservation r = reservationService.petsitterRevDetail(rNo);
		
		model.addAttribute("r", r);
		
		return "reservation/reservationDetailPetsiter";
	}
	
	
	/**
	 * 돌봄일지 작성 폼
	 * @param model
	 * @param cNo
	 * @return
	 */
	@RequestMapping("journalEnroll")
	public String carejournalEnrollForm(Model model, int cNo) {
		
		model.addAttribute("cNo", cNo);
		
		return "reservation/carejournalEnrollForm";
	}
	
	
	/**
	 * 돌봄일지 관리 리스트
	 * @param options
	 * @param currentPage
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("journalManager")
	public String careJournalManagement(@RequestParam(value="options", required=false) String options,
										@RequestParam(value="cPage", defaultValue="1") int currentPage,
										HttpSession session, Model model) {
		
		int userNo = ((Member)(session.getAttribute("loginUser"))).getUserNo();
		
		int listCount = reservationService.selectJournalCount(userNo);
		
		int pageLimit = 10;
		int boardLimit = 6;
		
		PageInfo pi = Pagination.getPageInfo(listCount, currentPage, pageLimit, boardLimit);
		
		ArrayList<CareJournal> list = reservationService.careJournalManagement(pi, userNo, options);
		
		System.out.println(list);
		model.addAttribute("list", list);
		model.addAttribute("options", options);
		model.addAttribute("pi", pi);
		
		return "reservation/careJournalManagement";
	}
	
	
	
	/**
	 * 돌봄일지 수정 초기정보 셋팅용
	 * @param jno
	 * @param model
	 * @return
	 */
	@RequestMapping("updateJournal")
	public String updateJournal(@RequestParam(value="jNo") int jno, Model model) {
		
		CareJournal c = reservationService.updateJournal(jno);
		
		model.addAttribute("c", c);
		
		return "reservation/carejournalUpdateForm";
	}
	
	
	/**
	 * 돌봄일지 수정
	 * @param fileNames
	 * @param careTitle
	 * @param careDesc
	 * @param jno
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="updateCare" , method = RequestMethod.POST)
	public String updateCare(
			@RequestParam(required = false, name = "fileNames") List<MultipartFile> fileNames,
            @RequestParam String careTitle,
            @RequestParam String careDesc,
            @RequestParam int jno,
            HttpSession session) {
		
		int result = reservationService.updateCare(jno, careTitle, careDesc);
		
		
		ArrayList<CareJournal> list = new ArrayList<>();
		if(fileNames != null) {
			
	        for (MultipartFile file : fileNames) {
	            String originalFileName = file.getOriginalFilename();
	            String savedFileName = saveFiles(file, session);
	            
	            CareJournal cj = new CareJournal();
		        cj.setOriginName(originalFileName);
		        cj.setChangeName(savedFileName);
		        cj.setJno(jno);
		        list.add(cj);
	        }
	        if(result > 0) {
	        	
	        	int res = reservationService.updateCareFile(list);
	        }
		}
		
		return "redirect:/careJournalManagement";
	}
	
	
	/**
	 * 돌봄일지 삭제
	 * @param model
	 * @param jno
	 * @return
	 */
	@RequestMapping("deleteJournal")
	public String deleteJournal(Model model, @RequestParam int jno, HttpSession session) {
		
		int result = reservationService.deleteJournal(jno);
		
		if(result > 0) {
			
			session.setAttribute("deleteMsg", "삭제되었습니다");
			
			return "redirect:/journalManager";
		} else {
			
			return null;
		}
		
	}
	
	
	/**
	 * 돌봄일지 다중 파일 업로드 
	 * @param delFile
	 * @param fileNames
	 * @param careTitle
	 * @param careDesc
	 * @param cNo
	 * @param session
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "insertJournal", method = RequestMethod.POST)
    public String insertJournal(@RequestParam(required = false, name = "delFile") List<String> delFile,
    							@RequestParam(required = false, name = "fileNames") List<MultipartFile> fileNames,
                                @RequestParam String careTitle,
                                @RequestParam String careDesc,
                                @RequestParam int cNo,
                                HttpSession session) {
		
		// saveFile 메소드 (수정파일명 생성 + 서버에 파일 저장 + 수정파일명을 리턴)
		// => 다중파일업로드를 하려면?? => fileNames 리스트의 사이즈만큼 반복을 돌리면서 saveFile 호출
		
		
		/*
	    // 파일 저장용
	    List<String> savedFileNames = new ArrayList<>();
	    if(delFile != null) {
		    for (String fileName : delFile) {
		        String savedFileName = saveFiless(fileName, session);
		        if (savedFileName != null) {
		            savedFileNames.add(savedFileName);
		        }
		    }
	    } 
	    
	    String 으로 받아온 단순 경로는 파일 저장시 용량이 0 이므로 무용지물이다
		*/
		
		
	    // 파일 업로드
	    // List<String> uploadedFileNames = new ArrayList<>();
	    
		int result = reservationService.insertJournal(cNo, careTitle, careDesc);

	    ArrayList<CareJournal> list = new ArrayList<>();
	    if (fileNames != null) {
	    							// List<MultipartFile> => 0 번째방부터 마지막방까지 MultipartFile 객체가 담겨잇음
	        for (MultipartFile file : fileNames) {
	            String originalFileName = file.getOriginalFilename();
	            String savedFileName = saveFiles(file, session);
	            
	            // 넘어온 첨부파일만큼 마이바티스의 파라미터로 넘기게 가공 (여기서부터 고민)
	            CareJournal cj = new CareJournal();
		        cj.setOriginName(originalFileName);
		        cj.setChangeName(savedFileName);
		        list.add(cj);
		        // System.out.println("originalFileName: " + originalFileName);
		        // System.out.println("savedFileName: " + savedFileName);
	        }
	        
	        // System.out.println("list: " + list);
	        if(result > 0) {
	        	
	        	int res = reservationService.insertJournalFile(list);
	        }
	    }
        return "";
    }
	


	
	
	
	
	/**
	 * 다중 파일 업로드 메소드
	 * @param fileName
	 * @param session
	 * @return
	 */
	public String saveFiles(MultipartFile upfile, HttpSession session) {
		
		String originName = upfile.getOriginalFilename();
		String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		
		int ranNum = (int)(Math.random() * 90000 + 10000);
		
		String ext = originName.substring(originName.lastIndexOf("."));
		
		String changeName = currentTime + ranNum + ext;
		
		String savePath = session.getServletContext().getRealPath("resources/upFiles/care_upfiles/");
		
		try {
			upfile.transferTo(new File(savePath + changeName));
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		return changeName;
	}
	
	
	/**
	 * 파일 저장 메소드
	 * @param upfile
	 * @param session
	 * @return
	 */
	public String saveFile(MultipartFile upfile, HttpSession session) {
		
		String originName = upfile.getOriginalFilename();
		String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		
		int ranNum = (int)(Math.random() * 90000 + 10000);
		
		String ext = originName.substring(originName.lastIndexOf("."));
		
		String changeName = currentTime + ranNum + ext;
		
		String savePath = session.getServletContext().getRealPath("resources/upFiles/review_upfiles/");
		
		try {
			upfile.transferTo(new File(savePath + changeName));
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		return changeName;
	}
	
	
	
	
	
	
	// 에러페이지 포워딩 테스트
	@RequestMapping("errorPage")
	public String error() {
		
		return "common/errorPage";
	}
	
	

}
