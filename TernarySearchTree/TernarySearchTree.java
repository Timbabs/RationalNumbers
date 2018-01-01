public class TernarySearchTree {
    private class Node {
        private int val;
        private Node left = null;
        private Node right = null;
        private Node mid = null;

        public Node(int val) {
            this.val = val;
        }
    }

    private Node root;

    //Mytest
    public static void main(String[] args) {
        TernarySearchTree myTree = new TernarySearchTree();
        myTree.insert(5);
        myTree.insert(4);
        myTree.insert(9);
        myTree.insert(5);
        myTree.insert(7);
        myTree.insert(2);
        myTree.insert(2);

        myTree.delete(4);

        System.out.println("myTree.root.val: " + myTree.root.val);
        System.out.println("myTree.root.left.val: " + myTree.root.left.val);
        System.out.println("myTree.root.right.val: " + myTree.root.right.val);
        System.out.println("myTree.root.mid.val: " + myTree.root.mid.val);
        System.out.println("myTree.root.right.left.val: " + myTree.root.right.left.val);
        System.out.println("myTree.root.left.mid.val: " + myTree.root.left.mid.val);

    }

    /*
     * Inserts val into the tree.
     */
    public void insert(int val) {
        if (root == null) {
            root = new Node(val);
        } else {
            if (val < root.val) {
                if (root.left == null) {
                    root.left = new Node(val);
                } else {
                    insert(val, root.left);
                }
            } else if (val > root.val) {
                if (root.right == null) {
                    root.right = new Node(val);
                } else {
                    insert(val, root.right);
                }
            } else {
                if (root.mid == null) {
                    root.mid = new Node(val);
                } else {
                    insert(val, root.mid);
                }
            }
        }
    }

    private void insert(int val, Node node) {
        if (val < node.val) {
            if (node.left == null) {
                node.left = new Node(val);
            } else {
                insert(val, node.left);
            }
        } else if (val > node.val) {
            if (node.right == null) {
                node.right = new Node(val);
            } else {
                insert(val, root.right);
            }
        } else {
            if (node.mid == null) {
                node.mid = new Node(val);
            } else {
                insert(val, node.mid);
            }
        }

    }

    /*
     * Deletes only one instance of val from the tree.
     * If val does not exist in the tree, do nothing.
     */
    public void delete(int val) {
        if (root != null) {
            if (root.val == val) {
                Node temp = replacement(root);
                if (temp == null) {
                    root = null;
                } else {
                    root = temp;
                }
            } else {
                if (val < root.val) {
                    delete(val, root.left, root);
                } else {
                    delete(val, root.right, root);
                }
            }
        }
    }

    private void delete(int val, Node node, Node parent) {
        if (node != null) {
            if (node.val == val) {
                Node temp = replacement(node);
                if (parent.right == node) {
                    parent.right = temp;
                } else if (parent.left == node) {
                    parent.left = temp;
                } else {
                    parent.mid = temp;
                }
            } else {
                if (val < node.val) {
                    delete(val, node.left, node);
                } else {
                    delete(val, node.right, node);
                }
            }
        }
    }

    private Node replacement(Node node) {
        Node result;
        if (node.left == null && node.right == null && node.mid == null) {
            result = null;
        } else if (node.left != null && node.right == null && node.mid == null) {
            result = node.left;
        } else if (node.left == null && node.right != null && node.mid == null) {
            result = node.right;
        } else if (node.left == null && node.right == null && node.mid != null) {
            result = node.mid;
        } else {
            if (node.mid != null) {
                result = node.mid;
                result.left = node.left;
                result.right = node.right;
            } else {//replace by inorder predecessor
                Node current = node.left;
                Node parent = node;
                while (node.right != null) {
                    parent = current;
                    current = node.right;
                }
                current.right = node.right;
                if (node.left != current) {
                    parent.right = current.left;
                    current.left = node.left;
                }
                result = current;
            }
        }
        return result;
    }
}