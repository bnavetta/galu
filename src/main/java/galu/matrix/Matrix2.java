package galu.matrix;

import galu.vector.Vector2;

import java.nio.FloatBuffer;

import static com.google.common.base.Preconditions.*;

public class Matrix2 implements Matrix<Matrix2>
{
	/**
	 * The 2x2 identity matrix.
	 * <pre>
	 * | 1 0 |
	 * | 0 1 |
	 * </pre>
	 */
	public static final Matrix2 IDENTITY = new Matrix2(1, 0, 0, 1);

	/**
	 * The zero matrix. All elements are {@code 0}.
	 */
	public static final Matrix2 ZERO = new Matrix2(0, 0, 0, 0);

	/**
	 * a
	 */
	public final float m00;
	
	/**
	 * b
	 */
	public final float m01;
	
	/**
	 * c
	 */
	public final float m10;
	
	/**
	 * d
	 */
	public final float m11;
	
	public Matrix2(float m00, float m01, float m10, float m11)
	{
		this.m00 = m00;
		this.m01 = m01;
		this.m10 = m10;
		this.m11 = m11;
	}

	@Override
	public int rowCount()
	{
		return 2;
	}

	@Override
	public int columnCount()
	{
		return 2;
	}

	@Override
	public float determinant()
	{
		return m00 * m11 - m01 * m10;
	}

	@Override
	public Matrix2 inverse()
	{
		//TODO: check for nonexistant inverse (determinant is 0?)
		float factor = 1 / determinant();
		return new Matrix2(m11 * factor, -m01 * factor, -m10 * factor, m00 * factor);
	}

	@Override
	public Matrix2 negate()
	{
		return new Matrix2(-m00, -m01, -m10, -m11);
	}

	@Override
	public Matrix2 transpose()
	{
		return new Matrix2(m00, m10, m01, m11);
	}

	@Override
	public Matrix2 add(Matrix2 other)
	{
		return new Matrix2(m00 + other.m00, m01 + other.m01, m10 + other.m10, m11 + other.m11);
	}

	@Override
	public Matrix2 subtract(Matrix2 other)
	{
		return new Matrix2(m00 - other.m00, m01 - other.m01, m10 - other.m10, m11 - other.m11);
	}

	@Override
	public Matrix2 multiply(Matrix2 other)
	{
		return new Matrix2(
			m00 * other.m00 + m01 * other.m10, m00 * other.m01 + m01 * other.m11,
			m10 * other.m00 + m11 * other.m10, m10 * other.m01 + m11 * other.m11
		);
	}

	@Override
	public Matrix2 divide(Matrix2 other)
	{
		return multiply(other.inverse());
	}

	@Override
	public Matrix2 multiply(float scalar)
	{
		return new Matrix2(m00 * scalar, m01 * scalar, m10 * scalar, m11 * scalar);
	}

	@Override
	public Matrix2 elementMultiply(Matrix2 other)
	{
		return new Matrix2(m00 * other.m00, m01 * other.m01, m10 * other.m10, m11 * other.m11);
	}

	@Override
	public Matrix2 elementDivide(Matrix2 other)
	{
		return new Matrix2(m00 / other.m00, m01 / other.m01, m10 / other.m10, m11 / other.m11);
	}

	public Vector2 transform(Vector2 vec)
	{
		// Note to self: when using LWJGL's matrix/vector code for reference, they use variables of the form m{c}{r}, but we use m{r}{c}
		float x = m00 * vec.x + m01 * vec.y;
		float y = m10 * vec.x + m11 * vec.y;
		return new Vector2(x, y);
	}

	@Override
	public void store(FloatBuffer buf, galu.matrix.Matrix.Order order)
	{
		switch(order)
		{
			case ROW_MAJOR:
				buf.put(m00).put(m01).put(m10).put(m11);
				break;
			case COLUMN_MAJOR:
				buf.put(m00).put(m10).put(m01).put(m11);
				break;
		}
	}

	@Override
	public void store(float[] array, galu.matrix.Matrix.Order order) {
		switch(order)
		{
			case ROW_MAJOR:
				array[0] = m00;
				array[1] = m01;
				array[2] = m10;
				array[3] = m11;
				break;
			case COLUMN_MAJOR:
				array[0] = m00;
				array[1] = m10;
				array[2] = m01;
				array[3] = m11;
				break;
		}
	}

	public static Matrix2 load(float[] array, Order order)
	{
		checkArgument(array.length >= 4, "Array must have at least 4 elements, the given array has %d", array.length);
		switch(order)
		{
			case ROW_MAJOR:
				return new Matrix2(array[0], array[1], array[2], array[3]);
			case COLUMN_MAJOR:
				return new Matrix2(array[0], array[2], array[1], array[3]);
			default:
				throw new IllegalArgumentException("Unsupported matrix ordering: " + order);
		}
	}

	public static Matrix2 load(FloatBuffer buffer, Order order)
	{
		checkArgument(buffer.remaining() >= 4, "Buffer has fewer than 4 elements remaining (%s)", buffer);
		switch(order)
		{
			case ROW_MAJOR:
				return new Matrix2(buffer.get(), buffer.get(), buffer.get(), buffer.get());
			case COLUMN_MAJOR:
				float m00 = buffer.get();
				float m10 = buffer.get();
				float m01 = buffer.get();
				float m11 = buffer.get();
				return new Matrix2(m00, m01, m10, m11);
			default:
				throw new IllegalArgumentException("Unsupported matrix ordering: " + order);
		}
	}

	@Override
	public String toString()
	{
		return "[" + m00 + " " + m01 + "\n" + m10 + " " + m11 + "]";
	}

	@Override
	public boolean equals(Object obj)
	{
		if(obj == null) return false;
		if(obj == this) return true;
		if(!(obj instanceof Matrix2)) return false;

		Matrix2 other = (Matrix2) obj;

		return Float.floatToIntBits(m00) == Float.floatToIntBits(other.m00) &&
				Float.floatToIntBits(m01) == Float.floatToIntBits(other.m01) &&
				Float.floatToIntBits(m10) == Float.floatToIntBits(other.m10) &&
				Float.floatToIntBits(m11) == Float.floatToIntBits(other.m11);
	}

	@Override
	public int hashCode()
	{
		int result = 47;
		result = 37 * result + Float.floatToIntBits(m00);
		result = 37 * result + Float.floatToIntBits(m01);
		result = 37 * result + Float.floatToIntBits(m10);
		result = 37 * result + Float.floatToIntBits(m11);
		return result;
	}
}
