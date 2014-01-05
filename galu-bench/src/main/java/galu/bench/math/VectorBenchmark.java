package galu.bench.math;

import com.google.caliper.Benchmark;
import com.google.caliper.Param;
import com.google.caliper.model.AllocationMeasurement;
import galu.vector.Vector;
import galu.vector.Vector2;
import galu.vector.Vector3;
import galu.vector.Vector4;

public class VectorBenchmark extends Benchmark
{
	@Param({"Vector2", "Vector3", "Vector4"})
	private VectorImpl implementation;

	@AllocationMeasurement
	public Vector timeAdd(int reps)
	{
		System.out.println("Starting with " + reps + " repetitions");
		Vector sum = implementation.create();
		for(int i = 0; i < reps; i++)
		{
//			System.out.println("Repetition " + i + ": " + sum);
			sum = sum.add(implementation.create());
		}
//		System.out.println("Finished running repetitions");
		return sum;
	}

	private enum VectorImpl
	{
		Vector2 {
			@Override Vector create() { return new Vector2(1, 2); }
		},
		Vector3 {
			@Override Vector create() { return new Vector3(1, 2, 3); }
		},
		Vector4 {
			@Override Vector create() { return new Vector4(1, 2, 3, 4); }
		};

		abstract Vector create();
	}
}
