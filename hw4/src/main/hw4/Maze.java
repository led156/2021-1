package hw4;
/*
 * CSE2010 Homework #4: Maze.java
 *
 * Complete the code below.
 */

import java.util.Arrays;

public class Maze {
	private final int numRows;
	private final int numCols;

	private int[][] maze;
	private boolean[][] visited;

	private final Location entry; // Entry Location
	private final Location exit;  // Exit Location

	///////
	private Location temp = new Location(-1, -1);
	///////

	private final ArrayStack<Location> _stack = new ArrayStack<>(100);

	public Maze(int[][] maze, Location entry, Location exit) {
		this.maze = maze;
		numRows = maze.length;
		numCols = maze[0].length;
		visited = new boolean[numRows][numCols]; // initialized to false

		this.entry = entry;
		this.exit = exit;
	}

	// For testing purpose
	public void printMaze() {
		System.out.println("Maze[" + numRows + "][" + numCols + "]");
		System.out.println("Entry index = (" + entry.row + ", " + entry.col + ")");
		System.out.println("Exit index = (" + exit.row + ", " + exit.col + ")" + "\n");

		for (int i = 0; i < numRows; i++) {
			System.out.println(Arrays.toString(maze[i]));
		}
		System.out.println();
	}

	public boolean findPath() {
		return moveTo(entry.row, entry.col);
	}

	private boolean moveTo(int row, int col) {

		/*
		 * Fill code here
		 */



		if(exit.col == col && exit.row == row){	//Base
			_stack.push(new Location(row, col));
			return true;
		}

		else{
			visited[row][col] = true;
			_stack.push(new Location(row, col));

			if(isIndex(row-1, col) && !visited[row-1][col] && isZero(row-1, col)){
				return moveTo(row-1, col);
			}
			if(isIndex(row, col+1) && !visited[row][col+1] && isZero(row, col+1)){
				return moveTo(row, col+1);
			}
			if(isIndex(row+1, col) && !visited[row+1][col] && isZero(row+1, col)){
				return moveTo(row+1, col);
			}
			if(isIndex(row, col-1) && !visited[row][col-1] && isZero(row, col-1)){
				return moveTo(row, col-1);
			}


			_stack.pop();
			if(_stack.isEmpty()) {return false;}
			temp = _stack.pop();
			return moveTo(temp.row, temp.col);
		}



	}

	public String getPath() {
		StringBuilder builder = new StringBuilder();
		while (!_stack.isEmpty()) {
			builder.append(_stack.pop() + " <-- ");
		}
		builder.append("Start");
		return builder.toString();
	}

	///////
	private boolean isIndex(int row, int col){
		return row > -1 && row < numRows && col > -1 && col < numCols;
	}

	private boolean isZero(int row, int col){
		return maze[row][col]==0;
	}



}
