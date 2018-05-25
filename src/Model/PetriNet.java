package Model;

import MCGGeneration.*;
import WAConvertion.WeightedAutomata;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

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

    public PetriNet(File file){

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
                  //todo i think this one should be pre and not c
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

    public ArrayList<Event> getEvents(){
        ArrayList<Event> result=new ArrayList<>();
        for (Transition t:pre.getTransitions()) {
            if(!result.contains(t.getEvent())){
                result.add(t.getEvent());
            }

        }
        return result;
    }

    public Matrix getPre() {
        return pre;
    }

    public void setPre(Matrix pre) {
        this.pre = pre;
    }
    /**
     * this methode return a list of the transitions markings from the pre matrix of the unbounded place
     * @param place
     * @return
     */
    public ArrayList<Integer> getPre(String place){
        ArrayList<Integer> res=new ArrayList();
        for(int i=0;i<pre.getPlaces().length;i++){
            if(pre.getPlaces()[i].equals(place)){
                for(int j=0;j<pre.getTransitions().length;j++){
                    if(pre.getValues()[j][i]!=0){
                        res.add(pre.getValues()[j][i]);
                    }
                }
            }
        }
        Collections.sort(res);
        return res;
    }
    /**
     * delta is the sum of the enabled transitions x unbounded place in the c matrix
     * @param unboundedPlace
     * @param node
     * @param event
     * @return
     */
    public int getDelta(Place unboundedPlace,Node node,Event event){
        ArrayList<Transition>enabledTransitions=this.getEnabledTransitions(event,node);
        int delta=0;
        for (int i=0;i<c.getPlaces().length;i++){
            if(c.getPlaces()[i].equals(unboundedPlace.getName())) {
                for (int j = 0; j < c.getTransitions().length; j++) {
                    if(enabledTransitions.contains(c.getTransitions()[j])){
                        delta+=c.getValues()[i][j];
                    }
                }
            }
        }
        return delta;
    }
    public Marking getInitialMarking(Place p){
        Node n=this.getInitialMarking();
        for (Place place:n.getPlaces()) {
            if(place.getName().equals(p.getName())){
                return place.getMarking();
            }
        }
        System.out.println("place not found in get initial marking methode");
        return null;
    }

}






