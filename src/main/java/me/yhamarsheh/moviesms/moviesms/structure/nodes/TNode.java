package me.yhamarsheh.moviesms.moviesms.structure.nodes;

public class TNode<T extends Comparable<T>> {

	public T data;
	public TNode<T> left;
	public TNode<T> right;

	public TNode(T data) {
		this.data = data;
	}

	public boolean hasLeft() {
		return left != null;
	}

	public boolean hasRight() {
		return right != null;
	}

	public boolean isLeaf() {
		return !hasLeft() && !hasRight();
	}

	@Override
	public String toString() {
		return data + " -> ";
	}
}
