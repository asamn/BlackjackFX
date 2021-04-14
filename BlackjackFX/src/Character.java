import java.util.ArrayList;

public abstract class Character {
    final int JACK = 11;
    final int QUEEN = 12;
    final int KING = 13;
    final int ACE = 14;

    private String name;
    boolean busted, normalTBusted;
    ArrayList<Card> cards = new ArrayList<Card>(); // each character has cards

    private int total, altTotal = 0; // altTotal if drawn an ace
    private int amountDrawn = 0;

    public Character(String n) // constructor
    {
        this.name = n;
        this.total = 0;
        this.altTotal = 0;
        this.busted = false;
    }

    public Card[] getCardArray() { // return ArrayList to array
        return cards.toArray(new Card[0]);
    }

    public void emptyCards() {
        cards.clear();
        this.total = 0;
        this.altTotal = 0;
        this.busted = false;
        this.amountDrawn = 0;

    }

    public void drawCard(Deck d) { // draw from top of deck
        Card drawn = d.getCardAt(d.getAmount() - 1); // draw from end of array since it's an
                                                     // arraylist
        cards.add(drawn);
        d.removeCardAt(d.getAmount() - 1);

        this.total = this.total + drawn.getValue();

        if (drawn.getRank() == ACE) {
            this.altTotal = (this.total - 11) + 1; // instead of adding 11, add 1 for aces

        } else {
            this.altTotal = this.altTotal + drawn.getValue();
        }
        System.out.println("TOTAL: " + this.total);
        System.out.println("ALT TOTAL: " + altTotal);
        this.amountDrawn++;

    }

    public void addCard(Card c) // add a specific card to character
    {
        cards.add(c);
        this.total = this.total + c.getValue();
        this.amountDrawn++;
    }

    public void printCards() {
        System.out.println("Character's cards");
        for (int i = 0; i < cards.size(); i++) {
            System.out.println(cards.get(i).getSuit() + " " + cards.get(i).getRank());
        }
    }

    public int getTotal() {
        return this.total;
    }

    public int getAltTotal() {
        return this.altTotal;
    }

    public int getUsedTotal() { // returns total that will be used if total

        if (this.getTotal() > 21 && this.getAltTotal() > 0) // if has an alt total and normal total
                                                            // is busted
        {
            System.out.println("USING ALT " + altTotal);
            return this.getAltTotal();
        } else {
            System.out.println(total);
            return this.getTotal();
        }
    }

    public int getAmountDrawn() {
        return this.amountDrawn;
    }

    public boolean isBusted() {
        if (this.getUsedTotal() > 21) {
            System.out.println("PLR BUSTED");
            return true;

        }
        return false;
    }

    public String getName() {
        return this.name;
    }
}
