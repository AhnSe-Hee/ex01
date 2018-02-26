package com.javalec.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint; //���޵Ǵ� �Ķ���� �ľ� ���� ���̺귯��
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component //�������� ������ �νĵǱ� ���� ����
@Aspect //AOP ����� �ϴ� Ŭ������ ���� �߰��ؾ� ��
public class SampleAdvice {

  private static final Logger logger = LoggerFactory.getLogger(SampleAdvice.class);

  /*
   * @Before : �ش� �޼ҵ� ���� �� target�޼ҵ尡 ����
   * JointPoint : ����� ���޵Ǵ� �Ķ���� �ľ� �� ���Ǵ� �ڷ���
   * execution : Pointcut�� �����ϴ� AspectJ ����
   */
//  @Before("execution(* com.javalec.service.MessageService*.*(..))")
  public void startLog(JoinPoint jp) { //���ʿ� STS�� AOP�� ���õ� ������ ����� Ȯ�ε�
	  
	  logger.info("----------------------------");
	  logger.info("----------------------------");
	  logger.info(Arrays.toString(jp.getArgs())); //jp.getArgs : ���޵Ǵ� ��� �Ķ���͵��� Object �迭�� ������
  }
  
  /*
   * @Around : �ش� �޼ҵ�� target�޼ҵ� ��ü�� ��, �ڷ� ���� ����
   * ProcedingJoinPoint : Around���� ��� ������ �Ķ���� Ÿ������ JoinPoint ��� + ���� Advice ���� ��� + target��ü�� �޼ҵ� ���� ����
   * �޼ҵ� ���� �Ϲ����� Exception�� �ƴ�, ������ Throwable �����
   */
//  @Around("execution(* com.javalec.service.MessageService*.*(..))")
  public Object timeLog(ProceedingJoinPoint pjp)throws Throwable{

	  long startTime = System.currentTimeMillis();
	  logger.info(Arrays.toString(pjp.getArgs()));
      
	  Object result = pjp.proceed(); //proceed : ���� �޼ҵ� ȣ��

	  long endTime = System.currentTimeMillis();
	  logger.info( pjp.getSignature().getName()+ " : " + (endTime - startTime) + "ms" );
	  logger.info("=============================================");
	  
	  return result; //@Around�� ��� �ݵ�� Object�� ��ȯ�ؾ� ��
  }
}
