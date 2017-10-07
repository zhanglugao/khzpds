package com.khzpds.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class TestPdf2Swf {
    public static int convertPDF2SWF(String sourcePath, String destPath) throws IOException {
        // 目标路径不存在则建立目标路径
        /*File dest = new File(destPath);
        if (!dest.exists())
            dest.mkdirs();*/

        // 源文件不存在则返回
        File source = new File(sourcePath);
        if (!source.exists())
            return 0;

        // 调用pdf2swf命令进行转换
        String command = "C:\\Program Files (x86)\\SWFTools\\pdf2swf.exe" + " " + sourcePath+ " -o "
                + destPath + " -f -T 9";
        System.out.println(command);
        Process pro = Runtime.getRuntime().exec(command);

        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(pro.getInputStream()));
        while (bufferedReader.readLine() != null);
        try {
            pro.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return pro.exitValue();
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        String sourcePath = "E:\\test.pdf"; //源文件路径
        String destPath = "E:\\test.swf";       //生成文件名
        try {
            TestPdf2Swf.convertPDF2SWF(sourcePath, destPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

