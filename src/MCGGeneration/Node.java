package MCGGeneration;

import java.util.ArrayList;

public class Node {

    private String name;
    private boolean newTag;
    private Place[] places;

    public Node(String name, Place[] places) {
        this.name = name;
        this.places = places;
    }

    /**
     * return true if the new tag is true.
     * @return
     */
    public boolean isNewTag() {
        return newTag;
    }

    public void setNewTag(boolean newTag) {
        this.newTag = newTag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Place[] getPlaces() {
        return places;
    }

    public void setPlaces(Place[] places) {
        this.places = places;
    }

    public String toString(){
        String result="<node:"+name+" marking=[";
        for (Place p:places) {
            result+=p.toString()+" ";
        }
        result+="]>";
        return result;
    }

    public boolean isEqual(Node n){
        for(int i=0;i<places.length;i++){
            if(!places[i].isEqual(n.getPlaces()[i]))
                return false;
        }
        return true;
    }

    public boolean containsWMarking(){
        for (Place p:places) {
            if(p.isWmarking())
                return true;

        }
        return false;
    }

    public ArrayList<Place> inferior(Node n){
        ArrayList<Place>result=new ArrayList<>();
        boolean b=false;
        for(int i=0;i<places.length;i++){
            Place[] x=places;
            x[i].toString();
            if(places[i].inferior(n.getPlaces()[i])){
                b=true;
                result.add(places[i]);
            }else if(places[i].isEqual(n.getPlaces()[i])){

            }else
                return null;
        }
        if(b)
        return result;
        else
            return null;
    }


}
