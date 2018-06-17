package Commands;

import machines.HabitatView;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.IOException;

public class ConsoleThread extends Thread {
    private DataInputStream readFromConsol;
    private ConsoleView consolView;
    private Cons consol;

    public ConsoleThread(ConsoleView consolView, DataInputStream readFromConsol, Cons consol) {
        this.readFromConsol = readFromConsol;
        this.consolView = consolView;
        this.consol = consol;
    }

    public void run() {
        while (!isInterrupted()) {
            try {
                String cmd = this.readFromConsol.readUTF().trim();
                consol.command(cmd);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
