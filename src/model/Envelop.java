package model;

/**
 * Created by megan on 19/07/16.
 */
public abstract class Envelop {
    protected Weapon weapon;
    protected Room room;
    protected Character character;

    public Envelop(Weapon weapon, Room room, Character character) {
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
