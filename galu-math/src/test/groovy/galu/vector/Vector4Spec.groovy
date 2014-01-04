package galu.vector

import spock.lang.Specification

import java.nio.FloatBuffer

import static spock.util.matcher.HamcrestSupport.that
import static spock.util.matcher.HamcrestMatchers.closeTo
import static galu.test.Helpers.*

public class Vector4Spec extends Specification
{
	def "size of vector"()
	{
		expect:
		new Vector4(1, 2, 3, 4).size() == 4
	}

	def "element access by index"()
	{
		given:
		def vector = new Vector4(1, 2, 3, 4)

		expect:
		vector.get(0) == 1
		vector.get(1) == 2
		vector.get(2) == 3
		vector.get(3) == 4
	}

	def "access invalid index"()
	{
		when:
		new Vector4(1, 2, 3, 4).get(1000)

		then:
		thrown(IndexOutOfBoundsException)
	}

	def "equals and hashCode"()
	{
		given:
		def vector = new Vector4(1, 2, 3, 4)
		def same = new Vector4(1, 2, 3, 4)

		expect:
		vector.equals(vector) == true
		vector.equals(same) == true
		vector.equals(null) == false
		vector.equals("I'm a vector") == false

		vector.equals(new Vector4(0, 7, 8, 9)) == false
		vector.equals(new Vector4(1, 7, 8, 9)) == false
		vector.equals(new Vector4(1, 2, 8, 9)) == false
		vector.equals(new Vector4(1, 2, 3, 9)) == false

		vector.hashCode() == vector.hashCode()
		vector.hashCode() == same.hashCode()
		vector.hashCode() != new Vector4(6, 7, 8, 9).hashCode()
	}

	def "length of vector"()
	{
		given:
		def vector = new Vector4(1, 2, 3, 4)
		def length = Math.sqrt(30)

		expect:
		that vector.length(), closeTo(length, 0.01)
	}

	def "square of length of vector"()
	{
		given:
		def vector = new Vector4(1, 2, 3, 4)
		def lengthSquared = 30

		expect:
		that vector.lengthSquared(), closeTo(lengthSquared, 0.01)
	}

	def "angle between two vectors"()
	{
		given:
		def a = new Vector4(1, 2, 3, 4)
		def b = new Vector4(5, 6, 7, 8)
		def angle = Math.acos(7 * Math.sqrt(5.0/29) / 3.0)

		expect:
		that a.angleBetween(b), closeTo(angle, 0.01)
	}

	def "normalized vector"()
	{
		given:
		def vector = new Vector4(1, 2, 3, 4)
		def normalized = new Vector4(1.0/Math.sqrt(30) as float, Math.sqrt(2.0/15) as float, Math.sqrt(3.0/10) as float, 2*Math.sqrt(2.0/15) as float)

		expect:
		that vector.normalize(), closeTo(normalized)
	}

	def "vector addition"()
	{
		given:
		def a = new Vector4(1, 2, 3, 4)
		def b = new Vector4(5, 6, 7, 8)
		def sum = new Vector4(6, 8, 10, 12)

		expect:
		that a.add(b), closeTo(sum)
	}

	def "vector subtraction"()
	{
		given:
		def a = new Vector4(1, 2, 3, 4)
		def b = new Vector4(5, 6, 7, 8)
		def difference = new Vector4(-4, -4, -4, -4)

		expect:
		that a.subtract(b), closeTo(difference)
	}

	def "vector dot product"()
	{
		given:
		def a = new Vector4(1, 2, 3, 4)
		def b = new Vector4(5, 6, 7, 8)
		def product = 70

		expect:
		that a.dot(b), closeTo(product, 0.01)
	}

	def "vector negate"()
	{
		given:
		def vector = new Vector4(1, 2, 3, 4)
		def negated = new Vector4(-1, -2, -3, -4)

		expect:
		that vector.negate(), closeTo(negated)
	}

	def "vector-scalar multiplication"()
	{
		given:
		def vector = new Vector4(1, 2, 3, 4)
		def scalar = 4
		def product = new Vector4(4, 8, 12, 16)

		expect:
		that vector.multiply(scalar), closeTo(product)
	}

	def "element-wise vector multiplication"()
	{
		given:
		def a = new Vector4(1, 2, 3, 4)
		def b = new Vector4(5, 6, 7, 8)
		def product = new Vector4(5, 12, 21, 32)

		expect:
		that a.multiply(b), closeTo(product)
	}

	def "vector storage"()
	{
		given:
		def vector = new Vector4(1, 2, 3, 4)
		def array = new float[4]
		def buffer = FloatBuffer.allocate(4)

		when:
		vector.store(array)
		vector.store(buffer)
		buffer.flip()

		then:
		Arrays.equals(array, [1, 2, 3, 4] as float[])
		buffer == FloatBuffer.wrap([1, 2, 3, 4] as float[])
	}

	def "store into too-small array"()
	{
		when:
		new Vector4(1, 2, 3, 4).store(new float[2])

		then:
		thrown(IllegalArgumentException)
	}

	def "store into too-small buffer"()
	{
		when:
		new Vector4(1, 2, 3, 4).store(FloatBuffer.allocate(2))

		then:
		thrown(IllegalArgumentException)
	}

	def "vector to array"()
	{
		expect:
		Arrays.equals(new Vector4(1, 2, 3, 4).toArray(), [1, 2, 3, 4] as float[])
	}

	def "load vector"()
	{
		given:
		def array = [1, 2, 3, 4] as float[]
		def buffer =  FloatBuffer.wrap(array)
		def expected = new Vector4(1, 2, 3, 4)

		expect:
		that Vector4.load(array), closeTo(expected)
		that Vector4.load(buffer), closeTo(expected)
	}

	def "load from too-small array"()
	{
		when:
		Vector4.load(new float[2])

		then:
		thrown(IllegalArgumentException)
	}

	def "load from too-small buffer"()
	{
		when:
		Vector4.load(FloatBuffer.allocate(2))

		then:
		thrown(IllegalArgumentException)
	}

	def "toString is properly formatted"()
	{
		expect:
		new Vector4(1, 2, 3, 4).toString() == "(1.0000, 2.0000, 3.0000, 4.0000)"
	}
}
