package syntaxtree;

import cse2010.trees.binary.linked.LinkedBinaryTree;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import static syntaxtree.Utils.isNumeric;
import static syntaxtree.Utils.parse;

public class TreeBuilder {
    private static Map<String, Integer> operators = new HashMap<>();
    static {
        // We will consider only four binary operators: *, /, +, -
        operators.put("(", 0);
        operators.put("*", 3);
        operators.put("/", 3);
        operators.put("+", 2);
        operators.put("-", 2);
        operators.put(")", 1);
        // you may put other entry/entries if needed ...
    }

    private static Stack<SyntaxTree> operandStack = new Stack<>();
    private static Stack<String> operatorStack = new Stack<>();

    /**
     * Construct a syntax free from infix arithmetic expression.
     * @param expression an infix arithmetic expression
     * @return the syntax tree
     */
    public static SyntaxTree buildFromInfix(String expression) {
        initStacks();

        /**
         * You code goes here ...
         */
        String[] tokens = parse(expression);

        for (String token : tokens){
            if(isNumeric(token) || Character.isLetter(token.charAt(0))){   //operand
                SyntaxTree tree = new SyntaxTree();
                tree.addRoot(token);
                operandStack.push(tree);
            }
            else {  //operator
                while(operandStack.size() > 1
                        && operators.get(operatorStack.peek()) >= (operators.get(token)==0 ? operators.get(token)+4 : operators.get(token))){
                    SyntaxTree right = operandStack.pop();
                    SyntaxTree left = operandStack.pop();
                    String ops = operatorStack.pop();

                    SyntaxTree tree = new SyntaxTree();
                    tree.addRoot(ops);
                    tree.attach(tree.root(), left, right);

                    operandStack.push(tree);
                }

                if(operators.get(token) != 1) operatorStack.push(token);
                if(operators.get(token) == 1 && operators.get(operatorStack.peek()) == 0) operatorStack.pop();
            }
        }

        while(operandStack.size() > 1){
            SyntaxTree right = operandStack.pop();
            SyntaxTree left = operandStack.pop();
            String ops = operatorStack.pop();

            SyntaxTree tree = new SyntaxTree();
            tree.addRoot(ops);
            tree.attach(tree.root(), left, right);

            operandStack.push(tree);
        }


        return operandStack.pop();
    }

    /**
     * Construct a syntax free from postfix arithmetic expression.
     * @param expression a postfix arithmetic expression
     * @return the syntax tree
     */
    public static SyntaxTree buildFromPostfix(String expression) {
        initStacks();

        /**
         * You code goes here ...
         */

        String[] tokens = parse(expression);

        for (String token : tokens){
            if(isNumeric(token) || Character.isLetter(token.charAt(0))){   //operand
                SyntaxTree tree = new SyntaxTree();
                tree.addRoot(token);
                operandStack.push(tree);
            }
            else {  //operator
                SyntaxTree right = operandStack.pop();
                SyntaxTree left = operandStack.pop();

                SyntaxTree tree = new SyntaxTree();
                tree.addRoot(token);
                tree.attach(tree.root(), left, right);

                operandStack.push(tree);
            }
        }
        return operandStack.pop();
    }

    /**
     * Reset stacks
     */
    private static void initStacks() {
        operandStack.clear();
        operatorStack.clear();
    }
}
