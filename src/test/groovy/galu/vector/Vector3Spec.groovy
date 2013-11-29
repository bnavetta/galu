package galu.vector

import galu.vector.tester.Vector3Data

class Vector3Spec extends AbstractVectorSpec
{
	def setupSpec()
	{
		type = Vector3.class
		data = new Vector3Data()
	} 
}
