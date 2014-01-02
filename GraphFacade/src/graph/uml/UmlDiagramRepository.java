package graph.uml;

import graph.uml.intern.DefaultUmlDiagramRepository;
import graph.uml.io.Format;

import java.awt.Component;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;

/**
 * This class offers various methods that can be helpful for creating, reading and writing. An application
 * may use one repository as a singleton, or many repositories.
 * {@link UmlDiagram}s.
 * @author Benjamin Sigg
 */
public abstract class UmlDiagramRepository {
	/**
	 * Creates a new default repository.
	 * @return a new repository, not <code>null</code>
	 */
	public static UmlDiagramRepository createDefaultRepository(){
		return new DefaultUmlDiagramRepository();
	}
	
	/**
	 * Creates a new, empty {@link UmlDiagram}.
	 * @return an empty diagram
	 */
	public abstract UmlDiagram createEmptyDiagram();
	
	/**
	 * Creates a {@link Component} that can be used to show a {@link UmlDiagram}.
	 * @return the component
	 */
	public abstract UmlDiagramView createView();

	/**
	 * Writes <code>diagram</code> into <code>file</code> using the format <code>format</code>.
	 * @param diagram a diagram to write, must have been created by this {@link UmlDiagramRepository}
	 * @param format the format of the file
	 * @param file the file to write into
	 * @throws IOException if the diagram cannot be written
	 */
	public abstract void write( UmlDiagram diagram, Format format, Path file ) throws IOException;

	/**
	 * Writes <code>diagram</code> into <code>out</code> using the format <code>format</code>.
	 * @param diagram a diagram to write, must have been created by this {@link UmlDiagramRepository}
	 * @param format the format of the file
	 * @param out the outputstream to write into
	 * @throws IOException if the diagram cannot be written
	 */
	public abstract void write( UmlDiagram diagram, Format format, OutputStream out ) throws IOException;

	/**
	 * Reads an {@link UmlDiagram} from <code>file</code>.
	 * @param format the format of the data
	 * @param file the file to read from
	 * @return the diagram that was read
	 * @throws IOException if reading fails
	 */
	public abstract UmlDiagram read( Format format, Path file ) throws IOException;

	/**
	 * Reads an {@link UmlDiagram} from <code>in</code>. Note that this method may not read the same amount
	 * of bytes that have been written by {@link #write(UmlDiagram, Format, OutputStream)}.
	 * @param format the format of the data
	 * @param in the stream to read from, this stream is not closed by this method
	 * @return the diagram that was read
	 * @throws IOException if reading fails
	 */
	public abstract UmlDiagram read( Format format, InputStream in ) throws IOException;
}
