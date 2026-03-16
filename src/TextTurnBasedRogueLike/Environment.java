package TextTurnBasedRogueLike;

import java.util.ArrayList;
import java.util.Collections;

import static TextTurnBasedRogueLike.ASCIIArt.goblinArt;
import static TextTurnBasedRogueLike.ASCIIArt.undeadArt;
import static TextTurnBasedRogueLike.TextWriter.print;
import static TextTurnBasedRogueLike.Tools.rand;
import static TextTurnBasedRogueLike.Tools.scan;

public class Environment {
    private final Game game;
    private final EnemyCollector enemyCollector;
    private final Player player;
    private final Enemy necromancer;
    public static ArrayList<Item> scatteredItems = new ArrayList<>();
    private int encounterNo;

    static {
        // DamageItems
        scatteredItems.add(new DamageItem("Sword", 12));
        scatteredItems.add(new DamageItem("Axe", 10));
        scatteredItems.add(new DamageItem("Bow", 8));
        scatteredItems.add(new DamageItem("Dagger", 6));
        scatteredItems.add(new DamageItem("Staff", 9));
        // ArmourItems
        scatteredItems.add(new DefenseItem("Chainmail", 0.8));
        scatteredItems.add(new DefenseItem("Shield", 0.85));
        scatteredItems.add(new DefenseItem("Iron greaves", 0.85));
        scatteredItems.add(new DefenseItem("Gold helmet", 0.9));
        scatteredItems.add(new DefenseItem("Gloves", 0.9));
    }

    public Environment(Game game, EnemyCollector enemyCollector, Player player) {
        this.enemyCollector = enemyCollector;
        this.player = player;
        this.game = game;
        this.necromancer = new Necromancer();
    }

    public void nextRoom(){
        encounterNo++;

        if (encounterNo == 11){
            bossEncounterSetup();
            return;
        }

        ArrayList<String> pathway = new ArrayList<>();
        pathway.add("loot");
        pathway.add("enemy");
        pathway.add("enemy");
        Collections.shuffle(pathway);

        print("The path ahead forks into three different pathways, each leading somewhere different. Choose a path. (1, 2, or 3)");
        int path;
        while(true){
            path = Integer.parseInt(scan.nextLine());
            if(path >= 1 && path <= 3){
                break;
            }
            else{
                System.out.println("Please choose a pathway 1, 2 or 3.");
            }
        }

        String chosenPathway = pathway.get(path-1);
        if (chosenPathway.equals("loot")){
            lootEncounterSetup();
            lootEncounter();
        } else {
            enemyEncounterSetup();
            enemyEncounter();
        }
    }

    public void enemyEncounterSetup(){
        int dialog = rand.nextInt(3);
        if (dialog==0){
            print("You hear faint noises in the shadows ahead...");
            print("An enemy emerges, ready to attack!\n");
        } else if (dialog==1) {
            print("The air grows tense and cold, as if the dungeon itself is warning you...");
            print("You sense movement just beyond your vision, ready to strike at any moment.\n");
        } else {
            print("A sudden shiver runs down your spine as something unseen approaches...");
            print("The silence breaks with a faint growl, signaling danger nearby.\n");

        }
    }

    public void lootEncounterSetup(){
        int dialog = rand.nextInt(3);
        if (dialog==0){
            print("You step into a dimly lit chamber. The air feels still and heavy.");
            print("Broken weapons and scraps of armor lie scattered across the floor.");
            print("Something glimmers faintly in the corner... you might have found loot.\n");
        } else if (dialog==1) {
            print("Walking down the pathway you notice a faint light.");
            print("As you get closer to the light, you spot a destroyed statue.");
            print("It seems that something shiny rests on the rubble. Perhaps some loot?\n");
        } else {
            print("The air in here feels still, heavier than the last room.");
            print("Old stone and broken wood scatter the floor beneath your feet.");
            print("Near the far wall, a small chest catches your eye.\n");
        }
    }

