
public class Player extends Character {

    int currency;
    boolean isStanding;

    public Player(String n) {
        super(n);
        isStanding = false;
        currency = 100;

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
}
