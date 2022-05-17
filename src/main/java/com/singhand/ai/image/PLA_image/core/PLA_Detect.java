package com.singhand.ai.image.PLA_image.core;


import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import com.singhand.gjj.ocr.bean.Image;
import com.singhand.gjj.ocr.bean.ImageText;
import com.singhand.gjj.ocr.bean.TextInfo;
import com.singhand.gjj.ocr.train_ticket.TicketDetectByYolo4;
import org.apache.commons.io.FileUtils;



public class PLA_Detect{

    private TicketDetectByYolo4 yolo4 = null;
    private List<String> labels = null;
    private float minScore = 0.5f;


    public PLA_Detect(String baseModelPath, int maxHW) throws Exception{
        String pbPath = baseModelPath + File.separator + "sensitive_flag.pb";
        String txtPath = baseModelPath + File.separator + "classes_qz.txt";
        this.labels = FileUtils.readLines(new File(txtPath), Charset.forName("UTF-8")).parallelStream().filter(ele -> !ele.trim().isEmpty()).collect(Collectors.toList());
        yolo4 = new TicketDetectByYolo4(pbPath, labels, maxHW);
        yolo4.setInputIOUName("non_max_suppression/iou_threshold:0");
        yolo4.setInputScoreName("non_max_suppression/score_threshold:0");
        yolo4.setInputLearningName("keras_learning_phase:0");
        yolo4.setScore(minScore);
    }


    private static byte[] readAllBytesOrExit(String path) throws Exception {
        try {
            return Files.readAllBytes(Paths.get(path));
        } catch (IOException var2) {
            throw new Exception("模型文件(" + path + ")读取失败：" + var2.getMessage(), var2);
        }
    }

    public boolean detect(Image image) throws Exception{
        ImageText detectRes = yolo4.detect(image);

        List<TextInfo> textInfos = detectRes.getTextInfos();
        textInfos = textInfos.parallelStream().filter(ele -> ele.getScore() > minScore).collect(Collectors.toList());

        if(textInfos == null || textInfos.isEmpty()){
            return false;
        }else{
            float maxScore = 0;
            for(TextInfo ele : textInfos){
                if(ele.getScore() > maxScore){
                    maxScore = ele.getScore();
                }
            }
            if(maxScore > 0.93f){
                return true;
            }
            return false;
        }
    }
}
