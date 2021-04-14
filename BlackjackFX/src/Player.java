
public class Player extends Character {

    int currency;
    boolean isStanding;
    int bet;

    public Player(String n) {
        super(n);
        isStanding = false;
        currency = 100;

    }

    public Player(String n, int credits) {
        super(n);
        isStanding = false;
        currency = credits;

    }

    public void stand() {
        this.isStanding = true;
    }

    public void unstand() {
        this.isStanding = false;
    }

    public int getCurrency() {
        return this.currency;
    }

    public void addCurrency(int x) {
        this.currency += x;
    }

    public void removeCurrency(int x) {
        this.currency -= x;
    }

    public void setBet(int y) {
        this.bet = y;
    }

    public int getBet() {
        return this.bet;
    }
}
