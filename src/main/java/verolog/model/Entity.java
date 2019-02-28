package verolog.model;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.lookup.PlanningId;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

@PlanningEntity
public class Entity {

    // TODO generate problem entities

    public Entity() { }


    @PlanningId
    public String getId(){
        return "Mi Id me identifica de forma unica";
    }

}
