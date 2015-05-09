package theranos_counter;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import theranos_counter.center_data.CenterData;
import theranos_counter.main.Controller_main;
import theranos_counter.preloader.Preloader;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application {

    public static Main inst;

    public Controller_main controller_main;
    public CenterData centerData;
    public CenterData oldCenterData;

    public Stage primaryStage;

    String fileName = "savedCount";

    boolean newFile = false;

    @Override
    public void start(Stage primaryStage) throws Exception {
        inst = this;

        shutHtmlUnitUp();

        this.primaryStage = primaryStage;
        primaryStage.close();
        primaryStage.setTitle("Theranos Center Counter");

        final Task<CenterData> task = new Task<CenterData>() {
            @Override
            protected CenterData call() throws Exception {
                System.out.println("Getting site...");
                CenterData data = WebHandler.getCenters();
                return data;
            }
        };

        Preloader.show(() -> {
            try {
                showMain(task.valueProperty());
            } catch (Exception e) {
                System.out.println("Can't get CenterData");
            }
        }, task);

        new Thread(task).start();
    }

    public void showMain(ReadOnlyObjectProperty<CenterData> d) throws Exception
    {
        Preloader.close();

        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource("main/fx_main.fxml").openStream());
        Scene scene = new Scene(root, 750, 750);

        primaryStage.setScene(scene);
        primaryStage.setMinHeight(400);
        primaryStage.setMinWidth(500);
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("theranos_icon.png")));
        primaryStage.show();

        centerData = d.get();
        controller_main = fxmlLoader.getController();

        oldCenterData = loadCenterData();
        saveCenterData(centerData);
        if (newFile) {
            oldCenterData = loadCenterData();
        }

        controller_main.refresh(centerData, oldCenterData);
    }

    public CenterData loadCenterData()
    {
        CenterData data = null;
        ObjectMapper mapper = new ObjectMapper();

        File file = new File(fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
                newFile = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            data = mapper.readValue(file, CenterData.class);
        } catch (JsonParseException e) {
            System.out.println("Unable to parse count file");
        } catch (JsonMappingException e)
        {
            System.out.println(e.toString());
        } catch (IOException e)
        {
            System.out.println("IO");
        }

        return data;
    }

    public void saveCenterData(CenterData data)
    {
        File file = new File(fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(file, data);
        } catch (IOException e){
            System.out.println("IOException");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void shutHtmlUnitUp()
    {
        Logger logger = Logger.getLogger("");
        logger.setLevel(Level.OFF);
    }
}
