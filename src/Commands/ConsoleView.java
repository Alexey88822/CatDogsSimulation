package Commands;

import javax.swing.*;
import java.awt.*;

public class ConsoleView extends JFrame {
    int wHeight;
    int wWindth;
    JTextArea consolArea;

    public ConsoleView(int wHeight, int wWidth) {
        this.wHeight = wHeight;
        this.wWindth = wWidth;
        drawConsol();
    }

    public void drawConsol(){
        setBounds(100, 100, wWindth, wHeight);
        consolArea = new JTextArea();
        consolArea.setBounds(0, 0, wWindth, wHeight);
        consolArea.setBackground(Color.BLACK);
        consolArea.setForeground(Color.WHITE);
        add(consolArea);
        setVisible(true);
    }
}
