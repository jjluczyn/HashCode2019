import java.io.*;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;

@SuppressWarnings("Duplicates")
public class InputParser {

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("b.txt")));
        int lines = Integer.parseInt(br.readLine());

        ArrayList<Photo> vert = new ArrayList<>();
        ArrayList<Photo> hor = new ArrayList<>();


        for (int i = 0; i < lines; i++) {
            String[] parts = br.readLine().split(" ");
            HashSet<String> tags = new HashSet<>();
            for (int j = 2; j < parts.length; j++) {
                tags.add(parts[j]);
            }
            if (parts[0].charAt(0) == 'V'){
                vert.add(new Photo(tags,i+""));
            } else {
                hor.add(new Photo(tags,i+""));
            }
        }

//        Collections.shuffle(hor);
//        Collections.shuffle(vert);

        PrintWriter pw = new PrintWriter(new File("b.out"));
        int slides = vert.size()/2 + hor.size();
        pw.println(slides);
        Random ran = new Random();
        int index = ran.nextInt(hor.size());
        Photo prev = hor.remove(index);
        pw.println(prev.name);

//        Photo prev = null;
//        {
//            Photo aux1 = vert.remove(ran.nextInt(vert.size()));
//            Photo aux2 = vert.remove(ran.nextInt(vert.size()));
//            aux1.tags.addAll(aux2.tags);
//            aux1.name = aux1.name + " " + aux2.name;
//            prev = aux1;
//        }
//        pw.println(prev.name);
        int totScore = 0;

        for (int i = 1; i < slides; i++) {
            int bestIndex1 = 0;
            int bestIndex2 = 0;
            boolean isPair = false;
            int bestVal = -1;
            for (int j = 0; j < hor.size(); j+=1) {
                Photo aux = hor.get(j);
                int score = getScore(aux.tags,prev.tags);
                if (score > bestVal){
                    bestIndex1 = j;
                    bestVal = score;
                }
            }
            for (int j = 0; j < vert.size(); j+=Math.max(1,(int)Math.sqrt(vert.size())/2)) {
            //for (int j = 0; j < vert.size(); j+=1){//Math.max(1,(int)Math.sqrt(vert.size())/2)) {
                for (int k = j+1; k < vert.size(); k+=Math.max(1,(int)Math.sqrt(vert.size())/2)) {
                //for (int k = j+1; k < vert.size(); k+=1){//Math.max(1,(int)Math.sqrt(vert.size())/2)) {
                    HashSet<String> set = new HashSet<>(vert.get(j).tags);
                    set.addAll(vert.get(k).tags);
                    int score = getScore(prev.tags,set);
                    if (score > bestVal){
                        isPair = true;
                        bestIndex1 = j;
                        bestIndex2 = k;
                        bestVal = score;
                    }
                }
            }
            totScore += bestVal;
            if (isPair){
                Photo aux1 = vert.remove(bestIndex2);
                Photo aux2 = vert.remove(bestIndex1);
                aux1.tags.addAll(aux2.tags);
                aux1.name = aux1.name+" "+aux2.name;
                prev = aux1;
            } else {
                prev = hor.remove(bestIndex1);
            }
            pw.println(prev.name);
            System.out.println(i+" / "+slides+" - "+totScore);
        }
        System.out.println();
        System.out.println(totScore);
        pw.flush();
        pw.close();
    }

    public static int getScore(HashSet<String> s1, HashSet<String> s2){
        int size1 = s1.size();
        int size2 = s2.size();
        int common = 0;
        for (String s : s1) {
            if (s2.contains(s)){
                size1--;
                size2--;
                common++;
            }
        }
        return Math.min(common,Math.min(size1,size2));
    }
}