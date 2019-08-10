package sample;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Barrier extends Pane {
    Rectangle rectangle;

    private int height;

    public Barrier(int height) {
        this.height = height;
        rectangle = new Rectangle(20, height, Color.BLUE);

        getChildren().add(rectangle);
    }
}
