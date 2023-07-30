package graph.uml;

import java.io.Serializable;

/**
 * A key is an immutable, unique identifier for an {@link Item} that is part of an {@link UmlDiagram}.<br>
 * {@link ItemKey}s are {@link Serializable}.
 * @param <T> the kind of {@link Item} that is described by this key
 * @author Benjamin Sigg
 */
public interface ItemKey<T extends Item> extends Serializable {
	/**
	 * Converts this {@link ItemKey} into a unique string that can be used to store the key
	 * to a file.
	 * @return this key formatted as string
	 */
	public String toUniqueString();
}
