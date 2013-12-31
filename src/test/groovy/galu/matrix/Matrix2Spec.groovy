package galu.matrix

import spock.lang.Specification

class Matrix2Spec extends Specification
{
	def "row count is 2"()
	{
		expect:
			new Matrix2(1, 2, 3, 4).rowCount() == 2
	}

	def "column count is 2"()
	{
		expect:
			new Matrix2(1, 2, 3, 4).columnCount() == 2
	}

	def "determinant is correct"()
	{
		expect:
			assert close(new Matrix2(a, b, c, d).determinant(), det)
		where:
		    a  |  b  |  c  |  d  |  det
		     0 |  -4 |  -6 |  -2 | -24
		    -6 |   0 |   6 |  -6 |  36
		     2 |  -2 |   7 |  -7 |   0
		     5 |   3 |   6 |   6 |  12
	}

	def "inverse is correct"()
	{
		expect:
			close(new Matrix2(*orig).inverse(), new Matrix2(*inverse))
		where:
		    orig              |    inverse
		    [11, -5, 2, -1]   | [1, -5, 2, -11]
			[0, -2, -1, -9]   | [4.5, -1, -0.5, 0]
			[1, -1, -6, -3]   | [1.0/3.0, -1.0/9.0, -2.0/3.0, -1.0/9.0]
	}

	def "negate is correct"()
	{
		expect:
			close(new Matrix2(*original).negate(), new Matrix2(*negated))
		where:
			original            |    negated
		    [1, 0.5, -6, 11]    | [-1, -0.5, 6, -11]
			[-0.3, -5, 13, 5]   | [0.3, 5, -13, -5]
	}

	def "transpose is correct"()
	{
		expect:
			close(new Matrix2(*original).transpose(), new Matrix2(*transpose))
		where:
			original        |  transpose
			[1, 2, 3, 4]    | [1, 3, 2, 4]
			[0.5, 7, -6, 4] | [0.5, -6, 7, 4]
	}

	def "add is correct"()
	{
		expect:
			close(new Matrix2(*a).add(new Matrix2(*b)), new Matrix2(*sum))
		where:
		    a                  |  b                       | sum
			[1, 2, 3, 4]       | [5, 6, 7, 8]             | [6, 8, 10, 12]
			[0.5, -6, 4.4, 11] | [-6.7, 100, 4.9, 0.0001] | [-6.2, 94, 9.3, 11.0001]
	}

	def "subtract is correct"()
	{
		expect:
			close(new Matrix2(*a).subtract(new Matrix2(*b)), new Matrix2(*difference))
		where:
			a                   |  b             | difference
			[1, 2, 3, 4]        | [1, 2, 3, 4]   | [0, 0, 0, 0]
			[0.33, 1.5, 5.5, 6] | [5, 7, 6, 8]   | [-4.67, -5.5, -0.5, -2]
	}

	def "multiply is correct"()
	{
		expect:
			close(new Matrix2(*a).multiply(new Matrix2(*b)), new Matrix2(*product))
		where:
			a               | b              | product
			[0, 2, -2, -5]  | [6, -6, 3, 0]  | [6, 0, -27, 12]
		    [-3, 5, -2, 1]  | [6, -2, 1, -5] | [-13, -19, -11, -1]
		    [-5, -5, -1, 2] | [-2, -3, 3, 5] | [-5, -10, 8, 13]
	}

	void close(Matrix2 a, Matrix2 b)
	{
		assert close(a.m00, b.m00)
		assert close(a.m01, b.m01)
		assert close(a.m10, b.m10)
		assert close(a.m11, b.m11)
	}

	boolean close(float a, float b)
	{
		return Math.abs(a - b) < 0.0001
	}
}
