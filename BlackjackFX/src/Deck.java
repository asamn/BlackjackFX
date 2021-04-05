import java.util.ArrayList;

public class Deck {
    private static ArrayList<Card> cardDeck = new ArrayList<Card>();

    // card construction, suit,value

    public Deck() { // create a deck of 52 cards
        reloadCards();
    }

    public void reloadCards() { // reload to default deck state
        int suitCount = 0;
        int currentRank = 2;
        String currentSuit;
        for (int i = 0; i < 52; i++) {
            if (suitCount >= 4) { // finished with all four of same value
                suitCount = 0;
                currentRank++;
            }
            if (currentRank > 14) {
                System.out.println("Error, past value");
                currentRank = 0;
            }

            switch (suitCount) // check suitCount
            {
                case 0:
                    currentSuit = "diamonds";
                    break;
                case 1:
                    currentSuit = "clubs";
                    break;
                case 2:
                    currentSuit = "spades";
                    break;
                case 3:
                    currentSuit = "hearts";
                    break;
                default:
                    System.out.println("error, invalid suit");
                    currentSuit = null;
                    break;
            }

            cardDeck.add(new Card(currentSuit, currentRank));
            suitCount++;
        }
    }

    public void shuffleCards() {
        // WIP
    }

    public void removeCardAt(int i) {
        cardDeck.remove(i);
    }

    public Card getCardAt(int i) {
        return cardDeck.get(i);
    }

    public int getAmount() {
        return cardDeck.size();
    }

    @Override
    public String toString() {

        for (int i = 0; i < cardDeck.size(); i++) {
            System.out.println(cardDeck.get(i).getSuit() + " " + cardDeck.get(i).getRank());
        }

        return (cardDeck.size() + " cards");
    }

}
