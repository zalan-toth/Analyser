package net.pyel.benchmark;

import org.openjdk.jmh.Main;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.RunnerException;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Measurement(iterations = 10)
@Warmup(iterations = 5)
@Fork(value = 1)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Thread)
public class MyBenchmark {
	public int[] data;

	@Setup(Level.Invocation)
	public void setup() {
		data = new int[1000];
		for (int i = 0; i < data.length; i++)
			data[i] = (int) (Math.random() * 10000);
	}

	@Benchmark
	public void testMethod() {
		System.out.println("Hello");
		// This is a demo/sample template for building your JMH benchmarks. Edit as needed.
		// Put your benchmark code here.
	}

	public static void main(String[] args) throws
			RunnerException, IOException {
		Main.main(args);
	}
}
