package Blackjack;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    ArrayList<Card> deck = new ArrayList<>();

    void createDeck(){
        for (int i = 2; i<=10; i++){
            deck.add(new Card(i+"", "Hearts", i));
            deck.add(new Card(i+"", "Spades", i));
            deck.add(new Card(i+"", "Clubs", i));
            deck.add(new Card(i+"", "Diamonds", i));
        }
        // Hearts
        deck.add(new Card("Jack", "Hearts", 10));
        deck.add(new Card("Queen", "Hearts", 10));
        deck.add(new Card("King", "Hearts", 10));
        deck.add(new Card("Ace", "Hearts", 11));
        // Spades
        deck.add(new Card("Jack", "Spades", 10));
        deck.add(new Card("Queen", "Spades", 10));
        deck.add(new Card("King", "Spades", 10));
        deck.add(new Card("Ace", "Spades", 11));
        // Clubs
        deck.add(new Card("Jack", "Clubs", 10));
        deck.add(new Card("Queen", "Clubs", 10));
        deck.add(new Card("King", "Clubs", 10));
        deck.add(new Card("Ace", "Clubs", 11));
        // Diamonds
        deck.add(new Card("Jack", "Diamonds", 10));
        deck.add(new Card("Queen", "Diamonds", 10));
        deck.add(new Card("King", "Diamonds", 10));
        deck.add(new Card("Ace", "Diamonds", 11));
    }

    void shuffleDeck(){
        Collections.shuffle(deck); // shuffles deck
    }

    Card drawCard(){
        return deck.removeFirst();
    }

    public static void main(String[] args) {
        Deck d = new Deck();
        d.createDeck();
        System.out.println(d.deck);
        d.shuffleDeck();
        System.out.println(d.deck);
        System.out.println(d.drawCard());
        System.out.println(d.deck);
    }
}
