package galu.matrix;

import java.nio.FloatBuffer;

public interface Matrix<M extends Matrix<M>>
{
	public int rowCount();
	public int columnCount();
	
	public float determinant();
	
	public M inverse();
	
	public M negate();
	
	public M transpose();
	
	public M add(M other);
	public M subtract(M other);
	
	public M multiply(M other);
	public M divide(M other);

	public M multiply(float scalar);

	public M elementMultiply(M other);
	public M elementDivide(M other);
	
	public void store(FloatBuffer buf, Order order);
	public void store(float[] array, Order order);
	
	public static enum Order
	{
		COLUMN_MAJOR,
		ROW_MAJOR;
	}
}
