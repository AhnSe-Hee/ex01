package com.javalec.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;

//���� ���ε�� Ŭ����
public class UploadFileUtils {

  private static final Logger logger = 
      LoggerFactory.getLogger(UploadFileUtils.class);

//  public static String uploadFile(String uploadPath, 
//      String originalName, 
//      byte[] fileData)throws Exception{
//    
//    return null;
//  }
//  

  //���� ���� �� ���� ����
  public static String uploadFile(String uploadPath, //������ ������
                              String originalName, //���� ������ �̸�
                              byte[] fileData //���� ������
                              )throws Exception{
    
    UUID uid = UUID.randomUUID(); //1. ������ �� ����
    
    String savedName = uid.toString() +"_"+originalName; //2. ���ε� ���� �̸� ����
    
    String savedPath = calcPath(uploadPath); //3. ������ ����� ��� ��� (������ ����� '/��/��/��'���� ���� & ���ε� �⺻��ο� '/��/��/��' ���� ����)
    
    File target = new File(uploadPath +savedPath,savedName); //4. �⺻���+�������+���� �̸����� ���� ����
    
    FileCopyUtils.copy(fileData, target); //���� ������ ����
    
    String formatName = originalName.substring(originalName.lastIndexOf(".")+1); //���� ������ Ȯ����
    
    String uploadedFileName = null;
    
    if(MediaUtils.getMediaType(formatName) != null){//�̹��� Ÿ���� ���
      uploadedFileName = makeThumbnail(uploadPath, savedPath, savedName); //����� ����
    }else{ //�̹��� Ÿ���� ������ �ƴ� ���
      uploadedFileName = makeIcon(uploadPath, savedPath, savedName); //��� ó�� ���ڿ�
    }
    
    return uploadedFileName;
    
  }
  
  private static  String makeIcon(String uploadPath, 
      String path, 
      String fileName)throws Exception{

    String iconName = 
        uploadPath + path + File.separator+ fileName;
    
    return iconName.substring(
        uploadPath.length()).replace(File.separatorChar, '/');
  }
  
  //����� �̹��� ����
  private static  String makeThumbnail(
              String uploadPath, //�⺻ ���
              String path, //'/��/��/��' ����
              String fileName //���� ���ε� �� ���� �̸�
              )throws Exception{
            
    BufferedImage sourceImg = //���� �̹��� �ƴ� �޸𸮻� �̹����� �ǹ�
        ImageIO.read(new File(uploadPath + path, fileName));
    
    BufferedImage destImg = 
        Scalr.resize(sourceImg, 
            Scalr.Method.AUTOMATIC, 
            Scalr.Mode.FIT_TO_HEIGHT,100); //FIT_TO_HEIGHT: ����� ���� ���̸� 100px�� �����ϰ� ����� ����
    
    String thumbnailName = 
        uploadPath + path + File.separator +"s_"+ fileName;
    //s_�� �����ϸ� ����� �̹���, s_�� �Ƚ����ϸ� ���� ����
    
    File newFile = new File(thumbnailName);
    String formatName = 
        fileName.substring(fileName.lastIndexOf(".")+1);
    
    
    ImageIO.write(destImg, formatName.toUpperCase(), newFile);
    return thumbnailName.substring(
        uploadPath.length()).replace(File.separatorChar, '/'); //���������� \���ڴ� �����η� �ν� �ȵǱ⿡ /�� ġȯ
  } 
  
  //������ ����� '/��/��/��'���� ���� ��, '/��/��/��' ���� ����
  private static String calcPath(String uploadPath){
    
    Calendar cal = Calendar.getInstance();
    
    String yearPath = File.separator+cal.get(Calendar.YEAR);
    
    String monthPath = yearPath + 
        File.separator + 
        new DecimalFormat("00").format(cal.get(Calendar.MONTH)+1);

    String datePath = monthPath + 
        File.separator + 
        new DecimalFormat("00").format(cal.get(Calendar.DATE));
    
    makeDir(uploadPath, yearPath,monthPath,datePath);
    
    logger.info(datePath);
    
    return datePath;
  }
  
  
  private static void makeDir(String uploadPath, String... paths){
    
    if(new File(paths[paths.length-1]).exists()){
      return;
    }
    
    for (String path : paths) {
      
      File dirPath = new File(uploadPath + path);
      
      if(! dirPath.exists() ){
        dirPath.mkdir();
      } 
    }
  }
  
  
}
