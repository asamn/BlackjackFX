import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
import javafx.scene.paint.Color;
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

    @Override
    public void start(Stage primaryStage) throws Exception { // window
        // Initialize game
        Deck d = new Deck();
        System.out.println(d);
        Player plr = new Player("Player");
        System.out.println(d);
        plr.printCards();

        // Font format
        Font btnFont = new Font(20); // font size
        Font textFont = new Font(20);

        // Nodes
        final int BTNW = 150;
        final int BTNH = 25;
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

        Text playerTotalTxt = new Text("Player's total: 0");
        playerTotalTxt.setFont(textFont);
        playerTotalTxt.setTextAlignment(TextAlignment.CENTER);

        // Panes
        GridPane dCards = new GridPane();
        GridPane pCards = new GridPane();
        pCards.setPrefSize(100, 250); // width, height
        dCards.setPrefSize(100, 250);
        pCards.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        dCards.setBorder(new Border(new BorderStroke(Color.RED,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        pCards.setVgap(50);
        pCards.setHgap(50); // set gaps between each grid coordinate
        pCards.setPadding(new Insets(5, 5, 5, 5)); // set 5 padding between each grid block
        dCards.setVgap(50);
        dCards.setHgap(50);
        dCards.setPadding(new Insets(5, 5, 5, 5));

        GridPane playerStats = new GridPane();
        playerStats.setPrefSize(100, 75); // width, height
        playerStats.setBorder(new Border(new BorderStroke(Color.BLUE,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        Pane bottomControls = new Pane(btnHit, btnStand, btnDouble, btnPowers, playerTotalTxt);
        bottomControls.setPrefSize(0, 125);
        bottomControls.setBorder(new Border(new BorderStroke(Color.GREEN,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        // System.out.print(btnHit.getPrefWidth());
        int bottomPadding = 50;
        btnHit.setLayoutX(bottomPadding + 50);
        btnStand.setLayoutX(btnHit.getPrefWidth() + bottomPadding + btnHit.getLayoutX());
        btnDouble.setLayoutX(btnStand.getPrefWidth() + bottomPadding + btnStand.getLayoutX());
        btnPowers.setLayoutX(btnDouble.getPrefWidth() + bottomPadding + btnDouble.getLayoutX());
        playerTotalTxt
                .setLayoutX(btnPowers.getPrefWidth() + bottomPadding + btnPowers.getLayoutX());
        playerTotalTxt.setLayoutY(50); // move 50 units down

        // BorderPanes
        BorderPane cardsPane = new BorderPane(null, dCards, null, pCards, null); // split between
                                                                                 // top and bottom
        BorderPane p = new BorderPane(cardsPane, playerStats, null, bottomControls, null); // center,top,right,bottom,left

        // pCards.setGridLinesVisible(true); // debug

        Scene table = new Scene(p, 1200, 750); // scene with pane, window dimensions
        primaryStage.setTitle("Blackjack");
        primaryStage.setScene(table);
        primaryStage.show();

        // Button Events
        EventHandler<ActionEvent> hit = new EventHandler<ActionEvent>() {
            int currentRow = 0;
            int currentCol = 0;
            String outputPlrTotal;

            public void handle(ActionEvent e) {
                System.out.println("Hit");
                int playerDrawn = plr.getAmountDrawn();

                plr.drawCard(d);

                Card[] cards = plr.getCardArray();
                outputPlrTotal = ("Player's total: " + plr.getTotal());

                if (plr.getAltTotal() > 0 && plr.getTotal() < 21) // if an alt total is present
                {
                    outputPlrTotal = ("Player's total: " + plr.getTotal() + " OR "
                            + plr.getAltTotal());
                } else if (plr.getAltTotal() > 0 && plr.getTotal() > 21) {
                    outputPlrTotal = ("Player's total: " + plr.getAltTotal());
                }
                if (plr.isBusted()) {
                    outputPlrTotal = "BUSTED";
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
            }
        };
        EventHandler<ActionEvent> stand = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                System.out.println("Stand");
                // Dealer's Turn
            }
        };
        EventHandler<ActionEvent> doubleDown = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                System.out.println("Doubling");
                // Double Bet, hit, then stand
            }
        };
        EventHandler<ActionEvent> showPowerUps = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {

                System.out.println("Opening inventory");
            }
        };
        // On button clicks
        btnHit.setOnAction(hit);
        btnStand.setOnAction(stand);
        btnDouble.setOnAction(doubleDown);
        btnPowers.setOnAction(showPowerUps);

    }

    public static void main(String[] args) {

        Application.launch(args);
    }

}
