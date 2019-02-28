package verolog.score;

import org.optaplanner.core.api.score.Score;
import org.optaplanner.core.api.score.buildin.hardsoftlong.HardSoftLongScore;
import org.optaplanner.core.impl.score.director.easy.EasyScoreCalculator;
import verolog.model.Solution;

import java.util.HashMap;
public class FullScoreCalculator implements EasyScoreCalculator<Solution> {

    public Score calculateScore(Solution solution) {
        long hardscore = 0;
        long softscore = 0;

        // TODO calculate solution score


        return HardSoftLongScore.of(hardscore, softscore);
    }
}
