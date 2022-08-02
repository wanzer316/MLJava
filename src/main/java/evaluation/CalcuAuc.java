package evaluation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.ScoreEntity;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author wangzhe
 * @date 2022/8/2 11:15 上午
 */
public class CalcuAuc {

    private static final Logger logger = LoggerFactory.getLogger(CalcuAuc.class);

    public static void main(String[] args) {
        String predPath = "./data/pred";
        float auc = calcuAuc(predPath);
        logger.info("auc:{}",auc);
    }

    public static float calcuAuc(String predFile){
        List<ScoreEntity> predData = new ArrayList<ScoreEntity>();
        try {
            FileReader reader = new FileReader(predFile);
            BufferedReader br = new BufferedReader(reader);
            String line = "";
            while((line=br.readLine()) != null){
                String[] temps = line.trim().split(" ");
                if(temps.length != 2){
                    continue;
                }
                ScoreEntity scoreEntity = new ScoreEntity();
                scoreEntity.setLabel(Integer.parseInt(temps[0]));
                scoreEntity.setScore(Float.parseFloat(temps[1]));
                predData.add(scoreEntity);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Collections.sort(predData);

        int posNum = 0;
        int negNum = 0;
        float posSum = 0;
        int i = 0;
        while(i < predData.size()){
            int j = i+1;
            int pos = 0;
            while(j< predData.size() && predData.get(j).getScore() == predData.get(i).getScore()){
                if(predData.get(j).getLabel() == 1){
                    pos += 1;
                    posNum += 1;
                }else{
                    negNum += 1;
                }
                j += 1;
            }
            if(predData.get(i).getLabel() == 1){
                pos += 1;
                posNum += 1;
            }else{
                negNum += 1;
            }
            //相等值的平均值
            float avgpos = ((j-(i+1)+1)*(j+(i+1))/2)*1.0f/(j-(i+1)+1);
            posSum += pos*avgpos;
            i = j;
        }

        float totalN = negNum*posNum;
        float auc = -1f;
        if(totalN != 0){
            auc = (posSum-(posNum*(posNum+1))/2)/totalN;
        }
        return auc;
    }
}
