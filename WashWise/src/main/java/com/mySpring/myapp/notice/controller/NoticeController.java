package com.mySpring.myapp.notice.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.mySpring.myapp.notice.service.NoticeService;
import com.mySpring.myapp.notice.vo.NoticeVO;
import com.mySpring.myapp.member.vo.MemberVO;

@Controller("noticeController")
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    // 공지사항 목록
    @RequestMapping(value = "/listNotices.do", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView listNotices(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String viewName = (String) request.getAttribute("viewName");
        System.out.println("View Name: " + viewName); // 디버깅 로그 추가
        ModelAndView mav = new ModelAndView(viewName);
        mav.addObject("noticesList", noticeService.listNotices());
        return mav;
    }

    // 공지사항 작성 화면
    @RequestMapping(value = "/writeForm.do", method = RequestMethod.GET)
    public ModelAndView writeForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String viewName = (String) request.getAttribute("viewName");
        ModelAndView mav = new ModelAndView(viewName);
        return mav;
    }

    // 공지사항 작성
    @RequestMapping(value = "/addNotice.do", method = RequestMethod.POST)
    public ModelAndView addNotice(@RequestParam Map<String, Object> noticeMap, HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        MemberVO memberVO = (MemberVO) session.getAttribute("member");

        // 작성자 ID 추가 및 관리자 권한 확인
        if (memberVO == null || !"ADMIN".equalsIgnoreCase(memberVO.getRole())) {
            ModelAndView mav = new ModelAndView("redirect:/notice/listNotices.do");
            mav.addObject("errorMessage", "관리자 권한이 필요합니다.");
            return mav;
        }

        String id = memberVO.getId();
        noticeMap.put("id", id);

        try {
            noticeService.addNewNotice(noticeMap);
        } catch (Exception e) {
            e.printStackTrace();
            ModelAndView mav = new ModelAndView("redirect:/notice/listNotices.do");
            mav.addObject("errorMessage", "공지사항 등록 중 오류가 발생했습니다.");
            return mav;
        }

        // 등록 후 공지사항 목록으로 리다이렉트
        return new ModelAndView("redirect:/notice/listNotices.do");
    }

    // 공지사항 상세 보기
    @RequestMapping(value = "/viewNotice.do", method = RequestMethod.GET)
    public ModelAndView viewNotice(@RequestParam("articleno") int articleno, HttpServletRequest request,
                                   HttpServletResponse response) throws Exception {
        String viewName = (String) request.getAttribute("viewName");
        ModelAndView mav = new ModelAndView(viewName);
        mav.addObject("notice", noticeService.viewNotice(articleno));
        return mav;
    }

    // 공지사항 수정
    @RequestMapping(value = "/editNotice.do", method = RequestMethod.POST)
    public ModelAndView editNotice(@RequestParam Map<String, Object> noticeMap) throws Exception {
        try {
            noticeService.modNotice(noticeMap);
            return new ModelAndView("redirect:/notice/listNotices.do");
        } catch (Exception e) {
            e.printStackTrace();
            ModelAndView mav = new ModelAndView("redirect:/notice/listNotices.do");
            mav.addObject("errorMessage", "공지사항 수정 중 오류가 발생했습니다.");
            return mav;
        }
    }

    // 공지사항 삭제
    @RequestMapping(value = "/deleteNotice.do", method = RequestMethod.POST)
    public ModelAndView deleteNotice(@RequestParam("articleno") int articleno) throws Exception {
        try {
            noticeService.removeNotice(articleno);
        } catch (Exception e) {
            e.printStackTrace();
            ModelAndView mav = new ModelAndView("redirect:/notice/listNotices.do");
            mav.addObject("errorMessage", "공지사항 삭제 중 오류가 발생했습니다.");
            return mav;
        }
        return new ModelAndView("redirect:/notice/listNotices.do");
    }
}
