import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.io.FileWriter;

public class NumberMaze {
    private static class Node {
        private int[] coords = new int[2];
        private int data;
        private Node Right;
        private Node Left;
        private Node Up;
        private Node Down;
        public Node(){}
    }

    public static boolean isConnected(Node s, Node d) {
        if(d.Right == s){
            return true;
        }
        if(d.Down == s) {
            return true;
        }
        if(d.Left == s) {
            return true;
        }
        if(d.Up == s) {
            return true;
        }
        return false;
    }

    public static boolean accepted(int[][] a, int x, int y) {
        if(x >= 0 && x < a.length && y >= 0 && y < a.length) {
            return true;
        }
        return false;
    }

    public static Node[][] nodeArray(int[][] a) {
        Node[][] newNode = new Node[a.length][a.length];
        for(int i = 0; i<newNode.length; i++){
            for(int j = 0; j<newNode[i].length; j++) {
                newNode[i][j] = new Node();
                newNode[i][j].coords[0] = i;
                newNode[i][j].coords[1] = j;
                newNode[i][j].data = a[i][j];
            }
        }
        for(int i = 0; i<newNode.length; i++) {
            for (int j = 0; j < newNode[i].length; j++) {
                if (accepted(a, i, j + newNode[i][j].data))
                    newNode[i][j].Right = newNode[i][j + newNode[i][j].data];
                if (accepted(a, i, j - newNode[i][j].data))
                    newNode[i][j].Left = newNode[i][j - newNode[i][j].data];
                if (accepted(a, i + newNode[i][j].data, j))
                    newNode[i][j].Down = newNode[i + newNode[i][j].data][j];
                if (accepted(a, i - newNode[i][j].data, j))
                    newNode[i][j].Up = newNode[i - newNode[i][j].data][j];
            }
        }
        return newNode;
    }

    public static void leastNumberofSteps(Node[][] nodes, int x, int y) {
        Stack<Node> stack = new Stack<>();
        Queue<Node> queue = new LinkedList<>();
        int[][] path = new int[nodes.length][nodes[0].length];
        boolean b = false;
        int level = 1;
        queue.add(nodes[x][y]);
        path[x][y] = level;
        Node bottomRight = nodes[nodes.length - 1][nodes[nodes.length - 1].length - 1];
        int destination = bottomRight.data;
        while(!queue.isEmpty()) {
            Node temp;
            level++;
            int size = queue.size();
            for(int i = 0; i<size; i++) {
                temp = queue.remove();
                if(temp.data == destination) {
                    b = true;
                    break;
                }
                if(temp.Right != null && path[temp.Right.coords[0]][temp.Right.coords[1]] == 0) {
                    queue.add(temp.Right);
                    path[temp.Right.coords[0]][temp.Right.coords[1]] = level;
                }
                if(temp.Down != null && path[temp.Down.coords[0]][temp.Down.coords[1]] == 0) {
                    queue.add(temp.Down);
                    path[temp.Down.coords[0]][temp.Down.coords[1]] = level;
                }
                if(temp.Left != null && path[temp.Left.coords[0]][temp.Left.coords[1]] == 0) {
                    queue.add(temp.Left);
                    path[temp.Left.coords[0]][temp.Left.coords[1]] = level;
                }
                if(temp.Up != null && path[temp.Up.coords[0]][temp.Up.coords[1]] == 0) {
                    queue.add(temp.Up);
                    path[temp.Up.coords[0]][temp.Up.coords[1]] = level;
                }
            }
            if(b) break;
        }
        if(b) {
            int x1 = 0, y1 = 0;
            for(int i = 0; i<nodes.length; i++) {
                for (int j = 0; j < nodes.length; j++) {
                    if (nodes[i][j].data == destination) {
                        x1 = i;
                        y1 = j;
                    }
                }
            }
            stack.add(nodes[x1][y1]);
            int d = path[x1][y1];
            while(d > 0){
                for(int i = 0; i<path.length; i++) {
                    if(path[x1][i] == d-1 && isConnected(nodes[x1][y1], nodes[x1][i])) {
                        stack.add(nodes[x1][i]);
                        y1 = i;
                        break;
                    }
                    else if(path[i][y1] == d-1 && isConnected(nodes[x1][y1], nodes[i][y1])) {
                        stack.add(nodes[i][y1]);
                        x1 = i;
                        break;
                    }
                }
                d--;
            }
            Node temp = null;
            int stackSize = stack.size() - 1;
            for(int i = 0; i<stackSize; i++) {
                temp = stack.pop();
                //System.out.print("("+temp.coords[0]+" "+temp.coords[1]+") ");
            }
            System.out.println("The minimum number of steps are: " + stackSize);
        } else{
            System.out.print("No Solution Possible.");
        }
        System.out.println("Runtime in Milliseconds: " + System.currentTimeMillis());
    }

    public static void main(String[] args) {
        try{
            System.out.println("Enter a filename: ");
            Scanner input = new Scanner(System.in);
            File readFile = new File(input.nextLine());
            Scanner scanFile = new Scanner(readFile);
            int edges = scanFile.nextInt();
            int[][] twoDarray = new int[edges][edges];
            int fileSize = (int) readFile.length();
            int[] exchange = new int[fileSize];
            int i = 0;
            while (scanFile.hasNextInt()){
                for(int a = 0; a < edges; a++){
                    for(int b = 0; b < edges; b++){
                        int digit = scanFile.nextInt();
                        exchange[i] = digit;
                        twoDarray[a][b] = exchange[i];

                    }
                }
                i++;
            }
            System.out.println(Arrays.deepToString(twoDarray));
            Node[][] maze = nodeArray(twoDarray);
            leastNumberofSteps(maze, 0, 0);
            scanFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}