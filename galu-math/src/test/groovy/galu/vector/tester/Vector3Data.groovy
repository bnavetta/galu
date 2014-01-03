package galu.vector.tester

class Vector3Data extends TestData
{
	@Override
	protected void generateData()
	{
		trial "get", [1.0f, 3.5f, 7.7f]
		trial "get", [5f, 10001f, 3.1414f]
		
		trial "size", [1f, 2f, 3f]
		
		trial "length", [1f, 1f, 1f], Math.sqrt(3) as float
		trial "length", [5f, 17.7f, 3.4f], Math.sqrt(349.85) as float
		
		trial "length squared", [3f, 7f, 1f], 59f
		trial "length squared", [2.2f, 9.5f, 11.7f], 231.98f
	}
}
