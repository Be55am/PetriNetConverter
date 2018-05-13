package Model;

import MCGGeneration.*;

import java.util.ArrayList;

public class PetriNet {

    private String name;
    private Matrix pre;
    private Matrix post;
    private Matrix c;
    private Node initialMarking;

    public PetriNet(String name, Matrix pre, Matrix post, Node initialMarking) {
        this.name=name;
        this.pre = pre;
        this.post = post;
        calculateC();
        this.initialMarking=initialMarking;


    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public void calculateC(){

        int[][] val=new int[pre.getPlaces().length][pre.getTransitions().length];
        for(int i=0;i<val[0].length;i++){
            for(int j=0;j<val.length;j++){
                int x=post.getValues()[j][i]-pre.getValues()[j][i];
                val[j][i]=x;
            }
        }
        c=new Matrix("C",pre.getTransitions(),pre.getPlaces(),val);
    }

    public String toString(){
        String result="net:"+name+"\n";
        result+=pre.toString()+"\n";
        result+=post.toString()+"\n";
        result+=c.toString()+"\n";
        result+=initialMarking.toString()+"\n";

        return result;
    }

    public Node getInitialMarking() {
        return initialMarking;
    }

    public void setInitialMarking(Node initialMarking) {
        this.initialMarking = initialMarking;
    }

    /**
     * return a list of the enabled transitions in the marking n
     * @param n
     * @return
     */
    public ArrayList<Transition> getEnabledTransitions(Node n){
        int sum=0;
        ArrayList result=new ArrayList();
      for(int i=0;i<pre.getTransitions().length;i++){
          boolean b=true;
          for(int j=0;j<pre.getPlaces().length;j++){
              if(n.getPlaces()[j].getMarking() instanceof IntMarking) {
                  //if the marking is an integer
                  int marking=((IntMarking)n.getPlaces()[j].getMarking()).getValue();
                  sum =   marking-pre.getValues()[j][i];
                  if(sum<0){
                      b=false;
                  }
              }else{
                  // if the marking is w marking
                  WMarking marking=(WMarking)n.getPlaces()[j].getMarking();
                  // todo im not sure about this but looks right you should check it later
                  int v=marking.getK()+marking.getR()+marking.getN();
                  sum=c.getValues()[j][i]+v;
                  if(sum<0){
                      b=false;
                  }

              }
          }
          if(b)
          result.add(pre.getTransitions()[i]);
      }
      return result;
    }

    /**
     * returns the events with the enabled transitions in the marking n
     * @param n
     * @return
     */
    public ArrayList<Event> getEnabledEvents(Node n){
        ArrayList<Event> result=new ArrayList<>();
        ArrayList<Transition> enabledTransitions=this.getEnabledTransitions(n);
        for (Transition t:enabledTransitions) {
            Model.Event e=t.getEvent();
            if(!result.contains(e)){
                result.add(e);
            }
        }
        return result;
    }

    public ArrayList<Transition> getEnabledTransitions(Event e,Node n){
        ArrayList<Transition> eTransitions=e.getTransitions();
        ArrayList<Transition> enabledTrans=this.getEnabledTransitions(n);
        ArrayList<Transition> executionTrans=new ArrayList<>();

        //getting the enabled transitions of e
        for (Transition t:eTransitions) {
            if(enabledTrans.contains(t)){
                executionTrans.add(t);
            }
        }

        return executionTrans;
    }

    public Node executeEvent(Event e, Node n){
        ArrayList<Transition> eTransitions=e.getTransitions();
        ArrayList<Transition> enabledTrans=this.getEnabledTransitions(n);
        ArrayList<Transition> executionTrans=new ArrayList<>();
        Place[] result=new Place[n.getPlaces().length];

        //getting the enabled transitions of e
        for (Transition t:eTransitions) {
           if(enabledTrans.contains(t)){
               executionTrans.add(t);
           }
        }

        //calculating the sum of c
        int[]cSum=new int[n.getPlaces().length];
        for(int i=0;i<cSum.length;i++){
            cSum[i]=0;
            for(int j=0;j<executionTrans.size();j++){
                for(int k=0;k<this.c.getTransitions().length;k++){
                    if(this.c.getTransitions()[k].equals(executionTrans.get(j))){
                        cSum[i]+=this.c.getValues()[i][k];
                    }
                }
            }

        }
        //the execution
        for(int i=0;i<cSum.length;i++){
            if(n.getPlaces()[i].getMarking() instanceof IntMarking){
                //the place has an int marking
                int x=cSum[i]+((IntMarking) n.getPlaces()[i].getMarking()).getValue();
                IntMarking marking=new IntMarking(x);
                Place p=new Place(n.getPlaces()[i].getName(),marking);
                result[i]=p;
            }else {
                // the place has wMarking
                int k=((WMarking)n.getPlaces()[i].getMarking()).getK();
                int r=((WMarking)n.getPlaces()[i].getMarking()).getR();
                int u=((WMarking)n.getPlaces()[i].getMarking()).getN();
                int x=cSum[i];

//                if(x==0){
//                    MCGGeneration.IntMarking newMarking=new MCGGeneration.IntMarking(r);
//                    MCGGeneration.Place p=new MCGGeneration.Place(n.getPlaces()[i].getName(),newMarking);
//                    result[i]=p;
//
//                }else{
                    WMarking newMarking=new WMarking(k,r+x,u);

                    Place p=new Place(n.getPlaces()[i].getName(),newMarking);
                    result[i]=p;
//                }
//



            }

        }
        return new Node(null,result);

    }

}






