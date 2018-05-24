package WAConvertion;

import Model.Event;

public class Link {
    private int energy;
    private Event event;
    private Node start;
    private Node end;

    public Link(int energy, Event event, Node start, Node end) {
        this.energy = energy;
        this.event = event;
        this.start = start;
        this.end = end;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
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

    public String print(){
        return "<Link: "+this.getStart().print()+"----"+this.getEnd().print()+" " +
                "event : "+this.getEvent().getName()+" Energy : "+this.getEnergy()+" />\n";
    }
}
