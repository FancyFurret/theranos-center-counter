package theranos_counter;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import theranos_counter.main.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public Controller_main controller_main;

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource("main/fx_main.fxml").openStream());
        primaryStage.setTitle("Theranos Center Counter");
        primaryStage.setScene(new Scene(root, 750, 500));
        primaryStage.show();

        controller_main = fxmlLoader.getController();
        controller_main.refresh();
        getCenters();
    }

    private void getCenters()
    {
        try {
            Connection.Response response= Jsoup.connect("https://www.theranos.com/centers")
                    .ignoreContentType(true)
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
                    .referrer("http://www.google.com")
                    .timeout(12000)
                    .followRedirects(true)
                    .execute();

            Document doc = response.parse();
            System.out.println(doc.body());
        }catch (IOException e)
        {
            System.out.println("Error getting site");
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}
