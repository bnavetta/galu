package galu.matrix

import galu.vector.Vector3
import spock.lang.Specification

import java.nio.FloatBuffer

import static spock.util.matcher.HamcrestSupport.that
import static spock.util.matcher.HamcrestMatchers.*

class Matrix3Spec extends Specification
{
	def "row count is 3"()
	{
		expect:
			new Matrix3(1, 2, 3, 4, 5, 6, 7, 8, 9).rowCount() == 3
	}

	def "column count is 3"()
	{
		expect:
			new Matrix3(1, 2, 3, 4, 5, 6, 7, 8, 9).columnCount() == 3
	}

	def "inverse of 3x3 matrix"()
	{
		given:
		    def matrix = new Matrix3(3, 6, 12, 2.4, 12.2, 532.2, 1.6, 64.2, 13.54)
			def det = 95477.292
		expect:
			assertClose(
			    matrix.inverse(),
				new Matrix3(
					34002.052/det as float, -689.16/det as float, -3046.8/det as float,
					-819.024/det as float, -21.42/det as float, 1567.8/det as float,
					-134.56/det as float, 183.0/det as float, -22.12/det as float
				)
			)
	}

	// TODO: test for non-invertible matrix

	def "determinant of a 3x3 matrix"()
	{
		given:
			def matrix = new Matrix3(3, 6, 12, 2.4, 12.2, 532.2, 1.6, 64.2, 13.54)
		expect:
			that matrix.determinant(), closeTo(-95477.29200000002, 0.1)
	}

	def "negate a 3x3 matrix"()
	{
		given:
			def matrix = new Matrix3(1, -5, 2, 0.12, -7.221, 1001.01, -345.21, 0.00000123, 3.1415)
			def negated = new Matrix3(-1, 5, -2, -0.12, 7.221, -1001.01, 345.21, -0.00000123, -3.1415)
		expect:
			assertClose(matrix.negate(), negated)
	}

	def "transpose of a 3x3 matrix"()
	{
		given:
		    def matrix = new Matrix3(1, 2, 3, 4, 5, 6, 7, 8,  9)
			def transpose = new Matrix3(1, 4, 7, 2, 5, 8, 3, 6, 9)
		expect:
			assertClose(matrix.transpose(), transpose)
	}

	def "3x3 matrix addition"()
	{
		given:
			def a   = new Matrix3(2, 4, 8,  16, 32, 64, 128, 256, 512)
			def b   = new Matrix3(1, 2, 3,  4,  5,  6,  7,   8,   9)
			def sum = new Matrix3(3, 6, 11, 20, 37, 70, 135, 264, 521)
		expect:
			assertClose(a.add(b), sum)
	}

	def "3x3 matrix subtraction"()
	{
		given:
			def a = new Matrix3(2, 2345.234, 1234.1234, -3.21, 452.72, 1.34664, -7.555, 2.3522, 0.0001234)
			def b = new Matrix3(2, 2345.234, 1234.1234, -3.21, 452.72, 1.34664, -7.555, 2.3522, 0.0001234)
			def difference = new Matrix3(0, 0, 0, 0, 0, 0, 0, 0, 0)
		expect:
			assertClose(a.subtract(b), difference)
	}

	def "3x3 matrix multiplication"()
	{
		given:
			def a = new Matrix3(1, 6.66, 123.213, 3.34, 135.3, 234.23, 7.22, 42, 21)
			def b = new Matrix3(1, 7, 3.4, 8, 111.2, 34.343, 22, 64, 48.96)
			def product = new Matrix3(2764.966, 8633.224, 6264.633, 6238.8, 30059.46, 16125.865, 805.22, 6064.94, 2495.114)
		expect:
			assertClose(a.multiply(b), product)
	}

	def "3x3 matrix division"()
	{
		given:
			def a = new Matrix3(1, 4, 6, 56, 77, 32, 44, 23, 92)
		    def b = new Matrix3(2384/22356, -774/22356, -2818/22356, 94/22356, 504/22356, -158/22356, -2220/22356, 702/22356, 3018/22356)
			def quotient = new Matrix3(403, 196, 431, 6015, 4194, 6073, 8157, 1854, 8395)
		expect:
			assertClose(a.divide(b), quotient)
	}

	def "matrix-scalar multiplication"()
	{
		given:
			def matrix = new Matrix3(1, 2, 3, 4, 5, 6, 7, 8, 9)
			def scalar = 2
			def product = new Matrix3(2, 4, 6, 8, 10, 12, 14, 16, 18)
		expect:
			assertClose(matrix.multiply(scalar), product)
	}

	def "element-wise multiplication"()
	{
		given:
			def a = new Matrix3(1, 2, 3, 4, 5, 6, 7, 8, 9)
			def b = new Matrix3(1, 2, 3, 4, 5, 6, 7, 8, 9)
			def product = new Matrix3(1, 4, 9, 16, 25, 36, 49, 64, 81)
		expect:
			assertClose(a.elementMultiply(b), product)
	}

	def "element-wise division"()
	{
		given:
			def a = new Matrix3(1, 2, 3, 4, 5, 6, 7, 8, 9)
			def b = new Matrix3(1, 2, 3, 4, 5, 6, 7 ,8, 9)
			def quotient = new Matrix3(1, 1, 1, 1, 1, 1, 1, 1, 1)
		expect:
			assertClose(a.elementDivide(b), quotient)
	}

	def "transform vector3"()
	{
		given:
			def matrix = new Matrix3(2, 5.5, 7.09, 4.443, 2.1123, 9.676, 6.798, 42, 13)
			def vector = new Vector3(3.752, 23.43, 16)
			def product = new Vector3(249.809, 220.977325, 1217.566096)
		expect:
			assertClose(matrix.transform(vector), product)
	}

