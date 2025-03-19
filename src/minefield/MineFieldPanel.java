package minefield;


import mvc.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

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

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        try {
            String command = actionEvent.getActionCommand();
            switch (command) {
                case "New":
                    if (Utilities.confirmSaveChanges(model)) {
                        setModel(factory.makeModel());
                        model.setUnsavedChanges(false);
                    }
                    break;
                case "Open":
                    Model newModel = Utilities.open(model);
                    if (newModel != null) {
                        setModel(newModel);
                        model.setUnsavedChanges(false);
                    }
                    break;
                case "Save":
                    Utilities.save(model, false);
                    break;
                case "Save As":
                    Utilities.save(model, true);
                    break;
                case "Quit":
                    if (Utilities.confirmSaveChanges(model))
                        System.exit(0);
                    break;

                case "About":
                    Utilities.inform(factory.about(), "About");
                    break;
                case "Help":
                    Utilities.inform(factory.getHelp(), "Help");
                    break;

                default:
                    if (model instanceof MineField && ((MineField) model).isGameOver()) {
                        JOptionPane.showMessageDialog(this, "Game is over! Cannot move.", "Game Over",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    Command editCommand = factory.makeEditCommand(model, command, actionEvent.getSource());
                    if (editCommand != null) {
                        editCommand.execute();
                    }
            }
        } catch (MineHitException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Game Over", JOptionPane.ERROR_MESSAGE);
        } catch (GoalReachedException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            handleException(e);
        }
    }

    public static void main(String[] args) {
        AppFactory factory = new MineFieldFactory();
        MineFieldPanel panel = new MineFieldPanel(factory);

        panel.display();
    }
}
