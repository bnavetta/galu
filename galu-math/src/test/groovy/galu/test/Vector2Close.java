package galu.test;

import galu.vector.Vector2;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import static galu.test.Helpers.close;

public class Vector2Close extends TypeSafeDiagnosingMatcher<Vector2>
{
	private final Vector2 expected;
	private final float delta;

	public Vector2Close(Vector2 expected, float delta)
	{
		this.expected = expected;
		this.delta = delta;
	}

	@Override
	public void describeTo(Description description)
	{
		description
			.appendText("a vector whose elements are within ")
			.appendValue(delta)
			.appendText(" of those of ")
			.appendValue(expected);
	}

	@Override
	protected boolean matchesSafely(Vector2 item, Description mismatchDescription)
	{
		if(!close(item.x, expected.x, delta))
		{
			mismatchDescription
					.appendText("x-value ")
					.appendValue(item.x)
					.appendText(" differed from expected value ")
					.appendValue(expected.x)
					.appendText(" by ")
					.appendValue(Math.abs(item.x - expected.x));
			return false;
		}

		if(!close(item.y, expected.y, delta))
		{
			mismatchDescription
					.appendText("y-value ")
					.appendValue(item.y)
					.appendText(" differed from expected value ")
					.appendValue(expected.y)
					.appendText(" by ")
					.appendValue(Math.abs(item.y - expected.y));
			return false;
		}

		return true;
	}
}
