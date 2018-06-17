package data;

import machines.HabitatView;

public abstract class BaseAI extends Thread{
    public String threadName;
    public final Object obj = new Object();
    public BaseAI(String threadName) {
        this.threadName = threadName;
    }
    public Boolean paused = Boolean.TRUE;

    @Override
    public void run() {
        while (HabitatView.isGoing){
            synchronized (obj){
                if (paused){
                    try {
                        obj.wait();
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            }
            try {
                this.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            nextStep();
        }
    }
    abstract void nextStep();
}
