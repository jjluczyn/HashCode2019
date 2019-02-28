package verolog;

import verolog.model.Solution;

import java.io.*;
import java.util.HashMap;

@SuppressWarnings("Duplicates")
public class IO {

    public static Solution loadInstance(File f){
        try(BufferedReader br = new BufferedReader(new FileReader(f))){
            var trozos = br.readLine().split(" ");
            assert trozos.length == 4;


            int nFilas = Integer.parseInt(trozos[0]);
            int nCols = Integer.parseInt(trozos[1]);
            int champs = 0, tomatoes = 0;
            var pizza = new boolean[nFilas][nCols];
            for (int i = 0; i < nFilas; i++) {
                var chars = br.readLine().toCharArray();
                for (int j = 0; j < nCols; j++) {
                    pizza[i][j] = chars[j] == 'T';
                    if (pizza[i][j]) tomatoes++;
                    else champs++;
                }
            }


            Solution s = new Solution(nFilas, nCols,Integer.parseInt(trozos[2]),Integer.parseInt(trozos[3]), Math.min(tomatoes, champs));

            s.setPizza(pizza);
            return s;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void save(Solution solution, File f){
        try (PrintWriter pw = new PrintWriter(f)){
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
            StringBuilder sb = new StringBuilder();
            int validos = 0;
            for (Memo pedazo : pares.values()) {
                int area = pedazo.area();
                int acc = 0;
                acc += area-pedazo.celdillas;
                acc += Math.max(0, solution.getMinIngred()-pedazo.tomatos);
                acc += Math.max(0, solution.getMinIngred()-pedazo.champs);
                acc += Math.max(0, pedazo.celdillas - solution.getMaxSliceSize());
                if (acc == 0){
                    validos++;
                    sb.append(pedazo.fMin+" "+pedazo.cMin+" "+pedazo.fMax+" "+pedazo.cMax+"\n");
                }
            }
            pw.println(validos);
            pw.print(sb);
            pw.flush();
            pw.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
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
