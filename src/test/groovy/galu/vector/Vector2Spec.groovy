package galu.vector

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
			//new Vector2
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
			7.6	|	2.2	||	62.6
	}
	
	def "angle between"()
	{
		expect:
			that new Vector2(0, 1).angleBetween(new Vector2(1, 0)), closeTo(Math.PI/2, 0.01f)
	}
}
