package galu.matrix;

import galu.vector.Vector3;

import java.nio.FloatBuffer;

import static com.google.common.base.Preconditions.*;

public final class Matrix3 implements Matrix<Matrix3>
{
	public final float m00, m01, m02,
	                   m10, m11, m12,
	                   m20, m21, m22;

	public Matrix3(float m00, float m01, float m02, float m10, float m11, float m12, float m20, float m21, float m22)
	{
		this.m00 = m00;
		this.m01 = m01;
		this.m02 = m02;
		this.m10 = m10;
		this.m11 = m11;
		this.m12 = m12;
		this.m20 = m20;
		this.m21 = m21;
		this.m22 = m22;
	}

	@Override
	public int rowCount()
	{
		return 3;
	}

	@Override
	public int columnCount()
	{
		return 3;
	}

	@Override
	public float determinant()
	{
		return m00 * (m11 * m22 - m12 * m21)
				- m01 * (m10 * m22 - m12 * m20)
				+ m02 * (m10 * m21 - m11 * m20);
	}

	@Override
	public Matrix3 inverse()
	{
		float det = determinant();
		checkState(det != 0.0, "Matrix is not invertible: determinant is 0");
		float factor = 1.0f / det;
		return new Matrix3(
			 factor * (m11 * m22 - m12 * m21),
		    -factor * (m01 * m22 - m21 * m02),
		     factor * (m01 * m12 - m11 * m02),
		    -factor * (m10 * m22 - m20 * m12),
		     factor * (m00 * m22 - m20 * m02),
		    -factor * (m00 * m12 - m10 * m02),
		     factor * (m10 * m21 - m20 * m11),
		    -factor * (m00 * m21 - m20 * m01),
		     factor * (m00 * m11 - m10 * m01)
		);
	}

	@Override
	public Matrix3 negate()
	{
		return new Matrix3(-m00, -m01, -m02, -m10, -m11, -m12, -m20, -m21, -m22);
	}

	@Override
	public Matrix3 transpose()
	{
		return new Matrix3(m00, m10, m20, m01, m11, m21, m02, m12, m22);
	}

	@Override
	public Matrix3 add(Matrix3 other)
	{
		return new Matrix3(m00 + other.m00, m01 + other.m01, m02 + other.m02,
		                   m10 + other.m10, m11 + other.m11, m12 + other.m12,
		                   m20 + other.m20, m21 + other.m21, m22 + other.m22);
	}

	@Override
	public Matrix3 subtract(Matrix3 other)
	{
		return new Matrix3(m00 - other.m00, m01 - other.m01, m02 - other.m02,
		                   m10 - other.m10, m11 - other.m11, m12 - other.m12,
		                   m20 - other.m20, m21 - other.m21, m22 - other.m22);
	}

	@Override
	public Matrix3 multiply(Matrix3 other)
	{
		return new Matrix3(
		    m00*other.m00 + m01*other.m10 + m02*other.m20,
		    m00*other.m01 + m01*other.m11 + m02*other.m21,
		    m00*other.m02 + m01*other.m12 + m02*other.m22,

		    m10*other.m00 + m11*other.m10 + m12*other.m20,
		    m10*other.m01 + m11*other.m11 + m12*other.m21,
		    m10*other.m02 + m11*other.m12 + m12*other.m22,

		    m20*other.m00 + m21*other.m10 + m22*other.m20,
		    m20*other.m01 + m21*other.m11 + m22*other.m21,
		    m20*other.m02 + m21*other.m12 + m22*other.m22
		);
	}

	@Override
	public Matrix3 divide(Matrix3 other)
	{
		return multiply(other.inverse());
	}

	@Override
	public Matrix3 multiply(float scalar)
	{
		return new Matrix3(scalar*m00, scalar*m01, scalar*m02,
		                   scalar*m10, scalar*m11, scalar*m12,
		                   scalar*m20, scalar*m21, scalar*m22);
	}

	@Override
	public Matrix3 elementMultiply(Matrix3 other)
	{
		return new Matrix3(m00*other.m00, m01*other.m01, m02*other.m02,
		                   m10*other.m10, m11*other.m11, m12*other.m12,
		                   m20*other.m20, m21*other.m21, m22*other.m22);
	}

	@Override
	public Matrix3 elementDivide(Matrix3 other)
	{
		return new Matrix3(m00/other.m00, m01/other.m01, m02/other.m02,
		                   m10/other.m10, m11/other.m11, m12/other.m12,
		                   m20/other.m20, m21/other.m21, m22/other.m22);
	}

	public Vector3 transform(Vector3 vec)
	{
		float x = m00 * vec.x + m01 * vec.y + m02 * vec.z;
		float y = m10 * vec.x + m11 * vec.y + m12 * vec.z;
		float z = m20 * vec.x + m21 * vec.y + m22 * vec.z;
		return new Vector3(x, y, z);
	}