    public void enemyEncounter(){
        Enemy randomEnemy = enemyCollector.getRandomEnemy();
        print("Oh no, "+grammarCheck(randomEnemy.name)+" "+randomEnemy.name+" has appeared!");

        if(randomEnemy.name.equals("goblin")){
            goblinArt();
        } else if (randomEnemy.name.equals("undead")) {
            undeadArt();
        }

        game.combat(player, randomEnemy);

        int chance = rand.nextInt(100);
        if (chance < 25){
            print("The "+randomEnemy.name+" dropped a health potion.");
            player.addHealthPotion();
        }

        postEncounter("enemy");
    }

    public void lootEncounter(){
        int loot = rand.nextInt(scatteredItems.size()); // random number from 0 - size of scatteredItems Arraylist
        Item foundLoot = scatteredItems.get(loot); // the random number is used to pluck item from scatteredItems list
        scatteredItems.remove(loot); // removes from lootRoom pool
        player.inventory.add(foundLoot); // adds to player inventory

        if(foundLoot instanceof DefenseItem){
            player.defenseItems.add((DefenseItem) foundLoot);
        } else {
            player.damageItems.add((DamageItem) foundLoot);
        }

        String grammar = grammarCheck(foundLoot.name);
        print("You pick up what is before you.");
        print("You found "+(grammar.isEmpty() ? "" : grammar + " ") +foundLoot.name.toLowerCase()+"!");
        print("What great fortune!\n");

        int chance = rand.nextInt(100);
        if (chance < 40){
            print("You also found a health potion in the room!\n");
            player.addHealthPotion();
        }

        postEncounter("loot");
    }

    public void postEncounter(String encounter){
        if(encounter.equals("loot")){
            print("You proceed deeper into the dungeon and stumble upon what looks to be a safe place to take a break.\n");
        } else {
            print("After a hard fought victory, you take a break before moving on.\n");
        }

        int choiceMenu;
        print("----- Break time -----");
        while (true){
            print("Proceed down the next pathway = 1");
            print("Check your inventory = 2");
            print("Check your stats = 3");
            print("Use a health potion = 4");
            while(true){
                choiceMenu = Integer.parseInt(scan.nextLine());
                if(choiceMenu >= 1 && choiceMenu <= 4){
                    break;
                }
                else{
                    System.out.println("Please choose 1, 2, 3 or 4.");
                }
            }
            if(choiceMenu==1){
                print("You get up and move deeper through the dungeon.");
                break;
            } else if (choiceMenu==2){
                print(player.getInventory());
            } else if (choiceMenu==3){
                print(player+"\n");
            } else {
                player.useHealthPotion();
                print(player+"\n");
            }
        }
        nextRoom();
    }

    public void bossEncounterSetup(){
        print("\nYou step into the darkened chamber. The air is thick with a foul, unholy stench.");
        print("Shadows twist and writhe along the walls, as if alive.");
        print("A tall figure emerges from the darkness, robes billowing, eyes glowing a sinister red.");
        print("The Necromancer glares at you, a cruel smile spreading across his face.");
        print("\"Foolish mortal,\" he hisses, \"you dare enter my domain?\"");
        print("Bones crack and rise from the ground around him, forming a skeletal army at his command.");
        print("The battle is about to begin...\n");

        game.combat(player, necromancer);

        if (player.isAlive()) {
            print("\nAgainst all odds, you have defeated the Necromancer!");
            print("The dark power in the chamber dissipates, and the skeletons collapse into piles of bones.");
            print("Victory is yours!");
            game.endGame();
        } else {
            print("\nYou have been defeated by the Necromancer...");
            game.endGame();
        }
    }

    public String grammarCheck(String itemName){
        if (itemName.endsWith("s")){
            return "";
        }

        char firstLetter = Character.toLowerCase(itemName.charAt(0));
        if (firstLetter == 'a' || firstLetter == 'e' || firstLetter == 'i' || firstLetter == 'o' || firstLetter == 'u'){
            return "an";
        } else {
            return "a";
        }
    }
}
