package galu.bench;

import com.google.common.collect.ImmutableSet;

import java.io.IOException;
import java.io.OutputStream;

public class MultiOutputStream extends OutputStream
{
	private final ImmutableSet<OutputStream> streams;

	public MultiOutputStream(ImmutableSet<OutputStream> streams)
	{
		this.streams = streams;
	}

	public MultiOutputStream(OutputStream... streams)
	{
		this(ImmutableSet.copyOf(streams));
	}

	@Override
	public void write(byte[] b, int off, int len) throws IOException
	{
		for(OutputStream stream : streams)
		{
			stream.write(b);
		}
	}

	@Override
	public void flush() throws IOException
	{
		for(OutputStream stream : streams)
		{
			stream.flush();
		}
		super.flush();
	}

	@Override
	public void close() throws IOException
	{
		for(OutputStream stream : streams)
		{
			stream.close();
		}
		super.close();
	}

	@Override
	public void write(int b) throws IOException
	{
		for(OutputStream stream : streams)
		{
			stream.write(b);
		}
	}
}
