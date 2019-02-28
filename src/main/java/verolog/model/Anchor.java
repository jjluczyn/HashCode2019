package verolog.model;


import org.optaplanner.core.api.domain.lookup.PlanningId;

public class Anchor implements ChainItem{

    private Slide nextSlide;

    private boolean isActive;

    public Anchor(boolean isActive) {
        this.isActive = isActive;
    }

    public Anchor() {
    }

    public boolean isActive() {
        return isActive;
    }

    @Override
    public Anchor getAnchor() {
        return this;
    }

    @Override
    public Slide getNextSlide() {
        return nextSlide;
    }

    @Override
    public void setNextSlide(Slide s) {
        this.nextSlide = s;
    }

    @PlanningId
    public String getId(){
        return isActive ? "0" : "1";
    }
}
