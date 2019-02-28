package verolog.model;

import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.valuerange.CountableValueRange;
import org.optaplanner.core.api.domain.valuerange.ValueRangeFactory;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoftlong.HardSoftLongScore;

import java.util.ArrayList;
import java.util.Collection;

@PlanningSolution
public class Solution {
    int nFilas;
    int nCols;
    int minIngred;
    int maxSliceSize;
    private int maxNOfSlices;

    @PlanningEntityCollectionProperty
    Collection<Celdilla> celdillas = new ArrayList<>();

    HardSoftLongScore score;

    public Solution() {
    }

    public Solution(int nFilas, int nCols, int minIngred, int maxSliceSize, int maxNOfSlices) {
        this.nFilas = nFilas;
        this.nCols = nCols;
        this.minIngred = minIngred;
        this.maxSliceSize = maxSliceSize;
        this.maxNOfSlices = maxNOfSlices;
        for (int i = 0; i < nFilas; i++) {
            for (int j = 0; j < nCols; j++) {
                celdillas.add(new Celdilla(i,j));
            }
        }
    }

    public Collection<Celdilla> getCeldillas() {
        return celdillas;
    }

    public void setCeldillas(Collection<Celdilla> celdillas) {
        this.celdillas = celdillas;
    }

    private boolean[][] pizza;

    public boolean[][] getPizza() {
        return pizza;
    }

    public void setPizza(boolean[][] pizza) {
        this.pizza = pizza;
    }

    public int getnFilas() {
        return nFilas;
    }

    public int getnCols() {
        return nCols;
    }

    public int getMinIngred() {
        return minIngred;
    }

    public int getMaxSliceSize() {
        return maxSliceSize;
    }

    @ValueRangeProvider(id = "papasPosibles")
    public CountableValueRange<Integer> getPapasPosibles(){
        return ValueRangeFactory.createIntValueRange(0, maxNOfSlices, 1);
    }

    @PlanningScore
    public HardSoftLongScore getScore() {
        return score;
    }

    public void setScore(HardSoftLongScore score) {
        this.score = score;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(var celdilla: celdillas){
            if(celdilla.col == 0){
                sb.append('\n');
            }
            sb.append(celdilla.papa).append(' ');
        }
        sb.append('\n');
        return sb.toString();
    }
}
