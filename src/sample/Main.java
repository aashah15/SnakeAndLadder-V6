package sample;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main extends Application {

    public PlayerPos xyArray[][] = new PlayerPos[10][10];
    public ArrayList<NewPos> sAndLPos = new ArrayList<>(12);

    public int rand = 0;
    public Label randResult;
    public Label gameResult;

    public static final int tileSize = 80;
    public static final int width = 10;
    public static final int height = 10;

    public Circle player1;
    public Circle player2;

    public Circle player3;
    public Circle player4;

    public boolean player1Turn = false;
    public boolean player2Turn = false;

    public boolean player3Turn = false;
    public boolean player4Turn = false;

    public int player1Position = 1;
    public int player2Position = 1;

    public int player3Position = 1;
    public int player4Position = 1;

    public int player1XPos = 150;
    public int player1YPos = 840;

    public int player2XPos = 640;
    public int player2YPos = 840;

    public int player3XPos = 150;
    public int player3YPos = 840;

    public int player4XPos = 640;
    public int player4YPos = 840;

    public int posCir1 = 1;
    public int posCir2 = 1;

    public int posCir3 = 1;
    public int posCir4 = 1;

    public boolean gameStart = false;
    public Button gameButton;

    public Image img2;
    public ImageView imageView2;

    private Group titleGroup = new Group();
    private PlayerPos playerXYPos;

    private Parent createContent(){
        // root
        Pane root = new Pane();
        root.setPrefSize(width * tileSize, (height * tileSize) + 160);
        root.getChildren().addAll(titleGroup);

        for(int i=0; i<width; i++){
            for(int j=0; j<height; j++){
                Tile tile = new Tile(tileSize, tileSize);
                tile.setTranslateX(j * tileSize);
                tile.setTranslateY(i * tileSize);
                titleGroup.getChildren().add(tile);

            }

        }
        // Array of Player xy coordinates and Player Position
        int p=100;
        int q = 0;
        int pos =0;
        for(int i=0; i<width; i++){
            for(int j=0; j<height; j++){

                if(posCir1 % 2 == 1) {
                    pos = p-j;
                }
                if(posCir1 % 2 == 0){
                    pos = p+j;
                }

                xyArray[i][j] = new PlayerPos(40+80*j, 40+80*i, pos);
                q = pos;
            }
            posCir1++;
            p = q-10;
        }

        ArrayList<PlayerPos> list = new ArrayList<>();

        for(int i=0; i<xyArray.length; i++){
            for(int j=0; j<xyArray[i].length; j++){
                list.add(xyArray[i][j]);
            }
        }

        // Array of Player New Positions because of Snakes and Ladders
        sAndLPos.add(new NewPos(3,39));
        sAndLPos.add(new NewPos(10,12));
        sAndLPos.add(new NewPos(27,53));
        sAndLPos.add(new NewPos(56,84));
        sAndLPos.add(new NewPos(61,99));
        sAndLPos.add(new NewPos(72,90));
        sAndLPos.add(new NewPos(16,13));
        sAndLPos.add(new NewPos(31,4));
        sAndLPos.add(new NewPos(47,25));
        sAndLPos.add(new NewPos(63,60));
        sAndLPos.add(new NewPos(66,52));
        sAndLPos.add(new NewPos(97,75));


        PlayerPos player1Pos = new PlayerPos(150,840, 1);

        player1 = new Circle(40);
        player1.setId("Player 1");
        player1.setFill(Color.GREEN);
        player1.getStyleClass().add("Style.css");
        player1.setTranslateX(player1Pos.getPlayerXPos());
        player1.setTranslateY(player1Pos.getPlayerYPos());

        PlayerPos player2Pos = new PlayerPos(640,840, 1);

        player2 = new Circle(40);
        player2.setId("Player 2");
        player2.setFill(Color.RED);
        player2.getStyleClass().add("Style.css");
        player2.setTranslateX(player2Pos.getPlayerXPos());
        player2.setTranslateY(player2Pos.getPlayerYPos());

        PlayerPos player3Pos = new PlayerPos(150,930, 1);

        player3 = new Circle(40);
        player3.setId("Player 3");
        player3.setFill(Color.CHOCOLATE);
        player3.getStyleClass().add("Style.css");
        player3.setTranslateX(player3Pos.getPlayerXPos());
        player3.setTranslateY(player3Pos.getPlayerYPos());

        PlayerPos player4Pos = new PlayerPos(640,930, 1);

        player4 = new Circle(40);
        player4.setId("Player 4");
        player4.setFill(Color.BLUE);
        player4.getStyleClass().add("Style.css");
        player4.setTranslateX(player4Pos.getPlayerXPos());
        player4.setTranslateY(player4Pos.getPlayerYPos());

        // Player 1 Button event
        Button button1 = new Button("Player 1");
        button1.setTranslateX(20);
        button1.setTranslateY(820);
        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(gameStart) {
                    if (player1Turn) {
                        getDiceValue();
                        randResult.setText(String.valueOf(rand));
                        player1Position += rand;

                        if(player1Position > 100){
                            player1Position = 100;
                        }

                        // Player 1 new positions due to snake and ladders using Java 8 list and stream API
                        List<NewPos> list2 = sAndLPos.stream().filter(m -> m.getOldPos() == player1Position).collect(Collectors.toList());
                        if(!list2.isEmpty()) {
                            Integer[] array = list2.stream().map(x -> x.getNewPos()).toArray(Integer[]::new);
                            System.out.println(array[0]);
                            player1Position = array[0];
                        }
                        // Player 1 xy co ordiantes and position using Java 8 list and stream API
                        List<PlayerPos> list1 = list.stream().filter(x -> x.getPlayerPos() == player1Position).collect(Collectors.toList());

                        Integer[] xArray = list1.stream().map(z -> z.getPlayerXPos()).toArray(Integer[]::new);
                        player1XPos = xArray[0];

                        Integer[] yArray = list1.stream().map(z -> z.getPlayerYPos()).toArray(Integer[]::new);
                        player1YPos = yArray[0];

                        translatePlayer(player1XPos, player1YPos, player1);

                        if(player1XPos < 30 || player1YPos < 30 || player1Position == 100) {
                            gameResult.setTranslateX(530);
                            gameResult.setText("Player One Won");
                            gameButton.setText("Start Again");
                            gameStart = false;
                        }

                        player1Turn = false;
                        player2Turn = true;
                        player3Turn = false;
                        player4Turn = false;

                        if(gameStart)
                            gameResult.setText("Player Two turn");
                    }
                }
            }
        });


        // Player 2 Button event
        Button button2 = new Button("Player 2");
        button2.setTranslateX(700);
        button2.setTranslateY(820);
        button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (gameStart) {
                    if (player2Turn) {
                        getDiceValue();
                        randResult.setText(String.valueOf(rand));

                        player2Position += rand;

                        if(player2Position > 100){
                            player2Position = 100;
                        }

                        // Player 2 new positions due to snake and ladders using Java 8 list and stream API
                        List<NewPos> list2 = sAndLPos.stream().filter(m -> m.getOldPos() == player2Position).collect(Collectors.toList());
                        if(!list2.isEmpty()) {
                            Integer[] array = list2.stream().map(x -> x.getNewPos()).toArray(Integer[]::new);
                            System.out.println(array[0]);
                            player2Position = array[0];
                        }

                        // Player 2 xy co ordiantes and position using Java 8 list and stream AP
                        List<PlayerPos> list1 = list.stream().filter(x -> x.getPlayerPos() == player2Position).collect(Collectors.toList());

                        Integer[] xArray = list1.stream().map(z -> z.getPlayerXPos()).toArray(Integer[]::new);
                        player2XPos = xArray[0];

                        Integer[] yArray = list1.stream().map(z -> z.getPlayerYPos()).toArray(Integer[]::new);
                        player2YPos = yArray[0];

                        translatePlayer(player2XPos, player2YPos, player2);

                        if(player2XPos < 30 || player2YPos < 30 || player2Position == 100) {
                            gameResult.setTranslateX(530);
                            gameResult.setText("Player Two Won");
                            gameButton.setText("Start Again");
                            gameStart = false;
                        }

                        player1Turn = false;
                        player2Turn = false;
                        player3Turn = true;
                        player4Turn = false;

                        if(gameStart)
                            gameResult.setText("Player Three turn");
                    }
                }
            }
        });

        // Player 3 Button event
        Button button3 = new Button("Player 3");
        button3.setTranslateX(20);
        button3.setTranslateY(910);
        button3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (gameStart) {
                    if (player3Turn) {
                        getDiceValue();
                        randResult.setText(String.valueOf(rand));
                        player3Position += rand;

                        if(player3Position > 100){
                            player3Position = 100;
                        }

                        // Player 3 new positions due to snake and ladders using Java 8 list and stream API
                        List<NewPos> list2 = sAndLPos.stream().filter(m -> m.getOldPos() == player3Position).collect(Collectors.toList());
                        if(!list2.isEmpty()) {
                            Integer[] array = list2.stream().map(x -> x.getNewPos()).toArray(Integer[]::new);
                            System.out.println(array[0]);
                            player3Position = array[0];
                        }

                        // Player 3 xy co ordiantes and position using Java 8 list and stream AP
                        List<PlayerPos> list1 = list.stream().filter(x -> x.getPlayerPos() == player3Position).collect(Collectors.toList());

                        Integer[] xArray = list1.stream().map(z -> z.getPlayerXPos()).toArray(Integer[]::new);
                        player3XPos = xArray[0];

                        Integer[] yArray = list1.stream().map(z -> z.getPlayerYPos()).toArray(Integer[]::new);
                        player3YPos = yArray[0];

                        translatePlayer(player3XPos, player3YPos, player3);

                        if(player3XPos < 30 || player3YPos < 30 || player3Position == 100) {
                            gameResult.setTranslateX(530);
                            gameResult.setText("Player Three Won");
                            gameButton.setText("Start Again");
                            gameStart = false;
                        }

                        player1Turn = false;
                        player2Turn = false;
                        player3Turn = false;
                        player4Turn = true;

                        if(gameStart)
                            gameResult.setText("Player Four turn");

                    }
                }
            }
        });

        // Player 4 Button event
        Button button4 = new Button("Player 4");
        button4.setTranslateX(700);
        button4.setTranslateY(910);
        button4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(gameStart){
                    if(player4Turn){
                        getDiceValue();
                        randResult.setText(String.valueOf(rand));
                        player4Position += rand;

                        if(player4Position > 100){
                            player4Position = 100;
                        }

                        // Player 4 new positions due to snake and ladders using Java 8 list and stream API
                        List<NewPos> list2 = sAndLPos.stream().filter(m -> m.getOldPos() == player4Position).collect(Collectors.toList());
                        if(!list2.isEmpty()) {
                            Integer[] array = list2.stream().map(x -> x.getNewPos()).toArray(Integer[]::new);
                            System.out.println(array[0]);
                            player4Position = array[0];
                        }

                        // Player 4 xy co ordiantes and position using Java 8 list and stream AP
                        List<PlayerPos> list1 = list.stream().filter(x -> x.getPlayerPos() == player4Position).collect(Collectors.toList());

                        Integer[] xArray = list1.stream().map(z -> z.getPlayerXPos()).toArray(Integer[]::new);
                        player4XPos = xArray[0];

                        Integer[] yArray = list1.stream().map(z -> z.getPlayerYPos()).toArray(Integer[]::new);
                        player4YPos = yArray[0];

                        translatePlayer(player4XPos, player4YPos, player4);

                        if(player4XPos < 30 || player4YPos < 30 || player4Position == 100) {
                            gameResult.setTranslateX(530);
                            gameResult.setText("Player Four Won");
                            gameButton.setText("Start Again");
                            gameStart = false;
                        }

                        player1Turn = true;
                        player2Turn = false;
                        player3Turn = false;
                        player4Turn = false;

                        if(gameStart)
                            gameResult.setText("Player One turn");

                    }
                }
            }
        });

        // Game starts here
        gameButton = new Button("Start Game");
        gameButton.setTranslateX(350);
        gameButton.setTranslateY(860);

        gameButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if (!gameStart) {
                    gameStart = true;
                    randResult.setText("");
                    randResult.setTranslateX(160);
                    gameButton.setText("Game Started");

                    player1XPos = 40;
                    player1YPos = 760;

                    player2XPos = 40;
                    player2YPos = 760;

                    player3XPos = 40;
                    player3YPos = 760;

                    player4XPos = 40;
                    player4YPos = 760;

                    player1Position = 1;
                    player2Position = 1;

                    posCir1 = 1;
                    posCir2 = 1;

                    player3Position = 1;
                    player4Position = 1;

                    posCir3 = 1;
                    posCir4 = 1;

                    player1.setTranslateX(player1XPos);
                    player1.setTranslateY(player1YPos);
                    player2.setTranslateX(player2XPos);
                    player2.setTranslateY(player2YPos);

                    player3.setTranslateX(player1XPos);
                    player3.setTranslateY(player1YPos);
                    player4.setTranslateX(player2XPos);
                    player4.setTranslateY(player2YPos);

                    player1Turn = true;

                    rand = (int) (Math.random() * 4 + 1);
                    if (rand == 1) {
                        player1Turn = true;
                        gameResult.setText("Player One Turn");
                    }
                    if (rand == 2) {
                        player2Turn = true;
                        gameResult.setText("Player two Turn");
                    }
                    if (rand == 3) {
                        player3Turn = true;
                        gameResult.setText("Player Three Turn");
                    }
                    if (rand == 4 ){
                        player4Turn = true;
                        gameResult.setText("Player Four Turn");
                    }
                }
            }

        });

        randResult = new Label("");
        randResult.setTranslateX(230);
        randResult.setTranslateY(870);

        gameResult = new Label("Game Result");
        gameResult.setTranslateX(490);
        gameResult.setTranslateY(870);

        Image img = new Image("/sample/res/snakebg.jpeg");
        ImageView imageView = new ImageView();
        imageView.setImage(img);
        imageView.setFitWidth(800);
        imageView.setFitHeight(800);

        imageView2 = new ImageView();
        img2 = new Image("/sample/res/icon.png");
        imageView2.setImage(img2);
        imageView2.setX(230);
        imageView2.setY(850);

        titleGroup.getChildren().addAll(imageView,imageView2, player1, player2, player3, player4, button1, button2, button3, button4, gameButton, randResult, gameResult);

        return root;

    }

    public void getDiceValue(){
        rand = (int)(Math.random() * 6 +1);

        imageView2.setImage(img2);
        if(rand == 1){

            img2 = new Image("/sample/res/d1.gif");
            imageView2.setImage(img2);
        }
        if(rand == 2){

            img2 = new Image("/sample/res/d2.gif");
            imageView2.setImage(img2);
        }
        if(rand == 3){

            img2 = new Image("/sample/res/d3.gif");
            imageView2.setImage(img2);
        }
        if(rand == 4){

            img2 = new Image("/sample/res/d4.gif");
            imageView2.setImage(img2);
        }
        if(rand == 5){

            img2 = new Image("/sample/res/d5.gif");
            imageView2.setImage(img2);
        }
        if(rand == 6){

            img2 = new Image("/sample/res/d6.gif");
            imageView2.setImage(img2);
        }

    }




    public void translatePlayer(int x, int y, Circle b){

        TranslateTransition animate = new TranslateTransition(Duration.millis(1000), b);
        animate.setToX(x);
        animate.setToY(y);
        animate.setAutoReverse(false);
        animate.play();
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        Scene scene = new Scene(createContent());
        primaryStage.setTitle("Snake and Ladder Game");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();


    }

    public static void main(String[] args) {
        launch(args);
    }
}


