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
  public ResponseEntity<String> register(@RequestBody ReplyVO vo) { //������ ���۹���� JSON�̱⿡ @RequestBody ���

    ResponseEntity<String> entity = null;
    try {
      service.addReply(vo);
      entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
    } catch (Exception e) {
      e.printStackTrace();
      entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST); //��� ��� ���н�, ���� �޽����� 400 �����ڵ带 ����
    }
    
    return entity; //��ȯŸ���� ResponseEntity<String>
  }

  //http://localhost:8181/replies/all/1111
  @RequestMapping(value = "/all/{bno}", method = RequestMethod.GET) //value�� {bno}�� �޼ҵ� �Ķ���Ϳ��� @PathVariable("bno")�� Ȱ��
  public ResponseEntity<List<ReplyVO>> list(@PathVariable("bno") Integer bno) {

    ResponseEntity<List<ReplyVO>> entity = null;
    try {
      entity = new ResponseEntity<>(service.listReply(bno), HttpStatus.OK); //������+HTTP�����ڵ�

    } catch (Exception e) {
      e.printStackTrace();
      entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    return entity;
  }

  //http://localhost:8181/replies/1111  
  @RequestMapping(value = "/{rno}", method = { RequestMethod.PUT, RequestMethod.PATCH }) //PUT, PATCH ��� ������
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

  //http://localhost:8181/replies/{�Խù� ��ȣ}/{������ ��ȣ}
  @RequestMapping(value = "/{bno}/{page}", method = RequestMethod.GET) //������ ó���� ����
  public ResponseEntity<Map<String, Object>> listPage(
      @PathVariable("bno") Integer bno,
      @PathVariable("page") Integer page) {
	  //Ajax�� ȣ��Ǳ⿡ model��ü ��� �Ұ��ϸ� ������ �����͸� ��� ���� ��ü�� �ʿ� (ex. Map, List..)
    ResponseEntity<Map<String, Object>> entity = null;
    
    try {
      Criteria cri = new Criteria();
      cri.setPage(page);

      PageMaker pageMaker = new PageMaker();
      pageMaker.setCri(cri);

      Map<String, Object> map = new HashMap<String, Object>();
      List<ReplyVO> list = service.listReplyPage(bno, cri);

      map.put("list", list); //����¡ ó���� ����� ���

      int replyCount = service.count(bno);
      pageMaker.setTotalCount(replyCount);

      map.put("pageMaker", pageMaker); //pageMaker Ŭ���� ��ü

      entity = new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);

    } catch (Exception e) {
      e.printStackTrace();
      entity = new ResponseEntity<Map<String, Object>>(HttpStatus.BAD_REQUEST);
    }
    
    return entity;
  }

}
