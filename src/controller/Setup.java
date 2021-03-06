package controller;

import model.*;
import model.Character;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Controller Setup to prepare the Game
 */
public class Setup {
    private Solution gameSolution;
    private int numPlayers;

    private Set<Weapon.Weapons> availableWeapons= new HashSet<>(Arrays.asList(Weapon.Weapons.values()));
    private List<Room.Rooms> availableRooms= new ArrayList<>(Arrays.asList(Room.Rooms.values()));
    private Set<Character.Characters> availableCharacters = new HashSet<>(Arrays.asList(Character.Characters.values()));

    /**
     * one character, one weapon and one room card are selected at random and is the solution
     */
    public void initGame(Game cluedo) {

        numPlayers = getNumberOfPlayers(cluedo);

        Weapon randomWeapon = new Weapon(null);
        Room randomRoom = new Room(null);
        Character randomCharacter = new Character(null);

        randomRoom.setRoomName((Room.Rooms) randomRoom.getRandom(Room.Rooms.class));
        randomWeapon.setWeaponName((Weapon.Weapons) randomWeapon.getRandom(Weapon.Weapons.class));
        randomCharacter.setCharacterName(randomCharacter.getRandom(Character.Characters.class));


        gameSolution = new Solution(randomWeapon, randomRoom, randomCharacter);
        gameSolution.printSolution();
        chooseCharacters(numPlayers,cluedo);
        dealCards(cluedo);
        placePlayersAtStart(cluedo);

    }

    /**
     * initialise player in starting positions
     */
    private void placePlayersAtStart(Game cluedo) {
        for(Player player: cluedo.getPlayerList()) {
            Point point = player.startingSquare(player.getCharacter(), cluedo.getBoard(), cluedo);
            player.setPositionPoint(point);
            cluedo.getPaintBoard().updateArray(player,cluedo);
        }
        cluedo.getPaintBoard().paintBoard();
    }
    /**
     * Get the number of players
     * @return the number of players
     */
    private int getNumberOfPlayers(Game cluedo) {
        int numPlayers = 0;
        while(numPlayers < 3 || numPlayers > 6) {
            numPlayers = cluedo.getTextBaseCluedo().getNumberOfPlayers();
            if(numPlayers < 3 || numPlayers > 6) {
                System.out.println("Please enter a number between 3-6");
                numPlayers = cluedo.getTextBaseCluedo().getNumberOfPlayers();
            }
        }
        System.out.println("Number of players is " + numPlayers);
        System.out.println("=======================================");
        return numPlayers;
    }
    /**
     * solution is picked, cards are dealt evenly to players
     */
     public void dealCards(Game cluedo) {
        dealCards(cluedo, Weapon.Weapons.class, Character.Characters.class, Room.Rooms.class);
        dealWeaponsAtStart(cluedo);
    }

    /**
     * the implementation for dealing the cards, this is done randomly
     * @param weaponClass the num class Weapons
     * @param characterClass the enum class Characters
     * @param roomClass the enum class Rooms
     */
    private void dealCards(Game cluedo, Class<Weapon.Weapons> weaponClass, Class<Character.Characters> characterClass, Class<Room.Rooms> roomClass) {
        int numWeapons = weaponClass.getEnumConstants().length-1; //number of available weapons minus the solution weapon
        int numChars = characterClass.getEnumConstants().length-1;
        int numRooms = roomClass.getEnumConstants().length-1;

        int numCards = numWeapons+numChars+numRooms;

        int dealtEvenly = numCards/numPlayers;
        List<Weapon.Weapons> weaponsList = new ArrayList<>(Arrays.asList(Weapon.Weapons.values()));
        weaponsList.remove(gameSolution.getWeapon().getEnum());
        List<Character.Characters> charactersList = new ArrayList<>(Arrays.asList(Character.Characters.values()));
        charactersList.remove(gameSolution.getCharacter().getEnum());
        List<Room.Rooms> roomsList = new ArrayList<>(Arrays.asList(Room.Rooms.values()));
        roomsList.remove(gameSolution.getRoom().getEnum());

        List<Card> cardsList = new ArrayList<>();
        for(Weapon.Weapons w: weaponsList) {
            Weapon weapon = new Weapon(w);
            cardsList.add(weapon);
        }
        for(Character.Characters c: charactersList) {
            Character character = new Character(c);
            cardsList.add(character);
        }
        for(Room.Rooms r: roomsList) {
            Room room = new Room(r);
            cardsList.add(room);
        }
        System.out.println("_______________________________________");

        Collections.shuffle(cardsList);

        int index = 0;
        for(Player player : cluedo.getPlayerList()) {
            for (int i = 0; i < dealtEvenly; i++) {
                Card c = cardsList.get(index);
                player.getHand().addToHand(c);
                index++;
            }
        }
        System.out.println("These are the cards everyone can see: (This can be empty if all the cards are dealt evenly)");
        for(int i = index; i < cardsList.size(); i++) {
            System.out.println(cardsList.get(i).getName());
        }
        System.out.println();

    }

