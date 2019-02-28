package verolog.score;

import org.optaplanner.core.api.score.Score;
import org.optaplanner.core.api.score.buildin.hardsoftlong.HardSoftLongScore;
import org.optaplanner.core.impl.score.director.easy.EasyScoreCalculator;
import verolog.model.Solution;

import java.util.HashMap;
@SuppressWarnings("Duplicates")
public class FullScoreCalculator implements EasyScoreCalculator<Solution> {

    public Score calculateScore(Solution solution) {
        long hardscore = 0;
        long softscore = 0;

        HashMap<Integer,Memo> pares = new HashMap<>();
        boolean[][] tomate = solution.getPizza();
        for(var celda: solution.getCeldillas()) {
            Integer trozo = celda.getPapa();
            if (trozo == null) continue;
            int fil = celda.getFila();
            int col = celda.getCol();
            pares.putIfAbsent(trozo, new Memo(fil, fil, col, col));
            pares.get(trozo).celdillas++;
            if (tomate[fil][col]) pares.get(trozo).tomatos++;
            else pares.get(trozo).champs++;
            pares.get(trozo).minMax(fil, col);
        }

        for (Memo pedazo : pares.values()) {
            int area = pedazo.area();

            int acc = 0;
            acc += area-pedazo.celdillas;
            acc += Math.max(0, solution.getMinIngred()-pedazo.tomatos);
            acc += Math.max(0, solution.getMinIngred()-pedazo.champs);

            hardscore += Math.max(0, pedazo.celdillas - solution.getMaxSliceSize());

            if(acc == 0) softscore += pedazo.celdillas;
        }


        return HardSoftLongScore.of(-hardscore, softscore);
    }

    static class Memo {
        public int fMin,fMax,cMin,cMax;
        public int celdillas, tomatos, champs;

        public Memo(int fMin, int fMax, int cMin, int cMax) {
            this.fMin = fMin;
            this.fMax = fMax;
            this.cMin = cMin;
            this.cMax = cMax;
            this.celdillas = 0;
            this.tomatos = 0;
            this.champs = 0;
        }

        public int area(){
            return (fMax-fMin+1)*(cMax-cMin+1);
        }

        public void minMax(int newFil, int newCol){
            this.fMin = Math.min(this.fMin,newFil);
            this.fMax = Math.max(this.fMax,newFil);
            this.cMin = Math.min(this.cMin,newCol);
            this.cMax = Math.max(this.cMax,newCol);
        }
    }
}
