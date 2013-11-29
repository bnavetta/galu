package galu.vector;

import java.nio.FloatBuffer;

/**
 * A {@code Vector} is a sequence of floating-point numbers. Vectors are immutable
 * @author ben
 *
 */
public interface Vector<V extends Vector<V>>
{
	/**
	 * Get the number of components in the vector. Vector operations must be done on vectors of the same size.
	 * @return the number of components (2, 3, or 4)
	 */
	public int size();
	
	public float get(int idx);
	
	public float length();
	
	public float lengthSquared();
	
	public V normalize();
	
	public V add(V other);
	
	public V subtract(V other);
	
	public float dot(V other);
	
	public float angleBetween(V other);
	
	public V negate();
	
	public V multiply(float factor);
	
	public V multiply(V other);
	
	public void store(FloatBuffer buf);
	
	public void store(float[] array);
	
	public float[] toArray();
}
