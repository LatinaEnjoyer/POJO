package Blackjack;

import java.util.ArrayList;

public class Hand {
    ArrayList<Card> hand = new ArrayList<>();

    void addCard(Card card){
        hand.add(card);
    }

    int getValueOfHand(){
        int totalValueOfHand = 0;
        int aces = 0;

        for (Card c : hand){
            totalValueOfHand+=c.value;

            if (c.value == 11){
                aces++;
            }
        }

        while (totalValueOfHand > 21 && aces > 0){
            totalValueOfHand -= 10;
            aces--;
        }

        return totalValueOfHand;
    }
    
    void showHand(){
        if (hand.size()==2){
            System.out.println(hand.getFirst() + " + " + hand.getLast());

            if (getValueOfHand()==21){
                System.out.println("Blackjack!");
                return;
            }
            System.out.println("Value: " + getValueOfHand());
            return;
        }

        for (Card c : hand){
            if (c.equals(hand.getFirst())){
                System.out.print(c + " + ");
            }
            else if (c.equals(hand.getLast())) {
                System.out.println(c);
            }
            else {
                System.out.print(c + " + ");
            }
        }

        if (getValueOfHand()==21){
            System.out.println("Blackjack!");
            return;
        }
        System.out.println("Value: " + getValueOfHand());
    }

    public static void main(String[] args) {
        Hand p = new Hand();
        Deck d = new Deck();
        d.createDeck();
        d.shuffleDeck();
        p.addCard(d.drawCard());
        p.showHand();
        p.getValueOfHand();
    }
}
