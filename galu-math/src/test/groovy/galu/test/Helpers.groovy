package galu.test

import galu.matrix.Matrix2
import galu.matrix.Matrix3
import galu.vector.Vector2
import galu.vector.Vector3
import galu.vector.Vector4
import org.hamcrest.Matcher

final class Helpers
{
	public static boolean close(float a, float b, float delta = 0.01)
	{
		return Math.abs(a - b) <= delta
	}

	public static Matcher<Vector2> closeTo(Vector2 expected, float delta = 0.01)
	{
		return new Vector2Close(expected, delta)
	}

	public static Matcher<Vector3> closeTo(Vector3 expected, float delta = 0.01)
	{
		return new Vector3Close(expected, delta)
	}

	public static Matcher<Vector4> closeTo(Vector4 expected, float delta = 0.01)
	{
		return new Vector4Close(expected, delta)
	}

	public static Matcher<Matrix2> closeTo(Matrix2 expected, float delta = 0.01)
	{
		return new Matrix2Close(expected, delta);
	}

	public static Matcher<Matrix3> closeTo(Matrix3 expected, float delta = 0.01)
	{
		return new Matrix3Close(expected, delta);
	}
}
