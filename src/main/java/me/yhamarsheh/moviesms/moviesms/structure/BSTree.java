package me.yhamarsheh.moviesms.moviesms.structure;

import me.yhamarsheh.moviesms.moviesms.structure.nodes.TNode;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class BSTree<T extends Comparable<T>> implements Iterable<T> {

	protected TNode root;

	public TNode find(T data) {
		return find(data, root);
	}

	public TNode find(T data, TNode node) {
		if (node != null) {
			int comp = node.data.compareTo(data);
			if (comp == 0)
				return node;
			else if (comp > 0 && node.hasLeft())
				return find(data, node.left);
			else if (comp < 0 && node.hasRight())
				return find(data, node.right);
		}
		return null;
	}

	public int height() {
		return height(root);
	}

	public int height(TNode node) {
		if (node == null)
			return 0;
		if (node.isLeaf())
			return 1;
		int left = 0;
		int right = 0;
		if (node.hasLeft())
			left = height(node.left);
		if (node.hasRight())
			right = height(node.right);
		return (left > right) ? (left + 1) : (right + 1);
	}

	public TNode smallest() {
		return smallest(root);
	}

	public TNode<T> smallest(TNode node) {
		if (node != null) {
			if (!node.hasLeft())
				return (node);
			return smallest(node.left);
		}
		return null;
	}

	public void insert(T data) {
		if (isEmpty())
			root = new TNode(data);
		else
			insert(data, root);
	}

	public void insert(T data, TNode node) {
		if (data.compareTo((T) node.data) >= 0) { // insert into right subtree
			if (!node.hasRight())
				node.right = new TNode(data);
			else
				insert(data, node.right);
		} else { // insert into left subtree
			if (!node.hasLeft())
				node.left = new TNode(data);
			else
				insert(data, node.left);
		}
	}

	public TNode delete(T data) {
		TNode current = root;
		TNode parent = root;
		boolean isLeftChild = false;

		// Check if the tree is empty
		if (isEmpty()) return null;

		// Search for the node to delete
		while (current != null && !current.data.equals(data)) {
			parent = current;
			if (data.compareTo((T) current.data) < 0) {
				current = current.left;
				isLeftChild = true;
			} else {
				current = current.right;
				isLeftChild = false;
			}
		}

		if (current == null) return null; // Node not found

		// Case 1: Node is a leaf
		if (current.left == null && current.right == null) {
			if (current == root) {
				root = null; // Tree has one node
			} else if (isLeftChild) {
				parent.left = null;
			} else {
				parent.right = null;
			}
		}
		// Case 2: Node has one child (left or right)
		else if (current.left == null) { // Only right child
			if (current == root) {
				root = current.right;
			} else if (isLeftChild) {
				parent.left = current.right;
			} else {
				parent.right = current.right;
			}
		} else if (current.right == null) { // Only left child
			if (current == root) {
				root = current.left;
			} else if (isLeftChild) {
				parent.left = current.left;
			} else {
				parent.right = current.left;
			}
		}
		// Case 3: Node has two children
		else {
			// Find the in-order successor (smallest in the right subtree)
			TNode successor = getSuccessor(current);

			// If deleting the root, set the root to the successor
			if (current == root) {
				root = successor;
			} else if (isLeftChild) {
				parent.left = successor;
			} else {
				parent.right = successor;
			}

			// Link the successor's left child to the current node's left child
			successor.left = current.left;
		}

		return current; // Return the deleted node
	}

	private TNode getSuccessor(TNode node) {
		TNode parentOfSuccessor = node;
		TNode successor = node;
		TNode current = node.right;
		while (current != null) {
			parentOfSuccessor = successor;
			successor = current;
			current = current.left;
		}
		if (successor != node.right) { // fix successor connections
			parentOfSuccessor.left = successor.right;
			successor.right = node.right;
		}
		return successor;
	}

	public boolean isEmpty() {
		return root == null;
	}

	public void traverseInOrder() {
		traverseInOrder(root);
	}

	private void traverseInOrder(TNode node) {
		if (node != null) {
			if (node.left != null)
				traverseInOrder(node.left);
			System.out.print(node + " ");
			if (node.right != null)
				traverseInOrder(node.right);
		}
	}

	protected int getHeightDifference(TNode node) {
		if (node == null) return 0;
		return height(node.left) - height(node.right);
	}

	public boolean isAVL(TNode node) {
		if (node == null) return false;
		if (getHeightDifference(node) < 2) return true;

		return (isAVL(node.left) && isAVL(node.right));
	}

	public boolean isBST(TNode root, TNode node) {
		if (node == null) return false;

		if (node.hasLeft()) {
			if (node.left.data.compareTo(node.data) < 0 && node.left.data.compareTo(root.data) < 0) {
				return isBST(root, node.left);
			} else return false;
		}

		if (node.hasRight()) {
			if (node.left.data.compareTo(node.data) > 0 && node.right.data.compareTo(root.data) > 0) return isBST(root, node.right);
			else return false;
		}

		return true;
	}

	public void levelTraversal() {
	    if (root == null) {
	        return;
	    }

	    Queue<TNode> q = new LinkedList<>();
	    q.add(root);

	    while (!q.isEmpty()) {
	        TNode currentNode = q.poll();
	        System.out.print(currentNode.data + " -> ");

	        if (currentNode.left != null) {
	            q.add(currentNode.left);
	        }
	        if (currentNode.right != null) {
	            q.add(currentNode.right);
	        }
	    }
	    System.out.println();
	}

	public TNode getRoot() {
		return root;
	}

	// UPDATE NEEDED
	@Override
	public Iterator<T> iterator() {
		return new BSTIterator();
	}

	private class BSTIterator implements Iterator<T> {
		private TNode current;
		private TNode prev;

		public BSTIterator() {
			current = root;
		}

		@Override
		public boolean hasNext() {
			return current != null;
		}

		@Override
		public T next() {
			if (!hasNext()) {
				return null;
			}

			while (current != null) {
				if (current.left == null) {
					T data = (T) current.data;
					current = current.right;
					return data;
				} else {
					prev = current.left;
					while (prev.right != null && prev.right != current) {
						prev = prev.right;
					}

					if (prev.right == null) {
						prev.right = current;
						current = current.left;
					} else {
						prev.right = null;
						T data = (T) current.data;
						current = current.right;
						return data;
					}
				}
			}

			return null;
		}
	}
}
