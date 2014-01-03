package galu.matrix;

import galu.vector.Vector4;

import java.nio.FloatBuffer;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

public final class Matrix4 implements Matrix<Matrix4>
{
	public final float m00, m01, m02, m03,
	                   m10, m11, m12, m13,
	                   m20, m21, m22, m23,
	                   m30, m31, m32, m33;

	public Matrix4(float m00, float m01, float m02, float m03, float m10, float m11, float m12, float m13, float m20, float m21, float m22, float m23, float m30, float m31, float m32, float m33)
	{
		this.m00 = m00;
		this.m01 = m01;
		this.m02 = m02;
		this.m03 = m03;
		this.m10 = m10;
		this.m11 = m11;
		this.m12 = m12;
		this.m13 = m13;
		this.m20 = m20;
		this.m21 = m21;
		this.m22 = m22;
		this.m23 = m23;
		this.m30 = m30;
		this.m31 = m31;
		this.m32 = m32;
		this.m33 = m33;
	}

	@Override
	public int rowCount()
	{
		return 4;
	}

	@Override
	public int columnCount()
	{
		return 4;
	}

	@Override
	public float determinant()
	{
		return m00*(m11*(m22*m33-m32*m23) - m12*(m21*m33-m31*m23) + m13*(m21*m32-m31*m22))
	         - m01*(m10*(m22*m33-m32*m23) - m12*(m20*m33-m30*m23) + m13*(m20*m32-m30*m22))
			 + m02*(m10*(m21*m33-m31*m23) - m11*(m20*m33-m30*m23) + m13*(m20*m31-m30*m21))
			 - m03*(m10*(m21*m32-m22*m31) - m11*(m20*m32-m30*m22) + m12*(m20*m31-m30*m21));
	}

	// http://www.cg.info.hiroshima-cu.ac.jp/~miyazaki/knowledge/teche23.html
	@Override
	public Matrix4 inverse()
	{
		float det = determinant();
		checkState(det != 0.0, "Matrix is not invertible: determinant is 0");
		float factor = 1.0f / det;
		return new Matrix4(
			factor * (m11*m22*m33 + m12*m23*m31 + m13*m21*m32 - m11*m23*m32 - m12*m21*m33 - m13*m22*m31),
		    factor * (m01*m23*m32 + m02*m21*m33 + m03*m22*m31 - m01*m22*m33 - m02*m23*m31 - m03*m21*m32),
		    factor * (m01*m12*m33 + m02*m13*m31 + m03*m11*m32 - m01*m13*m32 - m02*m11*m33 - m03*m12*m31),
		    factor * (m01*m13*m22 + m02*m11*m23 + m03*m12*m21 - m01*m12*m23 - m02*m13*m21 - m03*m11*m22),
		    factor * (m10*m23*m32 + m12*m20*m33 + m13*m22*m30 - m10*m22*m33 - m12*m23*m30 - m13*m20*m32),
		    factor * (m00*m22*m33 + m02*m23*m30 + m03*m20*m32 - m00*m23*m32 - m02*m20*m33 - m03*m22*m30),
		    factor * (m00*m13*m32 + m02*m10*m33 + m03*m12*m30 - m00*m12*m33 - m02*m13*m30 - m03*m10*m32),
		    factor * (m00*m12*m23 + m02*m13*m20 + m03*m10*m22 - m00*m13*m22 - m02*m10*m23 - m03*m12*m20),
		    factor * (m10*m21*m33 + m11*m23*m30 + m13*m20*m31 - m10*m23*m31 - m11*m20*m33 - m13*m21*m30),
		    factor * (m00*m23*m31 + m01*m20*m33 + m03*m21*m30 - m00*m21*m33 - m01*m23*m30 - m03*m20*m31),
		    factor * (m00*m11*m33 + m01*m13*m30 + m03*m10*m31 - m00*m13*m31 - m01*m10*m33 - m03*m11*m30),
		    factor * (m00*m13*m21 + m01*m10*m23 + m03*m11*m20 - m00*m11*m23 - m01*m13*m20 - m03*m10*m21),
		    factor * (m10*m22*m31 + m11*m20*m32 + m12*m21*m30 - m10*m21*m32 - m11*m22*m30 - m12*m20*m31),
		    factor * (m00*m21*m32 + m01*m22*m30 + m02*m20*m31 - m00*m22*m31 - m01*m20*m32 - m02*m21*m30),
		    factor * (m00*m12*m31 + m01*m10*m32 + m02*m11*m30 - m00*m11*m32 - m01*m12*m30 - m02*m10*m31),
		    factor * (m00*m11*m22 + m01*m12*m20 + m02*m10*m21 - m00*m12*m21 - m01*m10*m22 - m02*m11*m20)
		);
	}

