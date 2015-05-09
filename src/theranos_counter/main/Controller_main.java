package theranos_counter.main;

import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;
import theranos_counter.Main;
import theranos_counter.about.Controller_about;
import theranos_counter.center_data.Address;
import theranos_counter.center_data.CenterData;
import theranos_counter.center_data.City;
import theranos_counter.center_data.State;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller_main implements Initializable {

    public TreeView<String> treeView;
    public Label addressLine1;
    public Label addressLine2;
    public Hyperlink hyperlink;
    public Label total;
    public Label oldTotal;
    public Label newCenters;

    private CenterData centerData;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        treeView.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            Address address = centerData.getAddress(v.getValue().getValue());
            if (newValue != null && address != null)
            {
                addressLine1.setText(address.name);
                addressLine2.setText(address.city + ", " + address.state + " " + address.zip);
                hyperlink.setText("View center details");
                hyperlink.setOnAction(e -> openURL(address.url));
            }
            else
            {
                addressLine1.setText("Select an address");
                addressLine2.setText("");
                hyperlink.setText("");
                hyperlink.setOnAction(null);
            }
        });
    }

    public void refresh(CenterData data, CenterData oldData) {
        centerData = data;

        TreeItem<String> rootItem = new TreeItem<>(getInfo(data.getTotal() - oldData.getTotal()) + "States" + getTotalStr(data.getTotal()));
        rootItem.setExpanded(true);

        for (State state : data.states)
        {
            TreeItem<String> stateItem = new TreeItem<>(getInfo(data.getTotalFrom(state) - oldData.getTotalFrom(state)) + state.name + getTotalStr(data.getTotalFrom(state)));

            for (City city : state.cities)
            {
                TreeItem<String> cityItem = new TreeItem<>(getInfo(data.getTotalFrom(city) - oldData.getTotalFrom(city)) + city.name + getTotalStr(data.getTotalFrom(city)));

                for (Address address : city.addresses)
                {
                    TreeItem<String> addressItem = new TreeItem<>(getInfo(!oldData.contains(address)) + address.name);
                    cityItem.getChildren().add(addressItem);
                }
                stateItem.getChildren().add(cityItem);
            }
            rootItem.getChildren().add(stateItem);
            //stateItem.setExpanded(true);
        }
        treeView.setRoot(rootItem);
        total.setText(String.valueOf(data.getTotal()));
        newCenters.setText(String.valueOf(data.getTotal() - oldData.getTotal()));
        oldTotal.setText("Previously, the total was: " + String.valueOf(oldData.getTotal()));
    }

    private String getInfo(int difference)
    {
        System.out.println(difference);
        if (difference > 0)
            return "(+" + difference + ") ";
        else if (difference < 0)
            return "(" + difference + ") ";
        else
            return "";
    }

    private String getInfo(boolean bool)
    {
        if (bool)
            return "(+) ";
        else
            return "";
    }

    private String getTotalStr(int i)
    {
        return " (" + i + ")";
    }

    public void onAboutClick()
    {
        new Controller_about().show();
    }

    public void onRefreshClick()
    {
        Main.inst.primaryStage.close();
        try {
            Main.inst.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openURL(String URL)
    {
        System.out.println("Opening URL: " + URL);
        String url = URL;
        String os = System.getProperty("os.name").toLowerCase();
        Runtime rt = Runtime.getRuntime();

        try{

            if (os.indexOf( "win" ) >= 0) {

                // this doesn't support showing urls in the form of "page.html#nameLink"
                rt.exec( "rundll32 url.dll,FileProtocolHandler " + url);

            } else if (os.indexOf( "mac" ) >= 0) {

                rt.exec( "open " + url);

            } else if (os.indexOf( "nix") >=0 || os.indexOf( "nux") >=0) {

                // Do a best guess on unix until we get a platform independent way
                // Build a list of browsers to try, in this order.
                String[] browsers = {"chromium", "epiphany", "firefox", "mozilla", "konqueror",
                        "netscape","opera","links","lynx"};

                // Build a command string which looks like "browser1 "url" || browser2 "url" ||..."
                StringBuffer cmd = new StringBuffer();
                for (int i=0; i<browsers.length; i++)
                    cmd.append( (i==0  ? "" : " || " ) + browsers[i] +" \"" + url + "\" ");

                rt.exec(new String[] { "sh", "-c", cmd.toString() });

            } else {
                return;
            }
        }catch (Exception e){
            System.out.println("Error opening URL");
            return;
        }
        return;
    }
}
