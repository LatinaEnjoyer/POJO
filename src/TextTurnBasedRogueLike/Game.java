package TextTurnBasedRogueLike;

import java.util.Random;
import java.util.Scanner;

import static TextTurnBasedRogueLike.TextWriter.print;
import static TextTurnBasedRogueLike.Tools.scan;

public class Game {
    final Player player;
    final EnemyCollector enemyCollector;
    private final Environment environment;

    protected Game() {
        player = new Player("Adventurer", 30, 100);
        enemyCollector = new EnemyCollector();
        environment = new Environment(this, enemyCollector, player);
    }

    protected void start() {
        print("You find yourself at the entrance of a forgotten dungeon.");
        print("The air is thick with dust and the scent of danger.");
        print("Prepare yourself, adventurer, you must defeat the evil necromancer at the end of this ominous dungeon!\n");
        print("A chill runs down your spine as the air grows thick and silent.");
        print("With a deep breath, you step forward into the unknown...\n");
        print("From deep within, you hear faint echoes, scraping, and distant clatters.");
        print("You proceed cautiously deeper in the dungeon, the air thick with an unsettling silence.\n");
        print("Press enter to begin your adventure.");
        scan.nextLine();
        environment.nextRoom();
    }

    protected static int fight;

    protected void combat(Player p, Enemy e) {
        int turn = 1;
        fight++;
        print("----- Fight " + fight + " -----");
        while (p.isAlive() && e.isAlive()) {
            print(">>>> " + p);
            print(">>>> " + e);
            print("----- Turn " + turn + " -----");
            p.playerAttack(e);
            if (e.isAlive()) {
                e.attack(p);
            } else {
                e.died();
            }
            turn++;
        }
        e.resetHealth();
    }

    protected void endGame(){
        System.exit(0);
    }
}


class Tools {
    public static final Scanner scan = new Scanner(System.in);
    public static final Random rand = new Random();
}