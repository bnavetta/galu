package galu.vector

import galu.matrix.Matrix2

import java.nio.FloatBuffer

import static spock.util.matcher.HamcrestSupport.expect
import static spock.util.matcher.HamcrestSupport.that
import static spock.util.matcher.HamcrestMatchers.*

import spock.lang.Specification

class Vector2Spec extends Specification
{
	def "size of vector"()
	{
		expect:
			new Vector2(0, 0).size() == 2
	}
	
	def "element access by index"()
	{
		expect:
			new Vector2(x, y).get(i) == v
		where:
			x	|	y	||	i	||	v
			3	|	2	||	0	||	3
			3	|	2	||	1	||	2
		
	}
	
	def "element access out of bounds"()
	{
		when:
		new Vector2(1, 2).get(42)
		then:
			def e = thrown(IndexOutOfBoundsException)
			e.message == "Index 42 exceeds bounds of Vector2"
	}
	
	def "vector hashCode"()
	{
		expect:
			new Vector2(x, y).hashCode() == new Vector2(x, y).hashCode()
		where:
			x	|	y
			5	|	7
			3.14|	4.4
			2.2	|	7
	}
	
	def "vector equals"()
	{
		expect:
			a.equals(b) == result
		where:
			a						|			b				|	result
			new Vector2(5, 5)		|	new Vector2(5, 5)		|	true
			new Vector2(3.2, 3.2)	|	new Vector2(3.2, 3.2)	|	true
			new Vector2(7, 6)		| 	new Vector2(100, 77)	|	false
			new Vector2(1, 2)		| 	null					|	false
			new Vector2(1, 2)       |   new Vector2(1, 3)       |   false
			new Vector2(1, 2)       |   new Vector2(0, 1)       |   false
	}

	def "special cases of equals"()
	{
		expect:
			def vec = new Vector2(0.0, 1.0)
		    vec.equals(null) == false
			vec.equals(vec) == true
			vec.equals("I'm a vector") == false

	}
	
	def "length of vector"()
	{
		expect:
			that new Vector2(x, y).length(), closeTo(length, 0.01f)
		where:
			x	|	y	||	length
			6	|	8	||	10.0f
			2	|	7	||	Math.sqrt(53)
	}
	
	def "square of length of vector"()
	{
		expect:
			that new Vector2(x, y).lengthSquared(), closeTo(lengthSquared, 0.01f)
		where:
			x	|	y	||	lengthSquared
			4	|	3	||	25
		    7.6 |   2.2 ||  62.6
		    3   |   1   ||  10
	}
	
	def "angle between"()
	{
		expect:
			that new Vector2(0, 1).angleBetween(new Vector2(1, 0)), closeTo(Math.PI/2, 0.01f)
	}

	def "normalized vector"()
	{
		expect:
			close(new Vector2(x, y).normalize(), new Vector2(a, b))
		where:
			x   |  y    ||        a                        |             b
			1   |  2    || (1.0 / Math.sqrt(5.0)) as float | (2.0 / Math.sqrt(5.0)) as float
		    1.3 |  7.23 || 0.176968                        | 0.984217
	}

	def "vector add"()
	{
		expect:
			close(new Vector2(*a).add(new Vector2(*b)), new Vector2(*sum))
		where:
		    a            |       b       ||       sum
		    [0.01, 1.0]  |  [0.1, 5.7]   ||  [0.11, 6.7]
			[5.2, 234]   |  [6.23, 25]   ||  [11.43, 259]
	}

	def "vector subtract"()
	{
		expect:
			close(new Vector2(*a).subtract(new Vector2(*b)), new Vector2(*difference))
		where:
		    a    |          b       ||    difference
		[1, 5]   |      [3, 1]      ||  [-2, 4]
		[5.31, 23.3] | [57.8, 1.4]  ||  [-52.49, 21.9]
	}

	def "vector dot product"()
	{
		expect:
			that new Vector2(*a).dot(new Vector2(*b)), closeTo(product, 0.0001)
		where:
			a    |    b    ||    product
		[4, 6]   | [5, 2]  ||     32
		[5, 11]  | [2, -1] ||    -1
	}

	def "vector negate"()
	{
		expect:
			close(new Vector2(x, y).negate(), new Vector2(a,  b))
		where:
		    x    |    y    ||    a    |    b
		   12    |    4    ||   -12   |   -4
		  3.1343 | 23.2332 || -3.1343 | -23.2332
	}

	def "scalar multiply"()
	{
		expect:
			close(new Vector2(x, y).multiply(s), new Vector2(a, b))
		where:
		    x    |    y    |    s    ||    a    |    b
		    2    |    6    |    8    ||   16    |   48
		    3    |    9    |    0.5  ||   1.5   |   4.5
	}

	def "vector multiply"()
	{
		expect:
			close(new Vector2(x, y).multiply(new Vector2(a, b)), new Vector2(c, d))
		where:
		    x    |    y    |    a    |    b    ||    c    |    d
		    4    |    3    |    2    |    0    ||    8    |    0
		   1.6   |   0.5   |    3    |   7.9   ||   4.8   |   3.95
	}

	def "store into FloatBuffer"()
	{
		given:
			def buffer = FloatBuffer.allocate(2)
		when:
			new Vector2(1, 2).store(buffer)
			buffer.flip()
		then:
			that(buffer.get(), closeTo(1.0, 0.001))
			that(buffer.get(), closeTo(2.0, 0.001))
	}

	def "store into array"()
	{
		given:
			def array = new float[2]
		when:
			new Vector2(1, 2).store(array)
		then:
			that(array[0], closeTo(1.0, 0.001))
			that(array[1], closeTo(2.0, 0.001))
	}

	def "store into too-small array"()
	{
		when:
		new Vector2(1, 2).store(new float[1])

		then:
		thrown(IllegalArgumentException)
	}

	def "store into too-small buffer"()
	{
		when:
		new Vector2(1, 2).store(FloatBuffer.allocate(1))

		then:
		thrown(IllegalArgumentException)
	}

	def "convert to array"()
	{
		expect:
			Arrays.equals(new Vector2(1, 2).toArray(), [1.0, 2.0] as float[])
	}

	def "load from buffer"()
	{
		given:
			def buffer = FloatBuffer.allocate(2)
			buffer.put(0, 1.0).put(1, 2.0)
		expect:
		    Vector2 vec = Vector2.load(buffer)
			that vec.x, closeTo(1.0, 0.001)
			that vec.y, closeTo(2.0, 0.001)
	}

	def "load from array"()
	{
		given:
			def array = [1.0, 2.0] as float[]
		expect:
			Vector2 vec = Vector2.load(array)
			that vec.x, closeTo(1.0, 0.001)
			that vec.y, closeTo(2.0, 0.001)
	}

	def "load from too-small array"()
	{
		when:
		Vector2.load(new float[1])

		then:
		thrown(IllegalArgumentException)
	}

	def "load from too-small buffer"()
	{
		when:
		Vector2.load(FloatBuffer.allocate(1))

		then:
		thrown(IllegalArgumentException)
	}

	def "toString follows vector conventions"()
	{
		expect:
			new Vector2(3, 4).toString() == "(3.0000, 4.0000)"
	}

	void close(Vector2 a, Vector2 b)
	{
		assert that(a.x, closeTo(b.x, 0.0001))
		assert that(a.y, closeTo(b.y, 0.0001))
	}
}
