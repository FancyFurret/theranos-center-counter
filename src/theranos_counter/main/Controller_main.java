package theranos_counter.main;

import javafx.scene.control.*;

public class Controller_main {

    public TreeView<String> treeView;

    public void refresh() {
        TreeItem<String> test = new TreeItem<>("Arizona");
        TreeItem<String> rootItem = new TreeItem<>("Cities");
        rootItem.getChildren().addAll(test);
        rootItem.setExpanded(true);
        treeView.setRoot(rootItem);
    }
}
