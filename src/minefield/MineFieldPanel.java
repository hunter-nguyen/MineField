package minefield;


import javax.swing.*;
import java.awt.*;


import mvc.AppFactory;
import mvc.AppPanel;

public class MineFieldPanel extends AppPanel {
    
    public MineFieldPanel(AppFactory factory) {
        super(factory);
        controlPanel.setLayout(new GridLayout(4, 2)); 
        
        for (String cmd : factory.getEditCommands()) {
            JPanel p = new JPanel();
            JButton button = new JButton(cmd);
            p.add(button);
            button.addActionListener(this);
            controlPanel.add(p);
        }
    }

    public static void main(String[] args) {
        AppFactory factory = new MineFieldFactory();
        MineFieldPanel panel = new MineFieldPanel(factory);

        panel.display();
    }
}
