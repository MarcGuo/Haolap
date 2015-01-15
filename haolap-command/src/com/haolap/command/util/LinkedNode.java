package com.haolap.command.util;

import java.util.ArrayList;
import java.util.List;

public class LinkedNode<T> {
	private LinkedNode<T> parent;
	private T value;
	private List<LinkedNode<T>> subNodes;
	private int index;

	public LinkedNode(T value) {
		super();
		this.value = value;
		this.parent = null;
		this.subNodes = null;
		this.index = 0;
	}

	public LinkedNode(T value, LinkedNode<T> parent) {
		super();
		this.parent = parent;
		this.parent.addSubNode(this);
		this.subNodes = null;
		this.value = value;
	}

	public void setParent(LinkedNode<T> parent) {
		if (parent == this) {
			return;
		}
		if (this.parent != null) {
			this.parent.removeSubNode(this.value);
		}
		this.parent = parent;
		if (parent == null) {
			this.index = 0;
		} else {
			this.parent.addSubNode(this);
		}
	}

	public void clearSubNodes() {
		if (this.subNodes != null)
			this.subNodes.clear();
	}

	public LinkedNode<T> getParents() {
		return this.parent;
	}

	public LinkedNode<T> previous() {
		if (this.parent == null)
			return null;
		List<LinkedNode<T>> sibling = parent.getSubNodes();
		return sibling.get(this.index - 1);

	}

	public LinkedNode<T> next() {
		if (this.parent == null)
			return null;
		List<LinkedNode<T>> sibling = parent.getSubNodes();
		return sibling.get(this.index + 1);
	}

	public int getSiblingAmount() {
		return parent == null ? 1 : parent.getSubNodes().size();
	}

	public int getSubNodesAmount() {
		return this.subNodes == null ? 0 : this.subNodes.size();
	}

	public LinkedNode<T> getSubNode(int index) {
		return this.subNodes != null && index >= 0
				&& index < this.subNodes.size() ? this.subNodes.get(index)
				: null;
	}

	public List<LinkedNode<T>> getSubNode(T key) {
		if (this.subNodes == null) {
			return null;
		}
		List<LinkedNode<T>> nodes = new ArrayList<LinkedNode<T>>();
		for (LinkedNode<T> node : subNodes) {
			if (key.equals(node.getValue())) {
				nodes.add(node);
			}
		}
		return nodes;
	}

	public boolean isLeaf() {
		return this.subNodes == null || this.subNodes.size() == 0;
	}

	public boolean isRoot() {
		return this.parent == null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LinkedNode<?> other = (LinkedNode<?>) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	public boolean addSubNode(LinkedNode<T> node) {
		if (node == this) {
			return false;
		}
		if (node.getValue() == null) {
			return false;
		}
		if (this.subNodes == null) {
			this.subNodes = new ArrayList<LinkedNode<T>>();
		}
		node.setIndex(this.subNodes.size());
		this.subNodes.add(node);
		node.parent = this;
		return true;
	}

	public List<LinkedNode<T>> removeSubNode(T key) {
		if (key == null)
			return null;
		List<LinkedNode<T>> searchResult = new ArrayList<LinkedNode<T>>();
		List<LinkedNode<T>> removeResult = new ArrayList<LinkedNode<T>>();
		for (LinkedNode<T> node : subNodes) {
			if (key.equals(node.getValue())) {
				searchResult.add(node);
			}
		}
		for (int i = searchResult.size() - 1; i > -1; i--) {
			removeResult.add(this.removeSubNodeByIndex(searchResult.get(i)
					.getIndex()));
		}
		return removeResult;
	}

	public LinkedNode<T> removeSubNodeByIndex(int index) {
		LinkedNode<T> node = subNodes.remove(index);

		if (node != null) {
			node.parent = null;
			node.index = 0;
			int beginPos = index;
			int endPos = subNodes.size();
			while (beginPos < endPos) {
				LinkedNode<T> tempNode = this.subNodes.get(beginPos);
				tempNode.setIndex(tempNode.getIndex() - 1);
				beginPos++;
			}
		}
		return node;
	}

	public boolean hasPrevious() {
		return this.parent != null && index > 0;
	}

	public boolean hasNext() {
		return this.parent == null ? false : index < this.parent
				.getSubNodesAmount() - 1;
	}

	public int getNextAmount() {
		return this.getSiblingAmount() - index - 1;
	}

	private List<LinkedNode<T>> getSubNodes() {
		return this.subNodes;
	}

	public int getIndex() {
		return this.index;
	}

	private void setIndex(int index) {
		this.index = index;
	}

	public T getValue() {
		return this.value;
	}

	@Override
	public String toString() {
		if (!this.isLeaf()) {
			String info = new String();
			info += String.format("[%s]: --- \n", this.value);
			for (int i = 0; i < this.subNodes.size(); i++) {
				info += String.format("\t[%s]  ", this.subNodes.get(i)
						.getValue());
			}
			info += "\n";
			for (int i = 0; i < this.subNodes.size(); i++) {
				info += this.subNodes.get(i).toString();
			}
			return info;
		} else {
			return String.format("[%s] --- this is leaf\n", this.value);
		}
	}

}
