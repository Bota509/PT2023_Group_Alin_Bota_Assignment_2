package data;

public class Client {

    private int id; //number between 1 and N
    private int timeArrival; //simulation time when they are ready to enter the queue
    private  int time; //time interval or duration needed to serve the client; i.e. waiting time when the client is in front of the queue

    public Client(int id, int timeArrival, int time) {
        this.id = id;
        this.timeArrival = timeArrival;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTimeArrival() {
        return timeArrival;
    }

    public void setTimeArrival(int timeArrival) {
        this.timeArrival = timeArrival;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
