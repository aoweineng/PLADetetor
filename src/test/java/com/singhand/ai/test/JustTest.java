package com.singhand.ai.test;

import com.singhand.ai.image.PLA_image.core.PLA_Detect;
import com.singhand.gjj.ocr.bean.Image;

public class JustTest {
    public static void main(String[] args) throws Exception{

        String baseModel = "/media/fengyuejin/water/singhand_ai_model/PLA_model/";
//        String baseModel = "/home/singhand/PLA/workspace/Model/PLA_model/";
        PLA_Detect detect_qz = new PLA_Detect(baseModel, 416);

        boolean score = detect_qz.detect(Image.build("/home/fengyuejin/下载/a.jpg"));

        System.out.println(score);

//       String imgPath = "/home/singhand/PLA/Hbase_data/test_data";
//       String savePath = "/home/singhand/PLA/Hbase_data/java_res";
//
//       File imgBase = new File(imgPath);
//       File[] imgs = imgBase.listFiles();
//
//       for(File imgFile : imgs){
//
//           String imgName = imgFile.getName();
//           System.out.println("当前处理文件名为：" + imgName);
//
//            Image img = Image.build(imgFile.getAbsolutePath());
//
//            boolean score = detect_qz.detect(img);
//            System.out.println(score);
//           }
//       }
    }
}




