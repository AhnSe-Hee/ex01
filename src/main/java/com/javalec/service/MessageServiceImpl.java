package com.javalec.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.javalec.domain.MessageVO;
import com.javalec.persistence.MessageDAO;
import com.javalec.persistence.PointDAO;

@Service
public class MessageServiceImpl implements MessageService {

  @Inject
  private MessageDAO messageDAO;

  @Inject
  private PointDAO pointDAO;


  //@Transactional
  @Override
  public void addMessage(MessageVO vo) throws Exception {

    messageDAO.create(vo); //���ο� �޽��� �߰�
    pointDAO.updatePoint(vo.getSender(), 10); //�޽��� ���� �̿��� ����Ʈ 10�� �߰�
  }

  @Override
  public MessageVO readMessage(String uid, Integer mid) throws Exception {
 
    messageDAO.updateState(mid); //�޽��� ���� ����

    pointDAO.updatePoint(uid, 5); //�޽����� �� �̿��� ����Ʈ 5�� �߰�

    return messageDAO.readMessage(mid); //�޽����� ��ȸ�ؼ� ��ȯ
  }
}
