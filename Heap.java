import java.util.HashMap;
import java.util.LinkedList;

// _____________________Min Heap_______________________ //

public class Heap {
    public class Node {
        HashMap<String, Integer> value;
        Node parent;
        Node left_child;
        Node right_child;

        public Node(Node parent, String key, int value) {
            this.parent = parent;
            this.value = new HashMap<>();
            this.value.put(key, value);
            this.left_child = null;
            this.right_child = null;
        }

        public Node(Node parent) {
            this.parent = parent;
            this.value = new HashMap<>();
            this.left_child = null;
            this.right_child = null;
        }

        public void setvalue(String key, int value) {
            this.value = new HashMap<>();
            this.value.put(key, value);
        }
        public void setvalue(HashMap<String, Integer> hm) {this.value = hm;}
        public HashMap<String, Integer> getValue() {
            if (value.isEmpty()) return null;
            return value;
        }
        public boolean isLeaf() {return (left_child == null && right_child == null);}
        public Node getParent() {return parent;}

        public boolean hasLeftChild() {return left_child != null;}
        public void setLeftChild(String key, int value) {this.left_child = new Node(this, key, value);}
        public void setLeftChild() {this.left_child = null;}
        public Node getLeftChild() {return left_child;}
        public boolean isLeftChild() {
            if (this.parent == null) return false;
            return this == this.parent.left_child;
        }

        public boolean hasRightChild() {return right_child != null;}
        public void setRightChild(String key, int value) {this.right_child = new Node(this, key, value);}
        public void setRightChild() {this.right_child = null;}
        public Node getRightChild() {return right_child;}
        public boolean isRightChild() {
            if (this.parent == null) return false;
            return this == this.parent.right_child;
        }
    }

    Node root = new Node(null);
    Node to_up_bubble = null; boolean inserting = false;
    Node to_down_bubble = null; boolean deleting = false;
    int size = 0;

    private int compare(HashMap<String, Integer> x, HashMap<String, Integer> y) {
        int x1 = (Integer) x.values().toArray()[0];
        int y1 = (Integer) y.values().toArray()[0];

        if (x1 < y1) return -1;
        else if (x1 == y1) return 0;
        else return 1;
    }

    public boolean isEmpty() {return root.getValue() == null;}

    private void insertionBubbel(Node n) {
        if (n.getParent() == null) return;
        if (compare(n.getValue(), n.getParent().getValue()) == -1) {
            HashMap<String, Integer> temp = n.getValue();
            n.setvalue(n.getParent().getValue());
            n.getParent().setvalue(temp);
            insertionBubbel(n.getParent());
        }
    }

    private void deletionBubble(Node n) {
        if (n.hasLeftChild() && n.hasRightChild()) {
            int f = compare(n.getValue(), n.getLeftChild().getValue());
            int s = compare(n.getValue(), n.getRightChild().getValue());
            int t = compare(n.getLeftChild().getValue(), n.getRightChild().getValue());

            if (f == 1 || s == 1) {
                if (t == -1) {
                    HashMap<String, Integer> temp = n.getLeftChild().getValue();
                    n.getLeftChild().setvalue(n.getValue());
                    n.setvalue(temp);
                    deletionBubble(n.getLeftChild());
                } else if (t == 1) {
                    HashMap<String, Integer> temp = n.getRightChild().getValue();
                    n.getRightChild().setvalue(n.getValue());
                    n.setvalue(temp);
                    deletionBubble(n.getRightChild());
                }
            }
        } else if ( n.hasLeftChild()) {
            int f = compare(n.getValue(), n.getLeftChild().getValue());
            if (f == 1) {
                HashMap<String, Integer> temp = n.getLeftChild().getValue();
                n.getLeftChild().setvalue(n.getValue());
                n.setvalue(temp);
            }
        }
    }

    public void insert(String key, int value) {
        inserting = true;
        insertNode(key, value, root, height());
        size++;
        insertionBubbel(to_up_bubble);
    }
    private void insertNode(String key, int value, Node start, int height) {
        if (root.getValue() == null) {
            root.setvalue(key, value);
            to_up_bubble = root;
            inserting = false;
            return;
        } else if (height() == rightHeight()) {
            while (start.hasLeftChild()) {start = start.getLeftChild();}
            start.setLeftChild(key, value);
            to_up_bubble = start.getLeftChild();
            inserting = false;
            return;
        }

        if (start.hasLeftChild()) insertNode(key, value, start.getLeftChild(), height - 1);
        else if (height != 0 && inserting) {
            start.setLeftChild(key, value);
            to_up_bubble = start.getLeftChild();
            inserting = false;
            return;
        }
        else return;

        if (start.hasRightChild()) insertNode(key, value, start.getRightChild(), height - 1);
        else if (inserting) {
            start.setRightChild(key, value);
            to_up_bubble = start.getRightChild();
            inserting = false;
        }
    }

    public void delete(HashMap<String, Integer> name) {
        if (this.size == 1) root = new Node(null);
        else {
            deleting = true;
            delete(name, root);
            if (to_down_bubble != null) deletionBubble(to_down_bubble);
        }
    }

    private void delete(HashMap<String, Integer> name, Node start) {
        int comparison = compare(name, start.getValue());

        if (comparison == 0) {
            Node minimal = getMinimalKey();
            start.setvalue(minimal.getValue());
            to_down_bubble = start;
            size--;
            deleting = false;
            if (minimal.isLeftChild()) minimal.getParent().setLeftChild();
            else if (minimal.isRightChild()) minimal.getParent().setRightChild();
        }
        else if (comparison == -1) return;
        else {
            if (start.hasLeftChild() && deleting) delete(name, start.getLeftChild());
            if (start.hasRightChild() && deleting) delete(name, start.getRightChild());
        }
    }

    public HashMap<String, Integer> get() {
        HashMap<String, Integer> to_return = root.getValue();
        delete(to_return);
        return to_return;
    }

    private Node getMinimalKey() {return to_up_bubble;}

    public int height() {return height(root);}
    private int height(Node start) {
        int h = 0;
        while (start.hasLeftChild()) {
            start = start.getLeftChild();
            h++;
        }
        return h;
    }

    private int rightHeight() {return rightHeight(root);}
    private int rightHeight(Node start) {
        int h = 0;
        while (start.hasRightChild()) {
            start = start.getRightChild();
            h++;
        }
        return h;
    }

    public int size() {return size;}

    public int depth(Node n) {
        int depth = 0;

        while (n.getParent() != null) {
            depth++;
            n = n.getParent();
        }
        return depth;
    }
    
}
