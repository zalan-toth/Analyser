package net.pyel.utils;

/**
 * Custom Node
 *
 * @author Zalán Tóth
 */
public class CustomNode<N> {
	public CustomNode<N> next = null;
	public CustomNode<N> previous = null;
	private N contents;

	public N getContents() {
		return contents;
	}

	public CustomNode<N> getObject() {
		return this;
	}

	public void setContents(N c) {
		contents = c;
	}

	public void setNext(CustomNode<N> n) {
		next = n;
	}
}
