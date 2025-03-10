package mvc;

import java.io.Serializable;

public abstract class Model extends Publisher implements Serializable{
    boolean unsavedChanges = false;
    String fileName = null;

    public void changed(){
        unsavedChanges = true;
        notifySubscribers();
    }

    public boolean getUnsavedChanges() {
        return unsavedChanges;
    }

    public void setUnsavedChanges(boolean unsavedChanges) {
        this.unsavedChanges = unsavedChanges;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}