	@Override
	public void store(FloatBuffer buf, Order order)
	{
		checkArgument(buf.remaining() >= 9, "Buffer has fewer than 9 elements remaining (%s)", buf);
		switch(order)
		{
			case ROW_MAJOR:
				buf.put(m00).put(m01).put(m02)
			       .put(m10).put(m11).put(m12)
				   .put(m20).put(m21).put(m21);
				break;
			case COLUMN_MAJOR:
				buf.put(m00).put(m10).put(m20)
				   .put(m01).put(m11).put(m21)
				   .put(m02).put(m12).put(m22);
				break;
			default:
				throw new IllegalArgumentException("Unsupported matrix ordering: " + order);
		}
	}

	@Override
	public void store(float[] array, Order order)
	{
		checkArgument(array.length >= 9, "Array has fewer than 9 elements (%d)", array.length);
		switch(order)
		{
			case ROW_MAJOR:
				array[0] = m00;
				array[1] = m01;
				array[2] = m02;
				array[3] = m10;
				array[4] = m11;
				array[5] = m12;
				array[6] = m20;
				array[7] = m21;
				array[8] = m22;
				break;
			case COLUMN_MAJOR:
				array[0] = m00;
				array[1] = m10;
				array[2] = m20;
				array[3] = m01;
				array[4] = m11;
				array[5] = m21;
				array[6] = m02;
				array[7] = m12;
				array[8] = m22;
				break;
			default:
				throw new IllegalArgumentException("Unsupported matrix ordering: " + order);
		}
	}

	public static Matrix3 load(FloatBuffer buffer, Order order)
	{
		checkArgument(buffer.remaining() >= 9, "Buffer has fewer than 9 elements remaining (%s)", buffer);
		switch(order)
		{
			case ROW_MAJOR:
				return new Matrix3(
				    buffer.get(), buffer.get(), buffer.get(),
				    buffer.get(), buffer.get(), buffer.get(),
				    buffer.get(), buffer.get(), buffer.get()
				);
			case COLUMN_MAJOR:
				float m00 = buffer.get();
				float m10 = buffer.get();
				float m20 = buffer.get();
				float m01 = buffer.get();
				float m11 = buffer.get();
				float m21 = buffer.get();
				float m02 = buffer.get();
				float m12 = buffer.get();
				float m22 = buffer.get();
				return new Matrix3(m00, m01, m02, m10, m11, m12, m20, m21, m22);
			default:
				throw new IllegalArgumentException("Unsupported matrix ordering: " + order);
		}
	}

	public static Matrix3 load(float[] array, Order order)
	{
		checkArgument(array.length >= 9, "Array has fewer than 9 elements (%d)", array.length);
		switch(order)
		{
			case ROW_MAJOR:
				return new Matrix3(
				    array[0], array[1], array[2],
				    array[3], array[4], array[5],
				    array[6], array[7], array[8]
				);
			case COLUMN_MAJOR:
				return new Matrix3(
				    array[0], array[3], array[6],
				    array[1], array[4], array[7],
				    array[2], array[5], array[8]
				);
			default:
				throw new IllegalArgumentException("Unsupported matrix ordering: " + order);
		}
	}

	@Override
	public String toString()
	{
		return String.format("[%.4f %.4f %.4f\n%.4f %.4f %.4f\n%.4f %.4f %.4f]", m00, m01, m02, m10, m11, m12, m20, m21, m22);
	}

	@Override
	public boolean equals(Object obj)
	{
		if(obj == null) return false;
		if(obj == this) return true;
		if(!(obj instanceof Matrix3)) return false;

		Matrix3 other = (Matrix3) obj;

		return Float.floatToIntBits(m00) == Float.floatToIntBits(other.m00) &&
			   Float.floatToIntBits(m01) == Float.floatToIntBits(other.m01) &&
			   Float.floatToIntBits(m02) == Float.floatToIntBits(other.m02) &&
			   Float.floatToIntBits(m10) == Float.floatToIntBits(other.m10) &&
			   Float.floatToIntBits(m11) == Float.floatToIntBits(other.m11) &&
			   Float.floatToIntBits(m12) == Float.floatToIntBits(other.m12) &&
			   Float.floatToIntBits(m20) == Float.floatToIntBits(other.m20) &&
			   Float.floatToIntBits(m21) == Float.floatToIntBits(other.m21) &&
			   Float.floatToIntBits(m22) == Float.floatToIntBits(other.m22);
	}

	@Override
	public int hashCode()
	{
		int result = 47;
		result = 37 * result + Float.floatToIntBits(m00);
		result = 37 * result + Float.floatToIntBits(m01);
		result = 37 * result + Float.floatToIntBits(m02);
		result = 37 * result + Float.floatToIntBits(m10);
		result = 37 * result + Float.floatToIntBits(m11);
		result = 37 * result + Float.floatToIntBits(m12);
		result = 37 * result + Float.floatToIntBits(m20);
		result = 37 * result + Float.floatToIntBits(m21);
		result = 37 * result + Float.floatToIntBits(m22);
		return result;
	}
}
