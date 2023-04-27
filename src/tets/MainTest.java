package tets;

import data.Client;

import java.util.LinkedList;
import java.util.Queue;

public class MainTest {

    public static void main(String[] args) {


        /*for(int i=0;i<5;i++) {
            Threds myTHing = new Threds(i);
            myTHing.start();
        }*/

        Queue<Client> clients = new LinkedList<>();
        Client client1 = new Client(1,2,3);
        Client client2 = new Client(2,3,4);
        Client client3 = new Client(5,6,7);
        clients.offer(client1);
        clients.offer(client3);
        clients.offer(client2);


        Client client = clients.poll();  // remove and retrieve element
        System.out.println(client.getId());

        Client clienta = clients.peek(); // just retrieve the first element


       // Threds myTHing2 = new Threds();


        /*myTHing.start(); //start if you want them to run at the same time
        myTHing2.start();

        myTHing.run(); //run if you want the to run in sequential order
        myTHing2.run();*/

    }
}
