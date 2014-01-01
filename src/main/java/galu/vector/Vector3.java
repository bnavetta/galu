package galu.vector;

import java.nio.FloatBuffer;

import com.google.common.hash.Hashing;

public class Vector3 implements Vector<Vector3>
{
	public final float x;
	public final float y;
	public final float z;
	
	public Vector3(final float x, final float y, final float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public int size()
	{
		return 3;
	}
	
	public float get(int idx)
	{
		switch(idx)
		{
			case 0:
				return x;
			case 1:
				return y;
			case 2:
				return z;
			default:
				throw new IndexOutOfBoundsException("Index " + idx + " exceeds bounds of Vector3");
		}
	}

	public float length()
	{
		return (float) Math.sqrt(x*x + y*y + z*z);
	}
	
	public float lengthSquared()
	{
		return x*x + y*y + z*z;
	}

	public Vector3 normalize()
	{
		float magnitude = length();
		return new Vector3(x / magnitude, y / magnitude, z / magnitude);
	}

	public Vector3 add(Vector3 other)
	{
		return new Vector3(x + other.x, y + other.y, z + other.z);
	}
	
	public Vector3 subtract(Vector3 other)
	{
		return new Vector3(x - other.x, y - other.y, z - other.z);
	}

	public float dot(Vector3 other)
	{
		return x * other.x + y * other.y + z * other.z;
	}

	public float angleBetween(Vector3 other)
	{
		return (float) Math.acos(dot(other) / length() / other.length());
	}

	public Vector3 negate()
	{
		return new Vector3(-x, -y, -z);
	}

	public Vector3 multiply(float factor)
	{
		return new Vector3(x * factor, y * factor, z * factor);
	}

	public Vector3 multiply(Vector3 other)
	{
		return new Vector3(x * other.x, y * other.y, z * other.z);
	}

	public void store(FloatBuffer buf)
	{
		buf.put(x).put(y).put(z);
	}

	public void store(float[] array)
	{
		array[0] = x;
		array[1] = y;
		array[2] = z;
	}

	public float[] toArray()
	{
		return new float[] {x, y, z};
	}

	public static Vector3 load(FloatBuffer buffer)
	{
		return new Vector3(buffer.get(), buffer.get(), buffer.get());
	}
	
	public static Vector3 load(float[] buffer)
	{
		return new Vector3(buffer[0], buffer[1], buffer[2]);
	}
	
	@Override
	public String toString()
	{
		return String.format("[%f, %f, %f]", x, y, z);
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(obj == null) return false;
		if(obj == this) return true;
		if(!(obj instanceof Vector3)) return false;
		Vector3 other = (Vector3) obj;
		return Float.floatToIntBits(x) == Float.floatToIntBits(other.x) &&
				Float.floatToIntBits(y) == Float.floatToIntBits(other.y) &&
				Float.floatToIntBits(z) == Float.floatToIntBits(other.z);
	}
	
	@Override
	public int hashCode()
	{
		int result = 23;
		result = 37 * result + Float.floatToIntBits(x);
		result = 37 * result + Float.floatToIntBits(y);
		result = 37 * result + Float.floatToIntBits(z);
		return result;
	}
}
