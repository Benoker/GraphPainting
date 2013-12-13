package graph.uml;

/**
 * A set of tools that can be applied to a graph to modify it. 
 * @author Benjamin Sigg
 */
public interface UmlDiagramTools {
	/**
	 * The default tool is applied. The default tool allows selecting {@link Item}s, and
	 * allows to move them around. 
	 */
	public void applyDefaultTool();
	
	/**
	 * Applies a tool that creates new {@link Connection}s between {@link TypeBox}, just 
	 * as if a call to {@link TypeBox#addInheritsFrom(TypeBox)} was made. The tool prevents
	 * self references.
	 */
	public void applyAddInheritsFromTool();
}
