package controller;

import data.Client;
import view.ViewScreen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class Controller extends Thread {
    private ViewScreen viewScreen;
    private ArrayList<Client>  clients = new ArrayList<>();
    private int numberOfClients;
    private int numberOfQueues;
    private int maximumSimulationTime;
    private int minimumArrivalTime;
    private int maximumArrivalTime;
    private int minimumServiceTime;
    private int maximumServiceTime;

    public Controller(ViewScreen viewScreen) {
        this.viewScreen = viewScreen;


        this.viewScreen.submitListener(new SubmitListener());
    }

    class SubmitListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            read();
            randomClientGenerator();
            for (Client client : clients)
            {
                System.out.println(client.getId() + " " + client.getTimeArrival() + " " + client.getTimeService());
            }
        }
    }

    private  void read()
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

    private void randomClientGenerator()
    {

        Random random = new Random();
        for (int i=1;i<=numberOfClients;i++)
        {
            //genereate n random clients
            if(maximumArrivalTime > minimumArrivalTime && maximumServiceTime > minimumServiceTime) {
                int timeArrival = random.nextInt(maximumArrivalTime - minimumArrivalTime) + minimumArrivalTime;
                int timeService = random.nextInt(maximumServiceTime - minimumServiceTime) + minimumServiceTime;
                int id = i;
                Client randomClient = new Client(i, timeArrival, timeService);
                clients.add(randomClient);
            }
            else {
                System.out.println("Error");
            }

        }

    }

    public ViewScreen getViewScreen() {
        return viewScreen;
    }

    public void setViewScreen(ViewScreen viewScreen) {
        this.viewScreen = viewScreen;
    }

    public ArrayList<Client> getClients() {
        return clients;
    }

    public void setClients(ArrayList<Client> clients) {
        this.clients = clients;
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
}
