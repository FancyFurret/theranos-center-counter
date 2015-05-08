package theranos_counter;


import com.gargoylesoftware.htmlunit.BrowserVersion;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import theranos_counter.center_data.Address;
import theranos_counter.center_data.CenterData;
import theranos_counter.center_data.City;
import theranos_counter.center_data.State;

import javax.sound.midi.Soundbank;
import java.util.concurrent.TimeUnit;

public class WebHandler {

    public static final String STATE_CONTAINER = "container.centerlist";
    public static final String STATE = "state";
    public static final String CITY = "city";

    public static CenterData getCenters()
    {
        CenterData data = new CenterData();

        HtmlUnitDriver d = new HtmlUnitDriver(BrowserVersion.FIREFOX_24);
        d.setJavascriptEnabled(true);
        d.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        d.get("https://theranos.com/centers");

        // Wait for webpage to fully load
        Document doc;
        do {
            System.out.println("Waiting for javascript...");
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {}
        } while (countLines(d.getPageSource()) < 2000);

        // Analyze page
        doc = Jsoup.parse(d.getPageSource());
        System.out.println("Loaded page!");

        for (Element stateElement : doc.select("div." + STATE_CONTAINER))
        {
            State state = new State(stateElement.select("div." + STATE).first().text());

            for (Element cityElement : stateElement.getElementsByAttributeValue("ng-repeat", "city in column"))
            {
                String cityName = cityElement.select("div.city").text();

                boolean dupe = false;
                for (City city : state.cities)
                {
                    if (city.name.equals(cityName))
                    {
                        dupe = true;
                        break;
                    }
                }
                if (dupe)
                   continue;

                City city = new City(cityName);

                for (Element addressElement : cityElement.getElementsByAttributeValue("ng-repeat", "center in city.Centers"))
                {
                    Address address = new Address(
                        addressElement.getElementsByAttributeValue("ng-bind", "center.Address1").text(),
                        addressElement.getElementsByAttributeValue("ng-bind", "center.City").text(),
                        addressElement.getElementsByAttributeValue("ng-bind", "center.State").text(),
                        addressElement.getElementsByAttributeValue("ng-bind", "center.Zip").text(),
                        "https://www.theranos.com" + addressElement.select("a").attr("href")
                    );
                    city.addresses.add(address);
                }
                state.cities.add(city);
            }
            data.states.add(state);
        }
        return data;
    }

    public static int countLines(String str) {
        if(str == null || str.isEmpty())
        {
            return 0;
        }
        int lines = 1;
        int pos = 0;
        while ((pos = str.indexOf("\n", pos) + 1) != 0) {
            lines++;
        }
        return lines;
    }
}
