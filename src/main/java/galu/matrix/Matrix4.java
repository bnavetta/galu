package galu.matrix;

import java.nio.FloatBuffer;

public final class Matrix4 implements Matrix<Matrix4>
{
	@Override
	public int rowCount()
	{
		return 0;
	}

	@Override
	public int columnCount()
	{
		return 0;
	}

	@Override
	public float determinant()
	{
		return 0;
	}

	@Override
	public Matrix4 inverse()
	{
		return null;
	}

	@Override
	public Matrix4 negate()
	{
		return null;
	}

	@Override
	public Matrix4 transpose()
	{
		return null;
	}

	@Override
	public Matrix4 add(Matrix4 other)
	{
		return null;
	}

	@Override
	public Matrix4 subtract(Matrix4 other)
	{
		return null;
	}

	@Override
	public Matrix4 multiply(Matrix4 other)
	{
		return null;
	}

	@Override
	public Matrix4 divide(Matrix4 other)
	{
		return null;
	}

	@Override
	public Matrix4 multiply(float scalar)
	{
		return null;
	}

	@Override
	public Matrix4 elementMultiply(Matrix4 other)
	{
		return null;
	}

	@Override
	public Matrix4 elementDivide(Matrix4 other)
	{
		return null;
	}

	@Override
	public void store(FloatBuffer buf, Order order)
	{

	}

	@Override
	public void store(float[] array, Order order)
	{

	}
}
