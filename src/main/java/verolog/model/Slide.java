package verolog.model;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.lookup.PlanningId;
import org.optaplanner.core.api.domain.variable.AnchorShadowVariable;
import org.optaplanner.core.api.domain.variable.PlanningVariable;
import org.optaplanner.core.api.domain.variable.PlanningVariableGraphType;

import java.util.HashSet;

@PlanningEntity
public class Slide implements ChainItem{

    private String id;

    @AnchorShadowVariable(sourceVariableName = "prevSlide")
    private Anchor anchor;
    HashSet<String> tags;
    private ChainItem prevSlide;
    private Slide nextSlide;
    public Slide() {}

    public Slide(String id,HashSet<String> tags){
        this.id = id;
        this.tags = tags;
    }

    public HashSet<String> getTags() {
        return tags;
    }

    @PlanningId
    public String getId(){
        return id;
    }

    @PlanningVariable(
            valueRangeProviderRefs = {"anchors", "slides"},
            graphType = PlanningVariableGraphType.CHAINED
    )

    @Override
    public Anchor getAnchor() {
        return anchor;
    }

    public void setAnchor(Anchor anchor) {
        this.anchor = anchor;
    }

    @Override
    public Slide getNextSlide() {
        return nextSlide;
    }

    @Override
    public void setNextSlide(Slide s) {
        this.nextSlide = s;
    }



    public ChainItem getPrevSlide() {
        return prevSlide;
    }

    public void setPrevSlide(ChainItem prevSlide) {
        this.prevSlide = prevSlide;
    }
}
