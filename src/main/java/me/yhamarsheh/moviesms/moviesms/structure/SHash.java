package me.yhamarsheh.moviesms.moviesms.structure;

import me.yhamarsheh.moviesms.moviesms.structure.nodes.TNode;

public class SHash<T extends Comparable<T>> {

    private AVLTree<T>[] hashTable;
    private int m;
    private int size;
    public SHash(int size) {
		this.size = getFirstPrime(size);
        this.m = size/5;
        this.hashTable = new AVLTree[m];

        for (int i = 0; i < hashTable.length; i++) {
            hashTable[i] = new AVLTree<T>();
        }
    }

    public void insert(T data) {
        int index = hash(data);
        hashTable[index].insert(data);

        if (getAverageHeight() > 3) resize();
    }

    public void delete(T data) {
        int index = hash(data);
        hashTable[index].delete(data);
    }

    public boolean contains(T data) {
        int index = hash(data);
        return hashTable[index].find(data) != null;
    }

    public AVLTree<T> getTreeOfItem(int index) {
        return hashTable[index % m];
    }

    public int indexOf(T data) {
        return hash(data);
    }

    private int getFirstPrime(int x) {
        for (int i = x; i < x * 2; i++) {
            if (isPrime(i)) return i;
        }

        return -1;
    }

    private boolean isPrime(int x) {
        for (int i = 2; i < x / 2; i++) {
            if (x % i == 0) return false;
        }

        return true;
    }

    private int hash(T obj) {
        return Math.abs(obj.hashCode()) % m;
    }

    public AVLTree<T>[] getHashTable() {
        return hashTable;
    }

    public int getAverageHeight() {
        int heights = 0;
        for (AVLTree<T> tree : hashTable) {
            heights += tree.height();
        }

        return (int) Math.round((double)heights / hashTable.length);
    }

    public void resize() {
        size = getFirstPrime(2 * size);
        m = (2 * size) / 5;

        AVLTree<T>[] oldHashTable = hashTable;

        hashTable = new AVLTree[size];
        for (int i = 0; i < size; i++) {
            hashTable[i] = new AVLTree<>();
        }

        for (AVLTree<T> oldTree : oldHashTable) {
            if (oldTree != null) {
                insertTree(oldTree, oldTree.getRoot());
            }
        }
    }

    public void insertTree(AVLTree<T> tree, TNode<T> current) {
        if (current != null) {
            if (current.left != null)
                insertTree(tree, current.left);
            insert(current.data);
            if (current.right != null)
                insertTree(tree, current.right);
        }
    }
}