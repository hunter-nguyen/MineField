package mvc;

import javax.swing.JPanel;

public class View extends JPanel implements Subscriber{

    protected Model model;

    public View(Model model){
        this.model = model;
        this.model.subscribe(this);
    }

    public void setModel(Model model){
        this.model.unsubscribe(this);
        this.model = model;
        this.model.subscribe(this);
    }


    @Override
    public void update() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

}
