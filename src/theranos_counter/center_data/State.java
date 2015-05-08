package theranos_counter.center_data;

import java.util.ArrayList;

/**
 * Created by Forrest on 5/7/2015.
 */
public class State {
    public String name;
    public ArrayList<City> cities = new ArrayList<>();

    public State(String name) {
        this.name = name;
    }
}
