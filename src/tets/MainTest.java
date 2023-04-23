package tets;

public class MainTest {

    public static void main(String[] args) {


        for(int i=0;i<5;i++) {
            Threds myTHing = new Threds(i);
            myTHing.start();
        }


       // Threds myTHing2 = new Threds();


        /*myTHing.start(); //start if you want them to run at the same time
        myTHing2.start();

        myTHing.run(); //run if you want the to run in sequential order
        myTHing2.run();*/

    }
}
