package model;

public class Solution extends Envelope {

    public Solution(Weapon weapon, Room room, Character character) {
        super(weapon, room, character);
    }

    public void printSolution() {
        System.out.println(weapon.getName());
        System.out.println(character.getName());
        System.out.println(room.getName());
    }

}
