package galu.test;

import galu.matrix.Matrix3;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import static galu.test.Helpers.close;

/**
 * Created by ben on 1/4/14.
 */
public class Matrix3Close extends TypeSafeDiagnosingMatcher<Matrix3>
{
	private final Matrix3 expected;
	private final float delta;

	public Matrix3Close(Matrix3 expected, float delta)
	{
		super(Matrix3.class);
		this.expected = expected;
		this.delta = delta;
	}

	@Override
	protected boolean matchesSafely(Matrix3 item, Description mismatchDescription)
	{
		if(!close(item.m00, expected.m00))
		{
			mismatchDescription
					.appendValue(item.m00)
					.appendText(" in position (0, 0) differed from expected value ")
					.appendValue(expected.m00)
					.appendText(" by ")
					.appendValue(Math.abs(item.m00 - expected.m00));
			return false;
		}

		if(!close(item.m01, expected.m01))
		{
			mismatchDescription
					.appendValue(item.m01)
					.appendText(" in position (0, 1) differed from expected value ")
					.appendValue(expected.m01)
					.appendText(" by ")
					.appendValue(Math.abs(item.m01 - expected.m01));
			return false;
		}

		if(!close(item.m02, expected.m02))
		{
			mismatchDescription
					.appendValue(item.m02)
					.appendText(" in position (0, 2) differed from expected value ")
					.appendValue(expected.m02)
					.appendText(" by ")
					.appendValue(Math.abs(item.m02 - expected.m02));
			return false;
		}

		if(!close(item.m10, expected.m10))
		{
			mismatchDescription
					.appendValue(item.m10)
					.appendText(" in position (1, 0) differed from expected value ")
					.appendValue(expected.m10)
					.appendText(" by ")
					.appendValue(Math.abs(item.m10 - expected.m10));
			return false;
		}

		if(!close(item.m11, expected.m11))
		{
			mismatchDescription
					.appendValue(item.m11)
					.appendText(" in position (1, 1) differed from expected value ")
					.appendValue(expected.m11)
					.appendText(" by ")
					.appendValue(Math.abs(item.m11 - expected.m11));
			return false;
		}

		if(!close(item.m12, expected.m12))
		{
			mismatchDescription
					.appendValue(item.m12)
					.appendText(" in position (1, 2) differed from expected value ")
					.appendValue(expected.m12)
					.appendText(" by ")
					.appendValue(Math.abs(item.m12 - expected.m12));
			return false;
		}

		if(!close(item.m20, expected.m20))
		{
			mismatchDescription
					.appendValue(item.m20)
					.appendText(" in position (2, 0) differed from expected value ")
					.appendValue(expected.m20)
					.appendText(" by ")
					.appendValue(Math.abs(item.m20 - expected.m20));
			return false;
		}

		if(!close(item.m21, expected.m21))
		{
			mismatchDescription
					.appendValue(item.m21)
					.appendText(" in position (2, 1) differed from expected value ")
					.appendValue(expected.m21)
					.appendText(" by ")
					.appendValue(Math.abs(item.m21 - expected.m21));
			return false;
		}

		if(!close(item.m22, expected.m22))
		{
			mismatchDescription
					.appendValue(item.m22)
					.appendText(" in position (2, 2) differed from expected value ")
					.appendValue(expected.m22)
					.appendText(" by ")
					.appendValue(Math.abs(item.m22 - expected.m22));
			return false;
		}

		return true;
	}

	@Override
	public void describeTo(Description description)
	{
		description.appendText("a matrix whose elements are within ")
		           .appendValue(delta)
		           .appendText(" those of")
		           .appendValue(expected);
	}
}
