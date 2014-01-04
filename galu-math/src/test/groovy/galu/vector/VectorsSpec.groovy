package galu.vector

import spock.lang.Specification

import static spock.util.matcher.HamcrestSupport.that
import static spock.util.matcher.HamcrestMatchers.closeTo
import static galu.test.Helpers.*

class VectorsSpec extends Specification
{
	def "vector2 from floats"()
	{
		expect:
		that Vectors.vec2(1, 2), closeTo(new Vector2(1, 2))
	}

	def "vector2 from float"()
	{
		expect:
		that Vectors.vec2(1), closeTo(new Vector2(1, 1))
	}

	def "vector3 from floats"()
	{
		expect:
		that Vectors.vec3(1, 2, 3), closeTo(new Vector3(1, 2, 3))
	}

	def "vector3 from float"()
	{
		expect:
		that Vectors.vec3(1), closeTo(new Vector3(1, 1, 1))
	}

	def "vector3 from vector2"()
	{
		expect:
		that Vectors.vec3(new Vector2(1, 2), 3), closeTo(new Vector3(1, 2, 3))
	}

	def "vector4 from floats"()
	{
		expect:
		that Vectors.vec4(1, 2, 3, 4), closeTo(new Vector4(1, 2, 3, 4))
	}

	def "vector4 from float"()
	{
		expect:
		that Vectors.vec4(1), closeTo(new Vector4(1, 1, 1, 1))
	}

	def "vector4 from vector2"()
	{
		expect:
		that Vectors.vec4(new Vector2(1, 2), 3, 4), closeTo(new Vector4(1, 2, 3, 4))
	}

	def "vector4 from vector3"()
	{
		expect:
		that Vectors.vec4(new Vector3(1, 2, 3), 4), closeTo(new Vector4(1, 2, 3, 4))
	}
}
