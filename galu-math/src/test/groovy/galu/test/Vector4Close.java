package galu.test;

import galu.vector.Vector4;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import static galu.test.Helpers.close;

public class Vector4Close extends TypeSafeDiagnosingMatcher<Vector4>
{
	private final Vector4 expected;
	private final float delta;

	public Vector4Close(Vector4 expected, float delta)
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
				.appendValue(" those of ")
				.appendValue(expected);
	}

	@Override
	protected boolean matchesSafely(Vector4 item, Description mismatchDescription)
	{
		if(!close(item.x, expected.x, delta))
		{
			mismatchDescription.appendText("x differed by ").appendValue(item.x - expected.x);
			return false;
		}

		if(!close(item.y, expected.y, delta))
		{
			mismatchDescription.appendText("y differed by ").appendValue(item.y - expected.y);
			return false;
		}

		if(!close(item.z, expected.z, delta))
		{
			mismatchDescription.appendText("z differed by ").appendValue(item.z - expected.z);
			return false;
		}

		if(!close(item.w, expected.w, delta))
		{
			mismatchDescription.appendText("w differed by ").appendValue(item.w - expected.w);
			return false;
		}

		return true;
	}
}
