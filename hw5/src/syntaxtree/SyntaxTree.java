package syntaxtree;

import cse2010.trees.Position;
import cse2010.trees.TreeUtils;
import cse2010.trees.binary.linked.LinkedBinaryTree;

import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

import static syntaxtree.Utils.isNumeric;

/**
 * Linked-based Arithmetic expression tree.
 */
public class SyntaxTree extends LinkedBinaryTree<String> {

    /**
     * Evaluate syntax tree.
     * @return evaluated arithmetic expression
     */
    public double evaluate() {
        /**
         * Your code goes here ...
         */

        Stack<Double> operandStack = new Stack<>();
        List<String> list = TreeUtils.toElements(postOrder());
        Iterator iter = list.iterator();


        while(iter.hasNext()){
            String element = (String) iter.next();
            if(isNumeric(element)){
                operandStack.push(Double.parseDouble(element));
            }
            else{
                double right = operandStack.pop();
                double left = operandStack.pop();

                switch (element) {
                    case "*":
                        operandStack.push(left * right); break;
                    case "/":
                        operandStack.push(left / right); break;
                    case "+":
                        operandStack.push(left + right); break;
                    case "-":
                        operandStack.push(left - right); break;
                    default: break;
                }
            }
        }
        double result = operandStack.pop();

        return result;
    }

    /**
     * Returns postfix expression corresponding to this syntax tree.
     * @return postfix representation of this syntax three
     */
    public String toPostFix() {
        return cvtToString(postOrder());
    }

    /**
     * Returns prefix expression corresponding to this syntax tree.
     * @return prefix representation of this syntax three
     */
    public String toPreFix() {
        return cvtToString(preOrder());
    }

    /**
     * Returns fully parenthesized infix expression corresponding to this syntax tree.
     * @return fully parenthesized prefix representation of this syntax three
     */
    public String toInfix() {
        return toInfix(root());
    }

    /**
     * Returns fully parenthesized infix expression corresponding to this syntax subtree.
     * @return fully parenthesized infix representation of this syntax subtree
     */
    private String toInfix(Position<String> position) {
        /**
         * Your code goes here ...
         */

        StringBuilder builder = new StringBuilder();
        if (hasLeft(position)){
            builder.append("(");
            builder.append(toInfix(left(position)));
        }
        if (isInternal(position)) builder.append(" ");
        builder.append(position.getElement());
        if (isInternal(position)) builder.append(" ");
        if (hasRight(position)){
            builder.append(toInfix(right(position)));
            builder.append(")");
        }


        return builder.toString();
    }

    /**
     * Returns a formatted string representation of tree hierarchy.
     * The formatted string representation of the expression tree corresponsing
     * to {@code (a + b) * (c - d)} looks as follow:
     * *
     *   +
     *     a
     *     b
     *   -
     *     c
     *     d
     * @return a formatted string representation of tree hierarchy
     */
    public String showTree() {
        return indentTree(root(), 0);
    }

    /**
     * Returns a formatted string representation of the subtree hierarchy.
     * @param position a node position
     * @param level indentation level; 0 means no indentation; the unit of
     *              the indentation level is two spaces.
     * @return  a formatted string representation of the subtree hierarchy
     */
    private String indentTree(Position<String> position, int level) {
        Node<String> node = validate(position);
        StringBuilder builder = new StringBuilder();

        /**
         * You code goes here...
         */
        for(int i = 0; i < level*2; i++) {builder.append(" ");}
        builder.append(node.getElement()); builder.append("\n");
        if (hasLeft(position)) builder.append(indentTree(left(position), level+1));
        if (hasRight(position)) builder.append(indentTree(right(position), level+1));



        return builder.toString();
    }

    /**
     * Convert list of Positions to a serialized string in which
     * each element of the position is delimited by the ' ' character.
     * @param positions a list of Position<String>s
     * @return a string serialized as a list of elements
     */
    private String cvtToString(List<Position<String>> positions) {
        return positions.stream().map(Position::getElement).collect(Collectors.joining(" "));
    }
}