	@Override
	public Matrix4 negate()
	{
		return new Matrix4(
		    -m00, -m01, -m02, -m03,
		    -m10, -m11, -m12, -m13,
		    -m20, -m21, -m22, -m23,
		    -m30, -m31, -m32, -m33
		);
	}

	@Override
	public Matrix4 transpose()
	{
		return new Matrix4(
		    m00, m10, m20, m30,
		    m01, m11, m21, m31,
		    m02, m12, m22, m32,
		    m03, m13, m23, m33
		);
	}

	@Override
	public Matrix4 add(Matrix4 other)
	{
		return new Matrix4(
			m00 + other.m00, m01 + other.m01, m02 + other.m02, m03 + other.m03,
		    m10 + other.m10, m11 + other.m11, m12 + other.m12, m13 + other.m13,
		    m20 + other.m20, m21 + other.m21, m22 + other.m22, m23 + other.m23,
		    m30 + other.m30, m31 + other.m31, m32 + other.m32, m33 + other.m33
		);
	}

	@Override
	public Matrix4 subtract(Matrix4 other)
	{
		return new Matrix4(
				m00 - other.m00, m01 - other.m01, m02 - other.m02, m03 - other.m03,
				m10 - other.m10, m11 - other.m11, m12 - other.m12, m13 - other.m13,
				m20 - other.m20, m21 - other.m21, m22 - other.m22, m23 - other.m23,
				m30 - other.m30, m31 - other.m31, m32 - other.m32, m33 - other.m33
		);
	}

	@Override
	public Matrix4 multiply(Matrix4 other)
	{
		return new Matrix4(
			m00*other.m00 + m01*other.m10 + m02*other.m20 + m03*other.m30,
		    m00*other.m01 + m01*other.m11 + m02*other.m21 + m03*other.m31,
		    m00*other.m02 + m01*other.m12 + m02*other.m22 + m03*other.m32,
		    m00*other.m03 + m01*other.m13 + m02*other.m23 + m03*other.m33,

		    m10*other.m00 + m11*other.m10 + m12*other.m20 + m13*other.m30,
		    m10*other.m01 + m11*other.m11 + m12*other.m21 + m13*other.m31,
		    m10*other.m02 + m11*other.m12 + m12*other.m22 + m13*other.m32,
		    m10*other.m03 + m11*other.m13 + m12*other.m23 + m13*other.m33,

		    m20*other.m00 + m21*other.m10 + m22*other.m20 + m23*other.m30,
		    m20*other.m01 + m21*other.m11 + m22*other.m21 + m23*other.m31,
		    m20*other.m02 + m21*other.m12 + m22*other.m22 + m23*other.m32,
		    m20*other.m03 + m21*other.m13 + m22*other.m23 + m23*other.m33,

		    m30*other.m00 + m31*other.m10 + m32*other.m20 + m33*other.m30,
		    m30*other.m01 + m31*other.m11 + m32*other.m21 + m33*other.m31,
		    m30*other.m02 + m31*other.m12 + m32*other.m22 + m33*other.m32,
		    m30*other.m03 + m31*other.m13 + m32*other.m23 + m33*other.m33
		);
	}

	@Override
	public Matrix4 divide(Matrix4 other)
	{
		return multiply(other.inverse());
	}

	@Override
	public Matrix4 multiply(float scalar)
	{
		return new Matrix4(
			scalar*m00, scalar*m01, scalar*m02, scalar*m03,
		    scalar*m10, scalar*m11, scalar*m12, scalar*m13,
		    scalar*m20, scalar*m21, scalar*m22, scalar*m23,
		    scalar*m30, scalar*m31, scalar*m32, scalar*m33
		);
	}

