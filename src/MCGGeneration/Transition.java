package MCGGeneration;
import Model.*;

public class Transition {

    private String name;
    private Event event;

    public Transition(String name,Event e){
        this.name=name;
        this.event=e;
        e.addTransition(this);

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String toString(){
        return this.name+":"+event.getName();
    }
}
