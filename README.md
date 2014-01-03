# Galu

A group of useful libraries and tools for making games with OpenGL. Currently, it's just a matrix/vector library, but more will come.

The name 'galu' is Vulcan for 'atom'. The 'g' seems required because it's a graphics library, and I liked the sound of the rest of it.
If there was any deeper reason, I have no idea what that reason was.

***

### Math

The `galu-math` project contains vector and matrix implementations suitable for graphics. Vectors of length 2, 3, and 4 are implemented,
as are matrices of dimensions 2x2, 3x3, and 4x4. All matrices and vectors are immutable. Object allocation in Java, especially for small
objects like these, seems to be cheap enough that the extra code complexity of mutable vectors and matrices isn't worth it. Elements are
stored as primitive `float`s in public fields, since that's the fastest way to access them. Operations can only be conducted on
vectors/matrices of the same size.

#### Operations Supported

##### Vectors

* length
* normalization
* addition
* subtraction
* dot product
* angle between vectors
* negation
* multiplication by a scalar
* multiplication by another vector (element-wise)
* loading/storing from/to `float[]`s and `FloatBuffer`s

##### Matrices

* determinant
* inverse
* negation
* transpose
* addition
* subtraction
* multiplication
* division (multiply by inverse)
* element-wise multiplication
* element-wise division
* matrix-vector multiplication (transformation)
* loading/storing from/to `float[]`s and `FloatBuffer`s

***

### Building

Galu is built with [Gradle](http://www.gradle.org). To build it using the Gradle wrapper, run `./gradlew build`.