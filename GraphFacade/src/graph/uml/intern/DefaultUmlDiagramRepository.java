package graph.uml.intern;

import graph.ui.Graph;
import graph.uml.ConnectionLabelConfiguration;
import graph.uml.UmlDiagram;
import graph.uml.UmlDiagramRepository;
import graph.uml.UmlDiagramView;
import graph.uml.intern.config.DefaultUmlConfiguration;
import graph.uml.io.Format;
import graph.uml.io.UmlDiagramConverter;
import graph.uml.io.UmlDiagramData;
import graph.uml.io.XmlFormat;
import graph.uml.xml.XElement;

import java.awt.Component;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Default implementation of the {@link UmlDiagramRepository}.
 * @author Benjamin Sigg
 */
public class DefaultUmlDiagramRepository extends UmlDiagramRepository {
	private UmlDiagramConverter converter;
	private DefaultConnectionLabelConfigurations labelConfigurations = new DefaultConnectionLabelConfigurations();
	private DefaultUmlConfiguration configuration = new DefaultUmlConfiguration();

	/**
	 * Constructs a new repository
	 */
	public DefaultUmlDiagramRepository() {
		converter = new UmlDiagramConverter();
	}

	/**
	 * Creates a new, empty {@link UmlDiagram}.
	 * @return an empty diagram
	 */
	public UmlDiagram createEmptyDiagram() {
		return new DefaultUmlDiagram( new Graph(), this );
	}

	/**
	 * Creates a {@link Component} that can be used to show a {@link UmlDiagram}.
	 * @return the component
	 */
	public UmlDiagramView createView() {
		return new DefaultUmlDiagramView( this );
	}

	/**
	 * Stores or removes a configuration with name <code>name</code>. Changing a configuration does not have any effect
	 * on {@link DefaultConnectionLabel}s that already exist.
	 * @param name the name of the configuration
	 * @param config the configuration, can be <code>null</code>
	 */
	public void putLabelConfiguration( ConnectionLabelConfiguration name, DefaultConnectionLabelConfiguration config ) {
		labelConfigurations.putLabelConfiguration( name, config );
	}

	/**
	 * Gets the configuration for a {@link DefaultConnectionLabel}.
	 * @param name the key of the configuration
	 * @return the configuration, may be <code>null</code>
	 */
	public DefaultConnectionLabelConfiguration getLabelConfiguration( ConnectionLabelConfiguration name ) {
		return labelConfigurations.getLabelConfiguration( name );
	}

	/**
	 * Writes <code>diagram</code> into <code>file</code> using the format <code>format</code>.
	 * @param diagram a diagram to write, must have been created by this {@link UmlDiagramRepository}
	 * @param format the format of the file
	 * @param file the file to write into
	 * @throws IOException if the diagram cannot be written
	 */
	public void write( UmlDiagram diagram, Format format, Path file ) throws IOException {
		toDefault( diagram );
		try (OutputStream out = Files.newOutputStream( file )) {
			write( diagram, format, out );
		}
	}

	/**
	 * Writes <code>diagram</code> into <code>out</code> using the format <code>format</code>.
	 * @param diagram a diagram to write, must have been created by this {@link UmlDiagramRepository}
	 * @param format the format of the file
	 * @param out the outputstream to write into
	 * @throws IOException if the diagram cannot be written
	 */
	public void write( UmlDiagram diagram, Format format, OutputStream out ) throws IOException {
		DefaultUmlDiagram umlDiagram = toDefault( diagram );

		UmlDiagramData data = converter.toData( umlDiagram );

		BufferedOutputStream bufferedOut = new BufferedOutputStream( out ) {
			@Override
			public void close() throws IOException {
				// do not close "out"
			}
		};

		format.write( data, bufferedOut );
		bufferedOut.flush();
	}

	@Override
	public XElement writeXml( UmlDiagram diagram ) {
		DefaultUmlDiagram umlDiagram = toDefault( diagram );

		UmlDiagramData data = converter.toData( umlDiagram );

		XmlFormat format = new XmlFormat();
		return format.writeXml( data );
	}
	
	/**
	 * Reads an {@link UmlDiagram} from <code>file</code>.
	 * @param format the format of the data
	 * @param file the file to read from
	 * @return the diagram that was read
	 * @throws IOException if reading fails
	 */
	public UmlDiagram read( Format format, Path file ) throws IOException {
		try (InputStream in = Files.newInputStream( file )) {
			return read( format, in );
		}
	}

	/**
	 * Reads an {@link UmlDiagram} from <code>in</code>. Note that this method may not read the same amount
	 * of bytes that have been written by {@link #write(UmlDiagram, Format, OutputStream)}.
	 * @param format the format of the data
	 * @param in the stream to read from, this stream is not closed by this method
	 * @return the diagram that was read
	 * @throws IOException if reading fails
	 */
	public UmlDiagram read( Format format, InputStream in ) throws IOException {
		BufferedInputStream bufferedIn = new BufferedInputStream( in ) {
			@Override
			public void close() throws IOException {
				// do not close "in"
			}
		};

		UmlDiagramData data = format.read( bufferedIn, converter );
		return converter.toDiagram( data, this );
	}
	
	@Override
	public UmlDiagram readXml( XElement element ) {
		XmlFormat format = new XmlFormat();
		UmlDiagramData data = format.readXml( element, converter );
		return converter.toDiagram( data, this );
	}

	@Override
	public DefaultUmlConfiguration getConfiguration() {
		return configuration;
	}

	public DefaultUmlDiagram toDefault( UmlDiagram diagram ) {
		if( diagram instanceof DefaultUmlDiagram ) {
			DefaultUmlDiagram result = (DefaultUmlDiagram) diagram;
			if( result.getRepository() == this ) {
				return result;
			}
		}
		throw new IllegalArgumentException( "the diagram was not created by this repository" );
	}
}
