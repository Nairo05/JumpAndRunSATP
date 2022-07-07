package de.dhbw.satp.main;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

class SelectionsCheck extends JFrame implements ItemListener {

    static JComboBox combobox;
    static JLabel l1, l2, l3;

    public void itemStateChanged(ItemEvent e)
    {

        if (e.getSource() == combobox) {

            l2.setText(" ["+combobox.getSelectedItem()+"]");
        }

    }
}
