import machines.HabitatController;
import machines.HabitatModel;
import machines.HabitatView;

public class Main {

    public static void main(String[] args) {
        HabitatView view = new HabitatView(1000, 600, 10, 10);
        HabitatModel habitat = new HabitatModel(0.5, 0.5, 1,1,5,5);
        HabitatController controllers = new HabitatController(view, habitat);
    }
}
