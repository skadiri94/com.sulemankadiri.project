public class Timer implements java.lang.Runnable{

    private String time;
    public Timer(){
        run();
        //runTimer(time);
        System.out.println(time);

    }

    @Override
    public void run() {
        //this.runTimer();
    }

    public void runTimer(){
       String timer ="";
        int i = 86400;
        int j = 00;
        int k = 00;
        while (i>0){
            timer = "Time: "+j +":"+k;
            //System.out.println(timer);
            try {
                i--;
                k++;
                if(k==60) {
                    j++;
                    k=0;
                }
                Thread.sleep(1000L);    // 1000L = 1000ms = 1 second
            }
            catch (InterruptedException e) {
                //I don't think you need to do anything for your particular problem
            }
        }
    }

}