	def "row-major storage"()
	{
		given:
		    def matrix = new Matrix3(1, 2, 3, 4, 5, 6, 7, 8, 9)
		    def buffer = FloatBuffer.allocate(9)
			def array = new float[9]
		when:
			matrix.store(buffer, Matrix.Order.ROW_MAJOR)
			buffer.flip()
			matrix.store(array, Matrix.Order.ROW_MAJOR)
		then:
		    Arrays.equals(array, [1, 2, 3, 4, 5, 6, 7, 8, 9] as float[])
			9.times {
				buffer.get() == it + 1
			}
	}

	def "column-major storage"()
	{
		given:
			def matrix = new Matrix3(*(1..9))
			def buffer = FloatBuffer.allocate(9)
			def array = new float[9]
		when:
		    matrix.store(buffer, Matrix.Order.COLUMN_MAJOR)
			buffer.flip()
			matrix.store(array, Matrix.Order.COLUMN_MAJOR)
		then:
		    Arrays.equals(array, [1, 4, 7, 2, 5, 8, 3, 6, 9] as float[])
		    buffer == FloatBuffer.wrap([1, 4, 7, 2, 5, 8, 3, 6, 9] as float[])
	}

	def "store into too-small array"()
	{
		when:
			new Matrix3(*(1..9)).store(new float[5], Matrix.Order.COLUMN_MAJOR)
		then:
			thrown(IllegalArgumentException)
	}

	def "store into too-small buffer"()
	{
		when:
			new Matrix3(*(1..9)).store(FloatBuffer.allocate(5), Matrix.Order.COLUMN_MAJOR)
		then:
			thrown(IllegalArgumentException)
	}

	def "load row-major"()
	{
		when:
			def array = 1..9 as float[]
			def buffer = FloatBuffer.wrap(array)
			def expected = new Matrix3(*(1..9))
		then:
		    assertClose(Matrix3.load(array, Matrix.Order.ROW_MAJOR), expected)
			assertClose(Matrix3.load(buffer, Matrix.Order.ROW_MAJOR), expected)
	}

	def "load column-major"()
	{
		when:
			def array = [1, 4, 7, 2, 5, 8, 3, 6, 9] as float[]
			def buffer = FloatBuffer.wrap(array)
			def expected = new Matrix3(*(1..9))
		then:
		    assertClose(Matrix3.load(array, Matrix.Order.COLUMN_MAJOR), expected)
			assertClose(Matrix3.load(buffer, Matrix.Order.COLUMN_MAJOR), expected)
	}

	def "load from too-small buffer"()
	{
		when:
			Matrix3.load(FloatBuffer.allocate(5), Matrix.Order.COLUMN_MAJOR)
		then:
			thrown(IllegalArgumentException)
	}

	def "load from too-small array"()
	{
		when:
			Matrix3.load(new float[5], Matrix.Order.COLUMN_MAJOR)
		then:
			thrown(IllegalArgumentException)
	}

	def "3x3 matrix equality and hashCode"()
	{
		given:
			def matrix = new Matrix3(*(1..9))
			def same = new Matrix3(*(1..9))
			def different = new Matrix3(*(11..19))
		expect:
			matrix.equals(matrix) == true
			matrix.equals(null) == false
			matrix.equals("I'm a matrix") == false
			matrix.equals(same) == true
			matrix.equals(different) == false

			// first element same, first two elements same, etc.
			matrix.equals(new Matrix3(1, 3, 4, 5, 6, 7, 8, 9, 10)) == false
			matrix.equals(new Matrix3(1, 2, 4, 5, 6, 7, 8, 9, 10)) == false
			matrix.equals(new Matrix3(1, 2, 3, 5, 6, 7, 8, 9, 10)) == false
			matrix.equals(new Matrix3(1, 2, 3, 4, 6, 7, 8, 9, 10)) == false
			matrix.equals(new Matrix3(1, 2, 3, 4, 5, 7, 8, 9, 10)) == false
			matrix.equals(new Matrix3(1, 2, 3, 4, 5, 6, 8, 9, 10)) == false
			matrix.equals(new Matrix3(1, 2, 3, 4, 5, 6, 7, 9, 10)) == false
			matrix.equals(new Matrix3(1, 2, 3, 4, 5, 6, 7, 8, 10)) == false

			matrix.hashCode() == matrix.hashCode()
			matrix.hashCode() == same.hashCode()
			matrix.hashCode() != different.hashCode()
	}

	def "toString is properly formatted"()
	{
		expect:
			new Matrix3(*(1..9)).toString() == "[1.0000 2.0000 3.0000\n4.0000 5.0000 6.0000\n7.0000 8.0000 9.0000]"
	}

	private void assertClose(Matrix3 a, Matrix3 b, float delta = 0.1)
	{
		assert that(a.m00, closeTo(b.m00, delta))
		assert that(a.m01, closeTo(b.m01, delta))
		assert that(a.m02, closeTo(b.m02, delta))
		assert that(a.m10, closeTo(b.m10, delta))
		assert that(a.m11, closeTo(b.m11, delta))
		assert that(a.m12, closeTo(b.m12, delta))
		assert that(a.m20, closeTo(b.m20, delta))
		assert that(a.m21, closeTo(b.m21, delta))
		assert that(a.m22, closeTo(b.m22, delta))
	}

	private void assertClose(Vector3 a, Vector3 b, float delta = 0.1)
	{
		assert that(a.x, closeTo(b.x, delta))
		assert that(a.y, closeTo(b.y, delta))
		assert that(a.z, closeTo(b.z, delta))
	}
}
