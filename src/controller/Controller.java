package controller;

import data.Client;
import data.ClientQueue;
import view.ViewScreen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private ClientQueue clientQueue;


    public Controller(ViewScreen viewScreen) {
        this.viewScreen = viewScreen;
        this.simulationTime = new AtomicInteger(0);


        this.viewScreen.submitListener(new SubmitListener());
    }

    public Controller() {
        this.simulationTime = new AtomicInteger(0);
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
            //genereate n random clients
            if(maximumArrivalTime > minimumArrivalTime && maximumServiceTime > minimumServiceTime) {
                int timeArrival = random.nextInt(maximumArrivalTime - minimumArrivalTime) + minimumArrivalTime;
                int timeService = random.nextInt(maximumServiceTime - minimumServiceTime) + minimumServiceTime;
                Client randomClient = new Client(i, timeArrival, timeService);
                waitingClients.add(randomClient);
            }
            else {
                System.out.println("Error");
            }
        }
    }


    //THIS THREAD IS RESPONSIBLE FOR THE ENTIRE SIMULATION TIME
    @Override
    public void run()
    {
        while(simulationTime.intValue() <= maximumSimulationTime)
        {

            System.out.println("Time : " + simulationTime.toString());

            for(int i= 0;i<waitingClients.size();i++)
            {


                boolean waitingClientRemoved = false;
                    if(waitingClients.get(i).getTimeArrival() <= simulationTime.intValue())
                    {
                        scheduler.addInServiceQueue(waitingClients.get(i));
                        waitingClients.remove(waitingClients.get(i));
                        waitingClientRemoved = true;
                }

                    if(!waitingClientRemoved) {
                        System.out.println(waitingClients.get(i).getId() + " " + waitingClients.get(i).getTimeArrival() + " " + waitingClients.get(i).getTimeService());
                    }


            }

            for (ClientQueue queue : scheduler.getQueues())
            {
                if(queue.getQueueLenght() == 0)
                {
                    System.out.println("Queue " + queue.getQueueId() + " is Empty");

                }
                else {
                    System.out.print("Queue " + queue.getQueueId() + " ");
                    for (Client client : queue.getClientQueue()) {
                        System.out.print( client.getId());
                    }
                    System.out.println();

                }
            }


            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            simulationTime.getAndIncrement();

        }
        System.out.println("SIMULATION FINISHED!");

    }

    public ViewScreen getViewScreen() {
        return viewScreen;
    }

    public void setViewScreen(ViewScreen viewScreen) {
        this.viewScreen = viewScreen;
    }

    public ArrayList<Client> getWaitingClients() {
        return waitingClients;
    }

    public void setWaitingClients(ArrayList<Client> waitingClients) {
        this.waitingClients = waitingClients;
    }

    public int getNumberOfClients() {
        return numberOfClients;
    }

    public void setNumberOfClients(int numberOfClients) {
        this.numberOfClients = numberOfClients;
    }

    public int getNumberOfQueues() {
        return numberOfQueues;
    }

    public void setNumberOfQueues(int numberOfQueues) {
        this.numberOfQueues = numberOfQueues;
    }

    public int getMaximumSimulationTime() {
        return maximumSimulationTime;
    }

    public void setMaximumSimulationTime(int maximumSimulationTime) {
        this.maximumSimulationTime = maximumSimulationTime;
    }

    public int getMinimumArrivalTime() {
        return minimumArrivalTime;
    }

    public void setMinimumArrivalTime(int minimumArrivalTime) {
        this.minimumArrivalTime = minimumArrivalTime;
    }

    public int getMaximumArrivalTime() {
        return maximumArrivalTime;
    }

    public void setMaximumArrivalTime(int maximumArrivalTime) {
        this.maximumArrivalTime = maximumArrivalTime;
    }

    public int getMinimumServiceTime() {
        return minimumServiceTime;
    }

    public void setMinimumServiceTime(int minimumServiceTime) {
        this.minimumServiceTime = minimumServiceTime;
    }

    public int getMaximumServiceTime() {
        return maximumServiceTime;
    }

    public void setMaximumServiceTime(int maximumServiceTime) {
        this.maximumServiceTime = maximumServiceTime;
    }

    public AtomicInteger getSimulationTime() {
        return simulationTime;
    }

    public void setSimulationTime(AtomicInteger simulationTime) {
        this.simulationTime = simulationTime;
    }

    public boolean isButtonPressed() {
        return buttonPressed;
    }

    public void setButtonPressed(boolean buttonPressed) {
        this.buttonPressed = buttonPressed;
    }
}
