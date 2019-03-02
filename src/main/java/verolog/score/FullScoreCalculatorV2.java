package verolog.score;

import org.optaplanner.core.api.score.Score;
import org.optaplanner.core.api.score.buildin.hardsoftlong.HardSoftLongScore;
import org.optaplanner.core.impl.score.director.easy.EasyScoreCalculator;
import verolog.model.Slide;
import verolog.model.Solution;

import java.util.HashSet;

public class FullScoreCalculatorV2 implements EasyScoreCalculator<Solution> {

    public Score calculateScore(Solution solution) {
        long hardscore = 0;
        long softscore = 0;

        Slide slide = solution.getAnchors().get(0).getNextSlide();
        while(slide != null && slide.getNextSlide()!=null){
            Slide next = slide.getNextSlide();
            int score = getScore(slide, next);
            softscore += score;
            slide = slide.getNextSlide();
        }
        return HardSoftLongScore.of(hardscore, softscore);
    }

    public static int getScore(Slide slide, Slide next) {
        int common, intersectionS1S2, intersectionS2S1; common = 0;
        HashSet<String> thisTags = slide.getTags();
        HashSet<String> nextTags = next.getTags();
         intersectionS1S2 = nextTags.size(); intersectionS2S1 = thisTags.size();
        for (var item: nextTags) {
            if (thisTags.contains(item)) {
                intersectionS1S2--;
            }
        }
        for (var item: thisTags) {
            if (nextTags.contains(item)) {
                common++;
                intersectionS2S1--;
            }
        }
        return Math.min(Math.min(intersectionS1S2, intersectionS2S1), common);
    }
}
