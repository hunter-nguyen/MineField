import minefield.MineFieldFactory;
import mvc.AppPanel;

public class App {
    public static void main(String[] args) {
        AppPanel panel = new AppPanel(new MineFieldFactory());
        panel.display();
    }
}
