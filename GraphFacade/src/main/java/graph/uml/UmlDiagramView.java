package graph.uml;

import java.awt.image.BufferedImage;

import javax.swing.JComponent;

/**
 * A view that can show an {@link UmlDiagram}. A {@link UmlDiagramView} is connected to
 * a {@link UmlDiagramRepository}, and can only show diagrams that have been created by that
 * repository.
 * @author Benjamin Sigg
 */
public interface UmlDiagramView {
	/**
	 * Sets the diagram that should be shown on this view. Note that a diagram can only be shown
	 * on one view at a time.
	 * @param diagram the diagram, can be <code>null</code>
	 */
	public void setDiagram( UmlDiagram diagram );

	/**
	 * Gets access to some tools that can be used to modify the graph.
	 * @return all the tools
	 */
	public UmlDiagramTools getTools();
	
	/**
	 * Gets the {@link JComponent} that shows the diagram.
	 * @return the component, never <code>null</code>, always the same object
	 */
	public JComponent getComponent();
	
	/**
	 * Paints the contents of this view onto a new {@link BufferedImage}, the size of the image is just big enough to
	 * fit the entire graph plus a little bit of empty space on all sides.
	 * @return the newly painted image
	 */
	public BufferedImage paintImage();
	
	/**
	 * Paints the contents of this view onto a new {@link BufferedImage}, the size of the image is just big enough to
	 * fit the entire graph plus a little bit of empty space on all sides.
	 * @param imageType one of the image types that {@link BufferedImage#BufferedImage(int, int, int)} requires.
	 * @return the newly painted image
	 */
	public BufferedImage paintImage( int imageType );
}
