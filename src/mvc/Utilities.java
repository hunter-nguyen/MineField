package mvc;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Random;

public class Utilities {

    // asks user a yes/no question
    public static boolean confirm(String query) {
        int result = JOptionPane.showConfirmDialog(null,
                query, "choose one", JOptionPane.YES_NO_OPTION);
        return result == 0;
    }

    // asks user for info
    public static String ask(String query) {
        return JOptionPane.showInputDialog(null, query);
    }

    // tells user some info
    public static void inform(String info) {
        JOptionPane.showMessageDialog(null,info);
    }

    // tells user some info
    public static void inform(String info, String title) {
        JOptionPane.showMessageDialog(null,info, title, JOptionPane.INFORMATION_MESSAGE);
    }

    // tells user a lot of info
    public static void inform(String[] items) {
        String helpString = "";
        for(int i = 0; i < items.length; i++) {
            helpString = helpString + "\n" + items[i];
        }
        inform(helpString);
    }

    // tells user a lot of info
    public static void inform(String[] items, String title) {
        inform(items, title);
    }

    // tells user about an error
    public static void error(String gripe) {
        JOptionPane.showMessageDialog(null,
                gripe,
                "OOPS!",
                JOptionPane.ERROR_MESSAGE);
    }

    // tells user about an exception
    public static void error(Exception gripe) {
        gripe.printStackTrace();
        JOptionPane.showMessageDialog(null,
                gripe.getMessage(),
                "OOPS!",
                JOptionPane.ERROR_MESSAGE);
    }

    // asks user to save changes
    public static void saveChanges(Model model) {
        if (model.getUnsavedChanges() &&
                !Utilities.confirm("current model has unsaved changes, continue?")) {
            Utilities.save(model, false);
        }
    }

    /**
     * Confirms whether the user wants to continue, save changes or leave unsaved changes
     * @param model The model to save
     * @return Whether to continue
     */
    public static boolean confirmSaveChanges(Model model) {
        if (!model.getUnsavedChanges()) return true;

        int result = JOptionPane.showConfirmDialog(null, "Current model has unsaved changes, Save?", "Confirm", JOptionPane.YES_NO_CANCEL_OPTION);

        switch (result) {
            case JOptionPane.CANCEL_OPTION:
                return false;

            case JOptionPane.YES_OPTION:
                Utilities.save(model, false);
            case JOptionPane.NO_OPTION:
                return true;

            default:
                return false;
        }
    }

    // asks user for a file name
    public static String getFileName(String fName, Boolean open) {
        JFileChooser chooser = new JFileChooser();
        String result = null;
        if (fName != null) {
            // open chooser in directory of fName
            chooser.setCurrentDirectory(new File(fName));
        }
        if (open) {
            int returnVal = chooser.showOpenDialog(null);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
                result= chooser.getSelectedFile().getPath();
            }
        } else {
            int returnVal = chooser.showSaveDialog(null);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
                result= chooser.getSelectedFile().getPath();
            }
        }
        return result;
    }

    // save model
    public static boolean save(Model model, Boolean saveAs) {
        String fName = model.getFileName();
        if (fName == null || saveAs) {
            fName = getFileName(fName, false);
            if (fName == null) return false;
            model.setFileName(fName);
        }
        try {
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fName));
            model.setUnsavedChanges(false);
            os.writeObject(model);
            os.close();
            return true;
        } catch (Exception err) {
            model.setUnsavedChanges(true);
            Utilities.error(err);
        }
        return false;
    }

    // open model
    public static Model open(Model model) {
        if (!confirmSaveChanges(model)) return null;
        String fName = getFileName(model.getFileName(), true);
        if (fName == null) return null;
        Model newModel = null;
        try {
            ObjectInputStream is = new ObjectInputStream(new FileInputStream(fName));
            newModel = (Model)is.readObject();
            is.close();
        } catch (Exception err) {
            Utilities.error(err);
        }
        return newModel;
    }

    // simple menu maker
    public static JMenu makeMenu(String name, String[] items, ActionListener handler) {
        JMenu result = new JMenu(name);
        for(int i = 0; i < items.length; i++) {
            JMenuItem item = new JMenuItem(items[i]);
            item.addActionListener(handler);
            result.add(item);
        }
        return result;
    }

    // random number generator
    public static Random rng = new Random(System.currentTimeMillis());

    public static void log(String msg) {
        System.out.println(msg); // for now
    }

    private static int nextID = 100;
    public static int getID() {
        return nextID++;
    }

}
