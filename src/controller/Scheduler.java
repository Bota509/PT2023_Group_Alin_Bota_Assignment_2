package controller;

import data.Client;
import data.ClientQueue;

import java.util.ArrayList;
import java.util.List;

public class Scheduler {


   private List<ClientQueue> queues = new ArrayList<>();
   private  Controller controller = new Controller();


   //create the same number of threads as number of queues where put
   public Scheduler(int numberOfQueues)
   {
       for(int i=1;i<= numberOfQueues;i++)
       {
           ClientQueue queue = new ClientQueue(i);
           queues.add(queue);
           queue.start();
       }
   }


   public void  addInServiceQueue(Client client)
   {

       int minim = Integer.MAX_VALUE;
       ClientQueue auxClientQueue = new ClientQueue(-1);
       for(ClientQueue queue : queues)
       {
           if(minim >  queue.getQueueLenght())
           {
                    minim = queue.getQueueLenght();
                    auxClientQueue = queue;

           }
       }
         auxClientQueue.addInQueue(client);
   }


    public List<ClientQueue> getQueues() {
        return queues;
    }

    public void setQueues(List<ClientQueue> queues) {
        this.queues = queues;
    }
}