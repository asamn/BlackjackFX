
public class Dealer extends Character {
    boolean isStanding;

    public Dealer(String n) {
        super(n);
        isStanding = false;
        // TODO Auto-generated constructor stub
    }

    public void unstand() {
        isStanding = false;
    }

    public void stand() {
        isStanding = true;
    }

}
