package tets;

public class Threds extends  Thread{

private  int threadNumber;
  public Threds(int threadNumber)
  {
      this.threadNumber  =threadNumber;
  }
    @Override
public  void run()
{
    for(int i=0;i<5;i++)
    {
        System.out.println(i + " from thred " + threadNumber );

        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }


}
}
