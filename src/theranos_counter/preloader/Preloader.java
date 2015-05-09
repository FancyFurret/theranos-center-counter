package theranos_counter.preloader;

import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import theranos_counter.Main;

public class Preloader {

    private static Stage loadStage;

    public static void show(InitCompletionHandler initCompletionHandler, Task<?> task)
    {
        task.stateProperty().addListener((obserableValue, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED)
                initCompletionHandler.complete();
        });

        loadStage = new Stage(StageStyle.UNDECORATED);

        Label lblLoading = new Label("Getting site info...");
        ProgressBar bar = new ProgressBar();

        VBox layout = new VBox(20);
        layout.getChildren().addAll(lblLoading, bar);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout, 250, 100);

        loadStage.setScene(scene);
        loadStage.setResizable(false);
        loadStage.setAlwaysOnTop(true);
        loadStage.setMinHeight(100);
        loadStage.setMinWidth(250);
        loadStage.setMaxHeight(100);
        loadStage.setMaxWidth(250);
        loadStage.getIcons().add(new Image(Main.class.getResourceAsStream("theranos_icon.png")));
        loadStage.show();
    }

    public static void close()
    {
        loadStage.close();
    }

    public interface InitCompletionHandler {
        void complete();
    }
}
