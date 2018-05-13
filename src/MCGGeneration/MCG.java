package MCGGeneration;

import MCGGeneration.Connection;

import java.util.ArrayList;

public class MCG {

    private String name;
    private ArrayList <Node> nodes;
    private ArrayList <Connection> connections;


    public MCG(String name) {
        this.name = name;
        this.nodes = new ArrayList<>();
        this.connections = new ArrayList<>();
    }
    public void addNode(Node node){
        nodes.add(node);
    }
    public void addConnection(Connection c){
        connections.add(c);
    }
    public  String toString(){
        String result="coverability graph :"+name;
        result +="\n<Tree:"+this.name+">\n";
        for (Node node:nodes) {

            result+="   "+node.toString()+"\n";

        }
        for (Connection connection:connections) {
            result+="   "+connection.toString()+"\n";
        }



        result+="\n</Tree>\n";
        return result;
    }

    public boolean duplicated(Node n) {
        for (Node node:nodes) {
            if(node.isEqual(n))
                return true;
        }
        return false;
    }

    public Node getPrecedentQ(Node qPrim, ArrayList<Transition>t){
        for (Connection c:connections) {
            if(c.getEnd().equals(qPrim)){
                if(c.getTransitions().equals(t)){
                    if(c.getStart().inferior(qPrim)!=null){
                        return c.getStart();
                    }
                }
            }
        }
        return null;
    }

    /**
     * return new node if it exists else null
     * @return
     */
    public Node getNewNode(){
        for (Node n:nodes) {
            if(n.isNewTag()){
                return n;
            }
        }
        return null;
    }
}
