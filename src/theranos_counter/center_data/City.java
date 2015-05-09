package theranos_counter.center_data;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.ArrayList;

/**
 * Created by Forrest on 5/7/2015.
 */
@JsonSerialize
public class City {
    public String name;
    public ArrayList<Address> addresses = new ArrayList<>();

    public City(String name) {
        this.name = name;
    }

    public City()
    {

    }
}
