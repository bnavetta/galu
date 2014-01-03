package galu.vector.tester

class Helpers
{
	private Helpers() {}
	
	public static int sizeOf(Class<Vector> type)
	{
		return type.name.substring(type.name.size() - 1) as int
	}
	
	public static boolean closeTo(float a, float b, float delta)
	{
		return Math.abs(a - b) < delta
	}
}
