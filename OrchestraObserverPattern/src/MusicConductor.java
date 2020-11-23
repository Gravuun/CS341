import java.util.ArrayList;

public class MusicConductor implements Subject {
    private ArrayList<Observer> observers = new ArrayList<>();

    public void attach(Observer o){
        observers.add(o);
    }

    public void detach(Observer o){
        observers.remove(o);
    }

    public void notifyUpdate(StateChange sc) {
        for(Observer o: observers){
            o.update(sc);
        }
    }
}