package com.company;

public class Main {
    public static void main(String[] args) {
	Tree<Earthquake> tree=new Tree<Earthquake>();
	tree.add(new Earthquake("Arika", 9F));
	Earthquake Tibet=new Earthquake("Tibet", 8.6F);
	tree.add(Tibet);
	tree.add(new Earthquake("Sumatra", 9.1F));
	tree.add(new Earthquake("Lissabon", 8.7F));
	tree.add(new Earthquake("Kamchtka", 9F));
	tree.preOrder();
	tree.inOrder();
	tree.postOrder();
	System.out.println("Element Tibet, 8.6?:"+ tree.find(Tibet));
	tree.delete(Tibet);
	tree.preOrder();
	System.out.println("Element Tibet, 8.6?:"+ tree.find(Tibet));
    }
}

//public class Main {
//    public static void main(String[] args) {
//        Tree<Integer> tree=new Tree<Integer>();
//        tree.add(7);
//        tree.add(9);
//        tree.add(3);
//        tree.add(4);
//        tree.add(11);
//        tree.add(5);
//        tree.add(8);
//        tree.add(10);
//        tree.add(1);
//        tree.add(2);
//        tree.add(12);
//        tree.preOrder();
//        tree.inOrder();
//        tree.postOrder();
//        System.out.println("Element 13?:"+tree.find(13));
//        System.out.println("Element 9?:"+tree.find(9));
//        tree.delete(3);
//        tree.delete(9);
//        tree.delete(55);
//        tree.preOrder();
//    }
//}

