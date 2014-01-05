package galu.bench;

import com.google.caliper.config.InvalidConfigurationException;
import com.google.caliper.runner.CaliperMain;
import com.google.caliper.runner.InvalidBenchmarkException;
import com.google.caliper.util.InvalidCommandException;
import com.google.common.collect.ObjectArrays;
import galu.bench.math.VectorBenchmark;

import java.io.*;

public class BenchmarkRunner
{
	private static final Class<?>[] BENCHMARKS = new Class<?>[] {VectorBenchmark.class};

	private File baseDir;
	private String[] caliperArgs;

	public static void main(String[] args)
	{
		new BenchmarkRunner().run(args);
	}

	private void run(String[] args)
	{
		try
		{
			baseDir = args.length > 0 ? new File(args[0]) : new File("benchmarks");
			baseDir.mkdirs();

			caliperArgs = args.length > 1 ? args[1].split("\\s+") : new String[0];

			for(Class<?> benchmark : BENCHMARKS)
			{
				runBenchmark(benchmark);
			}
		}
		catch(IOException | InvalidConfigurationException | InvalidCommandException | InvalidBenchmarkException e)
		{
			System.err.println("Error running benchmark:");
			e.printStackTrace();
			System.exit(100);
		}
	}

	private void runBenchmark(Class<?> benchmarkClass) throws IOException, InvalidConfigurationException, InvalidCommandException, InvalidBenchmarkException
	{
		final File logFile = new File(baseDir, getBaseName(benchmarkClass) + ".out.txt");
		final File errFile = new File(baseDir, getBaseName(benchmarkClass) + ".err.txt");

		try(FileOutputStream out = new FileOutputStream(logFile); FileOutputStream err = new FileOutputStream(errFile))
		{
			String[] args = new String[] {
				"--verbose",
				"--trials", "10",
				"--time-limit", "60s",
				"--run-name", benchmarkClass.getSimpleName(),
				"--directory", baseDir.getAbsolutePath(),
			};
			args = ObjectArrays.concat(caliperArgs, args, String.class);
			args = ObjectArrays.concat(args, benchmarkClass.getName());

			CaliperMain.exitlessMain(args,
			    new PrintWriter(new MultiOutputStream(out, System.out)),
			    new PrintWriter(new MultiOutputStream(err, System.err)));
		}
	}

	private static String getBaseName(Class<?> benchmarkClass)
	{
		String javaVersion = System.getProperty("java.version");
		String osName = System.getProperty("os.name").replaceAll("\\s+", "_");
		String osArch = System.getProperty("os.arch");

		return String.format("%s-java%s-os%s-%s", benchmarkClass.getName(), javaVersion, osName, osArch);
	}
}
