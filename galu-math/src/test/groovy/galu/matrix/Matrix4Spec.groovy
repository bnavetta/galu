package galu.matrix

import galu.vector.Vector4
import spock.lang.Specification

import java.nio.FloatBuffer

import static spock.util.matcher.HamcrestSupport.that
import static spock.util.matcher.HamcrestMatchers.*

class Matrix4Spec extends Specification
{
	def "row count is 4"()
	{
		expect:
		new Matrix4(*(1..16)).rowCount() == 4
	}

	def "column count is 4"()
	{
		expect:
		new Matrix4(*(1..16)).columnCount() == 4
	}
	def "4 x 4 matrix determinant"()
	{
		given:
			def matrix = new Matrix4(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
		expect:
			that matrix.determinant(), closeTo(0.0, 0.01)
	}

	def "4 x 4 matrix inverse"()
	{
		given:
			def matrix = new Matrix4(1, 6, 2, 7, 5, 9, 4, 3, 2, 8, 1, 6, 0, 5, 3, 1)
			def inverse = new Matrix4(0.025, 0.234, -0.097, -0.296, -0.234, -0.065, 0.280, 0.150, 0.305, 0.112, -0.433, 0.125, 0.252, -0.009, -0.103, -0.121)
		expect:
			assertClose(matrix.inverse(), inverse)
	}

	def "4 x 4 matrix not invertible"()
	{
		given:
			def matrix = new Matrix4(*(1..16))
		when:
			matrix.inverse()
		then:
			thrown(IllegalStateException)
	}

	def "4 x 4 matrix negation"()
	{
		given:
			def matrix = new Matrix4(*(1..16))
			def expected = new Matrix4(*(-1..-16))
		expect:
			assertClose(matrix.negate(), expected)
	}

	def "4 x 4 matrix transpose"()
	{
		given:
			def matrix = new Matrix4(
				 1,  2,  3,  4,
				 5,  6,  7,  8,
				 9, 10, 11, 12,
				13, 14, 15, 16
			)
			def expected = new Matrix4(
				1, 5,  9, 13,
				2, 6, 10, 14,
				3, 7, 11, 15,
				4, 8, 12, 16
			)
		expect:
			assertClose(matrix.transpose(), expected)
	}

	def "4 x 4 matrix addition"()
	{
		given:
			def a = new Matrix4(*(1..16))
			def b = new Matrix4(*(1..16))
			def sum = new Matrix4(2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30, 32)
		expect:
			assertClose(a.add(b), sum)
	}

	def "4 x 4 matrix subtraction"()
	{
		given:
			def a = new Matrix4(*(1..16))
			def b = new Matrix4(*(1..16))
			def difference = new Matrix4(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
		expect:
			assertClose(a.subtract(b), difference)
	}

	def "4 x 4 matrix multiplication"()
	{
		given:
			def a = new Matrix4(*(1..16))
			def b = new Matrix4(*(2..17))
			def product = new Matrix4(100, 110, 120, 130, 228, 254, 280, 306, 356, 398, 440, 482, 484, 542, 600, 658)
		expect:
			assertClose(a.multiply(b), product)
	}

	def "4 x 4 matrix division"()
	{
		given:
			def a = new Matrix4(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
//			def b = new Matrix4(0.175, -0.179, 0.383, -0.209, -0.407, 0.367, -0.455, 0.368, -0.051, 0.072, -0.297, 0.254, 0.329, -0.184, 0.353, -0.309)
			def b = new Matrix4(1, 3, 5, 7, 2, 8, 1, 9, 8, 4, 2, 1, 9, 3, 7, 0).inverse()
			def quotient = new Matrix4(65, 43, 41, 28, 145, 115, 101, 96, 225, 187, 161, 164, 305, 259, 221, 232)
		expect:
			assertClose(a.divide(b), quotient)
	}

	def "4 x 4 matrix-scalar multiplication"()
	{
		given:
			def matrix = new Matrix4(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
			def scalar = 0.5
			def product = new Matrix4(0.5, 1, 1.5, 2, 2.5, 3, 3.5, 4, 4.5, 5, 5.5, 6, 6.5, 7, 7.5, 8)
		expect:
			assertClose(matrix.multiply(scalar), product)
	}

	def "4 x 4 element-wise multiplication"()
	{
		given:
			def a = new Matrix4(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
			def b = new Matrix4(2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2)
			def product = new Matrix4(2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30, 32)
		expect:
			assertClose(a.elementMultiply(b), product)
	}

	def "4 x 4 element-wise division"()
	{
		given:
			def a = new Matrix4(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
			def b = new Matrix4(2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2)
			def quotient = new Matrix4(0.5, 1, 1.5, 2, 2.5, 3, 3.5, 4, 4.5, 5, 5.5, 6, 6.5, 7, 7.5, 8)
		expect:
			assertClose(a.elementDivide(b), quotient)
	}

	def "transform vector4"()
	{
		given:
			def matrix = new Matrix4(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
			def vector = new Vector4(1, 2, 3, 4)
		    def transformed = new Vector4(30, 70, 110, 150)
		expect:
			assertClose(matrix.transform(vector), transformed)
	}

	def "row-major storage"()
	{
		given:
			def matrix = new Matrix4(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
		    def array = new float[16]
			def buffer = FloatBuffer.allocate(16)
		when:
			matrix.store(array, Matrix.Order.ROW_MAJOR)
			matrix.store(buffer, Matrix.Order.ROW_MAJOR)
			buffer.flip()
		then:
		    16.times {
			    array[it] == it + 1
			    buffer.get() == it + 1
		    }
	}

	def "column-major storage"()
	{
		given:
			def matrix = new Matrix4(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
			def array = new float[16]
			def buffer = FloatBuffer.allocate(16)
		when:
			matrix.store(array, Matrix.Order.COLUMN_MAJOR)
			matrix.store(buffer, Matrix.Order.COLUMN_MAJOR)
			buffer.flip()
		then:
		    Arrays.equals(array, [1, 5, 9, 13, 2, 6, 10, 14, 3, 7, 11, 15, 4, 8, 12, 16] as float[])
			buffer.equals(FloatBuffer.wrap([1, 5, 9, 13, 2, 6, 10, 14, 3, 7, 11, 15, 4, 8, 12, 16] as float[]))
	}

	def "store into too-small array"()
	{
		when:
			new Matrix4(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0).store(new float[10], Matrix.Order.COLUMN_MAJOR)
		then:
			thrown(IllegalArgumentException)
	}

	def "store into too-small buffer"()
	{
		when:
			new Matrix4(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0).store(FloatBuffer.allocate(10), Matrix.Order.COLUMN_MAJOR)
		then:
			thrown(IllegalArgumentException)
	}

	def "load row-major"()
	{
		when:
			def array = 1..16 as float[]
		    def buffer = FloatBuffer.wrap(array)
			def expected = new Matrix4(*(1..16))
		then:
		    assertClose(Matrix4.load(array, Matrix.Order.ROW_MAJOR), expected)
			assertClose(Matrix4.load(buffer, Matrix.Order.ROW_MAJOR), expected)
	}

	def "load column-major"()
	{
		when:
			def array = [1, 5, 9, 13, 2, 6, 10, 14, 3, 7, 11, 15, 4, 8, 12, 16] as float[]
			def buffer = FloatBuffer.wrap(array)
			def expected = new Matrix4(*(1..16))
		then:
		    assertClose(Matrix4.load(array, Matrix.Order.COLUMN_MAJOR), expected)
			assertClose(Matrix4.load(buffer, Matrix.Order.COLUMN_MAJOR), expected)
	}

	def "load from too-small array"()
	{
		when:
			Matrix4.load(new float[10], Matrix.Order.COLUMN_MAJOR)
		then:
			thrown(IllegalArgumentException)
	}

	def "load from too-small buffer"()
	{
		when:
			Matrix4.load(FloatBuffer.allocate(10), Matrix.Order.COLUMN_MAJOR)
		then:
			thrown(IllegalArgumentException)
	}

	def "4 x 4 matrix equality and hashCode"()
	{
		given:
			def matrix = new Matrix4(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
			def same = new Matrix4(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
			def different = new Matrix4(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 42)
		expect:
			matrix.equals(matrix) == true
			matrix.equals(null) == false
			matrix.equals("I'm a matrix") == false
			matrix.equals(same) == true
			matrix.equals(different) == false

			// Check with first different element in position 0, 1, ...
		    16.times { goodIdx ->
			    def values = []
			    goodIdx.times {
				    values << it + 1
			    }
			    (16-goodIdx).times {
				    values << 20 - it
			    }
			    matrix.equals(new Matrix4(*values)) == false
		    }

			matrix.hashCode() == matrix.hashCode()
			matrix.hashCode() == same.hashCode()
			matrix.hashCode() != different.hashCode()
	}

	def "toString is properly formatted"()
	{
		expect:
			new Matrix4(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16).toString() ==
					"[1.0000 2.0000 3.0000 4.0000\n5.0000 6.0000 7.0000 8.0000\n9.0000 10.0000 11.0000 12.0000\n13.0000 14.0000 15.0000 16.0000]"
	}

	//TODO: move all the assertClose methods in test classes into one helper

	void assertClose(Matrix4 a, Matrix4 b, float delta = 0.01)
	{
		assert that(a.m00, closeTo(b.m00, delta))
		assert that(a.m01, closeTo(b.m01, delta))
		assert that(a.m02, closeTo(b.m02, delta))
		assert that(a.m03, closeTo(b.m03, delta))
		assert that(a.m10, closeTo(b.m10, delta))
		assert that(a.m11, closeTo(b.m11, delta))
		assert that(a.m12, closeTo(b.m12, delta))
		assert that(a.m13, closeTo(b.m13, delta))
		assert that(a.m20, closeTo(b.m20, delta))
		assert that(a.m21, closeTo(b.m21, delta))
		assert that(a.m22, closeTo(b.m22, delta))
		assert that(a.m23, closeTo(b.m23, delta))
		assert that(a.m30, closeTo(b.m30, delta))
		assert that(a.m31, closeTo(b.m31, delta))
		assert that(a.m32, closeTo(b.m32, delta))
		assert that(a.m33, closeTo(b.m33, delta))
	}

	void assertClose(Vector4 a, Vector4 b, float delta = 0.01)
	{
		assert that(a.x, closeTo(b.x, delta))
		assert that(a.y, closeTo(b.y, delta))
		assert that(a.z, closeTo(b.z, delta))
		assert that(a.w, closeTo(b.w, delta))
	}
}
