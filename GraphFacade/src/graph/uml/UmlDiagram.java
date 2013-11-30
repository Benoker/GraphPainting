package graph.uml;

/**
 * Contains all the methods required to build and modify an UML diagram.
 * @author Benjamin Sigg
 */
public interface UmlDiagram {
	/**
	 * Creates a new {@link TypeBox}, the box has neither content, nor location, nor connections, not is
	 * it visible.
	 * @return the new, empty, invisible typebox
	 */
	public TypeBox createType();
}
