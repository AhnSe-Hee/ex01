package com.javalec.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.javalec.domain.Criteria;
import com.javalec.domain.PageMaker;
import com.javalec.domain.ReplyVO;
import com.javalec.service.ReplyService;

@RestController
@RequestMapping("/replies")
public class ReplyController {

  @Inject
  private ReplyService service;

  //http://localhost:8181/replies/
  @RequestMapping(value = "", method = RequestMethod.POST)
  public ResponseEntity<String> register(@RequestBody ReplyVO vo) { //데이터 전송방식은 JSON이기에 @RequestBody 사용

    ResponseEntity<String> entity = null;
    try {
      service.addReply(vo);
      entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
    } catch (Exception e) {
      e.printStackTrace();
      entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST); //댓글 등록 실패시, 에외 메시지와 400 상태코드를 전송
    }
    
    return entity; //반환타입이 ResponseEntity<String>
  }

  //http://localhost:8181/replies/all/1111
  @RequestMapping(value = "/all/{bno}", method = RequestMethod.GET) //value에 {bno}는 메소드 파라미터에서 @PathVariable("bno")로 활용
  public ResponseEntity<List<ReplyVO>> list(@PathVariable("bno") Integer bno) {

    ResponseEntity<List<ReplyVO>> entity = null;
    try {
      entity = new ResponseEntity<>(service.listReply(bno), HttpStatus.OK); //데이터+HTTP상태코드

    } catch (Exception e) {
      e.printStackTrace();
      entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    return entity;
  }

  //http://localhost:8181/replies/1111  
  @RequestMapping(value = "/{rno}", method = { RequestMethod.PUT, RequestMethod.PATCH }) //PUT, PATCH 모두 지원함
  public ResponseEntity<String> update(@PathVariable("rno") Integer rno, @RequestBody ReplyVO vo) {

    ResponseEntity<String> entity = null;
    try {
      vo.setRno(rno);
      service.modifyReply(vo);

      entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
    } catch (Exception e) {
      e.printStackTrace();
      entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
    return entity;
  }

  //http://localhost:8181/replies/1
  @RequestMapping(value = "/{rno}", method = RequestMethod.DELETE)
  public ResponseEntity<String> remove(@PathVariable("rno") Integer rno) {

    ResponseEntity<String> entity = null;
    try {
      service.removeReply(rno);
      entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
    } catch (Exception e) {
      e.printStackTrace();
      entity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
    return entity;
  }

  //http://localhost:8181/replies/{게시물 번호}/{페이지 번호}
  @RequestMapping(value = "/{bno}/{page}", method = RequestMethod.GET) //페이지 처리를 위함
  public ResponseEntity<Map<String, Object>> listPage(
      @PathVariable("bno") Integer bno,
      @PathVariable("page") Integer page) {
	  //Ajax로 호출되기에 model객체 사용 불가하며 전달할 데이터를 담기 위한 객체가 필요 (ex. Map, List..)
    ResponseEntity<Map<String, Object>> entity = null;
    
    try {
      Criteria cri = new Criteria();
      cri.setPage(page);

      PageMaker pageMaker = new PageMaker();
      pageMaker.setCri(cri);

      Map<String, Object> map = new HashMap<String, Object>();
      List<ReplyVO> list = service.listReplyPage(bno, cri);

      map.put("list", list); //페이징 처리된 댓글의 목록

      int replyCount = service.count(bno);
      pageMaker.setTotalCount(replyCount);

      map.put("pageMaker", pageMaker); //pageMaker 클래스 객체

      entity = new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);

    } catch (Exception e) {
      e.printStackTrace();
      entity = new ResponseEntity<Map<String, Object>>(HttpStatus.BAD_REQUEST);
    }
    
    return entity;
  }

}
