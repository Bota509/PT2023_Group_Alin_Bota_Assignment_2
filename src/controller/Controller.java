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
    private int totalTimeService = 0;

    public Controller(ViewScreen viewScreen) {
        try {
            writer = new FileWriter("Test3.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.viewScreen = viewScreen;
        this.simulationTime = new AtomicInteger(0);
        this.viewScreen.submitListener(new SubmitListener());
    }


    class SubmitListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            read();
            randomClientGenerator();
            buttonPressed = true;
            scheduler = new Scheduler(numberOfQueues);
        }
    }

    public void read() {
        numberOfClients = viewScreen.getNrOfClientsTextField();
        numberOfQueues = viewScreen.getNrOfLabelsTextField();
        maximumSimulationTime = viewScreen.getMaxSimulationTimeTextField();
        minimumArrivalTime = viewScreen.getMinArrivalTimeTextField();
        maximumArrivalTime = viewScreen.getMaxArrivalTimeTextField();
        minimumServiceTime = viewScreen.getMinServiceTimeTextField();
        maximumServiceTime = viewScreen.getMaxServiceTimeTextField();
        viewScreen.clear();
    }

    public void randomClientGenerator() {
        Random random = new Random();
        for (int i = 1; i <= numberOfClients; i++) {
            //generate n random clients
            if (maximumArrivalTime > minimumArrivalTime && maximumServiceTime > minimumServiceTime) {
                int timeArrival = random.nextInt(maximumArrivalTime - minimumArrivalTime + 1) + minimumArrivalTime;
                int timeService = random.nextInt(maximumServiceTime - minimumServiceTime + 1) + minimumServiceTime;
                Client randomClient = new Client(i, timeArrival, timeService);
                waitingClients.add(randomClient);
                totalTimeService += timeService;
            } else {
                System.out.println("Error from client generator");
            }
        }
    }

    public int calculateAverageWaiting() {                                   //gets the total waiting time from all the
        int waitTime = 0;                                                    //queues, sums them up and divide it by
        for (ClientQueue queue : scheduler.getQueues()) {                        //the number of queues
            waitTime += queue.getTotalTime().intValue();
        }
        return waitTime / numberOfQueues;
    }


    //THIS THREAD IS RESPONSIBLE FOR THE ENTIRE SIMULATION TIME
    @Override
    public void run() {
        int maximCurrentClients = -1;
        int peakHour = 0;
        boolean remainingClients = false;
        while (simulationTime.intValue() <= maximumSimulationTime && !remainingClients) {
            String string = "Time" + simulationTime.toString();
            writeToFileText(string + "\n");
            writeToFileText("Waiting Clients : ");
            for (int i = 0; i < waitingClients.size(); i++) {

                if (waitingClients.get(i).getTimeArrival() <= simulationTime.intValue()) {
                    scheduler.addInServiceQueue(waitingClients.get(i));
                    waitingClients.remove(waitingClients.get(i));
                }
            }
            for (int i = 0; i < waitingClients.size(); i++) {

                String string1 = "(" + waitingClients.get(i).getId() + ", " + waitingClients.get(i).getTimeArrival() + ", " + waitingClients.get(i).getTimeService() + ");";
                writeToFileText(string1);
                if (i % 10 == 0)    //o data la 10 clienti spatiu ca sa fie mai clar vizibil
                    writeToFileText("\n");
            }

            boolean areWaitingClients = true;
            if (waitingClients.size() == 0) {
                areWaitingClients = false;
            }
            int currentClients = 0;
            for (ClientQueue queue : scheduler.getQueues()) {
                currentClients += queue.getQueueLenght();
            }
            if (currentClients > maximCurrentClients) {
                maximCurrentClients = currentClients;
                peakHour = simulationTime.intValue();
            }
            writeToFileText("\n");
            boolean noMoreClientsInQueues = true;
            for (ClientQueue queue : scheduler.getQueues()) {
                if (!queue.getClientQueue().isEmpty())
                    noMoreClientsInQueues = false;
                if (queue.getQueueLenght() == 0) {
                    String string3 = "Queue " + queue.getQueueId() + ": closed";
                    writeToFileText(string3 + "\n");
                } else {
                    String string4 = "Queue " + queue.getQueueId() + ": ";
                    writeToFileText(string4);
                    int index = 0;
                    for (Client client : queue.getClientQueue()) {

                        String string5 = "(" + client.getId() + ", " + client.getTimeArrival() + ", " + client.getTimeService() + "); ";
                        writeToFileText(string5);
                        if (index == 10)
                            writeToFileText("\n");
                        index++;
                    }
                    writeToFileText("\n");
                }
            }
            if (noMoreClientsInQueues && !areWaitingClients)
                remainingClients = true;

            writeToFileText("\n");

            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            simulationTime.getAndIncrement();
        }

        writeToFileText("Average Waiting Time : " + calculateAverageWaiting() + "\n");
        writeToFileText("Peak Hour : " + peakHour + "\n");
        writeToFileText("Average Service Time : " + totalTimeService / numberOfClients + "\n");
        System.out.println("SIMULATION FINISHED!");

        try {
            writer.write("SIMULATION FIDNISHED");
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

    public boolean isButtonPressed() {
        return buttonPressed;
    }

}
