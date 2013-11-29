package galu.vector

import static org.junit.Assert.*
import galu.vector.tester.GetTester
import galu.vector.tester.LengthSquaredTester
import galu.vector.tester.LengthTester
import galu.vector.tester.SizeTester
import galu.vector.tester.TestData
import spock.lang.Shared
import spock.lang.Specification

abstract class AbstractVectorSpec extends Specification
{
	@Shared Class<Vector> type
	@Shared TestData data
	
	def "vector get"()
	{
		expect:
			new GetTester().test(type, trial.inputs, trial.outputs)
		where:
			trial << data.getData("get")
	}
	
	def "vector size"()
	{
		expect:
			new SizeTester().test(type, trial.inputs, trial.outputs)
		where:
			trial << data.getData("size")
	}
	
	def "vector length"()
	{
		expect:
			new LengthTester().test(type, trial.inputs, trial.outputs)
		where:
			trial << data.getData("length")
	}
	
	def "vector length squared"()
	{
		expect:
		new LengthSquaredTester().test(type, trial.inputs, trial.outputs)
	where:
		trial << data.getData("length squared")
	}
}
