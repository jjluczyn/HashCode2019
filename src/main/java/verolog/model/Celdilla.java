package verolog.model;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.lookup.PlanningId;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

@PlanningEntity
public class Celdilla {
    int col;
    int fila;

    public Celdilla() {
    }

    public Celdilla(int fila, int col) {
        this.col = col;
        this.fila = fila;
    }

    public Integer getPapa() {
        return papa;
    }

    public void setPapa(Integer papa) {
        this.papa = papa;
    }

    public int getCol() {
        return col;
    }

    public int getFila() {
        return fila;
    }

    @PlanningVariable(valueRangeProviderRefs = "papasPosibles")
    Integer papa;

    @PlanningId
    public String getId(){
        return fila + "_" + col;
    }

}
