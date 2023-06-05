import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Stack;

class Board {
    private int[][] grid = new int[3][3];

    public Board(int[][] blocks) {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                grid[i][j] = blocks[i][j];
    }
    
    // number of blocks out of place
    public int hamming() {
        int count = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid[i][j] == 0) continue;
                if (grid[i][j] != i * 3 + j + 1) {
                    count++;
                }   
            }
        }
        return count;
    }
    
    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int count = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid[i][j] == 0) continue;
                int m = (grid[i][j] % 3 == 0) ? (grid[i][j] / 3 - 1) : (grid[i][j] / 3);
                int n = (grid[i][j] % 3 == 0) ? 2 : (grid[i][j] % 3 - 1);
                count += Math.abs(m - i) + Math.abs(n - j);
            }
        }
        return count;
    }
    
    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid[i][j] != (i * 3 + j + 1) % 9) {
                    return false;
                }
            }
        }
        return true;
    }
    
    // a board obtained by exchanging two adjacent blocks in the same row
    public Board twin() {
        for (int i = 0; i < 3; i++) {
            if (grid[i][0] != 0 && grid[i][1] != 0) {
                // swap two elements
                int tmp = grid[i][0];
                grid[i][0] = grid[i][1];
                grid[i][1] = tmp;
                Board board = new Board(grid);
                // swap back
                tmp = grid[i][0];
                grid[i][0] = grid[i][1];
                grid[i][1] = tmp;
                return board;
            }
        }
        return null;
    }
    
    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (this.getClass() != y.getClass()) return false;
        
        Board that = (Board) y;
        
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (this.grid[i][j] != that.grid[i][j]) return false;
            }
        }
        return true;
    }
    
    // all neighboring boards
    public Iterable<Board> neighbors() {
        Stack<Board> stack = new Stack<Board>();
        // mark the blank position
        boolean found = false;
        int i = -1;
        int j = -1;
        for (int m = 0; !found && m < 3; m++) {
            for (int n = 0; !found && n < 3; n++) {
                if (grid[m][n] == 0) {
                    found = true;
                    i = m; 
                    j = n;
                    break;
                }
            }
        }
        // move zero up
        if (i > 0) {
            grid[i][j] = grid[i-1][j];
            grid[i-1][j] = 0;
            Board board = new Board(grid);
            stack.push(board);
            grid[i-1][j] = grid[i][j];
            grid[i][j] = 0;
        }
        // move zero down
        if (i < 3 - 1) {
            grid[i][j] = grid[i+1][j];
            grid[i+1][j] = 0;
            Board board = new Board(grid);
            stack.push(board);
            grid[i+1][j] = grid[i][j];
            grid[i][j] = 0;
        }
        // move zero left
        if (j > 0) {
            grid[i][j] = grid[i][j-1];
            grid[i][j-1] = 0;
            Board board = new Board(grid);
            stack.push(board);
            grid[i][j-1] = grid[i][j];
            grid[i][j] = 0;
        }
        // move zero right
        if (j < 3 - 1) {
            grid[i][j] = grid[i][j+1];
            grid[i][j+1] = 0;
            Board board = new Board(grid);
            stack.push(board);
            grid[i][j+1] = grid[i][j];
            grid[i][j] = 0;
        }
        return stack;
    }
    
    // string representation of the board
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                s.append(grid[i][j]);
				s.append(' ');
            }
            s.append("\n\n");
        }
        return s.toString();
    }
}

public class A3_121090661 { 
    private Node goal; 

    // find a solution to the initial board (using the A* algorithm)
    public A3_121090661(Board initial) {
        boolean found = false; // found solution for board or twin board
        PriorityQueue<Node> pq = new PriorityQueue<Node>();
        PriorityQueue<Node> twinPQ = new PriorityQueue<Node>();
        
        // start A star search simultaneously for two priority queues
        Node root = new Node(initial);
        Node twinRoot = new Node(initial.twin());
        
        pq.add(root);
        twinPQ.add(twinRoot);
        
        while (!found) {
            Node curr = pq.poll();
            Node twinCurr = twinPQ.poll();
            
            // find a solution 
            if (curr.board.isGoal() || twinCurr.board.isGoal()) {
                if (curr.board.isGoal()) goal = curr; // goal is not null
                found = true;
                break;
            }
            
            // if not, get the neighbors, put them in the priority queue
            for (Board board : curr.board.neighbors()) {
                if (curr.prev != null && board.equals(curr.prev.board)) {
                    continue; // skip the same node
                } else {
                    Node node = new Node(board);
                    node.moves = curr.moves + 1;
                    node.prev = curr;
                    pq.add(node);
                }
            }
            // same for twin priority queue
            for (Board board : twinCurr.board.neighbors()) {
                if (twinCurr.prev != null && board.equals(twinCurr.prev.board)) {
                    continue; // skip the same node
                } else {
                    Node node = new Node(board);
                    node.moves = twinCurr.moves + 1;
                    node.prev = twinCurr;
                    twinPQ.add(node);
                }
            }
        }
    }
    

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        Stack<Board> stk1 = new Stack<Board>();
		Stack<Board> stk2 = new Stack<Board>();
        // reconstruct the solution
        Node curr = goal;
        while (curr != null) {
            stk1.push(curr.board);
            curr = curr.prev;
        }
		while (!stk1.isEmpty()){
			stk2.push(stk1.pop());
		}
        return stk2;
    }
    
    //solve a slider puzzle
    public static void main(String[]args) {
        // create initial board from file
        int[][] blocks = new int[3][3];
        Scanner sc = new Scanner(System.in);
		for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
				blocks[i][j] = sc.nextInt();
            }
        }
        Board initial = new Board(blocks);
        
        // solve the puzzle
        A3_121090661 solver = new A3_121090661(initial);
        
        // print solution
            for (Board board : solver.solution()) {
                System.out.println(board);
            
        }
    }
}

class Node implements Comparable<Node> {
    public Board board; // point to the current board
    public int moves; // N moves to the current search node
    public Node prev; // point to the previous search node

    // constructor
    public Node (Board board) {
        this.board = board;
        prev = null;
        moves = 0;
    }

    public int compareTo(Node that) {
        return (this.board.manhattan() + this.moves) - (that.board.manhattan() + that.moves);
    }   
}