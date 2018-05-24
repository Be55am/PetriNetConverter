import MCGGeneration.*;
import Model.Event;
import Model.PetriNet;
import WAConvertion.Converter;
import WAConvertion.WeightedAutomata;

public class Main {

    public static void main(String[] args){
        Event e1=new Event("a");
        Event e2=new Event("b");
        //Event e3=new Event("c");
        Transition t1=new Transition("t1",e1);
        Transition t2=new Transition("t2",e1);
        Transition t3=new Transition("t3",e2);
        Transition t4=new Transition("t4",e1);
        String [] places={"p1","p2","p3","p4"};
        Transition [] transitions={t1,t2,t3,t4};
        int [][] val={{1,0,0,0},{0,1,0,0},{1,0,1,0},{0,0,0,2}};
        Matrix pre=new Matrix("Pre",transitions,places,val);
        int [][] val2={{0,1,0,0},{1,0,0,0},{1,0,1,0},{0,0,3,0}};
        Matrix post=new Matrix("Post",transitions,places,val2);
        Place p1=new Place("p1",new IntMarking(1));
        Place p2=new Place("p2",new IntMarking(0));
        Place p3=new Place("p3",new IntMarking(1));
        Place p4=new Place("p4",new IntMarking(0));

        Place[] listP= {p1,p2,p3,p4};
        Node m0=new Node("M0",listP);
        PetriNet net=new PetriNet("net",pre,post,m0);

        System.out.println(net.toString());

        MCGGenerator gen=new MCGGenerator("GRAPH");
        System.out.println(gen.generateMCG(net).toString());
        MCG graph=gen.generateMCG(net);
        Place unbounded=new Place(graph.getUnboundedPlaces().getName(),net.getInitialMarking(graph.getUnboundedPlaces()));

        // MCGGeneration.MCG creation

        System.out.println("-----------------------------------------------------------");

        Converter c=new Converter("WA");
        WeightedAutomata wa=c.Convert(net,unbounded);
        System.out.println(wa.print());







    }
}
