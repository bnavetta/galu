package galu.transform;

import galu.matrix.Matrix2;
import galu.matrix.Matrix3;
import galu.matrix.Matrix4;
import galu.vector.Vector2;
import galu.vector.Vector3;

/**
 * @see <a href="http://en.wikipedia.org/wiki/Transformation_matrix">Transformation matrix on Wikipedia</a>
 * @see <a href="http://www.cs.princeton.edu/~gewang/projects/darth/stuff/quat_faq.html">The Matrix and Quaternions FAQ</a>
 * @see <a href="http://www.opengl-tutorial.org/beginners-tutorials/tutorial-3-matrices/">OpenGL Tutorial 3: Matrices</a>
 */
public final class Transformations
{
	/*
	 * 2-D transformations based on:
	 * http://en.wikipedia.org/wiki/Transformation_matrix
	 */

	private Transformations() {}

	/**
	 * Rotate by an angle clockwise about the origin. This assumes that +x is right and +y is up.
	 * @param radians the angle to rotate by
	 * @return a transformation matrix
	 */
	public static Matrix2 rotate(float radians)
	{
		float cos = (float) Math.cos(radians);
		float sin = (float) Math.sin(radians);
		return new Matrix2(cos, -sin, sin, cos);
	}

	/**
	 * Scale by a factor of {@code scale.x} along the x-axis and {@code scale.y} along the y-axis.
	 * @param scale the scaling factors
	 * @return a transformation matrix
	 */
	public static Matrix2 scale(Vector2 scale)
	{
		return new Matrix2(scale.x, 0, 0, scale.y);
	}

	/**
	 * Reflect along a line where {@code direction} is along that line
	 * @param direction a vector in the direction of the line to reflect across
	 * @return a transformation matrix
	 */
	public static Matrix2 reflect(Vector2 direction)
	{
		float x = direction.x;
		float y = direction.y;
		return new Matrix2(x*x - y*y, 2*x*y, 2*x*y, y*y-x*x).multiply(1f / direction.lengthSquared());
	}

	/**
	 * Combine a series of transformation matrices.
	 * The order in which the matrices are given is the order in which they will be applied
	 * @param matrices the matrices to compose
	 * @return the combined transformation matrix
	 */
	public static Matrix2 combine(Matrix2... matrices)
	{
		Matrix2 result = Matrix2.IDENTITY;

		// Combine them backwards: to apply a, then b, we want b of a = b * a
		for(int i = matrices.length-1; i >= 0; i--)
		{
			result = result.multiply(matrices[i]);
		}
		return result;
	}

	/**
	 * Reflect across a line through the origin.
	 * @param direction a vector in the direction of the line to reflect across
	 * @return a transformation matrix
	 */
	public static Matrix2 projectOrthogonal(Vector2 direction)
	{
		float x = direction.x;
		float y = direction.y;

		return new Matrix2(x*x, x*y, x*y, y*y).multiply(1f/direction.lengthSquared());
	}

	public static Matrix3 rotation3(float radians, Vector3 axis)
	{
		float cos = (float) Math.cos(radians);
		float sin = (float) Math.sin(radians);

		float l = axis.x;
		float m = axis.y;
		float n = axis.z;

		return new Matrix3(
				l*l*(1f-cos) +   cos, m*l*(1f-cos) - n*sin, n*l*(1f-cos) + m*sin,
				l*m*(1f-cos) + n*sin, m*m*(1f-cos) +   cos, n*m*(1f-cos) - l*sin,
				l*n*(1f-cos) - m*sin, m*n*(1f-cos) + l*sin, n*n*(1f-cos) +   cos
		);
	}

	public static Matrix3 scale3(Vector3 scale)
	{
		return new Matrix3(
				scale.x, 0, 0,
				0, scale.y, 0,
				0, 0, scale.z
		);
	}

	/**
	 * Combine a series of transformation matrices.
	 * The order in which the matrices are given is the order in which they will be applied
	 * @param matrices the matrices to compose
	 * @return the combined transformation matrix
	 */
	public static Matrix3 combine(Matrix3... matrices)
	{
		Matrix3 result = Matrix3.IDENTITY;

		// Combine them backwards: to apply a, then b, we want b of a = b * a
		for(int i = matrices.length-1; i >= 0; i--)
		{
			result = result.multiply(matrices[i]);
		}
		return result;
	}

	/**
	 * Scale in three directions.
	 * Specifically, {@code scale.x} along the x-axis, {@code scale.y} along the y-axis, {@code scale.z} along the z-axis.
	 * @param scale the scaling factors
	 * @return a transformation matrix
	 */
	public static Matrix4 scale(Vector3 scale)
	{
		return new Matrix4(
				scale.x, 0, 0, 0,
				0, scale.y, 0, 0,
				0, 0, scale.z, 0,
				0, 0, 0, 1
		);
	}

	//TODO: check that these aren't defined in an incompatible column-major way

	/**
	 * Rotate about the x-axis by {@code angle} radians.
	 * @param angle an angle in radians
	 * @return a transformation matrix
	 */
	public static Matrix4 rotateX(float angle)
	{
		float cos = (float) Math.cos(angle);
		float sin = (float) Math.sin(angle);

		return new Matrix4(
				1,    0,   0, 0,
				0, cos, -sin, 0,
				0, sin,  cos, 0,
				0,    0,   0, 1
		);
	}

	/**
	 * Rotate about the y-axis by {@code angle} radians.
	 * @param angle an angle in radians
	 * @return a transformation matrix
	 */
	public static Matrix4 rotateY(float angle)
	{
		float cos = (float) Math.cos(angle);
		float sin = (float) Math.sin(angle);

		return new Matrix4(
				cos, 0, sin, 0,
				0, 1,   0, 0,
				-sin, 0, cos, 0,
				0, 0,   0, 1
		);
	}