	@Override
	public Matrix4 elementMultiply(Matrix4 other)
	{
		return new Matrix4(
				m00 * other.m00, m01 * other.m01, m02 * other.m02, m03 * other.m03,
				m10 * other.m10, m11 * other.m11, m12 * other.m12, m13 * other.m13,
				m20 * other.m20, m21 * other.m21, m22 * other.m22, m23 * other.m23,
				m30 * other.m30, m31 * other.m31, m32 * other.m32, m33 * other.m33
		);
	}

	@Override
	public Matrix4 elementDivide(Matrix4 other)
	{
		return new Matrix4(
			m00 / other.m00, m01 / other.m01, m02 / other.m02, m03 / other.m03,
			m10 / other.m10, m11 / other.m11, m12 / other.m12, m13 / other.m13,
			m20 / other.m20, m21 / other.m21, m22 / other.m22, m23 / other.m23,
			m30 / other.m30, m31 / other.m31, m32 / other.m32, m33 / other.m33
		);
	}

	public Vector4 transform(Vector4 vec)
	{
		float x = m00 * vec.x + m01 * vec.y + m02 * vec.z + m03 * vec.w;
		float y = m10 * vec.x + m11 * vec.y + m12 * vec.z + m13 * vec.w;
		float z = m20 * vec.x + m21 * vec.y + m22 * vec.z + m23 * vec.w;
		float w = m30 * vec.x + m31 * vec.y + m32 * vec.z + m33 * vec.w;
		return new Vector4(x, y, z, w);
	}

	@Override
	public void store(FloatBuffer buf, Order order)
	{
		checkArgument(buf.remaining() >= 16, "Buffer has fewer than 16 elements remaining (%s)", buf);
		switch(order)
		{
			case ROW_MAJOR:
				buf.put(m00).put(m01).put(m02).put(m03)
				   .put(m10).put(m11).put(m12).put(m13)
				   .put(m20).put(m21).put(m21).put(m23)
				   .put(m30).put(m31).put(m32).put(m33);
				break;
			case COLUMN_MAJOR:
				buf.put(m00).put(m10).put(m20).put(m30)
				   .put(m01).put(m11).put(m21).put(m31)
				   .put(m02).put(m12).put(m22).put(m32)
				   .put(m03).put(m13).put(m23).put(m33);
				break;
			default:
				throw new IllegalArgumentException("Unsupported matrix ordering: " + order);
		}
	}

	@Override
	public void store(float[] array, Order order)
	{
		checkArgument(array.length >= 16, "Array has fewer than 16 elements (%d)", array.length);
		switch(order)
		{
			case ROW_MAJOR:
				array[0] = m00;
				array[1] = m01;
				array[2] = m02;
				array[3] = m03;

				array[4] = m10;
				array[5] = m11;
				array[6] = m12;
				array[7] = m13;

				array[8] = m20;
				array[9] = m21;
				array[10] = m22;
				array[11] = m23;

				array[12] = m30;
				array[13] = m31;
				array[14] = m32;
				array[15] = m33;
				break;
			case COLUMN_MAJOR:
				array[0] = m00;
				array[1] = m10;
				array[2] = m20;
				array[3] = m30;

				array[4] = m01;
				array[5] = m11;
				array[6] = m21;
				array[7] = m31;

				array[8] = m02;
				array[9] = m12;
				array[10] = m22;
				array[11] = m32;

				array[12] = m03;
				array[13] = m13;
				array[14] = m23;
				array[15] = m33;
				break;
			default:
				throw new IllegalArgumentException("Unsupported matrix ordering: " + order);
		}
	}

	public static Matrix4 load(float[] array, Order order)
	{
		checkArgument(array.length >= 16, "Array has fewer than 16 elements (%d)", array.length);
		switch(order)
		{
		   case ROW_MAJOR:
			   return new Matrix4(
				   array[ 0], array[ 1], array[ 2], array[3],
			       array[ 4], array[ 5], array[ 6], array[ 7],
			       array[ 8], array[ 9], array[10], array[11],
			       array[12], array[13], array[14], array[15]
			   );
		   case COLUMN_MAJOR:
			   return new Matrix4(
				    array[0], array[4], array[ 8], array[12],
			        array[1], array[5], array[ 9], array[13],
			        array[2], array[6], array[10], array[14],
			        array[3], array[7], array[11], array[15]
			   );
			default:
				throw new IllegalArgumentException("Unsupported matrix ordering: " + order);
		}
	}

