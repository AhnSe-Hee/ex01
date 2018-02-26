package com.javalec.persistence;

import com.javalec.domain.MessageVO;

//�޽����� ���� ���, ����, ������Ʈ ó��
public interface MessageDAO {

  public void create(MessageVO vo) throws Exception;

  public MessageVO readMessage(Integer mid) throws Exception;

  public void updateState(Integer mid) throws Exception;

}
