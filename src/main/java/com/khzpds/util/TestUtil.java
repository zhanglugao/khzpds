package com.khzpds.util;

import com.artofsolving.jodconverter.BasicDocumentFormatRegistry;
import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.DocumentFormat;
import com.artofsolving.jodconverter.DocumentFormatRegistry;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;

import java.io.File;
import java.net.ConnectException;

public class TestUtil {
    public static void main(String[] args) {
        OpenOfficeConnection connection =null;
        try {
            File sourceFile=new File("E:\\test.docx");
            File targetFile=new File("E:\\test2.pdf");

            // 1: 打开连接
            connection=new SocketOpenOfficeConnection(8100);
            connection.connect();

            DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
            // 2:获取Format
            DocumentFormatRegistry factory = new BasicDocumentFormatRegistry();
            DocumentFormat inputDocumentFormat = factory
                    .getFormatByFileExtension("docx");
            DocumentFormat outputDocumentFormat = factory
                    .getFormatByFileExtension("docx");
            // 3:执行转换
            converter.convert(sourceFile, inputDocumentFormat, targetFile, outputDocumentFormat);
        } catch (ConnectException e) {
           e.printStackTrace();
        }finally {
            if(connection!=null){
                connection.disconnect();
            }
        }
    }
}
