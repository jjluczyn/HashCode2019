package verolog;

import verolog.model.Anchor;
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

            //hori.sort(Comparator.comparingInt(p -> -p.tags.size()));
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

    public static Solution loadInitSol(File f){
        try(BufferedReader br = new BufferedReader(new FileReader(f));
            BufferedReader pairs = new BufferedReader(new InputStreamReader(new FileInputStream(f.getAbsolutePath()+".sol")))){

            int photoNum = Integer.parseInt(br.readLine());
            HashMap<String,Photo> cosas = new HashMap<>();
            for (int i = 0; i < photoNum; i++) {
                String[] parts = br.readLine().split(" ");
                HashSet<String> tags = new HashSet<String>();
                for (int j = 2; j < parts.length; j++) {
                    tags.add(parts[j]);
                }
                cosas.put(i+"",new Photo(false,tags,i+""));
            }


            Solution s = new Solution(new ArrayList<>());

            Anchor anchor = s.getAnchors().get(0);

            int inis = Integer.parseInt(pairs.readLine());
            String[] parts = pairs.readLine().split(" ");
            Slide prev = null;
            if (parts.length == 2){
                cosas.get(parts[0]).tags.addAll(cosas.get(parts[1]).tags);
                Slide aux = new Slide(parts[0]+" "+parts[1],cosas.get(parts[0]).tags);
                prev = aux;
            } else {
                Slide aux = new Slide(parts[0],cosas.get(parts[0]).tags);
                prev = aux;
            }
            s.getSlides().add(prev);
            anchor.setNextSlide(prev);
            prev.setAnchor(anchor);
            prev.setPrevSlide(anchor);
            for (int i = 1; i < inis; i++) {
                parts = pairs.readLine().split(" ");
                Slide slide = null;
                if (parts.length == 2){
                    cosas.get(parts[0]).tags.addAll(cosas.get(parts[1]).tags);
                    Slide aux = new Slide(parts[0]+" "+parts[1],cosas.get(parts[0]).tags);
                    slide = aux;
                } else {
                    Slide aux = new Slide(parts[0],cosas.get(parts[0]).tags);
                    slide = aux;
                }
                prev.setNextSlide(slide);
                slide.setAnchor(anchor);
                slide.setPrevSlide(prev);
                prev = slide;
                s.getSlides().add(prev);
            }
            return s;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
