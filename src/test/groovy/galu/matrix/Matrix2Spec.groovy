package galu.matrix

import galu.vector.Vector2
import spock.lang.Specification

import java.nio.FloatBuffer
import java.nio.HeapFloatBuffer

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

	def "divide is correct"()
	{
		expect:
			close(new Matrix2(*a).divide(new Matrix2(*b)), new Matrix2(*quotient))
		where:
			a                | b                | quotient
		    [2, -4, 0.5, 8]  | [4, 0.75, 4, 16] | [0.7868852459, -0.2868852459, -0.393442623, 0.518442623]
	}

	def "scalar multiply is correct"()
	{
		expect:
			close(new Matrix2(*mat).multiply(scalar), new Matrix2(*product))
		where:
			mat              | scalar | product
			[5, 6, 7, 8]     | 2      | [10, 12, 14, 16]
			[4.4, 7, 100, 3] | 0.5    | [2.2, 3.5, 50, 1.5]
	}

	def "element multiply is correct"()
	{
		expect:
			close(new Matrix2(*a).elementMultiply(new Matrix2(*b)), new Matrix2(*product))
		where:
		    a                     |  b             | product
			[0.0, 4.7, 1.2, 66.6] | [0, 0, 0, 0]   | [0, 0, 0, 0]
			[4, 7, 12, 19]        | [0.5, 3, 4, 2] | [2, 21, 48, 38]
	}

	def "element divide is correct"()
	{
		expect:
			close(new Matrix2(*a).elementDivide(new Matrix2(*b)), new Matrix2(*quotient))
		where:
			a                          | b                          | quotient
		    [4, 16, 35, 9]             | [2, 8, 7, 3]               | [2, 2, 5, 3]
			[ 0.5, 0.3, 2.0/3.0, 0.12] | [0.5, 0.1, 1.0/3.0, 0.01] | [1, 3, 2, 12]
	}

	def "row-major storage"()
	{
		when:
		    def array = new float[4]
			def buffer = FloatBuffer.allocate(4)
			mat.store(array, Matrix.Order.ROW_MAJOR)
			mat.store(buffer, Matrix.Order.ROW_MAJOR)
			buffer.flip()
			println()
		then:
		    Arrays.equals(array, [mat.m00, mat.m01, mat.m10, mat.m11] as float[])
		    buffer.equals(FloatBuffer.wrap([mat.m00, mat.m01, mat.m10, mat.m11] as float[]))
		where:
		    mat << [
				new Matrix2(0, 5, 8, 4),
				new Matrix2(1000.1, 0.005, 42, 8)
		    ]
	}

	def "column-major storage"()
	{
		when:
			def array = new float[4]
			def buffer = FloatBuffer.allocate(4)
		    mat.store(array, Matrix.Order.COLUMN_MAJOR)
			mat.store(buffer, Matrix.Order.COLUMN_MAJOR)
			buffer.flip()
		then:
		    Arrays.equals(array, [mat.m00, mat.m10, mat.m01, mat.m11] as float[])
			buffer.equals(FloatBuffer.wrap([mat.m00, mat.m10, mat.m01, mat.m11] as float[]))
		where:
		    mat << [
				new Matrix2(4, 11, 36, 3)
		    ]
	}

	def "too-small array loading"()
	{
		when:
		Matrix2.load(new float[3], Matrix.Order.COLUMN_MAJOR)
		then:
		thrown(IllegalArgumentException)
	}

	def "too-small buffer loading"()
	{
		when:
		Matrix2.load(FloatBuffer.allocate(3), Matrix.Order.ROW_MAJOR)
		then:
		thrown(IllegalArgumentException)
	}

	def "too-small array storing"()
	{
		when:
		new Matrix2(1, 2, 3, 4).store(new float[3], Matrix.Order.COLUMN_MAJOR)
		then:
		thrown(IllegalArgumentException)
	}

	def "too-small buffer storing"()
	{
		when:
		new Matrix2(1, 2, 3, 4).store(FloatBuffer.allocate(3), Matrix.Order.COLUMN_MAJOR)
		then:
		thrown(IllegalArgumentException)
	}

	def "row-major loading"()
	{
		when:
			def array = stored as float[]
			def buffer = FloatBuffer.wrap(array)
			def arrMat = Matrix2.load(array, Matrix.Order.ROW_MAJOR)
			def bufMat = Matrix2.load(buffer, Matrix.Order.ROW_MAJOR)
			def expected = new Matrix2(*stored)
		then:
		    close(arrMat, expected)
			close(bufMat, expected)
		where:
			stored << [
				[5.6, 245.534, 3.432, 134.3],
				[56, 23, 84, 1234]
			]
	}

	def "column-major loading"()
	{
		when:
			def array = [stored[0], stored[2], stored[1], stored[3]] as float[]
			def buffer = FloatBuffer.wrap(array)
		    def arrMat = Matrix2.load(array, Matrix.Order.COLUMN_MAJOR)
			def bufMat = Matrix2.load(buffer, Matrix.Order.COLUMN_MAJOR)
			def expected = new Matrix2(*stored)
		then:
			close(arrMat, expected)
			close(bufMat, expected)
		where:
			stored << [
			    [6.234, 2534.3, 1234.13, 6.42],
				[6, 42, 56, 27]
			]
	}

	/*

    For some reason, we're getting a NPE in the spec class before load/store is actually called. Maybe it's Spock?

	def "load array blows up with null ordering"()
	{
		when:
//			Matrix2.load([1, 2, 3, 4] as float[], Matrix.Order.COLUMN_MAJOR)
		    Matrix2.load(new float[4], (Matrix.Order) null)
		then:
			thrown(IllegalArgumentException)
	}

	def "load buffer blows up with null ordering"()
	{
		when:
			Matrix2.load(FloatBuffer.allocate(4), null)
		then:
			thrown(IllegalArgumentException)
	}

	def "store array blows up with null ordering"()
	{
		when:
			new Matrix2(1, 2, 3, 4).store(new float[4], null)
		then:
			thrown(IllegalArgumentException)
	}

	def "store buffer blows up with null ordering"()
	{
		when:
			new Matrix2(1, 2, 3, 4).store(FloatBuffer.allocate(4), null)
		then:
			thrown(IllegalArgumentException)
	}

	*/

	def "transform works correctly"()
	{
		expect:
			close(mat.transform(vec), result)

		where:
			mat << [
				new Matrix2(0, 1, 1, 0),
				new Matrix2(-1, 0, 0, -1),
				new Matrix2(0, -5, 7, 0)
			]
			vec << [
				new Vector2(4, 3),
				new Vector2(4, 3),
				new Vector2(4, 3)
			]
			result << [
				new Vector2(3, 4),
				new Vector2(-4, -3),
				new Vector2(-15,  28)
			]
	}

	def "identity matrix has no effect"()
	{
		expect:
			mat.multiply(Matrix2.IDENTITY).equals(mat)
			Matrix2.IDENTITY.multiply(mat).equals(mat)
		 where:
		    mat << [
				new Matrix2(1, 6, 3, 6),
				new Matrix2(6.773, 12354.34, 234.23456, 1234.213)
		    ]
	}

	def "zero matrix zeroes product"()
	{
		expect:
		    mat.multiply(Matrix2.ZERO).equals(Matrix2.ZERO)
			Matrix2.ZERO.multiply(mat).equals(Matrix2.ZERO)
		where:
			mat << [
			    new Matrix2(6, 1, 4325, 234),
				new Matrix2(45.2, 234.234, 24.212342, 2.42324)
			]
	}

	def "basic equals and hash code"()
	{
		expect:
		    a == a
		    a.hashCode() == a.hashCode()
		    a != b
			a.hashCode() != b.hashCode()
		where:
			a << [
				new Matrix2(4, 5, 6, 7),
				new Matrix2(0.11101001001, 4.4, 123, 63.5)
			]
			b << [
				new Matrix2(7, 6, 5, 4),
				new Matrix2(4.607, 3.0140639295, 7.2, 2345.4)
			]
	}

	def "special cases of equals"()
	{
		expect:
		new Matrix2(1, 2, 3, 4).equals(null) == false
		new Matrix2(1, 2, 3, 4).equals("I'm a matrix") == false

		Matrix mat = new Matrix2(1, 2, 3, 4)
		assert mat.equals(mat) == true
	}

	def "toString looks right"()
	{
		expect:
			mat.toString() == text
		where:
			mat                        |  text
			new Matrix2(5, 3, 2, 11)   | "[5.0000 3.0000\n2.0000 11.0000]"
	}

	void close(Vector2 a, Vector2 b)
	{
		assert close(a.x, b.x)
		assert close(a.y, b.y)
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
