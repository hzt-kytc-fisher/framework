/*
 * Copyright 2020 tuhu.cn All right reserved. This software is the
 * confidential and proprietary information of tuhu.cn ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Tuhu.cn
 */
package com.kytc.framework.common.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * @Author: 何志同
 * @Date: 2020/9/22 17:35
 * @Description:
 **/
public class TxtUtils {
    private static final TxtUtils INSTANCE = new TxtUtils();
    public static TxtUtils getInstance(){
        return INSTANCE;
    }
    public void write(String fileName, List<String> list){
        try {
            String path = fileName.substring(0,fileName.lastIndexOf(File.separator));
            File writename = new File(path);// 相对路径，如果没有则要建立一个新的output。txt文件
            if(!writename.exists()){
                writename.mkdirs();
            }
            writename = new File(fileName);// 相对路径，如果没有则要建立一个新的output。txt文件
            writename.createNewFile(); // 创建新文件
            BufferedWriter out = new BufferedWriter(new FileWriter(writename));
            int i=0;
            for( String line : list ){
                out.write(line+"\n"); // \r\n即为换行
            }
            out.flush(); // 把缓存区内容压入文件
            out.close(); // 最后记得关闭文件
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String fileName = "D:\\111\\222\\333.txt";
        System.out.println(File.separator);
        String path = fileName.substring(0,fileName.lastIndexOf(File.separator));
        System.out.println(path);
    }
}