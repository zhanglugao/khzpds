package com.khzpds.util;


import java.io.File;

import org.artofsolving.jodconverter.OfficeDocumentConverter;
import org.artofsolving.jodconverter.office.DefaultOfficeManagerConfiguration;
import org.artofsolving.jodconverter.office.OfficeManager;

public class Ooo32pdf {

	private static  OfficeManager officeManager;
	private static String OFFICE_HOME = "C:\\Program Files (x86)\\OpenOffice 4";
	private static int port[] = {8100};
	
	  public  void convert2PDF(String inputFile, String pdfFile) {
	        if(inputFile.endsWith(".doc")||inputFile.endsWith(".docx")||inputFile.endsWith(".txt")||inputFile.endsWith(".ppt")){
	           File docFile = new File(inputFile);
	           System.out.println(docFile.getAbsolutePath());
	           if(!docFile.exists())
	        	   System.out.println("文件不存在!");
	           else{
	           	startService();
		        System.out.println("进行文档转换转换:" + inputFile + " --> " + pdfFile);
		        OfficeDocumentConverter converter = new OfficeDocumentConverter(officeManager);
		        converter.convert(new File(inputFile),new File(pdfFile));
		        stopService();
	           }
	        }
	    }
	  public static void startService(){
	        DefaultOfficeManagerConfiguration configuration = new DefaultOfficeManagerConfiguration();
	        try {
	          System.out.println("准备启动服务....");
	            configuration.setOfficeHome(OFFICE_HOME);//设置OpenOffice.org安装目录
	            configuration.setPortNumbers(port); //设置转换端口，默认为8100
	            configuration.setTaskExecutionTimeout(1000 * 60 * 5L);//设置任务执行超时为5分钟
	            configuration.setTaskQueueTimeout(1000 * 60 * 60 * 24L);//设置任务队列超时为24小时
	         
	            officeManager = configuration.buildOfficeManager();
	            officeManager.start();    //启动服务
	            System.out.println("office转换服务启动成功!");
	        } catch (Exception ce) {
	            System.out.println("office转换服务启动失败!详细信息:" + ce);
	        }
	    }
	  public static void stopService(){
          System.out.println("关闭office转换服务....");
            if (officeManager != null) {
                officeManager.stop();
            }
            System.out.println("关闭office转换成功!");
    }

	public static void main(String[] args) {

	  	File f=new File("E:\\test.docx");
	  	File f1=new File("E:\\test.pdf");
		Ooo32pdf pdf=new Ooo32pdf();
		pdf.convert2PDF("E:\\test.docx","E:\\test2.pdf");
	}
}
