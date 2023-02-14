package comp2402a3;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class BinaryTree<Node extends BinaryTree.BTNode<Node>> {

	public static class BTNode<Node extends BTNode<Node>> {
		public Node left;
		public Node right;
		public Node parent;
	}

	/**
	 * An extension of BTNode that you can actually instantiate.
	 */
	protected static class EndNode extends BTNode<EndNode> {
			public EndNode() {
				this.parent = this.left = this.right = null;
			}
	}

	/**
	 * Used to make a mini-factory
	 */
	protected Node sampleNode;

	/**
	 * The root of this tree
	 */
	protected Node r;

	/**
	 * This tree's "null" node
	 */
	protected Node nil;

	/**
	 * Create a new instance of this class
	 * @param sampleNode - a sample of a node that can be used
	 * to create a new node in newNode()
	 * @param nil - a node that will be used in place of null
	 */
	public BinaryTree(Node sampleNode, Node nil) {
		this.sampleNode = sampleNode;
		this.nil = nil;
		r = nil;
	}

	/**
	 * Create a new instance of this class
	 * @param sampleNode - a sample of a node that can be used
	 * to create a new node in newNode()
	 */
	public BinaryTree(Node sampleNode) {
		this.sampleNode = sampleNode;
	}

	/**
	 * Allocate a new node for use in this tree
	 * @return newly created node
	 */
	@SuppressWarnings({"unchecked"})
	protected Node newNode() {
		try {
			Node u = (Node)sampleNode.getClass().getDeclaredConstructor().newInstance();
			u.parent = u.left = u.right = nil;
			return u;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Construct a random binary tree
	 * @return an n-node BinaryTree that has the shape of a random
	 * binary search tree.
	 */
	public static BinaryTree<EndNode> randomBST(int n) {
		Random rand = new Random();
		EndNode sample = new EndNode();
		BinaryTree<EndNode> t = new BinaryTree<EndNode>(sample);
		t.r = randomBSTHelper(n, rand);
		return t;
	}

	protected static EndNode randomBSTHelper(int n, Random rand) {
		if (n == 0) {
			return null;
		}
		EndNode r = new EndNode();
		int ml = rand.nextInt(n);
		int mr = n - ml - 1;
		if (ml > 0) {
			r.left = randomBSTHelper(ml, rand);
			r.left.parent = r;
		}
		if (mr > 0) {
			r.right = randomBSTHelper(mr, rand);
			r.right.parent = r;
		}
		return r;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		toStringHelper(sb, r);
		return sb.toString();
	}

	protected void toStringHelper(StringBuilder sb, Node u) {
		if (u == null) {
			return;
		}
		sb.append('(');
		toStringHelper(sb, u.left);
		toStringHelper(sb, u.right);
		sb.append(')');
	}

	/**
	 * Tree empty or not
	 * @return true if empty, false otherwise
	 */
	public boolean isEmpty() {
		return r == nil;
	}

	/**
	 * Make this tree into the empty tree
	 */
	public void clear() {
		r = nil;
	}

	/**
	 * Compute the depth (distance to the root) of u
	 * @param u
	 * @return the distanct between u and the root, r
	 */
	public int depth(Node u) {
		int d = 0;
		while (u != r) {
			u = u.parent;
			d++;
		}
		return d;
	}

	/**
	 * Demonstration of a recursive traversal
	 * @param u
	 */
	public void traverse(Node u) {
		if (u == nil) return;
		traverse(u.left);
		traverse(u.right);
	}

	/**
	 * Demonstration of a non-recursive traversal
	 */
	public void traverse2() {
		Node u = r, prev = nil, next;
		while (u != nil) {
			if (prev == u.parent) {
				if (u.left != nil) {
					next = u.left;
				} else if (u.right != nil) {
					next = u.right;
				}	else {
					next = u.parent;
				}
			} else if (prev == u.left) {
				if (u.right != nil) {
					next = u.right;
				} else {
					next = u.parent;
				}
			} else {
				next = u.parent;
			}
			prev = u;
			u = next;
		}
	}

	/**
	 * Demonstration of a breadth-first traversal
	 */
	public void bfTraverse() {
		Queue<Node> q = new LinkedList<Node>();
		if (r != nil) q.add(r);
		while (!q.isEmpty()) {
			Node u = q.remove();
			if (u.left != nil) q.add(u.left);
			if (u.right != nil) q.add(u.right);
		}
	}

	/**
	 * Compute the size (number of nodes) of this tree
	 * @warning uses recursion so could cause a stack overflow
	 * @return the number of nodes in this tree
	 */
	public int size() {
		return size(r);
	}

	/**
	 * @return the size of the subtree rooted at u
	 */
	protected int size(Node u) {
		if (u == nil) return 0;
		return 1 + size(u.left) + size(u.right);
	}

	/**
	 * Compute the maximum depth of any node in this tree
	 * @return the maximum depth of any node in this tree
	 */
	public int height() {
		return height(r);
	}

	/**
	 * @return the height of the subtree rooted at u
	 */
	protected int height(Node u) {
		if (u == nil) return -1;
		return 1 + Math.max(height(u.left), height(u.right));
	}

	public int leafAndOnlyLeaf() {
		// TODO: Your code goes here, must avoid recursion
		Node u = r, prev = nil, next;
		int leafs = 0;
		while (u != nil) {
			if (prev == u.parent) {
				if (u.left != nil) next = u.left;
				else if (u.right != nil) next = u.right;
				else {
					next = u.parent;
					leafs++;
				} 
			} else if (prev == u.left) {
				if (u.right != nil) next = u.right;
				else {
					next = u.parent;
				} 
			} else {
				next = u.parent;
			}
			prev = u;
			u = next;
		}
		return leafs;
	}

	protected int leafAndOnlyLeafHelper(Node u) {
		if(u == nil)return 0;
		if(u.left == nil && u.right == nil)return 1;
		return leafAndOnlyLeafHelper(u.left)+ leafAndOnlyLeafHelper(u.right);
	}

	public int dawnOfSpring() {
		// TODO: Your code goes here, must avoid recursion
		int temp = 0;
		int depth = 0;
		Node u = r, prev = nil, next;
		if(r == nil) {
			return -1;
		}
		while (u != nil) {
			if (prev == u.parent) {
				if (u.left != nil) {
					next = u.left;
					temp++;
				}
				else if (u.right != nil) {
					next = u.right;
					temp++;
				}
				else {
					next = u.parent;
					if (depth == 0) {
						depth = temp;
					} else if (temp < depth) {
						depth = temp;
					}
					temp--;
				} 
			} else if (prev == u.left) {
				if (u.right != nil) {
					next = u.right;
					temp++;
				}
				else {
					next = u.parent;
					temp--;
				} 
			} else {
				next = u.parent;
				temp--;
			}
			prev = u;
			u = next;
		}
		return depth;
	}

	protected int dawnOfSpringHelper(Node u, int d) {
		if(u.left == nil && u.right == nil)
			return d;
		else if(u.left == nil)
			return dawnOfSpringHelper(u.right, d+1);
		else if(u.right == nil)
			return dawnOfSpringHelper(u.left, d+1);
		else
			return Math.min(dawnOfSpringHelper(u.left, d+1), dawnOfSpringHelper(u.right, d+1));
	}
	

	public int monkeyLand() {
		// TODO: Your code goes here, must avoid recursion
		HashMap<Integer, Integer> num = new HashMap<>();
		int d = 0;
		Node u = r, prev = nil, next;
		if(r == nil) {
			return 0;
		}
		num.put(0, 1);
		int largest = 1;
		while (u != nil) {
			if (prev == u.parent) {
				if (u.left != nil) {
					next = u.left;
					d++;
					if (num.containsKey(d)) {
						num.put(d, num.get(d)+1);
					} else {
						num.put(d, 1);
					}
					if (num.get(d) >= largest) {
						largest = num.get(d);
					}
				}
				else if (u.right != nil) {
					next = u.right;
					d++;
					if (num.containsKey(d)) {
						num.put(d, num.get(d)+1);
					} else {
						num.put(d, 1);
					}
					if (num.get(d) >= largest) {
						largest = num.get(d);
					}
				}
				else {
					next = u.parent;
					d--;
				} 
			} else if (prev == u.left) {
				if (u.right != nil) {
					next = u.right;
					d++;
					if (num.containsKey(d)) {
						num.put(d, num.get(d)+1);
					} else {
						num.put(d, 1);
					}
					if (num.get(d) >= largest) {
						largest = num.get(d);
					}
				}
				else {
					next = u.parent;
					d--;
				} 
			} else {
				next = u.parent;
				d--;
			}
			prev = u;
			u = next;
		}
		return largest;
	}

	protected int monkeyLandHelper(Node u, int d, int D) {
		if(u == nil) return 0;
		if(d == D)return 1;
		return monkeyLandHelper(u.left, d+1, D) + monkeyLandHelper(u.right, d+1, D);
	}

	public String bracketSequence() {
		StringBuilder our = new StringBuilder();
		our.append("(");
		if(r == nil) {
			return ".";
		}
		// TODO: Your code goes here, use sb.append(), must avoid recursion
		Node u = r, prev = nil, next;
		while (u != nil) {
			if (prev == u.parent) {
				if (u.left != nil) {
					next = u.left;
					our.append("(");
				} else if (u.right != nil) {
					next = u.right;
					our.append(".(");
				} else {
					next = u.parent;
					our.append("..)");
				}
			} else if (prev == u.left) {
				if (u.right != nil) {
					next = u.right;
					our.append("(");
				} else {
					next = u.parent;
					our.append(".)");
				}
			} else {
				next = u.parent;
				our.append(")");
			}
			prev = u;
			u = next;
		}
		return our.toString();
	}

	protected void bracketSequenceHelper(Node u, StringBuilder sb){
		if (u == nil) {
			sb.append('.');
			return;
		}
		sb.append('(');
		bracketSequenceHelper(u.left, sb);
		bracketSequenceHelper(u.right, sb);
		sb.append(')');
	}
}
