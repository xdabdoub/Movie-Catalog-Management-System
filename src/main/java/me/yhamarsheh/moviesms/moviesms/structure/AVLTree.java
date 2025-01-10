package me.yhamarsheh.moviesms.moviesms.structure;

import me.yhamarsheh.moviesms.moviesms.structure.nodes.TNode;

public class AVLTree<T extends Comparable<T>> extends BSTree<T> {

	public TNode rotateRight(TNode node) {
		TNode nodeC = node.left;
		node.left = nodeC.right;
		nodeC.right = node;

		return nodeC;
	}

	public TNode rotateLeft(TNode node) {
		TNode nodeC = node.right;
		node.right = nodeC.left;
		nodeC.left = node;

		return nodeC;
	}

	public TNode rotateRightLeft(TNode node) {
		TNode nodeC = node.right;
		node.right = rotateRight(nodeC);
		return rotateLeft(node);
	}

	public TNode rotateLeftRight(TNode node) {
		TNode nodeC = node.left;
		node.left = rotateLeft(nodeC);
		return rotateRight(node);
	}

	private TNode rebalance(TNode nodeN) {
		int diff = getHeightDifference(nodeN);
		if (diff > 1) { // addition was in node's left subtree
			if (getHeightDifference(nodeN.left) > 0)
				nodeN = rotateRight(nodeN);
			else
				nodeN = rotateLeftRight(nodeN);
		} else if (diff < -1) { // addition was in node's right subtree
			if (getHeightDifference(nodeN.right) < 0)
				nodeN = rotateLeft(nodeN);
			else
				nodeN = rotateRightLeft(nodeN);
		}
		return nodeN;
	}

	public void insert(T data) {
		if (isEmpty())
			root = new TNode<>(data);
		else {
			TNode rootNode = root;
			addEntry(data, rootNode);
			root = rebalance(rootNode);
		}
	}

	public void addEntry(T data, TNode rootNode) {
		assert rootNode != null;
		if (data.compareTo((T) rootNode.data) < 0) { // right into left subtree
			if (rootNode.hasLeft()) {
				TNode leftChild = rootNode.left;
				addEntry(data, leftChild);
				rootNode.left = rebalance(leftChild);
			} else
				rootNode.left = new TNode(data);
		} else { // right into right subtree
			if (rootNode.hasRight()) {
				TNode rightChild = rootNode.right;
				addEntry(data, rightChild);
				rootNode.right = rebalance(rightChild);
			} else
				rootNode.right = new TNode(data);
		}
	}

	public TNode delete(T data) {
		TNode temp = super.delete(data);
		if (temp != null) {
			TNode rootNode = root;
			root = rebalance(rootNode);
		}
		return temp;
	}
}
