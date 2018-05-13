package MCGGeneration;

import java.util.ArrayList;

public class Connection {

    private Node start;
    private Node end;

    private ArrayList<Transition> transitions;

    public Connection(Node start, Node end, ArrayList<Transition> transitions) {
        this.start = start;
        this.end = end;
        this.transitions = transitions;
    }

    public void addTransition(Transition t){
        transitions.add(t);
    }

    public Node getStart() {
        return start;
    }
    public void setStart(Node start) {
        this.start = start;
    }
    public Node getEnd() {
        return end;
    }
    public void setEnd(Node end) {
        this.end = end;
    }
    public ArrayList<Transition> getTransitions() {
        return transitions;
    }
    public void setTransitions(ArrayList<Transition> transitions) {
        this.transitions = transitions;
    }
    public String toString(){
        String result= "<link:"+start.toString()+"-->"+end.toString()+"transitions:{";
        for (Transition t:transitions) {
            result+=t+" ";
        }
        result+="} >";

        return result;
    }
}
