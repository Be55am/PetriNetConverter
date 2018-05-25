package WAConvertion;

import java.util.ArrayList;

public class Interval {
    private int min;
    private int Max;


    public Interval(int min,int max){
        this.setMin(min);
        this.setMax(max);
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        if(min<0)
            System.out.println("intervals should have positive values !");
        else
            this.min = min;
    }

    public int getMax() {

            return Max;
    }

    public void setMax(int max) {
        if(max<0)
            System.out.println("intervals should have positive values !");
        else
            Max = max;
    }

    public String print(){
        return "["+this.getMin()+","+this.getMax()+"]";
    }


}
