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

	def "3-D rotation about x-axis"()
	{
		given:
		def angle = Math.toRadians(120) as float

		def RAD3_2 = Math.sqrt(3) / 2f as float
		def expected = new Matrix4(
			1,      0,       0, 0,
			0,   -0.5, -RAD3_2, 0,
			0, RAD3_2,    -0.5, 0,
			0,      0,       0, 1
		);

		expect:
		that Transformations.rotateX(angle), closeTo(expected)
	}

	def "3-D rotation about y-axis"()
	{
		given:
		def angle = Math.toRadians(66) as float
		def expected = new Matrix4(
				 0.407, 0, 0.914, 0,
				     0, 1,     0, 0,
				-0.914, 0, 0.407, 0,
				     0, 0,     0, 1)

		expect:
		that Transformations.rotateY(angle), closeTo(expected)
	}

	def "3-D rotation about z-axis"()
	{
		given:
		def angle = 3 * Math.PI / 4 as float
		def INV_SQRT2 = 1 / Math.sqrt(2) as float
		def expected = new Matrix4(
			-INV_SQRT2, -INV_SQRT2, 0, 0,
			 INV_SQRT2, -INV_SQRT2, 0, 0,
			         0,          0, 1, 0,
			         0,          0, 0, 1
		)

		expect:
		that Transformations.rotateZ(angle), closeTo(expected)
	}

	def "3-D rotation about arbitrary axis"()
	{
		given:
		def angle = Math.toRadians(42) as float
		def axis = new Vector3(1, 2, 3)

		// note to self: glm prints matrices in column-major format
		def expected = new Matrix4(
			 0.761492, -0.499804,  0.412706, 0,
			 0.573192,  0.816532, -0.068752, 0,
			-0.302625,  0.288913,  0.908266, 0,
			        0,         0,         0, 1
		)

		expect:
		that Transformations.rotate(angle, axis), closeTo(expected)
	}

	def "3-D rotation around all 3 axes"()
	{
		given:
		def angles = new Vector3(42, 37, 99)
		def expected = Transformations.rotateX(angles.x)
				.multiply(Transformations.rotateY(angles.y))
				.multiply(Transformations.rotateZ(angles.z))

		expect:
		that Transformations.rotate(angles), closeTo(expected)
	}

	def "3-D translation"()
	{
		given:
		def offset = new Vector3(2, 4, 8)
		def expected = new Matrix4(
			1, 0, 0, 2,
			0, 1, 0, 4,
			0, 0, 1, 8,
			0, 0, 0, 1
		)

		def inVec = new Vector4(1, 2, 3, 1)
		def outVec = new Vector4(3, 6, 11, 1)

		expect:
		that Transformations.translate(offset), closeTo(expected)
		that Transformations.translate(offset).transform(inVec), closeTo(outVec)
	}
}
