package theranos_counter.main;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import theranos_counter.Main;
import theranos_counter.about.Controller_about;
import theranos_counter.center_data.*;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller_main implements Initializable {

    public TreeView<String> treeView;
    public Label addressLine1;
    public Label addressLine2;
    public Hyperlink hyperlink;
    public Label total;

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
                hyperlink.setOnAction(e ->
                {
                    try {
                        Desktop.getDesktop().browse(new URI("https://theranos.com/centers"));
                    } catch (URISyntaxException ex) {
                        System.out.println("Invalid URL");
                    } catch (IOException exe)
                    {
                        System.out.println("IO exception");
                    }
                });
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

    public void refresh(CenterData data) {
        centerData = data;

        TreeItem<String> rootItem = new TreeItem<>("Cities");
        rootItem.setExpanded(true);

        for (State state : data.states)
        {
            TreeItem<String> stateItem = new TreeItem<>(state.name);

            for (City city : state.cities)
            {
                TreeItem<String> cityItem = new TreeItem<>(city.name);

                for (Address address : city.addresses)
                {
                    TreeItem<String> addressItem = new TreeItem<>(address.name);
                    cityItem.getChildren().add(addressItem);
                }
                stateItem.getChildren().add(cityItem);
            }
            rootItem.getChildren().add(stateItem);
            stateItem.setExpanded(true);
        }
        treeView.setRoot(rootItem);

        total.setText(String.valueOf(data.getTotal()));
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
}
