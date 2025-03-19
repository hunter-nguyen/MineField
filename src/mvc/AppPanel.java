package mvc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import minefield.MineHitException;
import minefield.GoalReachedException;
import minefield.MineField;

public class AppPanel extends JPanel implements Subscriber, ActionListener {
    public static int FRAME_WIDTH = 500;
    public static int FRAME_HEIGHT = 300;

    protected AppFactory factory;
    protected Model model;
    protected View view;
    protected JPanel controlPanel;
    private final JFrame frame;

    public AppPanel(AppFactory factory) {
        this.factory = factory;

        this.model = factory.makeModel();
        this.view = factory.makeView(model);
        this.controlPanel = new JPanel();

        this.setLayout(new GridLayout(1, 2));
        this.add(controlPanel);
        this.add(view);

        this.frame = new SafeFrame();
        Container pane = frame.getContentPane();
        pane.add(this);
        frame.setJMenuBar(createMenuBar());
        frame.setTitle(factory.getTitle());
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(Utilities.makeMenu("File", new String[] { "New", "Open", "Save", "Save As", "Quit" }, this));
        menuBar.add(Utilities.makeMenu("Edit", factory.getEditCommands(), this));
        menuBar.add(Utilities.makeMenu("Help", new String[] { "About", "Help" }, this));
        return menuBar;
    }

    public void display() {
        frame.setVisible(true);
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model newModel) {
        model.unsubscribe(this);
        this.model = newModel;
        model.subscribe(this);

        view.setModel(this.model);
        model.changed();
    }

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

    protected void handleException(Exception e) {
        Utilities.error(e);
    }

    public void update() {
        view.repaint();
    }
}
