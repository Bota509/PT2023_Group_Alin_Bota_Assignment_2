import controller.Controller;
import data.Client;
import data.ClientQueue;
import view.ViewScreen;

import java.io.FileWriter;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        ViewScreen viewScreen = new ViewScreen();

        Controller controller = new Controller(viewScreen);
        while (!controller.isButtonPressed())
        {
            Thread.sleep(1000);
        }

        controller.start();
    }
}
