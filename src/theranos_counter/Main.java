package theranos_counter;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.openqa.selenium.Point;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
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
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource("main/fx_main.fxml").openStream());
        primaryStage.setTitle("Theranos Center Counter");
        primaryStage.setScene(new Scene(root, 750, 500));
        primaryStage.show();

        controller_main = fxmlLoader.getController();

        getCenters();

        controller_main.refresh();
    }

    private void getCenters()
    {
        HtmlUnitDriver d = new HtmlUnitDriver(BrowserVersion.FIREFOX_24);
        d.setJavascriptEnabled(true);
        d.get("https://theranos.com/centers");

        Document doc;
        do {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }

            doc = Jsoup.parse(d.getPageSource());

            System.out.println(doc.body());
            System.out.println(doc.getElementsByClass("modal-body").get(0).data() );
        } while (doc.getElementsByAttribute("h6").get(0).data() == "hi");

    }

    public static void main(String[] args) {
        launch(args);
    }
}
