package com.company;

import javax.swing.*;

public class MyTable extends JTable {

    @Override
        public Class getColumnClass(int column) {
        //return getValueAt(0,column).getClass();
        switch (column) {
            case 0:
                return ImageIcon.class;
            case 1:
                return String.class;
            case 2:
                return Integer.class;
            case 3:
                return Boolean.class;
            default:
                return Integer.class;
        }
    }
        @Override
        public boolean isCellEditable(int row, int column) {
        if(column==0)
            return false;
        if(row==getRowCount()-1){
            return false;
        }
        return true;
    };
}
