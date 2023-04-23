import controller.Controller;
import data.Client;
import view.ViewScreen;

public class Main {

    public static void main(String[] args) {


        Client client = new Client(1,2,3);
        ViewScreen viewScreen = new ViewScreen();

        Controller controller = new Controller(viewScreen,client);



    }
}
