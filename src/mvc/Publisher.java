package mvc;

import java.util.ArrayList;
import java.util.List;

public abstract class Publisher {
        List<Subscriber> Subscribers = new ArrayList<>();

    public void notifySubscribers(){
        for(Subscriber s : Subscribers){
            s.update();
        }
    }

    public void subscribe(Subscriber subscriber){
        Subscribers.add(subscriber);
    }

    public void unsubscribe(Subscriber subscriber){
        Subscribers.remove(subscriber);
    }
}
