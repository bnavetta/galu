package galu.matrix;

import java.nio.FloatBuffer;

public class Matrix2 implements Matrix<Matrix2>
{
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

	@Override
	public String toString()
	{
		return String.format("[%f %f, %f %f]", m00, m01, m10, m11);
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
