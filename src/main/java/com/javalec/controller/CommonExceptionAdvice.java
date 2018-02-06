package com.javalec.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice //�ش� Ŭ���� ��ü�� ��Ʈ�ѷ����� �߻��ϴ� Exception�� ó���ϴ� Ŭ������� ���� ���
public class CommonExceptionAdvice {
  private static final Logger logger = LoggerFactory.getLogger(CommonExceptionAdvice.class);

  //@ExceptionHandler(Exception.class) //Exception Ÿ������ ó���Ǵ� ��� ���ܸ� ó��
  public String common(Exception e) {

    logger.info(e.toString());

    return "error_common";
  }

  @ExceptionHandler(Exception.class) //Exception Ÿ������ ó���Ǵ� ��� ���ܸ� ó��
  private ModelAndView errorModelAndView(Exception ex) { //�Ķ���ͷ� ExceptionŸ���� ��ü�� ��� ����

    ModelAndView modelAndView = new ModelAndView(); //Model�����Ϳ� Viewó���� ���ÿ� ������ ��ü
    modelAndView.setViewName("/error_common");
    modelAndView.addObject("exception", ex);

    return modelAndView; //error_common.jsp���� BoardController�� ���� �� �߻��ϴ� Exception�� ���ϰ� �� �� ����
  }

}


