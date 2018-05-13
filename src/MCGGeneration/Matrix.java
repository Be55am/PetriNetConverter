package MCGGeneration;

public class Matrix {

    private String name;
    private Transition[] transitions;
    private String[] places;
    private int[][] values;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Matrix(String name, Transition[] transitions, String[] places, int[][] values){
        this.name=name;
        this.places=places;
        this.transitions=transitions;
        this.values=values;
    }

    public Transition[] getTransitions() {
        return transitions;
    }

    public void setTransitions(Transition[] transitions) {
        this.transitions = transitions;
    }

    public String[] getPlaces() {
        return places;
    }

    public void setPlaces(String[] places) {
        this.places = places;
    }

    public int[][] getValues() {
        return values;
    }

    public void setValues(int[][] values) {
        this.values = values;
    }

    public String toString(){
        String result="MCGGeneration.Matrix "+this.name+": \n";
        for(int i=0;i<transitions.length;i++){
            result+=" "+transitions[i].toString();
        }
        result+="\n";
        for(int i=0;i<places.length;i++){
            result+=places[i]+"  ";
            for(int j=0;j<transitions.length;j++){
                result+=values[i][j]+"   ";
            }
            result+="\n";
        }
        return result;
    }
}
