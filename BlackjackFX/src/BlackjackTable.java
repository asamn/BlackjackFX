import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class BlackjackTable extends Application {

    private ImageView generateImage(Card card) throws Exception {
        String path = "/resources/" + card.toFileName(); // getClass().getResource(/resources/card).toURI().toString())

        Image image = new Image(
                getClass().getResource(path).toURI().toString()); // get path to resource package
                                                                  // /resources/ace_of_spades.png
        ImageView i = new ImageView();
        i.setImage(image);
        i.setPreserveRatio(true); // preserve dimension ratio of image so width scales with height
                                  // changes
        i.setFitHeight(150);

        return i;
    }

    boolean foundSave = true;

    private void loadGame(Button btnBet, TextField txtF) { // button and textfield i
        try {
            File f = new File("data.txt");
            Scanner fileRead = new Scanner(f);
            String name = fileRead.next();
            int credits = fileRead.nextInt();

            plr = new Player(name, credits);
            fileRead.close();

        } catch (IOException e) {
            System.out.println("File not found! ");
            e.printStackTrace();
            plr = new Player("Player");
            File f = new File("data.txt");
            try {
                f.createNewFile();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        } catch (NoSuchElementException ex) {
            foundSave = false;
            System.out.println("No data found! Creating new game... ");
            btnBet.setText("Enter Name");

        }

    }

    private void saveGame()

    {
        PrintWriter writer;
        try {

            writer = new PrintWriter("data.txt");
            writer.println(plr.getName());
            writer.println(plr.getCurrency());
            writer.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    // Initialize game
    Deck d = new Deck();
    Player plr = new Player("NO DATA FOUND");
    Dealer dlr = new Dealer("Dealer");
    int dealerCol = 0, dealerRow = 0;
    int currentRow = 0;
    int currentCol = 0;

    @Override
    public void start(Stage primaryStage) throws Exception { // window

        // Font format
        Font btnFont = new Font(20); // font size
        Font textFont = new Font(20);
        Font bigFont = new Font(25);

        // Nodes
        final int BTNW = 150;
        final int BTNH = 25;
        Button eventFirer = new Button();
        Button btnHit = new Button("Hit");
        Button btnStand = new Button("Stand"); // create button nodes
        btnHit.setPrefWidth(BTNW);
        btnHit.setPrefHeight(BTNH);
        btnStand.setPrefWidth(BTNW);
        btnStand.setPrefHeight(BTNH);
        btnHit.setFont(btnFont);
        btnStand.setFont(btnFont);
        Button btnDouble = new Button("Double Down");
        btnDouble.setPrefWidth(BTNW);
        btnDouble.setPrefHeight(BTNH);
        btnDouble.setFont(btnFont);
        Button btnPowers = new Button("Powerups");
        btnPowers.setPrefWidth(BTNW);
        btnPowers.setPrefHeight(BTNH);
        btnPowers.setFont(btnFont);

        Button ok = new Button("OK");
        ok.setMinWidth(BTNW);
        ok.setPrefHeight(BTNH);
        ok.setFont(btnFont);

        Button btnBet = new Button("Bet");
        btnBet.setPrefWidth(BTNW);
        btnBet.setPrefHeight(BTNH);
        btnBet.setFont(btnFont);
        TextField txtF = new TextField();
        Text playerTotalTxt = new Text("Player's total: ");
        playerTotalTxt.setFont(textFont);
        playerTotalTxt.setTextAlignment(TextAlignment.CENTER);

        Text dealerTotalTxt = new Text("Dealer's total: ");
        dealerTotalTxt.setFont(textFont);
        dealerTotalTxt.setTextAlignment(TextAlignment.CENTER);

        Text resultText = new Text("You win!"); // win or bust
        resultText.setFont(bigFont);
        // resultText.setTextAlignment(TextAlignment.CENTER);
        Text actionOutput = new Text(""); // player hits, player stands
        actionOutput.setFont(textFont);

        loadGame(btnBet, txtF);

        Text playerCText = new Text("Credits: " + plr.getCurrency());
        playerCText.setFont(textFont);
        Text playerNText = new Text(plr.getName());
        playerNText.setFont(textFont);

        // Panes
        GridPane dCards = new GridPane();
        GridPane pCards = new GridPane();

        txtF.setPrefSize(150, 40);
        txtF.setFont(textFont);
        pCards.setPrefSize(100, 250); // width, height
        dCards.setPrefSize(100, 250);
        pCards.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        dCards.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        pCards.setVgap(50);
        pCards.setHgap(50); // set gaps between each grid coordinate
        pCards.setPadding(new Insets(5, 100, 5, 100)); // set padding of pane top right bottom left
        dCards.setVgap(50);
        dCards.setHgap(50);
        dCards.setPadding(new Insets(5, 100, 5, 100)); // same as pCards except top

        GridPane playerStats = new GridPane();
        playerStats.setPrefSize(100, 75); // width, height
        playerStats.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        playerStats.add(playerCText, 1, 0);
        playerStats.add(playerNText, 0, 0);
        playerStats.setPadding(new Insets(20, 50, 20, 50));
        playerStats.setHgap(50);
        Pane bottomControls = new Pane(btnBet, txtF); // pane only starts with bet button
        bottomControls.setPrefSize(0, 125);
        bottomControls.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        int bottomPadding = 50;
        btnHit.setLayoutX(bottomPadding + 50);
        btnStand.setLayoutX(btnHit.getPrefWidth() + bottomPadding + btnHit.getLayoutX());
        btnDouble.setLayoutX(btnStand.getPrefWidth() + bottomPadding + btnStand.getLayoutX());
        btnPowers.setLayoutX(btnDouble.getPrefWidth() + bottomPadding + btnDouble.getLayoutX());
        playerTotalTxt
                .setLayoutX(btnPowers.getPrefWidth() + bottomPadding + btnPowers.getLayoutX());
        playerTotalTxt.setLayoutY(50); // move 50 units down
        actionOutput.setLayoutX(playerTotalTxt.getLayoutX());
        actionOutput.setLayoutY(playerTotalTxt.getLayoutY() + 40);
        btnBet.setLayoutX(btnHit.getLayoutX());
        txtF.setLayoutX(btnBet.getLayoutX() + 200);
        dealerTotalTxt.setLayoutX(playerTotalTxt.getLayoutX());
        dealerTotalTxt.setLayoutY(playerTotalTxt.getLayoutY() + 20);

        VBox resultPane = new VBox(); // popup pane to show result
        resultPane.setLayoutX(475);
        resultPane.setLayoutY(300);
        // resultPane.setAlignment(Pos.CENTER);
        resultPane.setPrefHeight(200);
        resultPane.setPrefWidth(200);
        Rectangle resultBackground = new Rectangle();

        resultBackground.setFill(Color.LIGHTGREY);
        resultBackground.setWidth(300);
        resultBackground.setHeight(100);
        resultBackground.setX(
                resultPane.getLayoutX() - (resultPane.getLayoutX() - resultBackground.getWidth()));
        resultBackground.setY(resultPane.getLayoutY() + resultBackground.getHeight() + 55500);
        resultBackground.toBack();
        resultPane.getChildren().add(resultBackground);

        // BorderPanes
        BorderPane cardsPane = new BorderPane(null, dCards, null, pCards, null); // split between //
                                                                                 // top and bottom
        BorderPane p = new BorderPane(cardsPane, playerStats, null, bottomControls, null); // center,top,right,bottom,left
        // rootpane

        // pCards.setGridLinesVisible(true); // debug

        Scene table = new Scene(p, 1200, 775); // scene with pane, window dimensions

        primaryStage.setTitle("Blackjack");
        primaryStage.setScene(table);
        primaryStage.show();

        // Button Events
        EventHandler<ActionEvent> hit = new EventHandler<ActionEvent>() {

            String outputPlrTotal;

            public void handle(ActionEvent e) {
                System.out.println("Hit");
                actionOutput.setText("You hit.");
                int playerDrawn = plr.getAmountDrawn();

                plr.drawCard(d);

                Card[] cards = plr.getCardArray();
                outputPlrTotal = ("Player's total: " + plr.getTotal());

                if (plr.isBusted()) {
                    outputPlrTotal = "BUSTED";
                    showResult(resultPane, resultText, p, ok, btnHit, btnStand, btnDouble,
                            btnPowers, resultBackground, "You busted!");

                } else if (plr.getAltTotal() > 0 && plr.getTotal() < 21
                        && plr.getAltTotal() != plr.getTotal()) // if an alt total is
                // present
                {
                    outputPlrTotal = ("Player's total: " + plr.getTotal() + " OR "
                            + plr.getAltTotal());
                } else if (plr.getAltTotal() > 0 && plr.getTotal() > 21) {
                    outputPlrTotal = ("Player's total: " + plr.getAltTotal());
                }

                playerTotalTxt.setText(outputPlrTotal);

                ImageView[] cardViews = new ImageView[cards.length];
                for (int i = 0; i < cards.length; i++) {
                    try {
                        cardViews[i] = generateImage(cards[i]);
                    } catch (Exception e1) {
                        System.out.println("Error generating image. ");
                    } // cards array alligned with imageview array
                }
                // plr.printCards();
                pCards.add(cardViews[playerDrawn], currentCol, currentRow); // col,row

                currentCol++;
                if (currentCol >= 7) {
                    currentRow++; // move on to the next row if drawn that many cards
                    currentCol = 0;
                }
                if (plr.isBusted()) {
                    currentCol = 0;
                }
                System.out.println("current colum " + currentCol);
            }
        };
        EventHandler<ActionEvent> stand = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {

                System.out.println("Stand");
                actionOutput.setText("You stand.");
                btnHit.setDisable(true);
                btnStand.setDisable(true);
                btnDouble.setDisable(true);
                btnPowers.setDisable(true);

                // Dealer's Turn

                actionOutput.setText("Dealer's turn.");
                bottomControls.getChildren().add(dealerTotalTxt);

                // Start a new Thread to update screen during loop
                Thread taskThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (dlr.getUsedTotal() < 17) {
                            int dealerDrawn = dlr.getAmountDrawn();
                            dlr.drawCard(d);
                            String outputDlrTotal = ("Dealer's total: " + dlr.getTotal());
                            Card[] cards = dlr.getCardArray();

                            if (dlr.isBusted()) {
                                outputDlrTotal = ("Dealer's total: " + dlr.getTotal() + " BUST! ");
                                // showResult(resultPane, resultText, p, ok, resultBackground, "You
                                // win!");

                            } else if (dlr.getAltTotal() > 0 && dlr.getTotal() < 21
                                    && dlr.getAltTotal() != dlr.getTotal()) // if an alt total is
                            // present
                            {
                                outputDlrTotal = ("Dealer's total: " + dlr.getTotal() + " OR "
                                        + dlr.getAltTotal());
                            } else if (dlr.getAltTotal() > 0 && dlr.getTotal() > 21) {
                                outputDlrTotal = ("Dealer's total: " + dlr.getAltTotal());
                            }

                            dealerTotalTxt.setText(outputDlrTotal);

                            ImageView[] dCardViews = new ImageView[cards.length];
                            for (int i = 0; i < cards.length; i++) {
                                try {
                                    dCardViews[i] = generateImage(cards[i]);
                                } catch (Exception e1) {
                                    System.out.println("Error generating image. ");
                                } // cards array alligned with imageview array
                            }
                            // plr.printCards();
                            Platform.runLater(new Runnable() { // update with results
                                @Override
                                public void run() {
                                    dCards.add(dCardViews[dealerDrawn], dealerCol, dealerRow); // col,row
                                    dealerCol++;
                                    if (dlr.getUsedTotal() >= 17) // if stand
                                    {
                                        if (dlr.getUsedTotal() > plr.getUsedTotal()
                                                && dlr.isBusted() == false) {
                                            showResult(resultPane, resultText, p, ok, btnHit,
                                                    btnStand, btnDouble,
                                                    btnPowers, resultBackground, "You lose! ");
                                        } else {
                                            showResult(resultPane, resultText, p, ok, btnHit,
                                                    btnStand, btnDouble,
                                                    btnPowers, resultBackground, "You win! ");
                                            plr.addCurrency(plr.getBet() * 2);
                                            playerCText.setText("Credits: " + plr.getCurrency());
                                        }

                                    }
                                    saveGame();
                                }
                            });

                            // dlr.printCards();

                            dealerTotalTxt.setText(outputDlrTotal);

                            if (dealerCol >= 7) {
                                dealerRow++; // move on to the next row if drawn that many cards
                                dealerCol = 0;
                            }

                            waitHalfSec(1);

                        } // end loop

                    }

                });

                taskThread.start();

                // Dealer stand
            }
        };
        EventHandler<ActionEvent> doubleDown = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                System.out.println("Doubling");

                // Double Bet, hit, then stand
                plr.setBet(plr.getBet() * 2);
                btnHit.fire();
                btnStand.fire();
                actionOutput.setText("You double down.");

            }
        };
        EventHandler<ActionEvent> showPowerUps = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {

                System.out.println("Opening inventory");
            }
        };
        EventHandler<ActionEvent> beginGame = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {

                System.out.println(plr.isBusted());
                d.shuffleCards();
                bottomControls.getChildren().remove(btnBet);
                bottomControls.getChildren().remove(txtF);
                bottomControls.getChildren().add(btnHit);
                bottomControls.getChildren().add(btnStand);
                bottomControls.getChildren().add(btnDouble);
                bottomControls.getChildren().add(playerTotalTxt);
                bottomControls.getChildren().add(actionOutput);
                // bottomControls.getChildren().add(btnPowers);

                btnHit.fire(); // fire btnHit's event twice to deal two cards

                btnHit.fire();

                actionOutput.setText("");
            }
        };
        EventHandler<ActionEvent> resetBoard = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                plr.emptyCards();
                plr.unstand();
                plr.setBet(0);
                dlr.unstand();
                dlr.emptyCards();
                bottomControls.getChildren().add(btnBet);
                bottomControls.getChildren().add(txtF);
                bottomControls.getChildren().remove(btnHit);
                bottomControls.getChildren().remove(btnStand);
                bottomControls.getChildren().remove(btnDouble);
                bottomControls.getChildren().remove(playerTotalTxt);
                bottomControls.getChildren().remove(actionOutput);
                bottomControls.getChildren().remove(btnPowers);
                bottomControls.getChildren().remove(dealerTotalTxt);
                btnHit.setDisable(false);
                btnStand.setDisable(false);
                btnDouble.setDisable(false);
                btnPowers.setDisable(false);
                resultPane.getChildren().remove(resultText);
                resultPane.getChildren().remove(ok);
                actionOutput.setText("");
                pCards.getChildren().clear();
                dCards.getChildren().clear();

                d.reloadCards();

                p.getChildren().remove(resultPane);
                currentCol = 0;
                currentRow = 0;
                dealerCol = 0;
                dealerRow = 0;

            }
        };
        EventHandler<ActionEvent> setBet = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                int betToInt = Integer.parseInt(txtF.getText());

                if (betToInt > plr.getCurrency()) {
                    txtF.setText("Not enough! ");
                } else if (betToInt <= 0) {
                    txtF.setText("Invalid bet! ");
                } else {
                    plr.setBet(betToInt);
                    plr.removeCurrency(betToInt);
                    playerCText.setText("Credits: " + plr.getCurrency());
                    eventFirer.fire();
                }
            }
        };
        EventHandler<ActionEvent> setName = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                String name = txtF.getText();
                plr = new Player(name, 100);

                btnBet.setText("Bet");
                btnBet.setOnAction(setBet);
                txtF.clear();
                playerNText.setText(plr.getName());
                foundSave = true;
                saveGame();
            }
        };
        if (foundSave == false) { // if save data was not found
            btnBet.setOnAction(setName);
        } else {
            btnBet.setOnAction(setBet);
        }

        // On button clicks
        eventFirer.setOnAction(beginGame);
        btnHit.setOnAction(hit);
        btnStand.setOnAction(stand);
        btnDouble.setOnAction(doubleDown);
        btnPowers.setOnAction(showPowerUps);

        ok.setOnAction(resetBoard);
    }

    // show the end result to the resultPane, need to have panes as arguments
    public void showResult(VBox resultPane, Text resultText, BorderPane p, Button ok, Button btnHit,
            Button btnStand, Button btnDouble, Button btnPowers,
            Rectangle resultBackground, String msg)

    // result
    {
        resultText.setTextAlignment(TextAlignment.CENTER);
        btnHit.setDisable(true);
        btnStand.setDisable(true);
        btnDouble.setDisable(true);
        btnPowers.setDisable(true);

        // Group g = new Group(resultBackground);
        p.getChildren().add(resultPane); // show the resultPane

        resultText.setText(msg);
        resultPane.getChildren().add(resultText);
        resultText.setTranslateX(resultBackground.getWidth() / 4); // originally 1/4 at default
        resultText.setTranslateY(-1 * resultBackground.getHeight());
        resultPane.getChildren().add(ok);
        ok.setTranslateY(-1 * resultBackground.getHeight());
        ok.setTranslateX(resultText.getTranslateX());
        resultPane.toFront();

    }

    public void waitHalfSec(int s) {
        try {
            Thread.sleep(500 * s);
            System.out.println("waiting");
        } catch (InterruptedException e) {

            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        Application.launch(args);
    }

}
