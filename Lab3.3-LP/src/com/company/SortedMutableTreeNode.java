package com.company;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import java.util.Collections;
import java.util.Comparator;

public class SortedMutableTreeNode extends DefaultMutableTreeNode {

    public SortedMutableTreeNode(Object userObject) {
        super(userObject);
    }

    @Override
    public void add(MutableTreeNode newChild) {
        super.add(newChild);
    }
    void sort(){
        Collections.sort(this.children,Comparator.comparing(Object::toString));
    }
}


