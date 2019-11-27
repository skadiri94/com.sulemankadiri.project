/*
  https://stackoverflow.com/questions/31759972/timer-delay-method-for-creating-stop-watch-in-java
 */

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class myTimer extends JLabel {

    private String displayTime;
    private Timer refreshRate;
    private int milliseconds;
    private int seconds;
    private int minutes;
    private int hours;

    public myTimer(int delay){

        refreshRate = new Timer(delay, new ActionListen());
        start();

    }

    private class ActionListen implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(refreshRate)) {
                setMillSeconds(milliseconds);
                setSeconds(seconds);
                setMinutes(minutes);
                setHours(hours);

                setDisplayTime();
                setText(displayTime);
            }
        }
    }
    private void setMillSeconds(int milliseconds){
        milliseconds+=100;
        if(milliseconds >= 1000){
            milliseconds = 0;
            seconds++;
        }
        this.milliseconds = milliseconds;
    }

    private void setSeconds(int seconds){
        if(seconds > 59){
            seconds = 0;
            minutes++;
        }
        this.seconds = seconds;
    }
    private void setMinutes(int minutes){
        if(minutes > 59){
            minutes = 0;
            hours++;
        }
        this.minutes = minutes;
    }
    private void setHours( int hours){
        if(hours > 23){
            hours = 0;
        }
        this.hours = hours;
    }
    private void setDisplayTime(){

        displayTime= (getHours() + ":" +getMinutes()+ ":" +getSeconds()+ "." +getMilliseconds()/100);
    }

    public String getDisplayTime() {
        return displayTime;
    }

    public Timer getRefreshRate() {
        return refreshRate;
    }

    private int getMilliseconds() {
        return milliseconds;
    }

    private int getSeconds() {
        return seconds;
    }

    private int getMinutes() {
        return minutes;
    }

    private int getHours() {
        return hours;
    }

    public void start(){
        refreshRate.start();
    }

    public void reSet(){
        displayTime = "00:00:00";
        milliseconds = 0;
        seconds = 0;
        minutes = 0;
        hours = 0;
    }

    public void stop(){
        refreshRate.stop();
    }


}
