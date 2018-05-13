package MCGGeneration;

import java.util.ArrayList;
import Model.*;

public class MCGGenerator {


    private MCG graph;


    public MCGGenerator(String graphName){
        graph=new MCG(graphName);
    }



    public  MCG generateMCG(PetriNet net){




        //1
        Node m0=net.getInitialMarking();
        m0.setNewTag(true);
        graph.addNode(m0);
        ArrayList<Place>unboudedPlaces=new ArrayList<>();
        //2
        Event loopEvent=null;

        while (graph.getNewNode()!=null) {
            //2.1
            Node n = graph.getNewNode();
            n.setNewTag(false);
            //2.2
            ArrayList<Event>events=net.getEnabledEvents(n);
            if(unboudedPlaces.size()>1){
                System.out.println("this net has more than one unbounded places !");
                return null;
            }


            for (int l=0;l<events.size();l++) {
                Event e=events.get(l);
                System.out.println("why this happening");
                //2.2.1
                Node newNode=null;
                if(!e.equals(loopEvent)) {
                    newNode = net.executeEvent(e, n);


                    Connection c = new Connection(n, newNode, net.getEnabledTransitions(e, n));
                    graph.addConnection(c);
                    //2.2.2
                    // 2.2.2.1

//                    if (n.containsWMarking() & !newNode.containsWMarking()) {
//                        // if q is wmarking and Q' isnt
//
//                    }
                    //2.2.2.2
                    if (!graph.duplicated(newNode)) {
                        newNode.setNewTag(true);
                        graph.addNode(newNode);

                        //2.2.2.3
                        if (n.inferior(newNode) != null) {// the transitions should be the same
                            Node q = graph.getPrecedentQ(n, c.getTransitions());
                            if (q != null) {
                                for (int i = 0; i < q.getPlaces().length; i++) {
                                    if (q.getPlaces()[i].inferior(n.getPlaces()[i])) {
                                        if (n.getPlaces()[i].inferior(newNode.getPlaces()[i])) {
                                            int k = ((IntMarking) newNode.getPlaces()[i].getMarking()).getValue() - ((IntMarking) n.getPlaces()[i].getMarking()).getValue();
                                            int r = ((IntMarking) newNode.getPlaces()[i].getMarking()).getValue() % k;
                                            int nn = ((IntMarking) newNode.getPlaces()[i].getMarking()).getValue() / k;
                                            // i made n =0
                                            WMarking wm = new WMarking(k, r, nn);
                                            newNode.getPlaces()[i].setM(wm);
                                            unboudedPlaces.add(newNode.getPlaces()[i]);
                                            loopEvent = e;

                                            Connection cc=new Connection(newNode,newNode,net.getEnabledTransitions(e,n));
                                            graph.addConnection(cc);
                                            // newNode.setNewTag(false);

                                        }
                                    }
                                }
                            }

                        }


                    }

                    if (newNode.containsWMarking()) {
                        //  l=events.size();
                    }
                }
            }



        }



        return graph;
    }
}
