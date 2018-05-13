package MCGGeneration;

public class Place {

    private String name;
    private Marking m;

    public Place(String name, Marking m) {
        this.name = name;
        this.m = m;
    }

    public String toString(){
        return m.toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Marking getMarking() {
        return m;
    }

    public boolean isWmarking(){
        if (this.getMarking() instanceof WMarking)
            return true;
        else return false;
    }

    public void setM(Marking m) {
        this.m = m;
    }

    public boolean isEqual(Place p){
        if(p.getMarking() instanceof WMarking & this.m instanceof WMarking){
            //THE tow places are wMarking
//            MCGGeneration.WMarking m1=(MCGGeneration.WMarking)m;
//            MCGGeneration.WMarking m2=(MCGGeneration.WMarking)p.getMarking();
//            if(m1.getK()==m2.getK()&m1.getR()==m2.getR()){
                return true;
//            }else
//                return false;

        }else if(p.getMarking() instanceof IntMarking & this.m instanceof IntMarking){
            IntMarking m1=(IntMarking) m;
            IntMarking m2=(IntMarking) p.getMarking();
            if(m1.getValue()==m2.getValue()){
                return true;
            }else return false;
        }else
            return false;

    }

    public boolean inferior(Place p){
        if(m instanceof IntMarking){
            if(p.getMarking() instanceof IntMarking){
                // the tow markings are Int
                if(((IntMarking) m).getValue()<((IntMarking) p.getMarking()).getValue()){
                    return true;
                }else
                    return false;

            }else{
                // the p marking is wMarking
                return true;
            }
        }else {
            if(p.getMarking() instanceof IntMarking){
                //the m is w and p is int
                return false;

            }else{
                // the tow are w marking
                return false;
            }

        }
    }
}