	public static Matrix4 load(FloatBuffer buf, Order order)
	{
		checkArgument(buf.remaining() >= 16, "Buffer has fewer than 16 elements remaining (%s)", buf);
		switch(order)
		{
			case ROW_MAJOR:
				return new Matrix4(
				    buf.get(), buf.get(), buf.get(), buf.get(),
				    buf.get(), buf.get(), buf.get(), buf.get(),
				    buf.get(), buf.get(), buf.get(), buf.get(),
				    buf.get(), buf.get(), buf.get(), buf.get()
				);
			case COLUMN_MAJOR:
				float m00 = buf.get();
				float m10 = buf.get();
				float m20 = buf.get();
				float m30 = buf.get();

				float m01 = buf.get();
				float m11 = buf.get();
				float m21 = buf.get();
				float m31 = buf.get();

				float m02 = buf.get();
				float m12 = buf.get();
				float m22 = buf.get();
				float m32 = buf.get();

				float m03 = buf.get();
				float m13 = buf.get();
				float m23 = buf.get();
				float m33 = buf.get();

				return new Matrix4(m00, m01, m02, m03, m10, m11, m12, m13, m20, m21, m22, m23, m30, m31, m32, m33);
			default:
				throw new IllegalArgumentException("Unsupported matrix ordering: " + order);
		}
	}

	@Override
	public String toString()
	{
		return String.format("[%.4f %.4f %.4f %.4f\n%.4f %.4f %.4f %.4f\n%.4f %.4f %.4f %.4f\n%.4f %.4f %.4f %.4f]",
		                     m00, m01, m02, m03, m10, m11, m12, m13, m20, m21, m22, m23, m30, m31, m32, m33);
	}

	@Override
	public boolean equals(Object obj)
	{
		if(obj == null) return false;
		if(obj == this) return true;
		if(!(obj instanceof Matrix4)) return false;

		Matrix4 other = (Matrix4) obj;

		return Float.floatToIntBits(m00) == Float.floatToIntBits(other.m00) &&
		       Float.floatToIntBits(m01) == Float.floatToIntBits(other.m01) &&
		       Float.floatToIntBits(m02) == Float.floatToIntBits(other.m02) &&
			   Float.floatToIntBits(m03) == Float.floatToIntBits(other.m03) &&

		       Float.floatToIntBits(m10) == Float.floatToIntBits(other.m10) &&
		       Float.floatToIntBits(m11) == Float.floatToIntBits(other.m11) &&
		       Float.floatToIntBits(m12) == Float.floatToIntBits(other.m12) &&
			   Float.floatToIntBits(m13) == Float.floatToIntBits(other.m13) &&

		       Float.floatToIntBits(m20) == Float.floatToIntBits(other.m20) &&
		       Float.floatToIntBits(m21) == Float.floatToIntBits(other.m21) &&
		       Float.floatToIntBits(m22) == Float.floatToIntBits(other.m22) &&
			   Float.floatToIntBits(m23) == Float.floatToIntBits(other.m23) &&

			   Float.floatToIntBits(m30) == Float.floatToIntBits(other.m30) &&
			   Float.floatToIntBits(m31) == Float.floatToIntBits(other.m31) &&
			   Float.floatToIntBits(m32) == Float.floatToIntBits(other.m32) &&
			   Float.floatToIntBits(m33) == Float.floatToIntBits(other.m33);
	}

	@Override
	public int hashCode()
	{
		int result = 79;

		result = 37 * result + Float.floatToIntBits(m00);
		result = 37 * result + Float.floatToIntBits(m01);
		result = 37 * result + Float.floatToIntBits(m02);
		result = 37 * result + Float.floatToIntBits(m03);

		result = 37 * result + Float.floatToIntBits(m10);
		result = 37 * result + Float.floatToIntBits(m11);
		result = 37 * result + Float.floatToIntBits(m12);
		result = 37 * result + Float.floatToIntBits(m13);

		result = 37 * result + Float.floatToIntBits(m20);
		result = 37 * result + Float.floatToIntBits(m21);
		result = 37 * result + Float.floatToIntBits(m22);
		result = 37 * result + Float.floatToIntBits(m23);

		result = 37 * result + Float.floatToIntBits(m30);
		result = 37 * result + Float.floatToIntBits(m31);
		result = 37 * result + Float.floatToIntBits(m32);
		result = 37 * result + Float.floatToIntBits(m33);

		return result;
	}
}
