package verolog.model;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.InverseRelationShadowVariable;

@PlanningEntity
public interface ChainItem {

    Anchor getAnchor();

    /**
     * @return sometimes null
     */
    @InverseRelationShadowVariable(sourceVariableName = "prevSlide")
    Slide getNextSlide();
    void setNextSlide(Slide s);
}
