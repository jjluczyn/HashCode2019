package verolog;

import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.config.solver.SolverConfig;
import org.optaplanner.core.config.solver.termination.TerminationConfig;
import verolog.model.Solution;

import java.io.File;
import java.time.LocalDateTime;

public class Main {

    private static final File CONFIG_FILE = new File("config.xml");

    public static void main(String[] args) {
        if(args.length < 4 || args.length > 5){
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

        Solver<Solution> solver = buildSolver(maxSeconds, seed);
        System.out.println(String.format("[%s] Solving %s with seed", LocalDateTime.now().toString(), in.getAbsolutePath(), seed));
        Solution resuelto = solver.solve(sol);
        System.out.println(resuelto);
        System.out.println(String.format("[%s] Saving results for %s", LocalDateTime.now().toString(), in.getAbsolutePath()));

        // Debug final score calculation
        try(var scoreDirector = solver.getScoreDirectorFactory().buildScoreDirector()){
            scoreDirector.setWorkingSolution(resuelto);
            var finalScore = scoreDirector.calculateScore().toLevelNumbers();
            System.out.println(String.format("Broken constraints: %s -- Best value found: %s", finalScore[0], finalScore[1]));
        }

        IO.save(resuelto, new File(baseOut));
        System.out.println(String.format("[%s] Finished processing %s", LocalDateTime.now().toString(), in.getAbsolutePath()));
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
}
