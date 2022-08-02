package util;

/**
 * @author wangzhe
 * @date 2022/8/2 2:21 下午
 */
public class ScoreEntity implements Comparable<ScoreEntity>{
    private int label;
    private float score;

    public int getLabel() {
        return label;
    }

    public void setLabel(int label) {
        this.label = label;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    @Override
    public int compareTo(ScoreEntity scoreEntity) {
        if(this.score > scoreEntity.score) return 1;
        else if(this.score == scoreEntity.score) return 0;
        else return -1;
    }

    @Override
    public String toString() {
        return "ScoreEntity{" +
                "label=" + label +
                ", score=" + score +
                '}';
    }
}
