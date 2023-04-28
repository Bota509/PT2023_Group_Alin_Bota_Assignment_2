package data;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;




public class ClientQueue extends Thread{


    BlockingQueue<Client> clientQueue = new ArrayBlockingQueue<>(99999);
    //Controller controller = new Controller();
    private AtomicInteger timeArrival; //use Atomic Integer for using multiple threads
    private  AtomicInteger timeService;
    private  AtomicInteger totalTime;
    private  int id;

    public ClientQueue(int id)
    {
        this.timeService = new AtomicInteger(0);
        this.timeArrival = new AtomicInteger(0);
        this. totalTime = new AtomicInteger(0);
        this.id = id;
    }

    public synchronized void  addInQueue(Client client)
    {
        clientQueue.offer(client);
        timeArrival.addAndGet(client.getTimeArrival());
        timeService.addAndGet(client.getTimeService());
        totalTime.addAndGet(client.getTimeService());
    }
    public void run()
    {
        while (currentThread().isAlive())
        {
            if(clientQueue.size() > 0)
            {
                try {
                    Client currentClient = clientQueue.peek();
                    sleep(1000 * currentClient.getTimeService());
                    timeService.getAndAdd(-currentClient.getTimeService());

                    clientQueue.poll();

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
    public int getQueueId() {
        return id;
    }

    public AtomicInteger getTotalTime() {
        return totalTime;
    }

}