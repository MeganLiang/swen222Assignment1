package model;

import java.util.ArrayList;

/**
 * Created by megan on 19/07/16.
 */
public class Player {
    private String name;
    private Character.Characters character;
    private Hand hand;

    public Player(String name) {
        this.name = name;
        this.hand = new Hand(new ArrayList<Weapon>(), new ArrayList<Character>(), new ArrayList<Room>());
    }

    public Hand getHand() {
        return hand;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public Character.Characters getCharacter() {
        return character;
    }

    public void setCharacter(Character.Characters character) {
        this.character = character;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void printHand() {
        System.out.println(this.name + ": ");
        for(Weapon w : this.hand.getWeaponsHand()) {
            System.out.println(w.getName());
        }
        for(Room w : this.hand.getRoomsHand()) {
            System.out.println(w.getName());
        }
        for(Character w : this.hand.getCharactersHand()) {
            System.out.println(w.getName());
        }
    }

}