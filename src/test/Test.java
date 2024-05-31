package test;

import javax.swing.UIManager;
import ui.Dashboard_GUI;
import ui.Login_GUI;

public class Test {

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Login_GUI run = new Login_GUI();
//                Dashboard_GUI run = new Dashboard_GUI();
                run.setVisible(true);
            }
        });
    }
}
