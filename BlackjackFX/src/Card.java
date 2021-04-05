
public class Card {
    final private String suit;
    final private int rank;

    public Card(String suit, int value) {
        this.suit = suit;
        this.rank = value;
    }

    public int getRank() {
        return rank;
    }

    public String getSuit() {
        return suit;
    }

    public int getValue() {
        if (rank > 10) {
            if (rank == 14) // if ace
            {
                return 11;
            } else {
                return 10;
            }

        } else {
            return rank; // 2 should be 2, 5 should be 5, 10 should be 10, 11 should be 10
        }

    }

    public String toFileName() {
        String rank = "ace"; // for non-numerical ranks

        StringBuilder name = new StringBuilder(); // 9_of_spades.png

        if (this.getRank() > 10) // 11 = jack 12 = queen 13 = king 14 = ace
        {
            switch (this.getRank()) {
                case 11:
                    rank = "jack";
                    break;
                case 12:
                    rank = "queen";
                    break;
                case 13:
                    rank = "king";
                    break;
                case 14:
                    rank = "ace";
                    break;
                default:
                    rank = "ace";
            }

            name.append(rank);

        } else {
            name.append(this.getRank());
        }

        name.append("_of_");
        name.append(this.getSuit());
        name.append(".png");
        return name.toString();
    }
}
