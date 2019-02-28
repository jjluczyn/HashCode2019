package verolog.score;

import org.optaplanner.core.api.score.Score;
import org.optaplanner.core.api.score.buildin.hardsoftlong.HardSoftLongScore;
import org.optaplanner.core.impl.score.director.incremental.AbstractIncrementalScoreCalculator;
import verolog.model.Slide;
import verolog.model.Solution;

public class DeltaScoreCalculator extends AbstractIncrementalScoreCalculator<Solution> {

    long softscore;


    @Override
    public void resetWorkingSolution(Solution solution) {
        this.softscore = 0;
    }

    @Override
    public void beforeEntityAdded(Object o) {
        // nothing
    }

    @Override
    public void afterEntityAdded(Object o) {
        if(!(o instanceof Slide)){
            return;
        }
        insert((Slide) o);
    }

    @Override
    public void beforeVariableChanged(Object o, String s) {

    }

    @Override
    public void afterVariableChanged(Object o, String s) {

    }

    @Override
    public void beforeEntityRemoved(Object o) {
        if(!(o instanceof Slide)){
            return;
        }
        delete((Slide) o);
    }

    @Override
    public void afterEntityRemoved(Object o) {
        // nothing
    }

    @Override
    public Score calculateScore() {
        return HardSoftLongScore.of(0, softscore);
    }

    private void insert(Slide s){
//        if(s.getPreviousRequestDelivered() instanceof Anchor) return;
//        softscore += FullScoreCalculator.getScore(s, (Slide) s.getPreviousRequestDelivered());
    }

    private void delete(Slide s){

    }


}
