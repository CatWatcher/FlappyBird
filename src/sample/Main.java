package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Main extends Application {

    public static Pane appRoot = new Pane();
    public static Pane gameRoot = new Pane();

    public static ArrayList<Barrier> barriers = new ArrayList<>();
    private HashMap<KeyCode, Boolean> keys = new HashMap<>();

    Character bird = new Character();

    public static int score = 0;
    // FIXME
//    public Label scoreLabel = new Label("Score: " + score);

    // метод для настройки сцены
    public Parent createScene () {
        gameRoot.setPrefSize(900, 600);

        // создаем препятствия
        for (int i = 0; i < 100; i++) {
            int size = (int) (Math.random() * 100 + 50); // проем не меньше 50
            int height = new Random().nextInt(600 - size); // высота препятствия
            Barrier barrierTop = new Barrier(height);
            // создаем верхний блок
            // устанавливаем препятствие через каждые 350пкс
            barrierTop.setTranslateX(i * 350 + 600);
            barrierTop.setTranslateY(0);
            // добавляем в список блоков
            barriers.add(barrierTop);

            // нижний блок
            Barrier barrierBot = new Barrier(600 - height - size); // размер - остаток экрана
            barrierBot.setTranslateX(i * 350 + 600);
            barrierBot.setTranslateY(height + size);
            barriers.add(barrierBot);

            gameRoot.getChildren().addAll(barrierTop, barrierBot);

        }

        gameRoot.getChildren().add(bird);
        appRoot.getChildren().addAll(gameRoot);
        return appRoot;
    }

    public boolean isPressed (KeyCode keyCode) {
        return keys.getOrDefault(keyCode, false);
    }

    public void update () {
        if (isPressed(KeyCode.SPACE)) {
            bird.jump();
        }

        if (bird.velocity.getY() < 5) {
            bird.velocity = bird.velocity.add(0, 1);
        }

        bird.moveX((int) Character.velocity.getX());
        bird.moveY((int) Character.velocity.getY());
//        scoreLabel.setText("Score: " + score); FIXME

        // при изменении свойств птички
        // делаем чтобы экран (game root) двигался вправо
        bird.translateXProperty().addListener( ((observableValue, number, t1) -> {
            int offset = t1.intValue();
            if (offset > 200) {
                gameRoot.setLayoutX(-(offset - 200));
            }
        }) );
    }

    @Override
    public void start(Stage primaryStage) {



        Scene scene = new Scene(createScene());
        scene.setOnKeyPressed( e -> {
            keys.put(e.getCode(), true);
        });
        scene.setOnKeyReleased(e -> {
            keys.put(e.getCode(), false);
        });

        primaryStage.setTitle("Flappy bird");
        primaryStage.setScene(scene);
        primaryStage.show();

        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                update();
            }
        };
        animationTimer.start();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
