package WAConvertion;

import MCGGeneration.IntMarking;
import MCGGeneration.Place;
import Model.Event;

import java.util.ArrayList;

public class WeightedAutomata {

    private String name;
    private ArrayList<Node> nodeList;
    private ArrayList<Link> linkList;
    private ArrayList<Event> alphabet;
    private ArrayList<Integer> delta;
    private ArrayList<Interval> intervalsList;

    public WeightedAutomata(String name) {
        this.name = name;
        nodeList=new ArrayList<>();
        linkList=new ArrayList<>();
        alphabet=new ArrayList<>();
        delta=new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Node> getNodeList() {
        return nodeList;
    }

    public ArrayList<Link> getLinkList() {
        return linkList;
    }

    public void setNodeList(ArrayList<Node> nodeList) {
        this.nodeList = nodeList;
    }

    public void setLinkList(ArrayList<Link> linkList) {
        this.linkList = linkList;
    }

    public ArrayList<Event> getAlphabet() {
        return alphabet;
    }

    public void setAlphabet(ArrayList<Event> alphabet) {
        this.alphabet = alphabet;
    }

    public ArrayList<Integer> getDelta() {
        return delta;
    }

    public void setDelta(ArrayList<Integer> delta) {
        this.delta = delta;
    }

    public ArrayList<Interval> getIntervalsList() {
        return intervalsList;
    }

    public void setIntervalsList(ArrayList<Interval> intervalsList) {
        this.intervalsList = intervalsList;
    }

    //todo recheck
    public ArrayList<Interval> intervalGenerator(ArrayList<Integer> val){
        ArrayList<Interval> res=new ArrayList<>();
        Interval interval0 =new Interval(0,val.get(0)-1);

        res.add(interval0);
        for (int i=0;i<val.size()-1;i++) {
            Interval interval=new Interval(val.get(i),val.get(i+1)-1);
            res.add(interval);

        }
        Interval last=new Interval(val.get(val.size()-1),999999999);
        res.add(last);

        return res;

    }

    public void addNode(Node n){
        nodeList.add(n);
    }

    public void addLink(Link link){
        linkList.add(link);
    }

    public Interval getInterval(int p){
        for (Interval i:intervalsList) {
            if(p>=i.getMin()){
                if(p<=i.getMax()){
                    return i;
                }
                else if(i.getMax()==999999999){
                    return i;
                }
            }
        }
        System.out.println("we cant find this interval : getInterval ()");
        return null;
    }

    public int getIntervalPos(int num){
        for(int i=0;i<intervalsList.size();i++){
            Interval interval=intervalsList.get(i);
            if(num>=interval.getMin()){
                if(num<=interval.getMax()){
                    return i;
                }
                else if(interval.getMax()==999999999){
                    return i;
                }
            }
        }
        System.out.println("interval not found");
        return -0;
    }

    public Node getNewNode(){
        for (Node n:nodeList) {
            if(n.isNewTag()){
                return n;
            }
        }

        return null;
    }


    public boolean exists(Node n,Place unboundedPlace){

        for (Node node:nodeList) {

            if(node.exists(n,unboundedPlace)){
                    return true;
            }

        }
        return false;
    }


    public String print(){
        String result="<Automate:"+getName()+">\n";

        for (Node n:this.getNodeList()) {
            result+=n.print()+"\n";
        }
        for (Link l:this.getLinkList()) {
            result+=l.print();
        }
        result+="</Automate>\n";

        return result;
    }

}
