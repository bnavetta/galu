package galu.vector

import spock.lang.Specification

import java.nio.FloatBuffer

import static spock.util.matcher.HamcrestSupport.expect
import static spock.util.matcher.HamcrestSupport.that
import static spock.util.matcher.HamcrestMatchers.closeTo
import static galu.test.Helpers.*

class Vector3Spec extends Specification
{
	def "size of vector"()
	{
		expect:
		new Vector3(0, 0, 0).size() == 3
	}

	def "element access by index"()
	{
		given:
		def vector = new Vector3(1, 2, 3)

		expect:
		vector.get(0) == 1
		vector.get(1) == 2
		vector.get(2) == 3
	}

	def "access invalid index"()
	{
		when:
		new Vector3(1, 2, 3).get(-1)

		then:
		thrown(IndexOutOfBoundsException)
	}

	def "equals and hashCode"()
	{
		given:
		def vector = new Vector3(1, 2, 3)
		def same = new Vector3(1, 2, 3)

		expect:
		vector.equals(vector) == true
		vector.equals(same) == true
		vector.equals(null) == false
		vector.equals("I'm a vector") == false

		vector.equals(new Vector3(0, 1, 2)) == false
		vector.equals(new Vector3(1, 5, 6)) == false
		vector.equals(new Vector3(1, 2, 6)) == false

		vector.hashCode() == vector.hashCode()
		vector.hashCode() == same.hashCode()
		vector.hashCode() != new Vector3(7, 8, 9).hashCode()
	}

	def "length of vector"()
	{
		given:
		def vector = new Vector3(1, 2, 3)
		def length = Math.sqrt(14)

		expect:
		that vector.length(), closeTo(length, 0.01)
	}

	def "square of length of vector"()
	{
		given:
		def vector = new Vector3(1, 2, 3)
		def lengthSquared = 14

		expect:
		that vector.lengthSquared(), closeTo(lengthSquared, 0.01)
	}

	def "angle between two vectors"()
	{
		given:
		def a = new Vector3(1, 2, 3)
		def b = new Vector3(7, 15, 4)
		def angle = Math.acos(7.0 * Math.sqrt(7.0/145.0) * 0.5) // what Wolfram|Alpha said

		expect:
		that a.angleBetween(b), closeTo(angle, 0.01)
	}

	def "normalized vector"()
	{
		given:
		def vector = new Vector3(1, 2, 3)
		def normalized = new Vector3(1.0 / Math.sqrt(14) as float, Math.sqrt(2.0/7.0) as float, 3.0 / Math.sqrt(14.0) as float)

		expect:
		that vector.normalize(), closeTo(normalized)
	}

	def "vector add"()
	{
		given:
		def a = new Vector3(1, 2, 3)
		def b = new Vector3(5, 6, 7)
		def sum = new Vector3(6, 8, 10)

		expect:
		that a.add(b), closeTo(sum)
	}

	def "vector subtract"()
	{
		given:
		def a = new Vector3(1, 2, 3)
		def b = new Vector3(5, 6, 7)
		def difference = new Vector3(-4, -4, -4)

		expect:
		that a.subtract(b), closeTo(difference)
	}

	def "vector dot product"()
	{
		given:
		def a = new Vector3(1, 2, 3)
		def b = new Vector3(4, 5, 6)
		def product = 32

		expect:
		that a.dot(b), closeTo(product, 0.01)
	}

	def "vector negate"()
	{
		given:
		def vector = new Vector3(1, 2, 3)
		def negated = new Vector3(-1, -2, -3)

		expect:
		that vector.negate(), closeTo(negated)
	}

	def "vector-scalar multiplication"()
	{
		given:
		def vector = new Vector3(1, 2, 3)
		def scalar = 3
		def product = new Vector3(3, 6, 9)

		expect:
		that vector.multiply(scalar), closeTo(product)
	}

	def "element-wise vector multiplication"()
	{
		given:
		def a = new Vector3(1, 2, 3)
		def b = new Vector3(4, 5, 6)
		def product = new Vector3(4, 10, 18)

		expect:
		that a.multiply(b), closeTo(product)
	}

	def "vector storage"()
	{
		given:
		def vector = new Vector3(1, 2, 3)
		def array = new float[3]
		def buffer = FloatBuffer.allocate(3)

		when:
		vector.store(array)
		vector.store(buffer)
		buffer.flip()

		then:
		Arrays.equals(array, [1, 2, 3] as float[])
		buffer == FloatBuffer.wrap([1, 2, 3] as float[])
	}

	def "store into too-small array"()
	{
		when:
		new Vector3(1, 2, 3).store(new float[2])

		then:
		thrown(IllegalArgumentException)
	}

	def "store into too-small buffer"()
	{
		when:
		new Vector3(1, 2, 3).store(FloatBuffer.allocate(2))

		then:
		thrown(IllegalArgumentException)
	}

	def "vector to array"()
	{
		expect:
		Arrays.equals(new Vector3(1, 2, 3).toArray(), [1, 2, 3] as float[])
	}

	def "load vector"()
	{
		given:
		def array = [1, 2, 3] as float[]
		def buffer = FloatBuffer.wrap(array)
		def expected = new Vector3(1, 2, 3)

		expect:
		that Vector3.load(array), closeTo(expected)
		that Vector3.load(buffer), closeTo(expected)
	}

	def "load from too-small array"()
	{
		when:
		Vector3.load(new float[2])

		then:
		thrown(IllegalArgumentException)
	}

	def "load from too-small buffer"()
	{
		when:
		Vector3.load(FloatBuffer.allocate(2))

		then:
		thrown(IllegalArgumentException)
	}

	def "toString is properly formatted"()
	{
		expect:
		new Vector3(1, 2, 3).toString() == "(1.0000, 2.0000, 3.0000)"
	}
}
