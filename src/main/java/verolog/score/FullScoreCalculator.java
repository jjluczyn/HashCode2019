package verolog.score;

import org.optaplanner.core.api.score.Score;
import org.optaplanner.core.api.score.buildin.hardsoftlong.HardSoftLongScore;
import org.optaplanner.core.impl.score.director.easy.EasyScoreCalculator;
import verolog.model.Slide;
import verolog.model.Solution;

import java.util.HashSet;

public class FullScoreCalculator implements EasyScoreCalculator<Solution> {

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
        int common = 0;
        HashSet<String> thisTags = slide.getTags();
        int soloThis = thisTags.size();
        HashSet<String> nextTags = next.getTags();
        int soloNext = nextTags.size();
        for (var item: thisTags) {
            if (nextTags.contains(item)) {
                common++;
                soloNext--;
                soloThis--;
            }
        }
        return Math.min(Math.min(soloNext, soloThis), common);
    }
}
