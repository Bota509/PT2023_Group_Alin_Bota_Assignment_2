package data;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.sleep;


public class ClientQueue extends Thread{

    BlockingQueue<Client> clientQueue = new ArrayBlockingQueue<>(2000);
    //Controller controller = new Controller();
    private AtomicInteger timeArrival; //use Atomic Integer for using multiple threads
    private  AtomicInteger timeService;
    private AtomicInteger totalSimulationTime;
    private  int id;

    public ClientQueue(int id)
    {
        this.timeService = new AtomicInteger(0);
        this.timeArrival = new AtomicInteger(0);
        this.totalSimulationTime = new AtomicInteger(0);
        this.id = id;
    }

    public void addInQueue(Client client)
    {
        clientQueue.offer(client);
        timeArrival.addAndGet(client.getTimeArrival());
        timeService.addAndGet(client.getTimeService());


    }


    public void run()
    {

        while (currentThread().isAlive())
        {
            if(clientQueue.size() > 0)
            {
                try {
                    Client currentClient = clientQueue.peek();
                    this.sleep(1000 * currentClient.getTimeService());
                    clientQueue.poll();
                    timeService.getAndAdd(-currentClient.getTimeService());

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }

        }


    }

    public BlockingQueue<Client> getClientQueue() {
        return clientQueue;
    }
    public int getQueueLenght()
    {
        return clientQueue.size();
    }

    public void setClientQueue(BlockingQueue<Client> clientQueue) {
        this.clientQueue = clientQueue;
    }

    public int getQueueId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
