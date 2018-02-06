package com.javalec.test;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import com.javalec.domain.BoardVO;
import com.javalec.domain.Criteria;
import com.javalec.domain.SearchCriteria;
import com.javalec.persistence.BoardDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations ={"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class BoardDAOTest {

  @Inject
  private BoardDAO dao;

  private static Logger logger = LoggerFactory.getLogger(BoardDAOTest.class);

  //@Test �տ� �ּ�"//"�� �ϳ��� Ǯ�鼭 DB�� ��ȭ Ȯ��
  //@Test
  public void testCreate() throws Exception {

    BoardVO board = new BoardVO();
    board.setTitle("���ο� ���� �ֽ��ϴ�.");
    board.setContent("���ο� ���� �ֽ��ϴ�.");
    board.setWriter("ahns0206");
    dao.create(board);
  }

  //@Test
  public void testRead() throws Exception {

	  logger.info(dao.read(8).toString()); //bno
  }

  //@Test
  public void testUpdate() throws Exception {

    BoardVO board = new BoardVO();
    board.setBno(1); //bno
    board.setTitle("������ ���Դϴ�.");
    board.setContent("���� �׽�Ʈ ");
    dao.update(board);
  }

  //@Test
  public void testDelete() throws Exception {

    dao.delete(8); //bno
  }

  //@Test
  public void testListAll() throws Exception {

    logger.info(dao.listAll().toString());

  }

  //@Test
  public void testListPage() throws Exception {

    int page = 3;

    List<BoardVO> list = dao.listPage(page);

    //���� �ֱ� ������ 3������ �з��� 10���� ��µǾ�� �� (�ֱ� 20��° �����ͺ��� 10��)
    for (BoardVO boardVO : list) {
      logger.info(boardVO.getBno() + ":" + boardVO.getTitle());
    }
  }

  //@Test
  public void testListCriteria() throws Exception {

    Criteria cri = new Criteria();
    cri.setPage(2);
    cri.setPerPageNum(20);
    //�ϱ� SQL���� ������
    //select * from tbl_board where bno>0 order by bno desc limit 20, 20

    List<BoardVO> list = dao.listCriteria(cri);

    for (BoardVO boardVO : list) {
      logger.info(boardVO.getBno() + ":" + boardVO.getTitle());
    }
  }

  //@Test
  public void testURI() throws Exception {

	//UriComponentsŬ������ Builder�������� path�� query�� �ش��ϴ� ���ڿ��� �߰��ؼ� ���ϴ� URI�� ������ �� �ְ� ��
    UriComponents uriComponents = UriComponentsBuilder.newInstance().path("/board/read").queryParam("bno", 12)
        .queryParam("perPageNum", 20).build();

    logger.info("/board/read?bno=12&perPageNum=20");
    logger.info(uriComponents.toString());

  }

  @Test
  public void testURI2() throws Exception {

	//UriComponentsŬ������ Ư�� URI�� ���� �����ϰ� �۾� �����ϰ� ��
    UriComponents uriComponents = UriComponentsBuilder.newInstance().path("/{module}/{page}").queryParam("bno", 12)
        .queryParam("perPageNum", 20).build().expand("board", "read").encode();
    //{module}��θ� board��, {page}��θ� read�� ����

    logger.info("/board/read?bno=12&perPageNum=20");
    logger.info(uriComponents.toString());
  }

  //@Test
  public void testDynamic1() throws Exception {

    SearchCriteria cri = new SearchCriteria();
    cri.setPage(1);
    cri.setKeyword("��");
    cri.setSearchType("t");

    logger.info("=====================================");

    List<BoardVO> list = dao.listSearch(cri);

    for (BoardVO boardVO : list) {
      logger.info(boardVO.getBno() + ": " + boardVO.getTitle());
    }

    logger.info("=====================================");

    logger.info("COUNT: " + dao.listSearchCount(cri));
  } //INFO : jdbc.sqltiming - select count(bno) from tbl_board where bno > 0 and title like CONCAT('%', '��', '%') 
    //�������� ���� ���� SQL���� ����� ���� Ȯ��

}