    /**
     * deals weapons to rooms randomly
     */
    public void dealWeaponsAtStart(Game cluedo) {
        List<Weapon.Weapons> weaponsList = new ArrayList<>(Arrays.asList(Weapon.Weapons.values()));
        Collections.shuffle(weaponsList); //randomise the weapons
        Collections.shuffle(availableRooms); //randomise the rooms
        System.out.println("================================");
        System.out.println("Weapons is each room:");
        for(int index = 0; index < 6; index++) {
            Room room = new Room(availableRooms.get(index));
            room.addWeapon(new Weapon(weaponsList.get(index)));

            Weapon.Weapons enumW = weaponsList.get(index);
            Weapon weapon = new Weapon(enumW);
            weapon.setPrevRoom(null);
            weapon.setInRoom(room);
            System.out.println(weapon.getName() + " added to " + room.getName());
        }
    }

    /**
     * players are asked to choose a character from the game, which determines their
     * starting position
     * @param numOfPlayers number of players
     * @return Set<Characters></>
     */
    public void chooseCharacters(int numOfPlayers, Game cluedo) {
        int count = 0;
        Set<Character.Characters> chosenCharacters = new HashSet<>();
        System.out.println("List of characters:");
        cluedo.getTextBaseCluedo().printHelp();
        while (count != numOfPlayers) {

            String playerName = cluedo.getTextBaseCluedo().getPlayers();
            Player player = new Player(playerName);
            cluedo.addToPlayersList(player);
            player.setName(playerName);

            String next = cluedo.getTextBaseCluedo().choosingCharacters();
            if(next.contains("help")) {
                cluedo.getTextBaseCluedo().printHelp();
                next = cluedo.getTextBaseCluedo().choosingCharacters();
            }
            while(chosenCharacters.contains(Character.Characters.fromString(next))
                    || !availableCharacters.contains(Character.Characters.fromString(next))) { //invalid input, can be duplicate character or not a token
                next = cluedo.getTextBaseCluedo().invalidCharacterInput();
            }
            if(!chosenCharacters.contains(Character.Characters.fromString(next))
                    && availableCharacters.contains(Character.Characters.fromString(next))) {
                chosenCharacters.add(Character.Characters.valueOf(next));
                player.setCharacter(Character.Characters.valueOf(next));
                count++;
            }
        }
        System.out.println("=======================================");

    }

    /**
     * getter for number of players
     * @return
     */
    public int getNumPlayers() {
        return numPlayers;
    }

    /**
     * Getter for game solution
     * @return
     */
    public Solution getGameSolution() {
        return gameSolution;
    }

    /**
     * Getter for weapons list
     * @return
     */
    public Set<Weapon.Weapons> getAvailableWeapons() {
        return availableWeapons;
    }
    /**
     * Getter for room list
     * @return
     */
    public List<Room.Rooms> getAvailableRooms() {
        return availableRooms;
    }
    /**
     * Getter for character list
     * @return
     */
    public Set<Character.Characters> getAvailableCharacters() {
        return availableCharacters;
    }
}
