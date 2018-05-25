package WAConvertion;

import MCGGeneration.IntMarking;
import MCGGeneration.Place;
import Model.Event;
import Model.PetriNet;

import java.util.ArrayList;
import java.util.Collections;

public class Converter {

    private WeightedAutomata automat;

    public Converter(String automateName){
        this.automat=new WeightedAutomata(automateName);
    }

    public WeightedAutomata Convert(PetriNet net, Place unboundedPlace){
        //1
        this.automat.setAlphabet(net.getEvents());
        //2

        automat.setIntervalsList(automat.intervalGenerator(net.getPre(unboundedPlace.getName())));
        for (Interval d:automat.getIntervalsList()) {
            System.out.println(d.print());
        }

        //3
        int energy=automat.getIntervalPos(((IntMarking)unboundedPlace.getMarking()).getValue());
        Node n0=new Node("l0",getM0(net,unboundedPlace),unboundedPlace,automat.getInterval(energy),energy,true);
        System.out.println("interval : min ="+n0.getSafe().getMin()+"   max ="+n0.getSafe().getMax()+"\n");
        automat.addNode(n0);

        //4

        int num=1;
        while (automat.getNewNode()!=null){
            //4.1
            System.out.println("qmsdlkfjqsfmqlsdkj");
            Node node=automat.getNewNode();
            //4.2
            for (Event e:automat.getAlphabet()) {
                //4.2.1
                String d=e.getName();

                MCGGeneration.Node newNode=net.executeEvent(e,node);


                Node waNewNode=newNode.convertToWANode(unboundedPlace);

                System.out.println("node ="+node.print());
                System.out.println(waNewNode.print());

                int deltaM=net.getDelta(unboundedPlace,node,e);
                //4.2.2
                //todo
                //System.out.println("bla bal bal"+node.getEnergy());
                int i=automat.getIntervalPos(((automat.getIntervalsList().get(node.getEnergy())).getMin())+deltaM);
                int j=automat.getIntervalPos(((automat.getIntervalsList().get(node.getEnergy())).getMax())+deltaM);
                System.out.println("delta = "+deltaM+" i= "+i+" j="+j);
                //4.2.3
                for(int y=i;y<=j;y++){
                    //4.2.3.1
                    Node newN=new Node(null,waNewNode.getPlaces(),unboundedPlace,null,y,true);
                    //waNewNode.setEnergy(y);
                  //  automat.addNode(waNewNode);
                    //4.2.3.2
                    Link link=new Link(deltaM,e,node,newN);
                    automat.addLink(link);
                    //4.2.3.3
                    if(automat.exists(newN,unboundedPlace)){
                        System.out.println(automat.exists(newN,unboundedPlace));
                        newN.setNewTag(false);


                    }else{
                        newN.setNewTag(true);
                        newN.setSafe(automat.getIntervalsList().get(y));/////////
                        newN.setName("l"+num);
                        num++;
                        automat.addNode(newN);

                    }


                }


            }
            //4.3
            node.setNewTag(false);

        }
        return automat;
    }


    public Place[] getM0(PetriNet net,Place unboundedPlace){
       ArrayList<Place>result=new ArrayList<>();
//       String sqmdlfk=unboundedPlace.getName();
//
        for (MCGGeneration.Place p:net.getInitialMarking().getPlaces()) {
            if(!p.getName().equals(unboundedPlace.getName())){
               Place waP=new Place(p.getName(),((IntMarking) p.getMarking()));
                result.add(waP);
            }else {
                Place unBounded=new Place(unboundedPlace.getName(),new IntMarking(0));
                result.add(unBounded);
            }

        }

        Place[] res=new Place[result.size()];
        for(int i=0;i<result.size();i++){
            res[i]=result.get(i);
        }

        return res;
    }
}
