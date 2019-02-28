package verolog.score;

import org.optaplanner.core.api.score.Score;
import org.optaplanner.core.impl.score.director.incremental.AbstractIncrementalScoreCalculator;
import verolog.model.Solution;

public class DeltaScoreCalculator extends AbstractIncrementalScoreCalculator<Solution> {

    @Override
    public void resetWorkingSolution(Solution solution) {

    }

    @Override
    public void beforeEntityAdded(Object o) {

    }

    @Override
    public void afterEntityAdded(Object o) {

    }

    @Override
    public void beforeVariableChanged(Object o, String s) {

    }

    @Override
    public void afterVariableChanged(Object o, String s) {

    }

    @Override
    public void beforeEntityRemoved(Object o) {

    }

    @Override
    public void afterEntityRemoved(Object o) {

    }

    @Override
    public Score calculateScore() {
        return null;
    }
}
