package galu.vector.tester

import galu.vector.Vector

class GetTester implements Tester
{
	public void test(Class<Vector> type, Object inputs, Object outputs)
	{
		Vector vec = type.newInstance(*inputs)
		int size = Helpers.sizeOf(type)
		size.times {
			assert vec.get(it) == inputs[it]
		}
	}
}
