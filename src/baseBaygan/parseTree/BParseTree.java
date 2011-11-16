package baseBaygan.parseTree;

import java.util.ArrayList;

import tools.Pair;

/**
 * BParseTree is the class that contains a root node for the the tree and list
 * of errors. and a way to match errors to nodes.
 * 
 * @author Salim
 * 
 */
public class BParseTree {
	/**
	 * Keeps the root of the parse tree. If the parse tree contains any error
	 * then it will still contain a parse tree root node, but the parse tree
	 * would not be complete and leave nodes may be the nodes which contain
	 * errors.
	 */
	private final BExpression root;
	/**
	 * Errors that the parse tree have.
	 */
	private final ArrayList<Pair<BExpression, String>> errors;

	public BParseTree(BExpression root,
			ArrayList<Pair<BExpression, String>> errors) {
		this.root = root;
		this.errors = errors;
	}

	/**
	 * This constructor create an instance which contains no errors.
	 * 
	 * @param root
	 */
	public BParseTree(BExpression root) {
		this.root = root;
		this.errors = new ArrayList<Pair<BExpression, String>>(1);
	}

	public ArrayList<Pair<BExpression, String>> getErrors() {
		return errors;
	}

	public BExpression root() {
		return root;
	}

	public boolean hasError() {
		return errors.size() == 0;
	}

}
