package galu.vector.tester

import galu.vector.Vector

class LengthSquaredTester implements Tester
{
	public void test(Class<Vector> type, Object inputs, Object outputs)
	{
		Vector vec = type.newInstance(*inputs)
		assert Helpers.closeTo(vec.lengthSquared(), outputs, 0.01f)
	}
}
