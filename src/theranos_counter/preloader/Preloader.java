package theranos_counter.preloader;

import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

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
