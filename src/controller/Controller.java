package controller;

import data.Client;
import data.ClientQueue;
import view.ViewScreen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Controller extends Thread {
    private ViewScreen viewScreen;
    private ArrayList<Client> waitingClients = new ArrayList<>();
    private int numberOfClients;
    private int numberOfQueues;
    private int maximumSimulationTime;
    private int minimumArrivalTime;
    private int maximumArrivalTime;
    private int minimumServiceTime;
    private int maximumServiceTime;
    private AtomicInteger simulationTime;
    private boolean buttonPressed = false;
    private Scheduler scheduler;
    private FileWriter writer;
    private int maxim = -1;


    public Controller(ViewScreen viewScreen)  {


        try {
            writer = new FileWriter("Text1.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        this.viewScreen = viewScreen;
        this.simulationTime = new AtomicInteger(0);


        this.viewScreen.submitListener(new SubmitListener());
    }


    class SubmitListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            read();
            randomClientGenerator();
            buttonPressed = true;
             scheduler = new Scheduler(numberOfQueues);

        }
    }

    public   void read()
    {
        numberOfClients = viewScreen.getNrOfClientsTextField();
        numberOfQueues = viewScreen.getNrOfLabelsTextField();
        maximumSimulationTime = viewScreen.getMaxSimulationTimeTextField();
        minimumArrivalTime = viewScreen.getMinArrivalTimeTextField();
        maximumArrivalTime = viewScreen.getMaxArrivalTimeTextField();
        minimumServiceTime = viewScreen.getMinServiceTimeTextField();
        maximumServiceTime = viewScreen.getMaxServiceTimeTextField();
        viewScreen.clear();
    }

    public void randomClientGenerator()
    {


        Random random = new Random();
        for (int i=1;i<=numberOfClients;i++)
        {
            //generate n random clients
            if(maximumArrivalTime > minimumArrivalTime && maximumServiceTime > minimumServiceTime) {
                int timeArrival = random.nextInt(maximumArrivalTime - minimumArrivalTime) + minimumArrivalTime;
                int timeService = random.nextInt(maximumServiceTime - minimumServiceTime) + minimumServiceTime;
                Client randomClient = new Client(i, timeArrival, timeService);
                waitingClients.add(randomClient);
                if(maxim < timeArrival + timeService )
                    maxim = timeArrival + timeService;
            }
            else {
                System.out.println("Error");
            }
        }
    }


    //THIS THREAD IS RESPONSIBLE FOR THE ENTIRE SIMULATION TIME
    @Override
    public void run() {


        while (simulationTime.intValue() <= maximumSimulationTime && simulationTime.intValue() <= maxim) {

            String string = "Time" + simulationTime.toString();
            writeToFileText(string + "\n");
            writeToFileText("Waiting Clients : ");


            for (int i = 0; i < waitingClients.size(); i++) {

                boolean waitingClientRemoved = false;
                if (waitingClients.get(i).getTimeArrival() <= simulationTime.intValue()) {
                    scheduler.addInServiceQueue(waitingClients.get(i));
                    waitingClients.remove(waitingClients.get(i));
                    waitingClientRemoved = true;
                }

                if (!waitingClientRemoved) {
                    String string1 = "(" + waitingClients.get(i).getId() + ", " + waitingClients.get(i).getTimeArrival() + ", " + waitingClients.get(i).getTimeService() + ");";
                    writeToFileText(string1);
                    if(i % 10 == 0)    //o data la 10 clienti spatiu ca sa fie mai clar vizibil
                        writeToFileText("\n");
                }

            }
            writeToFileText("\n");

            for (ClientQueue queue : scheduler.getQueues()) {



                if (queue.getQueueLenght() == 0) {
                    String string3 = "Queue " + queue.getQueueId() + ": closed";
                    writeToFileText(string3 + "\n");

                } else {
                    String string4 = "Queue " + queue.getQueueId() + ": ";
                    writeToFileText(string4);
                    for (Client client : queue.getClientQueue()) {
                        String string5 ="(" + client.getId() + ", " + client.getTimeArrival() + ", " + client.getTimeService() + "); ";
                        writeToFileText(string5);
                    }
                    writeToFileText("\n");



                }
            }


            writeToFileText("\n");


            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            simulationTime.getAndIncrement();
        }
        System.out.println("SIMULATION FINISHED!");

        try {
            writer.write("SIMULATION FINISHED");
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void writeToFileText(String string) {
        try {
            writer.write(string);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.print(string);
    }

    public boolean isButtonPressed()
    {
        return buttonPressed;
    }

}
