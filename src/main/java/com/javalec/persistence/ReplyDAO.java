package com.javalec.persistence;

import java.util.List;

import com.javalec.domain.Criteria;
import com.javalec.domain.ReplyVO;

public interface ReplyDAO {

  public List<ReplyVO> list(Integer bno) throws Exception;

  public void create(ReplyVO vo) throws Exception;

  public void update(ReplyVO vo) throws Exception;

  public void delete(Integer rno) throws Exception;

  public List<ReplyVO> listPage(Integer bno, Criteria cri) throws Exception; 
  //�Խù� ��ȣ�� �´� ������ �˻� ���� bno, ����¡ ó���� ����ϴ� cri�� �Ű������� ����

  public int count(Integer bno) throws Exception;
  //����¡ ó�� ���� �ش� �Խù��� ��� ���� ī��Ʈ

  public int getBno(Integer rno) throws Exception;

}
