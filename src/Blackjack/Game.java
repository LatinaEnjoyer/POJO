package Blackjack;

import java.util.Scanner;

public class Game {
    Hand player = new Hand();
    Hand dealer = new Hand();
    Deck deck = new Deck();
    static Scanner scan = new Scanner(System.in);
    int wins = 0;
    int losses = 0;
    int ties = 0;

    void gameFlow(){
        startGame();
        playerTurn();
        dealerTurn();
        compareScores();
        playAgain();
    }

    void startGame() {
        //Create deck
        deck.createDeck();
        deck.shuffleDeck();
        //Deal cards
        player.addCard(deck.drawCard());
        player.addCard(deck.drawCard());
        dealer.addCard(deck.drawCard());
        dealer.addCard(deck.drawCard());
        //Game begins
        System.out.println("Blackjack");
        System.out.println("Dealer stands on 17 or higher\n");

        System.out.println("Wins: " + wins);
        System.out.println("Losses " + losses);
        System.out.println("Ties " + ties);

        System.out.println("Dealer's hand:");
        System.out.println(dealer.hand.getFirst() + "\n");
        System.out.println("Your hand:");
        player.showHand();
        System.out.println();

        System.out.println("Press enter to start");
        scan.nextLine();
    }

    void playerTurn(){
        if (player.getValueOfHand()==21){
            System.out.println("Blackjack!");
            System.out.println("Skipping turn\n");
        }

        while (player.getValueOfHand() < 21){
            System.out.println("Hit or stand?");
            System.out.println("1) Hit");
            System.out.println("2) Stand");
            switch (choice()) {
                case "1" -> player.addCard(deck.drawCard());
                case "2" -> {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return;
                }
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println("Your hand:");
            player.showHand();
            System.out.println();
        }

        if (player.getValueOfHand()>21){
            System.out.println("Bust!\n");
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    void dealerTurn(){
        System.out.println("Dealer's turn");
        dealer.showHand();
        System.out.println();

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        while (dealer.getValueOfHand() < 17){
            dealer.addCard(deck.drawCard());
            dealer.showHand();
            System.out.println();

            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        if (dealer.getValueOfHand()>21){
            System.out.println("Bust!\n");
        }
        else {
            System.out.println("Dealer stands\n");
        }
    }

    void compareScores(){
        int playerScore = player.getValueOfHand();
        int dealerScore = dealer.getValueOfHand();

        if (playerScore > 21) {
            System.out.println("Dealer wins\n");
            losses++;
        }
        else if (dealerScore > 21) {
            System.out.println("You win!\n");
            wins++;
        }
        else if (playerScore > dealerScore) {
            System.out.println("You win!\n");
            wins++;
        }
        else if (dealerScore > playerScore) {
            System.out.println("Dealer wins\n");
            losses++;
        }
        else {
            System.out.println("You and dealer have the same hand value");
            System.out.println("Push\n");
            ties++;
        }
    }

    void playAgain(){
        System.out.println("Keep playing?");
        System.out.println("1) Yes");
        System.out.println("2) No");

        switch (choice()){
            case "1" -> {
                deck.deck.addAll(player.hand);
                deck.deck.addAll(dealer.hand);
                player.hand.clear();
                dealer.hand.clear();
                deck.shuffleDeck();
                gameFlow();
            }
            case "2" -> System.exit(0);
        }
    }

    String choice(){
        String choice = scan.nextLine();
        while (!choice.equals("1") && !choice.equals("2")){
            choice = scan.nextLine();
        }
        return choice;
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.gameFlow();
    }
}
