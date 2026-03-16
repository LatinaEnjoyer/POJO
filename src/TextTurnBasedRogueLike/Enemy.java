package TextTurnBasedRogueLike;

import java.util.ArrayList;

import static TextTurnBasedRogueLike.TextWriter.print;
import static TextTurnBasedRogueLike.Tools.rand;

abstract class Enemy {
    final String name;
    int health;
    int baseDamage;
    final int maxHealth;

    Enemy(String name, int health, int baseDamage){
        this.name = name;
        this.health = health;
        this.baseDamage = baseDamage;
        this.maxHealth = this.health;
    }

    protected void resetHealth(){
        health = maxHealth;
    }

    protected abstract void attack(Player a);

    protected int takeDamage(double damage){
        int finalDamageTaken = (int) damage;
        health -= (int) damage;
        if (health <= 0) {
            health = 0;
        }
        return finalDamageTaken;
    }

    protected boolean isAlive(){
        return health > 0;
    }

    protected abstract void died();

    public String toString(){
        return name+" Health: "+health;
    }
}

class Undead extends Enemy{
    Undead(){
        super("undead", 80, 15);
    }

    protected void attack(Player a){
        int randomAttack = 1 + (int)(Math.random()*3);
        int doesItHit = 1 + (int)(Math.random()*20);
        switch(randomAttack){
            case 1: // light attack
                print("The "+name+" punches you in a flurry!");
                if (doesItHit == 20){
                    print("The "+name+" has dealt a critical strike! You took "+a.playerTakeDamage((baseDamage * 0.67) * 1.2)+" damage.");
                }
                else if (doesItHit >= 5){
                    print("You took "+a.playerTakeDamage(baseDamage * 0.67)+" damage.");
                }
                else {
                    print("You dodged the "+name+"'s flurry of strikes!");
                }
                break;
            case 2: // medium attack
                print("The "+name+" claws at you!");
                if (doesItHit == 20){
                    print("The "+name+" has dealt a critical strike! You took "+a.playerTakeDamage(baseDamage * 1.2)+" damage.");
                }
                else if (doesItHit >= 10){
                    print("You took "+a.playerTakeDamage(baseDamage)+" damage.");
                }
                else {
                    print("You dodged the clawing "+name+"!");
                }
                break;
            case 3: // heavy attack
                print("The "+name+" charges at you!");
                if (doesItHit == 20){
                    print("The "+name+" has dealt a critical strike! You took "+a.playerTakeDamage((baseDamage * 1.33) * 1.2)+" damage.");
                }
                else if (doesItHit >= 15){
                    print("You took "+a.playerTakeDamage(baseDamage * 1.33)+" damage.");
                }
                else {
                    print("You dodged the enraged "+name+"'s deadly charge!");
                }
                break;
        }
        if (!a.isAlive()){
            a.playerDied();
        }
        System.out.println();
    }

    protected void died(){
        print("The "+name+" has been slain!");
    }
}

class Goblin extends Enemy{
    Goblin(){
        super("goblin", 65, 10);
    }

    protected void attack(Player a) {
        int randomAttack = 1 + (int)(Math.random()*3);
        int doesItHit = 1 + (int)(Math.random()*20);
        switch(randomAttack){
            case 1: // light attack
                print("The "+name+" picks up scattered pebbles and throws them at you!");
                if (doesItHit == 20){
                    print("The "+name+" has dealt a critical strike! You took "+a.playerTakeDamage((baseDamage * 0.67) * 1.2)+" damage.");
                }
                else if (doesItHit > 3){
                    print("You took "+a.playerTakeDamage(baseDamage * 0.67)+" damage.");
                }
                else {
                    print("You dodged the pebbles thrown by the "+name+"!");
                }
                break;
            case 2: // medium attack
                print("The "+name+" jabs its spear at you!");
                if (doesItHit == 20){
                    print("The "+name+" has dealt a critical strike! You took "+a.playerTakeDamage(baseDamage * 1.2)+" damage.");
                }
                else if (doesItHit > 6){
                    print("You took "+a.playerTakeDamage(baseDamage)+" damage.");
                }
                else {
                    print("You dodged the spear attack!");
                }
                break;
            case 3: // heavy attack
                print("The "+name+" picks up an enormous rock and slams it on you!");
                if (doesItHit == 20){
                    print("The "+name+" has dealt a critical strike! You took "+a.playerTakeDamage((baseDamage * 1.33) * 1.2)+" damage.");
                }
                else if (doesItHit > 9){
                    print("You took "+a.playerTakeDamage(baseDamage * 1.33)+" damage.");
                }
                else {
                    print("You deflected the rock before it hit your skull!");
                }
                break;
        }
        a.isAlive();
        System.out.println();
    }

    protected void died() {
        print("The "+name+" has been slain!");
    }
}
// giant spider

class EnemyCollector{
    private final ArrayList<Enemy> enemyList = new ArrayList<>();

    protected EnemyCollector(){
        enemyList.add(new Undead());
        enemyList.add(new Goblin());
    }

    protected Enemy getRandomEnemy(){
        int randomEnemy = rand.nextInt(enemyList.size());
        return enemyList.get(randomEnemy);
    }
}
