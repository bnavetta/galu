package galu.test;

import galu.matrix.Matrix2;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import static galu.test.Helpers.close;

public class Matrix2Close extends TypeSafeDiagnosingMatcher<Matrix2>
{
	private final Matrix2 expected;
	private final float delta;

	public Matrix2Close(Matrix2 expected, float delta)
	{
		super(Matrix2.class);
		this.expected = expected;
		this.delta = delta;
	}

	@Override
	protected boolean matchesSafely(Matrix2 item, Description mismatchDescription)
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
