package verolog.score;

import org.optaplanner.core.api.score.Score;
import org.optaplanner.core.api.score.buildin.hardsoftlong.HardSoftLongScore;
import org.optaplanner.core.impl.score.director.incremental.AbstractIncrementalScoreCalculator;
import verolog.model.Slide;
import verolog.model.Solution;

public class DeltaScoreCalculator extends AbstractIncrementalScoreCalculator<Solution> {

    private long softscore;

    @Override
    public void resetWorkingSolution(Solution solution) {
        this.softscore = 0;
        Slide s = solution.getAnchors().get(0).getNextSlide();
        while(s != null ){
            insertPrevSlide(s);
            s = s.getNextSlide();
        }
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

        insertPrevSlide((Slide) o);
    }

    @Override
    public void beforeVariableChanged(Object o, String s) {
        if(!(o instanceof Slide)) return;
        switch (s){
            case "prevSlide":
                deletePrevSlide((Slide) o);
                break;
            case "nextSlide":
                //deleteNextSlide((Slide) o);
                break;
        }
    }

    @Override
    public void afterVariableChanged(Object o, String s) {
        if(!(o instanceof Slide)) return;
        switch (s){
            case "prevSlide":
                insertPrevSlide((Slide) o);
                break;
            case "nextSlide":
                //insertNextSlide((Slide) o);
                break;
        }
    }


    @Override
    public void beforeEntityRemoved(Object o) {
        if(!(o instanceof Slide)){
            return;
        }
        deletePrevSlide((Slide) o);
    }

    @Override
    public void afterEntityRemoved(Object o) {
        // nothing
    }

    @Override
    public Score calculateScore() {
        return HardSoftLongScore.of(0, softscore);
    }

    private void insertNextSlide(Slide o) {
        if(o.getNextSlide() != null){
            softscore += FullScoreCalculator.getScore(o, o.getNextSlide());
        }
    }

    private void insertPrevSlide(Slide o) {
        if(o.getPrevSlide() != null && o.getPrevSlide() instanceof Slide){
            softscore += FullScoreCalculator.getScore((Slide) o.getPrevSlide(), o);
        }
    }

    private void deleteNextSlide(Slide o) {
        if(o.getNextSlide() != null){
            softscore -= FullScoreCalculator.getScore(o, o.getNextSlide());
        }
    }

    private void deletePrevSlide(Slide o) {
        if(o.getPrevSlide() != null && o.getPrevSlide() instanceof Slide){
            softscore -= FullScoreCalculator.getScore((Slide) o.getPrevSlide(), o);
        }
    }


}
