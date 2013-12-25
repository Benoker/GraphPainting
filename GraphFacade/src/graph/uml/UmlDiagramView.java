package graph.uml;

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
}
