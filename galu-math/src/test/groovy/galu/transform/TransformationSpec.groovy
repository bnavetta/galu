package galu.transform

import galu.matrix.Matrix2
import galu.matrix.Matrix3
import galu.matrix.Matrix4
import galu.vector.Vector2
import galu.vector.Vector3
import galu.vector.Vector4
import spock.lang.Specification

import static spock.util.matcher.HamcrestSupport.*
import static spock.util.matcher.HamcrestMatchers.closeTo
import static galu.test.Helpers.*

class TransformationSpec extends Specification
{
	def "2-D rotate"()
	{
		given:
		def angle = Math.toRadians(42) as float
		def expected = new Matrix2(0.743, -0.669, 0.669, 0.743)

		expect:
		that Transformations.rotate(angle), closeTo(expected)
	}

	def "2-D scale"()
	{
		given:
		def xScale = 4
		def yScale = 6
		def expected = new Matrix2(xScale, 0,
				                   0, yScale)

		expect:
		that Transformations.scale(new Vector2(xScale, yScale)), closeTo(expected)

		that Transformations.scale(new Vector2(xScale, yScale)).transform(new Vector2(1, 2)),
				closeTo(new Vector2(1 * xScale, 2 * yScale))
	}

	def "2-D reflect"()
	{
		given:
		def direction = new Vector2(1, 2)
		def expected = new Matrix2(-0.6, 0.8, 0.8, 0.6)

		expect:
		that Transformations.reflect(direction), closeTo(expected)
	}

	def "combine 2-D transformations"()
	{
		given:
		def first = Transformations.rotate(Math.PI / 2.0f as float)
		def second = Transformations.scale(new Vector2(2, 2))
		def expected = second.multiply(first)

		expect:
		that Transformations.combine(first, second), closeTo(expected)
	}

	def "3-D rotation with Matrix3"()
	{
		given:
		def angle = Math.toRadians(45) as float
		def direction = new Vector3(1, 2, 3)
//		def expected = new Matrix3(0.728, -0.5251, 0.4407, 0.6088, 0.7908, -0.0635, -0.3152, 0.3145, 0.8954)
		def expected = new Matrix3(1, -1.53553, 2.29289, 2.7071, 1.87868, 1.05025, -0.53553, 2.464466, 3.34314575)

		expect:
		that Transformations.rotation3(angle, direction), closeTo(expected)
	}

	def "3-D scale with Matrix3"()
	{
		given:
		def factors = new Vector3(2, 3, 4)
		def expected = new Matrix3(2, 0, 0, 0, 3, 0, 0, 0, 4)

		expect:
		that Transformations.scale3(factors), closeTo(expected)

		that Transformations.scale3(factors).transform(new Vector3(1, 2, 3)), closeTo(new Vector3(2, 6, 12))
	}

	def "combine 3-D transformations with Matrix3"()
	{
		given:
		def first = Transformations.scale3(new Vector3(1, 2, 3))
		def second = Transformations.rotation3(Math.PI as float, new Vector3(1, 2, 3))
		def expected = second.multiply(first)

		expect:
		that Transformations.combine(first, second), closeTo(expected)
	}

	def "3-D scale"()
	{
		given:
		def factors = new Vector3(2, 4, 8)
		def expected = new Matrix4(2, 0, 0, 0,
				                   0, 4, 0, 0,
		                           0, 0, 8, 0,
		                           0, 0, 0, 1)
		def inVector = new Vector4(1, 2, 3, 1)
		def outVector = new Vector4(2, 8, 24, 1)

		expect:
		that Transformations.scale(factors), closeTo(expected)
		that Transformations.scale(factors).transform(inVector), closeTo(outVector)
	}
}
