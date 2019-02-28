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
            ArrayList<Photo> hori = new ArrayList<>();
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
            Collections.sort(vert,(p1, p2) -> {
                return Integer.compare(p1.tags.size(),p2.tags.size());
            });
            while (vert.size()>2){
                Photo p1 = vert.removeLast();
                Photo p2 = vert.removeFirst();
                p2.tags.addAll(p1.tags);
                p2.name = p2.name+" "+p1.name;
                p2.vertical = false;
                hori.add(p2);
            }

            List<Slide> slides = new ArrayList<>();
            hori.forEach(photo -> {
                slides.add(new Slide(photo.name,photo.tags));
            });

            Solution s = new Solution(slides);

            // TODO parse input




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
