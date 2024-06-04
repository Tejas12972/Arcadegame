/* Tejas B and Nischay S
 * Player class for RingFighter
 */
public class Player {
    private String name;
    private int health;
    private int mitigation;

    public Player(String name) {
        System.out.println("Creating player: " + name);
        this.name = name;
        this.health = 100;
        this.mitigation = 0;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public void takeDamage(int damage) {
        System.out.println(name + " takes " + damage + " damage.");
        damage = Math.max(0, damage - mitigation);
        health = Math.max(0, health - damage);
        mitigation = 0; 
    }

    public void mitigateDamage(int mitigation) {
        System.out.println(name + " mitigates " + mitigation + " damage.");
        this.mitigation += mitigation;
    }

    public void setHealth(int health) {
        this.health = Math.max(0, Math.min(health, 100)); 
    }
}
