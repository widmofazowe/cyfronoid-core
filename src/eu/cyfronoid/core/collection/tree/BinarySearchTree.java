package eu.cyfronoid.core.collection.tree;

public class BinarySearchTree {
    public BinarySearchTreeNode root;

    public void insert(int value) {
        BinarySearchTreeNode node = new BinarySearchTreeNode(value);
        if(root == null) {
            root = node;
            return;
        }
        insertRec(root, node);
    }

    private void insertRec(BinarySearchTreeNode latestRoot, BinarySearchTreeNode node) {
        if(latestRoot.getValue() > node.getValue()) {
            if(latestRoot.getLeft() == null) {
                latestRoot.setLeft(node);
                return;
            }  else {
                insertRec(latestRoot.getLeft(), node);
            }
        } else {
            if(latestRoot.getRight() == null) {
                latestRoot.setRight(node);
                return;
            } else {
                insertRec(latestRoot.getRight(), node);
            }
        }
    }

    public int findMinimum(){
        if(root == null) {
            return 0;
        }
        BinarySearchTreeNode currNode = root;
        while(currNode.getLeft() != null) {
            currNode = currNode.getLeft();
        }
        return currNode.getValue();
    }


    public int findMaximum(){
        if(root == null) {
            return 0;
        }
        BinarySearchTreeNode currNode = root;
        while(currNode.getRight() != null) {
            currNode = currNode.getRight();
        }
        return currNode.getValue();
    }
}

