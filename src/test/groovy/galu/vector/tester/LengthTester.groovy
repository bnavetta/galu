package galu.vector.tester

import galu.vector.Vector

class LengthTester implements Tester
{
	public void test(Class<Vector> type, Object inputs, Object outputs)
	{
		Vector vec = type.newInstance(*inputs)
		assert Helpers.closeTo(vec.length(), outputs, 0.01f)
	}
}
