package theranos_counter.center_data;

import java.util.*;

public class CenterData {
    public ArrayList<State> states = new ArrayList<>();

    public Address getAddress (String addressString)
    {
        Address address = null;

        for (State s : states)
            for (City c : s.cities)
                for (Address a : c.addresses)
                    if (a.name.equals(addressString))
                        address = a;
        return address;
    }

    public int getTotal()
    {
        int total = 0;

        for (State s : states)
            for (City c : s.cities)
                for (Address a : c.addresses)
                    total++;

        return total;
    }
}