package theranos_counter.about;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import theranos_counter.Main;

public class Controller_about {

    public void show ()
    {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("About");
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("theranos_icon.png")));

        Label title = new Label("About");
        title.setFont(Font.font(30));

        Label author = new Label("Developed by Forrest Jones");
        author.setFont(Font.font(15));

        Label copyright = new Label("Copyright 2015");
        copyright.setFont(Font.font(10));
        copyright.setPadding(new Insets(0, 0, 25, 0));

        Label info = new Label("This is Theranos Center Counter. It is a little program I created for quickly checking on how many Theranos centers there currently are.");
        info.setWrapText(true);
        info.setPadding(new Insets(20, 20, 20, 20));

        VBox layout = new VBox(10);
        layout.getChildren().addAll(title, author, copyright, info);
        layout.setAlignment(Pos.CENTER);

        stage.setScene(new Scene(layout, 250, 300));
        stage.setResizable(false);
        stage.showAndWait();
    }
}
