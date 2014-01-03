package galu.vector.tester;

import com.google.common.collect.*

public abstract class TestData
{
	private Multimap<String, Trial> data = LinkedListMultimap.create()
	
	public TestData()
	{
		generateData();
	}
	
	protected abstract void generateData()
	
	protected void trial(String operation, def inputs, def outputs)
	{
		data.put(operation, new Trial(inputs: inputs, outputs: outputs))
	}
	
	protected void trial(String operation, def inputs)
	{
		data.put(operation, new Trial(inputs: inputs))
	}
	
	public Collection<Trial> getData(String operation)
	{
		return data.get(operation)
	}
	
	@Override
	public String toString()
	{
		return data.toString()
	}
	
	public static class Trial
	{
		def inputs
		def outputs
	}
}
