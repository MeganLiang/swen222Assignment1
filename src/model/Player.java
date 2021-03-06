package model;

import controller.Game;
import model.Squares.RoomSquare;
import model.Squares.StartSquare;

import java.awt.*;
import java.util.ArrayList;

/**
 * Player class for game
 */
public class Player {
    private String name;
    private Character.Characters character;
    private Hand hand;
    private Point point;
    private Point previousPoint;
    private boolean justEnteredRoom = false;
    private boolean inRoom;
    private boolean madeAccusation = false;

    public Player(String name) {
        this.name = name;
        this.hand = new Hand(new ArrayList<Card>());
        this.inRoom = false;
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
        for(Card c : this.hand.getCards()) {
            System.out.println(c.getName());
        }
    }

    /**
     * starting point for player determined by board
     * @param characterEnum character the player has chosen
     * @param board board of game
     * @return point of start point
     */
    public Point startingSquare(Character.Characters characterEnum, Board board, Game cluedo) {
        for(int x = 0; x< cluedo.getBoard().getWIDTH(); x++) {
            for(int y=0; y<cluedo.getBoard().getHEIGHT(); y++) {
                if(board.getBoard()[x][y] instanceof StartSquare) {
                    StartSquare startSquare = (StartSquare) board.getBoard()[x][y];
                    if(startSquare.getCharacter().equals(characterEnum)) {
                        return new Point(startSquare.getxPos(), startSquare.getYpos());
                    }
                }
            }
        }
        return null;
    }

    /**
     * returns if player is in a room
     * @param player the player
     * @param board the game board
     * @return true if player is in room
     */
    public boolean isInRoom(Player player, Board board, Game cluedo) {

        for(int x=0; x<cluedo.getBoard().getWIDTH(); x++) {
            for (int y = 0; y < cluedo.getBoard().getHEIGHT(); y++) {
                if (board.getBoard()[player.getPositionPoint().x][player.getPositionPoint().y] instanceof RoomSquare) {
                    inRoom = true;
                    return true;
                }
            }
        }
        inRoom = false;
        return false;
    }

    /**
     * returns the room that the player is in
     * @param player player
     * @param board game board
     * @return room of player
     */
    public Room findRoom(Player player, Board board, Game cluedo) {
        for(int x=0; x<cluedo.getBoard().getWIDTH(); x++) {
            for (int y = 0; y < cluedo.getBoard().getHEIGHT(); y++) {
                if (board.getBoard()[player.getPositionPoint().x][player.getPositionPoint().y] instanceof RoomSquare) {
                    RoomSquare roomSquare = (RoomSquare)board.getBoard()[player.getPositionPoint().x][player.getPositionPoint().y];
                    return roomSquare.getRoom();
                }
            }
        }
        return null;
    }
    /**
     * boolean for entering a room
     * @return boolean
     */
    public boolean isJustEnteredRoom() {
        return justEnteredRoom;
    }
    /**
     * For testing, setting the boolean for entering square
     * @param justEnteredRoom
     */
    public void setJustEnteredRoom(boolean justEnteredRoom) {
        this.justEnteredRoom = justEnteredRoom;
    }
    public void setInRoom(boolean inRoom) {
        this.inRoom = inRoom;
    }

    public Point getPositionPoint() {
        return point;
    }

    public void setPositionPoint(Point point) {
        this.point = point;
    }

    public boolean hasMadeAccusation() {
        return madeAccusation;
    }

    public void setMadeAccusation(boolean madeAccusation) {
        this.madeAccusation = madeAccusation;
    }

    public Point getPreviousPoint() {
        return previousPoint;
    }

    public void setPreviousPoint(Point previousPoint) {
        this.previousPoint = previousPoint;
    }

}
