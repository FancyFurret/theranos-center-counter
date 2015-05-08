package theranos_counter.center_data;

import java.util.ArrayList;

/**
 * Created by Forrest on 5/7/2015.
 */
public class City {
    public String name;
    public ArrayList<Address> addresses = new ArrayList<>();

    public City(String name) {
        this.name = name;
    }
}
