package com.company;

public class Tree<T extends Comparable<T>> { //
    private class Node{
        T elem;
        Node left,right;
        public Node(T t){
            elem = t;
            left = null;
            right = null;
        }
    }
    //Fields
    Node root;
    //Constructors
    public Tree(){
        root=null;
    }
    public Tree(T t){
        root = new Node(t);
    }
    //Methods
    public boolean add(T t){
        while(true){
            root = add(t, root);
            break;
        }
        return true;
    }
    private Node add(T t,Node node){
        if(node==null){
            node=new Node(t);
            return node;
        }
        else {
            if (t.compareTo(node.elem) >= 0) {
                node.right = add(t, node.right);
                return node;
            } else {
                node.left = add(t, node.left);
                return node;
            }
        }
    }
    void delete(T t)
    {
        root = delete(root, t);
    }
    Node delete(Node root, T t)
    {
        if (root == null)  {
            return root;
        }
        if (root.elem.compareTo(t)>0)
            root.left = delete(root.left, t);
        else if (root.elem.compareTo(t)<0)
            root.right = delete(root.right, t);
        else
        {
            if (root.left == null) // one child or no child
                return root.right;
            else if (root.right == null) // one child or no child
                return root.left;
            root.elem = minValue(root.right); // two children
            root.right = delete(root.right, root.elem);
        }
        return root;
    }
    T minValue(Node root)
    {
        T min = root.elem;
        while (root.left != null)
        {
            min = root.left.elem;
            root = root.left;
        }
        return min;
    }
    public boolean find(T t){
        Node temp=root;
        while(temp!=null && temp.elem.compareTo(t)!=0)
        {
            if(temp.elem.compareTo(t)>0){
                temp=temp.left;
            }
            else
            {
                temp=temp.right;
            }
        }
        if(temp!=null){
            return true;
        }
        else{
            return false;
        }
    }
    public void preOrder(){
        preOrder(root);
        System.out.println();
    }
    private void preOrder(Node node){
        if(node!=null){
            System.out.print(node.elem + " ");
            preOrder(node.left);
            preOrder(node.right);
        }
    }
    public void inOrder(){
        inOrder(root);
        System.out.println();
    }
    private void inOrder(Node node){
        if(node!=null){
            inOrder(node.left);
            System.out.print(node.elem + " ");
            inOrder(node.right);
        }
    }
    public void postOrder(){
        postOrder(root);
        System.out.println();
    }
    private void postOrder(Node node){
        if(node!=null){
            postOrder(node.left);
            postOrder(node.right);
            System.out.print(node.elem+ " ");
        }
    }
    public boolean isEmpty(){
        return root==null;
    }
}
