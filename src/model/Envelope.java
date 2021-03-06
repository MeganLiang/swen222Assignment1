package model;

/**
 * Abstract class for Accusation, Solution and Suggestion classes
 * Holds a weapon, room and character
 */
public abstract class Envelope {
    protected Weapon weapon;
    protected Room room;
    protected Character character;

    public Envelope(Weapon weapon, Room room, Character character) {
        this.weapon = weapon;
        this.room = room;
        this.character = character;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public Room getRoom() {
        return room;
    }

    public Character getCharacter() {
        return character;
    }


}
