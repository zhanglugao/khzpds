package com.khzpds.util;

public class ChangeSuffixUtil {

    public static String change(String sourcePath,String suffix){
        return sourcePath.substring(0,sourcePath.lastIndexOf("."))+"."+suffix;
    }
}
