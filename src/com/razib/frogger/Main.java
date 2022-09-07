package com.razib.frogger;

import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;


import java.util.ArrayList;

public class Main extends Application {

    private Pane root;
    private AnimationTimer timer;
    private ArrayList<Node> cars = new ArrayList<Node>();
    private Node frog;
    private ArrayList<Image> vehicles = new ArrayList<>();

    Image car1Icon = new Image("com/razib/frogger/Icons/pixel-car.png");
    Image vehicleSprites = new Image("com/razib/frogger/Icons/vehicle-sprites.png");
    WritableImage car2Icon = new WritableImage(vehicleSprites.getPixelReader(),10,10,120,80);





    @Override
    public void start(Stage stage) throws Exception {

        vehicles.add(car1Icon);
        vehicles.add(car2Icon);

        Scene mainScene = new Scene(createContent());
        stage.setResizable(false);
        stage.setTitle("Frogger by Razib Sarker");
        stage.setScene(mainScene);
        stage.show();

        mainScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch(keyEvent.getCode()){

                    case UP:
                    case W:
                        frog.setTranslateY(frog.getTranslateY() - 40);
                        break;

                    case DOWN:
                    case S:
                        frog.setTranslateY(frog.getTranslateY() + 40);
                        break;

                    case LEFT:
                    case A:
                        frog.setTranslateX(frog.getTranslateX() - 40);
                        break;

                    case RIGHT:
                    case D:
                        frog.setTranslateX(frog.getTranslateX() + 40);
                        break;
                }
            }
        });





    }


    private Parent createContent(){

        root = new Pane();
        root.setPrefSize(800,600);

        frog = createFrog();



        timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                update();
            }
        };


        timer.start();



        return root;
    }

    private void update(){

        for(Node car: cars){

            car.setTranslateX(car.getTranslateX() + (Math.random()*10));
            if(car.getBoundsInParent().intersects(frog.getBoundsInParent())){

                frog.setTranslateX(0);
                frog.setTranslateY(600-39);
            }
        }

        checkState();

        if(Math.random() < 0.125){
            cars.add(spawnCar());
        }

    }

    private boolean checkState(){

        if(frog.getTranslateY() <= 0){

            String win = "YOU WIN";
            HBox hbox = new HBox();
            hbox.setTranslateX(300);
            hbox.setTranslateY(250);
            root.getChildren().add(hbox);


            for(int i=0; i <win.length(); i++){

                Text text = new Text(String.valueOf(win.charAt(i)));
                text.setFont(Font.font(45));
                text.setFill(Color.GREEN);
                text.setOpacity(0);

                hbox.getChildren().add(text);


                FadeTransition fadeTransition = new FadeTransition(Duration.millis(666),text);
                fadeTransition.setToValue(1);
                fadeTransition.setDelay(Duration.seconds(i*0.15));
                fadeTransition.play();

            }


            return true;
        }

        return false;

    }

    private Node createFrog(){


//        Rectangle frog =  new Rectangle(38,38, Color.GREEN);
        Image frogSprites = new Image("com/razib/frogger/Icons/chicken-sprites.jpg");

        PixelReader pixelReader = frogSprites.getPixelReader();
        WritableImage frogIcon = new WritableImage(pixelReader,49,49,59,50);

        ImageView frog = new ImageView(frogIcon);

        frog.setFitHeight(38);
        frog.setFitWidth(38);

        frog.setTranslateY(600-39);
        root.getChildren().add(frog);



        return frog;

    }

    private Node spawnCar(){

//        Rectangle car = new Rectangle(40,40,Color.RED);

//        Image carIcon = new Image("com/razib/frogger/Icons/car2.png");

        int vehicle = (int)(Math.random()*vehicles.size());


        ImageView car = new ImageView(vehicles.get(vehicle));
        car.setFitHeight(40);
        car.setFitWidth(50);



        car.setTranslateY((int)(Math.random()*14)*40);


        root.getChildren().add(car);


        return car;
    }


    public static void main(String[] args) {
	launch(args);
    }
}
