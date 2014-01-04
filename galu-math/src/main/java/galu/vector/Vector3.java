package galu.vector;

import java.nio.FloatBuffer;

import com.google.common.hash.Hashing;

import static com.google.common.base.Preconditions.checkArgument;

public final class Vector3 implements Vector<Vector3>
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
		checkArgument(buf.remaining() >= 3, "Buffer has fewer than 3 elements remaining (%s)", buf);
		buf.put(x).put(y).put(z);
	}

	public void store(float[] array)
	{
		checkArgument(array.length >= 3, "Array has fewer than 3 elements (%d)", array.length);
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
		checkArgument(buffer.remaining() >= 3, "Buffer has fewer than 3 elements remaining (%s)", buffer);
		return new Vector3(buffer.get(), buffer.get(), buffer.get());
	}
	
	public static Vector3 load(float[] array)
	{
		checkArgument(array.length >= 3, "Array has fewer than 3 elements (%d)", array.length);
		return new Vector3(array[0], array[1], array[2]);
	}
	
	@Override
	public String toString()
	{
		return String.format("(%.4f, %.4f, %.4f)", x, y, z);
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
