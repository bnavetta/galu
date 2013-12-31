package galu.vector;

import java.nio.FloatBuffer;

import com.google.common.hash.Hashing;

public final class Vector4 implements Vector<Vector4>
{
	public final float x;
	public final float y;
	public final float z;
	public final float w;
	
	public Vector4(float x, float y, float z, float w)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public Vector4(Vector4 other)
	{
		this(other.x, other.y, other.z, other.w);
	}
	
	public int size()
	{
		return 4;
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
			case 3:
				return w;
			default:
				throw new IndexOutOfBoundsException("Index " + idx + " exceeds bounds of Vector4");
		}
	}

	public float length()
	{
		return (float) Math.sqrt(x*x + y*y + z*z + w*w);
	}

	public float lengthSquared()
	{
		return x*x + y*y + z*z + w*w;
	}

	public Vector4 normalize()
	{
		float magnitude = length();
		return new Vector4(x / magnitude, y / magnitude, z / magnitude, w / magnitude);
	}

	public Vector4 add(Vector4 other)
	{
		return new Vector4(x + other.x, y + other.y, z + other.z, w + other.w);
	}

	public Vector4 subtract(Vector4 other)
	{
		return new Vector4(x - other.x, y - other.y, z - other.z, w - other.w);
	}

	public float dot(Vector4 other)
	{
		return x * other.x + y * other.y + z * other.z + w * other.w;
	}

	public float angleBetween(Vector4 other)
	{
		return (float) Math.acos(dot(other) / length() / other.length());
	}

	public Vector4 negate()
	{
		return new Vector4(-x, -y, -z, -w);
	}

	public Vector4 multiply(float factor)
	{
		return new Vector4(x * factor, y * factor, z * factor, w * factor);
	}

	public Vector4 multiply(Vector4 other)
	{
		return new Vector4(x * other.x, y * other.y, z * other.z, w * other.w);
	}

	public void store(FloatBuffer buf)
	{
		buf.put(x).put(y).put(z).put(w);
	}

	public void store(float[] array)
	{
		array[0] = x;
		array[1] = y;
		array[2] = z;
		array[3] = w;
	}

	public float[] toArray()
	{
		return new float[] {x, y, z, w};
	}
	
	public static Vector4 load(FloatBuffer buffer)
	{
		return new Vector4(buffer.get(), buffer.get(), buffer.get(), buffer.get());
	}
	
	public static Vector4 load(float[] array)
	{
		return new Vector4(array[0], array[1], array[2], array[3]);
	}
	
	@Override
	public String toString()
	{
		return String.format("[%f, %f, %f, %f]", x, y, z, w);
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(obj == null) return false;
		if(obj == this) return true;
		if(!(obj instanceof Vector4)) return false;
		Vector4 other = (Vector4) obj;
		return Float.floatToIntBits(x) == Float.floatToIntBits(other.x) &&
				Float.floatToIntBits(y) == Float.floatToIntBits(other.y) &&
				Float.floatToIntBits(z) == Float.floatToIntBits(other.z) &&
				Float.floatToIntBits(w) == Float.floatToIntBits(other.w);
	}
	
	@Override
	public int hashCode()
	{
		int result = 43;
		result = 37 * result + Float.floatToIntBits(x);
		result = 37 * result + Float.floatToIntBits(y);
		result = 37 * result + Float.floatToIntBits(z);
		result = 37 * result + Float.floatToIntBits(w);
		return result;
	}
}
