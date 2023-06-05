import java.util.Scanner;

public class A2_121090661 {

    class TreapNode {
        int size = 1;
        int count = 1;
        int priority = (int)(Math.random() * 100);
        int data;
        TreapNode left;
        TreapNode right;
    }
    
    private static boolean LEFT = true;
    private static boolean RIGHT = false;
    TreapNode ROOT = null;
    
    public void update(TreapNode root) {
        int left = root.left == null ? 0 : root.left.size;
        int right = root.right == null ? 0 : root.right.size;
        root.size = left + root.count + right;
    }
    
    public TreapNode rotate(TreapNode root, boolean dir) {
        TreapNode op = dir ? root.right : root.left;
        if(dir){
            root.right = op.left;
        }
        else{
            root.left = op.right;
        }
        if(dir){
            op.left = root;
        }
        else{
            op.right = root;
        }
        update(root);
        update(op);
        return op;
    }
    
    public TreapNode insert(TreapNode root, int value) {
        if(root == null) {
            root = new TreapNode();
            root.data = value;
            return root;
        }
        if(root.data == value){
            root.count ++;
            root.size ++;
            return root;
        }
        else{
            TreapNode d;
            if(value > root.data){
                root.right = insert(root.right, value);
                d = root.right;
            } 
            else{
                root.left = insert(root.left, value);
                d = root.left;
            }
            if(root.priority < d.priority){
                root = rotate(root,value > root.data);
            }
            update(root);
        }
        return root;
    }

    public TreapNode delete(TreapNode root, int value) {
        if(root == null){
            return root;
        }
        if(value < root.data){
            root.left = delete(root.left, value);
        }
        else if(value > root.data){
            root.right = delete(root.right, value);
        }
        else {
            boolean lf = root.left == null;
            boolean rf = root.right == null;
            if(lf && rf){
                root.count --;
                if(root.count == 0){
                    return null;
                }
            }
            else if(lf){
                root = rotate(root,LEFT);
                root.left = delete(root.left, value);
            } 
            else if(rf){
                root = rotate(root,RIGHT);
                root.right = delete(root.right, value);
            } 
            else{
                boolean dir = root.left.priority < root.right.priority;
                root = rotate(root,dir);
                if(dir){
                    root.left = delete(root.left, value);
                }
                else{
                    root.right = delete(root.right, value);
                }
            }
        }
        -- root.size;
        return root;
    }
    
    public int rank(TreapNode root, int value) {
        if(root == null){
            return 0;
        }
        else if(root.data == value){
            return root.left == null ? 1 : root.left.size + 1;
        }
        else if(root.data < value){
            return root.count + rank(root.right,value) + (root.left == null ? 0 : root.left.size);
        }
        else{
            return rank(root.left, value);
        }
    }
    
    public int find(TreapNode root, int value) {
        if(root == null){
            return 0;
        }
        int left = root.left == null ? 0 : root.left.size;
        if(left >= value){
            return find(root.left, value);
        }
        else if(left + root.count < value){
            return find(root.right,value - root.count - left);
        }
        else{
            return root.data;
        }
    }
    
    public int pre(TreapNode root, int value) {
        if(root == null){
            return Integer.MIN_VALUE;
        } 
        if(root.data >= value){
            return pre(root.left,value);
        }
        else{
            return Math.max(root.data, pre(root.right, value));
        }
    }
    
    public int nxt(TreapNode root, int value) {
        if(root == null){
            return Integer.MAX_VALUE;
        }
        if(root.data <= value){
            return nxt(root.right, value);
        }
        else{
            return Math.min(nxt(root.left, value), root.data);
        }
    }
    public static void main(String[] args) {
        A2_121090661 treap = new A2_121090661();
        try (Scanner sc = new Scanner(System.in)) {
            int nn = sc.nextInt();
            for (int i = 1; i <= nn; i++) {
                int opt;
                int x;
                opt = sc.nextInt();
                x = sc.nextInt();
                switch (opt) {
                    case 1:
                        treap.ROOT = treap.insert(treap.ROOT, x);
                        break;
                    case 2:
                        treap.ROOT = treap.delete(treap.ROOT, x);
                        break;
                    case 3:
                        System.out.println(treap.rank(treap.ROOT, x));
                        break;
                    case 4:
                        System.out.println(treap.find(treap.ROOT, x));
                        break;
                    case 5:
                        System.out.println(treap.pre(treap.ROOT, x));
                        break;
                    case 6:
                        System.out.println(treap.nxt(treap.ROOT, x));
                        break;
                }
            }
        }
    }
}