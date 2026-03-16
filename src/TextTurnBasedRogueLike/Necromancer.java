package TextTurnBasedRogueLike;

import static TextTurnBasedRogueLike.TextWriter.print;
import static TextTurnBasedRogueLike.Tools.rand;

public class Necromancer extends Enemy{
    boolean phaseTwo = false;

    Necromancer(){
        super("Necromancer", 300, 30);
    }

    static int defensiveState;
    static int chargedAttack;
    @Override
    protected void attack(Player a) {
        if (!phaseTwo && health < maxHealth * 0.5){
            phaseTwo = true;
            baseDamage = 40;
            defensiveState = 0;
            print("\nThe Necromancer screams in fury! Dark energy surges around him.");
            print("The temperature drops, the shadows deepen, and his skeleton army rattles with renewed life.");
            print("\"You will not survive this!\" he roars.");
            print("Phase Two has begun!\n");
        }

        if (!phaseTwo){
            attackPhaseOne(a);
        } else {
            attackPhaseTwo(a);
        }
    }

    protected void attackPhaseOne(Player a){
        if(defensiveState == 1 || defensiveState == 2){defensiveState++;}

        int percentAttack;
        if (chargedAttack == 0){
            int randAttack = rand.nextInt(100);
            if (randAttack < 20){
                percentAttack = 0;
            } else if (randAttack < 40){
                percentAttack = 1;
            } else {
                percentAttack = 2;
            }
            int hitOrMiss = rand.nextInt(20)+1;
            switch (percentAttack){
                // charged attack (cant miss, starts charging in round x and hits in round x+1) (Player can break attack with heavy)
                case 0: chargedAttack++;
                    print("The Necromancer raises his staff, dark energy swirling around him... He begins to charge a deadly spell! (Perhaps a heavy attack can break his spell)");
                    break;
                // defensive state (50% less damage for two turns)
                case 1:
                    if (defensiveState == 0){
                        print("The Necromancer conjures a barrier of shadowy skulls. His form shimmers, taking less damage!");
                        defensiveState++;
                    } else {
                        print("The Necromancer strengthens his shadow barrier. He looks even more protected!");
                        defensiveState = 1;
                    }
                    break;
                // normal attack (nothing special happens here)
                case 2:
                    if (hitOrMiss == 20){
                        print("With a burst of dark magic, the Necromancer lands a critical strike!");
                        a.playerTakeDamage(baseDamage*1.5);
                    } else if (hitOrMiss > 10){
                        print("The Necromancer swings his dark staff, striking you!");
                        a.playerTakeDamage(baseDamage);
                    } else {
                        print("The Necromancer's attack misses as you nimbly dodge!");
                        // missed attack
                    }
                    break;
            }
        } else if (chargedAttack == 1){
            print("The Necromancer is still gathering dark energy, preparing a devastating spell!");
            chargedAttack++;
        } else if (chargedAttack == 2){
            print("The Necromancer unleashes his charged attack! The blast hits with brutal force!");
            chargedAttack = 0;
            a.playerTakeDamage(baseDamage * 2);
        } else if (chargedAttack == 3){
            print("The Necromancer stumbles, momentarily stunned by his failed attempt!");
            chargedAttack = 0;
        }
        System.out.println();
    }

    protected void attackPhaseTwo(Player a){
        if(defensiveState == 1 || defensiveState == 2){defensiveState++;}

        int percentAttack;
        if (chargedAttack == 0) {
            int randAttack = rand.nextInt(100);
            if (randAttack < 30) {
                percentAttack = 0;
            } else if (randAttack < 50) {
                percentAttack = 1;
            } else {
                percentAttack = 2;
            }
            int hitOrMiss = rand.nextInt(20) + 1;
            switch (percentAttack) {
                // charged attack (cant miss, starts charging in round x and hits in round x+1) (Player can break attack with heavy)
                case 0:
                    chargedAttack++;
                    print("The Necromancer gathers forbidden energy, his eyes glowing red! A catastrophic spell is forming! (Perhaps a heavy attack can break his spell)");
                    break;
                // life leech
                case 1:
                    int damage = a.playerTakeDamage(baseDamage * 0.9);
                    health = health + (int) (damage * 0.25);
                    print("Dark tendrils reach out from the Necromancer, draining your life to mend his own wounds!");
                    break;
                // normal attack (nothing special happens here)
                case 2:
                    if (hitOrMiss == 20) {
                        print("A violent surge of necrotic energy hits critically!");
                        a.playerTakeDamage(baseDamage * 1.5);
                    } else if (hitOrMiss > 10) {
                        print("The Necromancer lashes out with dark magic, striking you!");
                        a.playerTakeDamage(baseDamage);
                    } else {
                        print("You evade the Necromancer's dark strike just in time!");
                        // missed attack
                    }
                    break;
            }
        } else if (chargedAttack == 1){
            print("The Necromancer releases the charged dark energy! The spell hits with devastating force!");
            chargedAttack = 0;
            a.playerTakeDamage(baseDamage * 2);
        } else if (chargedAttack == 3){
            print("The Necromancer falters, stunned by his own failed spell!");
            chargedAttack = 0;
        }
        System.out.println();
    }

    protected double defenseAdd(){
        if (defensiveState == 0){
            return 1.0;
        } else if (defensiveState == 1 || defensiveState == 2){
            // print takes less damage
            return 0.5;
        } else {
            defensiveState = 0;
            return 0.5;
        }
    }

    @Override
    protected int takeDamage(double damage){
        double finalDamage = defenseAdd() * damage;
        health -= (int) finalDamage;
        if (health <= 0) {
            health = 0;
        }
        return (int) finalDamage;
    }

    @Override
    protected void died() {
        print("The Necromancer and his skeleton army have been defeated! The dark power fades from the chamber...");
    }
}
