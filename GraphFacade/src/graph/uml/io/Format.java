package graph.uml.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * A format describes how data is written or read from or to a file (or any kind of {@link InputStream} or {@link OutputStream})
 * @author Benjamin Sigg
 */
public interface Format {
	/**
	 * XML format
	 */
	public static final Format XML = new XmlFormat();
	
	/**
	 * Writes <code>data</code> into <code>out</code>. <code>out</code> is buffered, and closing <code>out</code> does
	 * not have any effect.
	 * @param data the data to write
	 * @param out the output stream to write into
	 * @throws IOException if writing to <code>out</code> fails
	 */
	public void write( UmlDiagramData data, OutputStream out ) throws IOException;
	
	/**
	 * Reads some {@link UmlDiagramData} from <code>in</code>. <code>in</code> is buffered, and
	 * closing <code>in</code> does not have any effect.
	 * @param in the stream to read from
	 * @param converter converter that is used to read data
	 * @return the data that was read
	 * @throws IOException if the stream cannot be read
	 */
	public UmlDiagramData read( InputStream in, UmlDiagramConverter converter ) throws IOException;
}
