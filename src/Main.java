import controller.Controller;
import data.Client;
import data.ClientQueue;
import view.ViewScreen;

public class Main {

    public static void main(String[] args) throws InterruptedException {



        ViewScreen viewScreen = new ViewScreen();

        Controller controller = new Controller(viewScreen);
        while (!controller.isButtonPressed())
        {
            Thread.sleep(1000);
        }

        controller.start();


      /*  System.out.println("sdsd");
        Client client = new Client(1,2,3);
        Client client1 = new Client(1,2,3);
        Client client2 = new Client(1,2,3);


        ClientQueue clientQueue = new ClientQueue(1);
        clientQueue.addInQueue(client);
        clientQueue.addInQueue(client1);
        clientQueue.addInQueue(client2);

        System.out.println("nr of lenght: " + clientQueue.getQueueLenght());*/



    }
}