	/**
	 * Rotate about the z-axis by {@code angle} radians.
	 * @param angle an angle in radians
	 * @return a transformation matrix
	 */
	public static Matrix4 rotateZ(float angle)
	{
		float cos = (float) Math.cos(angle);
		float sin = (float) Math.sin(angle);

		return new Matrix4(
				cos, -sin, 0, 0,
				sin,  cos, 0, 0,
				0,    0, 1, 0,
				0,    0, 0, 1
		);
	}

	/**
	 * Rotate by Euler angles (along x-, y-, and z-axes).
	 * @param angles the angles to rotate by (in radians)
	 * @return a transformation matrix
	 */
	public static Matrix4 rotate(Vector3 angles)
	{
		float a = (float) Math.cos(angles.x);
		float b = (float) Math.sin(angles.x);
		float c = (float) Math.cos(angles.y);
		float d = (float) Math.sin(angles.y);
		float e = (float) Math.cos(angles.z);
		float f = (float) Math.sin(angles.z);

		float ad = a * d;
		float bd = b * d;

		return new Matrix4(
				c*e,      -c*f,    d, 0,
				bd*e+a*f, -bd*f+a*e, -b*c, 0,
				-ad*e+b*f,  ad*f+b*e,  a*c, 0,
				0,         0,    0, 1
		);
	}

	/**
	 * Rotate by an angle through an axis
	 * @param angle the angle to rotate by (radians)
	 * @param axis the axis to rotate through
	 * @return a transformation matrix
	 */
	public static Matrix4 rotate(float angle, Vector3 axis)
	{
		float rcos = (float) Math.cos(angle);
		float rsin = (float) Math.sin(angle);

		Vector3 normalized = axis.normalize(); // GLM normalizes the axis (maybe so there isn't scaling?)
		float u = normalized.x;
		float v = normalized.y;
		float w = normalized.z;

		float m00 =      rcos + u * u * (1f-rcos);
		float m10 =  w * rsin + v * u * (1f-rcos);
		float m20 = -v * rsin + w * u * (1f-rcos);

		float m01 = -w * rsin + u * v * (1f-rcos);
		float m11 =      rcos + v * v * (1f-rcos);
		float m21 =  u * rsin + w * v * (1f-rcos);

		float m02 =  v * rsin + u * w * (1f-rcos);
		float m12 = -u * rsin + v * w * (1f-rcos);
		float m22 =      rcos + w * w * (1f-rcos);

		return new Matrix4(
				m00, m01, m02, 0,
				m10, m11, m12, 0,
				m20, m21, m22, 0,
				0,   0,   0, 1
		);
	}

	/**
	 * Translate by the values in {@code translation}
	 * @param translation the amounts to translate by along each axis
	 * @return a transformation matrix
	 */
	public static Matrix4 translate(Vector3 translation)
	{
		return new Matrix4(
				1, 0, 0, translation.x,
				0, 1, 0, translation.y,
				0, 0, 1, translation.z,
				0, 0, 0,             1
		);
	}

	/**
	 * Shear a model. Parameter {a}{b} shears a by b.
	 * @param xy shear X by Y
	 * @param xz shear X by Z
	 * @param yx shear Y by X
	 * @param yz shear Y by Z
	 * @param zx shear Z by X
	 * @param zy shear Z by Y
	 * @return a transformation matrix
	 */
	public static Matrix4 shear(float xy, float xz, float yx, float yz, float zx, float zy)
	{
		return new Matrix4(
				1, yx, zx, 0,
				xy,  1, zy, 0,
				xz, yz,  1, 0,
				0,  0,  0, 1
		);
	}

	/**
	 * Combine a series of transformation matrices.
	 * The order in which the matrices are given is the order in which they will be applied
	 * @param matrices the matrices to compose
	 * @return the combined transformation matrix
	 */
	public static Matrix4 combine(Matrix4... matrices)
	{
		Matrix4 result = Matrix4.IDENTITY;

		// Combine them backwards: to apply a, then b, we want b of a = b * a
		for(int i = matrices.length-1; i >= 0; i--)
		{
			result = result.multiply(matrices[i]);
		}
		return result;
	}

	/**
	 * Convert a 3x3 transformation matrix to a 4x4 transformation matrix that can be used with homogeneous coordinates.
	 * The conversion is as follows:
	 * <pre>
	 *     | m00 m01 m02 0 |
	 *     | m10 m11 m12 0 |
	 *     | m20 m21 m22 0 |
	 *     |   0   0   0 1 |
	 * </pre>
	 * @param mat a 3x3 transformation matrix
	 * @return the corresponding 4x4 transformation matrix
	 */
	public static Matrix4 toHomogeneous(Matrix3 mat)
	{
		return new Matrix4(
			mat.m00, mat.m01, mat.m02, 0,
		    mat.m10, mat.m11, mat.m12, 0,
		    mat.m20, mat.m21, mat.m22, 0,
		          0,       0,       0, 1
		);
	}

	/**
	 * Convert a 4x4 matrix for transforming homogeneous coordinates to the corresponding 3x3 matrix.
	 * The last row and last column are removed.
	 * @param mat a 4x4 transformation matrix
	 * @return the corresponding 3x3 transformation matrix
	 */
	public static Matrix3 fromHomogeneous(Matrix4 mat)
	{
		return new Matrix3(
			mat.m00, mat.m01, mat.m02,
		    mat.m10, mat.m11, mat.m12,
		    mat.m20, mat.m21, mat.m22
		);
	}
}
