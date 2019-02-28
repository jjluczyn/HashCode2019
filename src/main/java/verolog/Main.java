package verolog;

import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.config.solver.SolverConfig;
import org.optaplanner.core.config.solver.termination.TerminationConfig;
import verolog.model.Anchor;
import verolog.model.Slide;
import verolog.model.Solution;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;

public class Main {

    private static final File CONFIG_FILE = new File("config.xml");

    public static void main(String[] args) {
        if(args.length != 4){
            System.out.println("Usage: java -jar file.jar instance.txt solution.txt timelimit seed");
            System.out.println("timelimit in seconds, seed must be a positive signed 64 bits number");
            System.exit(-1);
        }

        File in = new File(args[0]);
        String baseOut = args[1];
        long maxSeconds = Long.parseLong(args[2]);
        long seed = Long.parseLong(args[3]);

        System.out.println(String.format("[%s] Loading file %s", LocalDateTime.now().toString(), in.getAbsolutePath()));

        Solution sol = IO.loadInstance(in);
        //initRandomSol(sol);
        Solution resuelto = solve(sol, maxSeconds, seed, in.getAbsolutePath());

        IO.save(resuelto, new File(baseOut));
        System.out.println(String.format("[%s] Finished processing %s", LocalDateTime.now().toString(), in.getAbsolutePath()));
    }

    // TODO IF YOU DO NOT WANT TO USE OPTAPLANNER OR WANT TO MODIFY THE SOLVING ORDER MODIFY THIS METHOD
    public static Solution solve(Solution sol, long maxSeconds, long seed, String solutionName){
        Solver<Solution> solver = buildSolver(maxSeconds, seed);
        System.out.println(String.format("[%s] Solving %s with seed %s", LocalDateTime.now().toString(), solutionName, seed));
        Solution resuelto = solver.solve(sol);
        System.out.println(resuelto);
        System.out.println(String.format("[%s] Saving results for %s", LocalDateTime.now().toString(), solutionName));

        // Debug final score calculation
        try(var scoreDirector = solver.getScoreDirectorFactory().buildScoreDirector()){
            scoreDirector.setWorkingSolution(resuelto);
            var finalScore = scoreDirector.calculateScore().toLevelNumbers();
            System.out.println(String.format("Broken constraints: %s -- Best value found: %s", finalScore[0], finalScore[1]));
        }

        return sol;
    }

    private static Solver<Solution> buildSolver(long maxSeconds, long seed){
        SolverFactory<Solution> factory = buildFactory(maxSeconds, seed);
        return factory.buildSolver();
    }

    private static SolverFactory<Solution> buildFactory(long maxSeconds, long seed) {
        SolverFactory<Solution> factory = SolverFactory.createFromXmlFile(CONFIG_FILE);
        SolverConfig config = factory.getSolverConfig();
        config.setRandomSeed(seed);
        TerminationConfig tconfig = new TerminationConfig();
        tconfig.setSecondsSpentLimit(maxSeconds);
        config.setTerminationConfig(tconfig);
        return factory;
    }

    public static void initRandomSol(Solution s){
        ArrayList<Slide> slides = new ArrayList<>(s.getSlides());
        Collections.shuffle(slides);

        Anchor anch = s.getAnchors().get(0);
        anch.setNextSlide(slides.get(0));

        slides.get(0).setAnchor(anch);
        slides.get(0).setPrevSlide(anch);

        for (int i = 1; i < slides.size(); i++) {
            slides.get(i-1).setNextSlide(slides.get(i));
            slides.get(i).setAnchor(anch);
            slides.get(i).setPrevSlide(slides.get(i-1));
        }
    }

    public static void initGreedySol(Solution s){
        ArrayList<Slide> slides = new ArrayList<>(s.getSlides());
        Collections.shuffle(slides);

        Anchor anch = s.getAnchors().get(0);
        anch.setNextSlide(slides.get(slides.size()-1));

        slides.get(slides.size()-1).setAnchor(anch);
        slides.get(slides.size()-1).setPrevSlide(anch);
        slides.remove(slides.size()-1);

        while(slides.size()>2){
            int best = 0;
            int bestPos = 0;
            for (int i = 0; i < slides.size(); i++) {
                //FullScoreCalculator.getScore()
            }
        }
    }
}
