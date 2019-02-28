package verolog.model;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.lookup.PlanningId;
import org.optaplanner.core.api.domain.variable.AnchorShadowVariable;
import org.optaplanner.core.api.domain.variable.PlanningVariable;
import org.optaplanner.core.api.domain.variable.PlanningVariableGraphType;

import java.util.Set;

@PlanningEntity
public class Slide implements ChainItem{

    private String id;

    Set<String> tags;

    public Slide() {}

    public Slide(String id, Set<String> tags){
        this.id = id;
        this.tags = tags;
    }

    @PlanningId
    public String getId(){
        return id;
    }

    @PlanningVariable(
            valueRangeProviderRefs = {"anchors", "slides"},
            graphType = PlanningVariableGraphType.CHAINED
    )
    private ChainItem prevSlide;

    private Slide nextSlide;

    @AnchorShadowVariable(sourceVariableName = "prevSlide")
    private Anchor anchor;

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

    public ChainItem getPreviousRequestDelivered() {
        return this.prevSlide;
    }

    public void setPreviousRequestDelivered(ChainItem prev) {
        this.prevSlide = prev;
    }

}
