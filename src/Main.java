import controller.Controller;

import view.ViewScreen;



public class Main {

    public static void main(String[] args) throws InterruptedException {

        ViewScreen viewScreen = new ViewScreen();

        Object lock = new Object();
        Controller controller = new Controller(viewScreen);
        while (!controller.isButtonPressed())
        {
            Thread.sleep(1000);
        }

        controller.start();
    }
}
