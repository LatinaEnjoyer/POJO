package TextTurnBasedRogueLike;

import java.util.ArrayList;

import static TextTurnBasedRogueLike.TextWriter.print;
import static TextTurnBasedRogueLike.Tools.scan;

class Player {
    private final String name;
    private final int baseAttackDamage;
    private int health;
    private final int maxHealth;
    private int healthPotion;
    protected final ArrayList<Item> inventory = new ArrayList<>();
    protected final ArrayList<DefenseItem> defenseItems = new ArrayList<>();
    protected final ArrayList<DamageItem> damageItems = new ArrayList<>();


    Player(String name, int baseAttackDamage, int health){
        this.name = name;
        this.baseAttackDamage = baseAttackDamage;
        this.health = health;
        maxHealth = health;
        healthPotion = 1;
    }

    protected void playerAttack(Enemy e){
        int attackDamage = getTotalAttackDamage();

        String choice;
        print("Choose your attack. \n1 = light, 2 = medium, 3 = heavy, 4 = health potion");
        while(true){
            choice = scan.nextLine();
            if(choice.equals("1") || choice.equals("2") || choice.equals("3") || choice.equals("4")){
                break;
            }
            else{
                System.out.println("Please choose 1, 2 3, or 4.");
            }
        }

        int doesItHit = 1 + (int)(Math.random()*20);
        switch(choice) {
            case "1": // light attack
                print("You used a light attack");
                if (doesItHit == 20) {
                    print("You hit a critical strike! The "+e.name+" took "+e.takeDamage((attackDamage * 0.67) * 1.2)+" damage.");
                } else {
                    print("The "+e.name+" took "+e.takeDamage(attackDamage * 0.67)+" damage.");
                }
                break;
            case "2": // medium attack
                print("You used a medium attack");
                if (doesItHit == 20) {
                    print("You hit a critical strike! The "+e.name+" took "+e.takeDamage(attackDamage * 1.2)+" damage.");
                } else if (doesItHit >= 5) {
                    print("The "+e.name+" took "+e.takeDamage(attackDamage)+" damage.");
                } else {
                    print("You missed! The "+e.name+" dodged the attack.");
                }
                break;
            case "3": // heavy attack
                print("You used a heavy attack");
                if (doesItHit > 10) {
                    if (Necromancer.chargedAttack == 1){
                        Necromancer.chargedAttack = 3;
                        System.out.println("Interrupts charge");
                    }
                    print("The "+e.name+" took "+e.takeDamage(attackDamage * 1.34)+" damage.");
                } else {
                    print("You missed! The "+e.name+" dodged the attack.");
                }
                break;
            case "4":
                useHealthPotion();
                break;
        }
        e.isAlive();
        System.out.println();
    }

    protected int playerTakeDamage(double damage){
        double reduction = getTotalArmorMultiplier();
        int finalDamageTaken = (int)(damage * reduction);
        damage = damage * reduction;
        health -= (int) damage;
        if (health <= 0) {
            health = 0;
            playerDied();
        }
        return finalDamageTaken;
    }

    protected int getTotalAttackDamage(){
        int total = baseAttackDamage;
        for(DamageItem damageItem : damageItems){
            total +=damageItem.damage;
        }
        return total;
    }

    protected double getTotalArmorMultiplier(){
        double total = 1.0;
        for(DefenseItem armour : defenseItems){
            total *=armour.damageMultiplier;
        }
        return total;
    }

    public String toString(){
        int totalDamage = getTotalAttackDamage();
        return name+" Health: "+health+" Damage: "+totalDamage+" Health potions: "+healthPotion;
    }

    public String getInventory(){
        StringBuilder sb = new StringBuilder();

        if (!damageItems.isEmpty()) {
            sb.append(">>>> Equipment\n");
            for (DamageItem item : damageItems) {
                sb.append(item.name)
                        .append(" (Damage added: ")
                        .append(item.damage)
                        .append(")\n");
            }
        }

        // Printer alle defensive items (Armour)
        if (!defenseItems.isEmpty()) {
            sb.append("\n>>>> Armour\n");
            for (DefenseItem item : defenseItems) {
                int reductionPercent = (int)((1 - item.damageMultiplier) * 100);
                sb.append(item.name)
                        .append(" (Reduces damage by ")
                        .append(reductionPercent)
                        .append("%)\n");
            }
        }
        if (damageItems.isEmpty() && defenseItems.isEmpty()){
            return "Your inventory is empty.\n";
        } else {
            return sb.toString();
        }
    }

    protected void useHealthPotion(){
        if (healthPotion > 0){
            health += 40;
            if (health > maxHealth){
                health = maxHealth;
            }
            print("You used a potion and regained 40 health.\n");
            healthPotion--;
        }
    }

    protected void addHealthPotion(){
        healthPotion++;
        print("You now have "+healthPotion+" health potions.\n");
    }

    protected boolean isAlive(){
        return health > 0;
    }

    protected void playerDied(){
        print("You died!");
        System.exit(0);
    }
}
