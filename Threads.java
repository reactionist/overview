package clientserver;

public class Threads implements Runnable {
    private Thread thread;
    private String tName;
    private boolean end;

    public Threads(String threadName)
    {
        tName = threadName;
        thread = new Thread(this, tName);
        System.out.println("New thread: " + thread);
        end = false;
        thread.start();
    }

    public void stop()
    {
        end = true;
    }
    @Override
    public void run() {
        int i = 0;
        while (!end) {
            System.out.println(tName + ": " + i);
            i++;
            try {
                Thread.sleep(10);
            }
            catch (InterruptedException e) {
                System.out.println("Caught:" + e);
            }
        }
        System.out.println(tName + " Stopped.");
    }
}
