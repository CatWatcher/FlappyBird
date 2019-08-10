package sample;

import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Character extends Pane {

    public Rectangle rectangle;
    // точка с координатами
    // с её помощью будем двигать объект в методах moveX and moveY
    public static Point2D  velocity;

    public Character () {
        rectangle = new Rectangle(20, 20, Color.GREEN);
        velocity = new Point2D(0, 0);
        // координаты относятся к панели птицы
        this.setTranslateX(100);
        this.setTranslateY(300);
        getChildren().add(rectangle);
    }

    public void moveY (int val) {
        boolean down = val > 0 ? true : false;
        for (int i = 0; i < Math.abs(val); i++) {
            for (Barrier barrier : Main.barriers) {
                // чтобы объект не проваливался в препятствия
                if (this.getBoundsInParent().intersects(barrier.getBoundsInParent())) {
                    if (down) {
                        this.setTranslateY(this.getTranslateY() - 1);
                        return;
                    } else {
                        this.setTranslateY(this.getTranslateY() + 1);
                        return;
                    }
                }
            }

            // чтобы объект не улетал за границы экрана
            if (this.getTranslateY() < 0) {
                this.setTranslateY(0);
            }
            if (this.getTranslateY() > 580) {
                this.setTranslateY(580);
            }
            // и собственно перемещение по У
            this.setTranslateY(this.getTranslateY() + (down ? 1 : -1));
        }
    }

    public void moveX (int val) {
        // т.к двигаемся только в право, то проверка на движение не нужна
        for (int i = 0; i < Math.abs(val); i++) {
            for (Barrier barrier : Main.barriers) {
                if (this.getTranslateX() == barrier.getTranslateX()) {
                    Main.score++;
                    return;
                }
                if (this.getBoundsInParent().intersects(barrier.getBoundsInParent())) {
                    if (this.getTranslateX() + 20 == barrier.getTranslateX()) {
                        this.setTranslateX(this.getTranslateX() - 1);
                        return;
                    }
                }
            }
            this.setTranslateX(this.getTranslateX() + 1);
        }
    }

    public void jump () {
        velocity = new Point2D(3, -15);
    }

}
