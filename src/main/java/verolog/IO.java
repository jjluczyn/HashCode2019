package verolog;

import verolog.model.Slide;
import verolog.model.Solution;
import verolog.score.Photo;

import java.io.*;
import java.util.*;

@SuppressWarnings("Duplicates")
public class IO {

    public static Solution loadInstance(File f){
        try(BufferedReader br = new BufferedReader(new FileReader(f))){


            int photoNum = Integer.parseInt(br.readLine());
            LinkedList<Photo> hori = new LinkedList<>();
            LinkedList<Photo> newhori = new LinkedList<>();
            LinkedList<Photo> vert = new LinkedList<>();
            for (int i = 0; i < photoNum; i++) {
                String[] parts = br.readLine().split(" ");
                HashSet<String> tags = new HashSet<String>();
                for (int j = 2; j < parts.length; j++) {
                    tags.add(parts[j]);
                }
                if (parts[0].equals("H")){
                    hori.add(new Photo(false,tags,i+""));
                } else {
                    vert.add(new Photo(true,tags,i+""));
                }
            }
            vert.sort(Comparator.comparingInt(p -> p.tags.size()));

            while (vert.size()>=2){
                Photo p1 = vert.removeFirst();
                Photo p2 = vert.removeLast();
                p2.tags.addAll(p1.tags);
                p2.name = p2.name+" "+p1.name;
                p2.vertical = false;
                hori.add(p2);

            }
            /*while (!hori.isEmpty()){
                Photo p1 = hori.removeFirst();
                newhori.add(p1);
                if(!hori.isEmpty()) {
                    Photo p2 = hori.removeLast();
                    newhori.add(p2);
                }
            }
            hori.clear();
            hori.addAll(newhori);*/

            while (vert.size()>2){
                Photo p1 = vert.removeFirst();
                Photo p2 = vert.removeLast();
                p1.tags.addAll(p2.tags);
                p1.name = p1.name+" "+p2.name;
                p1.vertical = false;
                hori.add(p1);
            }
            //Random
            /*
            while(vert.size()>=2)
            {
                Random r = new Random();
                int val = r.nextInt(vert.size());
                Photo p1 = vert.get(val);
                vert.remove(val);
                val = r.nextInt(vert.size());
                Photo p2 = vert.get(val);
                vert.remove(val);
                p2.tags.addAll(p1.tags);
                p2.name = p2.name+" "+p1.name;
                p2.vertical = false;
                hori.add(p2);
            }
            */

            hori.sort(Comparator.comparingInt(p -> -p.tags.size()));
            List<Slide> slides = new ArrayList<>();
            hori.forEach(photo -> {
                slides.add(new Slide(photo.name,photo.tags));
            });

            Solution s = new Solution(slides);

            return s;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void save(Solution solution, File f){
        try (PrintWriter pw = new PrintWriter(f)){

            StringBuilder sb  = new StringBuilder();
            Slide aux = solution.getAnchors().get(0).getNextSlide();
            int slides = 0;
            while (aux != null){
                sb.append(aux.getId());
                sb.append('\n');
                slides++;
                aux = aux.getNextSlide();
            }
            pw.println(slides);
            pw.print(sb.toString());
            pw.flush();
            // TODO export Solution to file

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
