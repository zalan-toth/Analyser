package net.pyel.benchmark;

import net.pyel.models.Labeler;
import org.openjdk.jmh.Main;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.RunnerException;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Measurement(iterations = 10)
@Warmup(iterations = 5)
@Fork(value = 1)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Thread)
public class MyBenchmark {
	public int[] data;
	public int[] data2;
	Random rnd = new Random();

	@Setup(Level.Invocation)
	public void setup() {
		data = new int[3600];
		data2 = new int[3600];
		for (int v = 0; v < data.length; v++) {
			Random random = new Random();
			data[v] = random.nextBoolean() ? -1 : -2;
			data2[v] = random.nextBoolean() ? -1 : -2;
			Labeler.processData(data);
			rnd.nextInt(3600);
		}
	}

	@Benchmark
	public void testFind() {
		Labeler.staticFind(data, rnd.nextInt(3600));
		// This is a demo/sample template for building your JMH benchmarks. Edit as needed.
		// Put your benchmark code here.
	}

	@Benchmark
	public void testUnion() {
		Labeler.staticUnion(data, rnd.nextInt(3600), rnd.nextInt(3600));
		// This is a demo/sample template for building your JMH benchmarks. Edit as needed.
		// Put your benchmark code here.
	}

	@Benchmark
	public void testProcess() {
		Labeler.processData(data2);
	}

	@Benchmark
	public void testCalcForPixelAmount() {
		Labeler.calcPixelAmount(data);
	}

	@Benchmark
	public void testForGettingNewID() {
		Labeler.newID(data);
	}

	public static void main(String[] args) throws
			RunnerException, IOException {
		Main.main(args);
	}
}
