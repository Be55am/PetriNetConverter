package MCGGeneration;

import MCGGeneration.Marking;

public class WMarking implements Marking {

    private int k;
    private int r;
    private int n;

    public WMarking(int k, int r, int n) {
        this.k = k;
        this.r = r;
        this.n = n;
    }

    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }



    public String toString(){
        return k+"W"+n+"+"+r;
    }
}
