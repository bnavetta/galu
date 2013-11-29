package galu.vector;

public final class Vectors
{
	private Vectors() {}
	
	public static Vector2 vec2(float x, float y)
	{
		return new Vector2(x, y);
	}
	
	public static Vector2 vec2(Vector2 vec)
	{
		return new Vector2(vec);
	}
	
	public static Vector3 vec3(float x, float y, float z)
	{
		return new Vector3(x, y, z);
	}
	
	public static Vector3 vec3(Vector3 vec)
	{
		return new Vector3(vec);
	}
	
	public static Vector3 vec3(Vector2 vec, float z)
	{
		return new Vector3(vec.x, vec.y, z);
	}
}
