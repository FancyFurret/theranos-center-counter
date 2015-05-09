package theranos_counter.center_data;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Created by Forrest on 5/7/2015.
 */
@JsonSerialize
public class Address {
    public String name;
    public String city;
    public String state;
    public String zip;
    public String url;

    public Address(String name, String city, String state, String zip, String url) {
        this.name = name;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.url = url;
    }

    public Address()
    {

    }

}
