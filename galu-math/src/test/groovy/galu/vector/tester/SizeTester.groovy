package galu.vector.tester

import galu.vector.Vector

class SizeTester implements Tester
{
	public void test(Class<Vector> type, Object inputs, Object outputs)
	{
		int expectedSize = Helpers.sizeOf(type)
		assert type.newInstance(*inputs).size() == expectedSize
	}
}
