package verolog.model;

import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.score.buildin.hardsoftlong.HardSoftLongScore;

import java.util.ArrayList;
import java.util.List;

@PlanningSolution
public class Solution {

    @PlanningEntityCollectionProperty
    List<Slide> slides;

    List<Anchor> anchors;

    HardSoftLongScore score;

    public Solution() {}

    public Solution(List<Slide> slides) {
        this.slides = slides;
        this.anchors = new ArrayList<>();
        anchors.add(new Anchor(true));
        anchors.add(new Anchor(false));
    }

    @PlanningScore
    public HardSoftLongScore getScore() {
        return score;
    }

    public void setScore(HardSoftLongScore score) {
        this.score = score;
    }




    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        return sb.toString();
    }
}
