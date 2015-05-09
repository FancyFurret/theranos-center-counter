package theranos_counter.center_data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.ArrayList;

@JsonSerialize
public class CenterData {
    public ArrayList<State> states = new ArrayList<>();

    public Address getAddress (String addressString)
    {
        Address address = null;

        if (addressString.startsWith("(+) "))
            addressString = addressString.substring(4);

        for (State s : states)
            for (City c : s.cities)
                for (Address a : c.addresses)
                    if (a.name.equals(addressString))
                        address = a;
        return address;
    }

    @JsonIgnore
    public boolean contains(Address address)
    {
        for (State s : states)
            for (City c: s.cities)
                for (Address a : c.addresses)
                    if (a.name.equals(address.name))
                        return true;
        return false;
    }

    @JsonIgnore
    public int getTotal()
    {
        int total = 0;

        for (State s : states)
            for (City c : s.cities)
                for (Address a : c.addresses)
                    total++;

        return total;
    }

    @JsonIgnore
    public int getTotalFrom(State stateStr)
    {
        int total = 0;
        State state = null;

        for (State s : states)
            if (stateStr.name.equals(s.name))
                state = s;

        if (state == null)
            return 0;

        for (City c : state.cities)
            for (Address a : c.addresses)
                total++;


        return total;
    }

    @JsonIgnore
    public int getTotalFrom(City cityStr)
    {
        int total = 0;
        City city = null;

        for (State s : states)
            for (City c : s.cities)
                if (cityStr.name.equals(c.name))
                    city = c;

        if (city == null)
            return 0;

        for (Address a : city.addresses)
            total++;

        return total;
    }
}