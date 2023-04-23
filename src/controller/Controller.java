package controller;

import data.Client;
import view.ViewScreen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {
    private ViewScreen viewScreen;
    private Client client;
    private int numberOfClients;
    private int numberOfQueues;
    private int maximumSimulationTime;
    private int minimumArrivalTime;
    private int maximumArrivalTime;
    private  int minimumServiceTime;
    private  int maximumServiceTime;

    public Controller(ViewScreen viewScreen, Client client) {
        this.viewScreen = viewScreen;
        this.client = client;

        this.viewScreen.submitListener(new SubmitListener());
    }

    class SubmitListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e) {
            read();
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

    public ViewScreen getViewScreen() {
        return viewScreen;
    }

    public void setViewScreen(ViewScreen viewScreen) {
        this.viewScreen = viewScreen;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
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
