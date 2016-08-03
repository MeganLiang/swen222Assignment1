package controller;

import model.Move;
import model.Player;
import model.Room;
import model.Squares.*;

import java.awt.*;
import java.util.*;
import java.util.List;


public class Moving {
    /**
     * Get a list of squares the player can move too
     * @return List<int><int>
     */
//    public static boolean moveCheck(Point point, Player player, int roll){
//        //Get List of Available Squares
//        Set<Point> availableSquares = availableMoves(player, roll);
//        System.out.println("available squares");
//        for(Point p: availableSquares) {
//            System.out.println(p.toString());
//        }
//
//        //Remove Squares that Players are in
//        for(Player p: Game.getPlayerList()){
//            Point checkPoint = p.getPositionPoint();
//            if(availableSquares.contains(checkPoint)){
//                availableSquares.remove(checkPoint);
//            }
//        }
//
//        //CHeck if intended movement is possible
//        if(availableSquares.contains(point)){
//            //Move players point to that position
//            player.setPositionPoint(point);
//            return true;
//        }
//        //square cannot be moved too
//        return false;
//
//    }
    /**
     * Get a list of squares the player can move to
     * @return List<int><int>
     */
    public boolean moveCheck(Point point, Player player, int roll, Game cluedo){
        if(point.getX() < 0 || point.getX() > 23 || point.getY() < 0 || point.getY() > 24){
            return false;
        }

        //Create List of Availabe Squares
        Set<Point> availableSquares;
        //If moving to stairway square
        if(cluedo.getBoard().returnSquare(point)instanceof StairwaySquare) {
            System.out.println("In stairway");
            //make sure player is in a room
            if(!(cluedo.getBoard().returnSquare(player.getPositionPoint())instanceof RoomSquare)){
                System.out.println("In room check failed");
                return false;
            }
            StairwaySquare st = (StairwaySquare) cluedo.getBoard().returnSquare(point);
            //if in same room as chosen staircase
            String playerLRoom = ((RoomSquare) cluedo.getBoard().returnSquare(player.getPositionPoint())).getRoom().getName();
            String stairwayRoom = st.inRoom.getName();
            //System.out.println(((RoomSquare) board.returnSquare(player.getPositionPoint())).getRoom().getName());
            //System.out.println(st.inRoom.getName());

            if(playerLRoom.equals(stairwayRoom)){
                System.out.println("Room names equal");
                Room goingTo = st.outRoom;
                System.out.println("Room going to: " + goingTo.getName());
                for(int x = 0; x < 24; x++) {
                    for (int y = 0; y < 25; y++) {
                        if (cluedo.getBoard().getBoard()[x][y] instanceof RoomSquare) {
                            RoomSquare rs = (RoomSquare) cluedo.getBoard().getBoard()[x][y];
                            //System.out.println("Looking at room: " + rs.getRoom().getName());
                            if(rs.getRoom().getName().equals(goingTo.getName())){
                                System.out.println("Names equal");
                                if(!(playerAtPoint(new Point(x,y), cluedo))){
                                    System.out.println("No player here");
                                    player.setPreviousPoint(player.getPositionPoint());
                                    player.setPositionPoint(new Point(x,y));
                                    cluedo.getPaintBoard().repaint(player,cluedo);
                                    System.out.println();
                                    return true;
                                }
                            }
                        }
                    }
                }
                return false;
            }
            //not in same room as staircase
            else{
                return false;
            }


        }

        //If player is in a room square
        else if(cluedo.getBoard().returnSquare(player.getPositionPoint())instanceof RoomSquare){
            System.out.println("In Room");
            availableSquares = new HashSet<>();
            Room current = ((RoomSquare) cluedo.getBoard().returnSquare(player.getPositionPoint())).getRoom();
            System.out.println(current.toString());
            Set<Square> doorSquares = new HashSet<Square>();
            for(int x = 0; x < 24; x++){
                for(int y = 0; y < 25; y++){
                    if(cluedo.getBoard().getBoard()[x][y] instanceof DoorSquare){
                        DoorSquare rs = (DoorSquare) cluedo.getBoard().getBoard()[x][y];
                        System.out.println(rs.getRoom().getName());
                        if(rs.getRoom().getName().equals(current.getName())){
                            availableSquares.addAll(avaialableMoves(new Point(x,y),roll-1, cluedo));
                        }
                    }
                }
            }
            System.out.println(availableSquares.size());
            if (availableSquares.contains(point)) {
                //If movement is a room sqaure
                if (cluedo.getBoard().returnSquare(point) instanceof RoomSquare) {
                    Room room = ((RoomSquare) cluedo.getBoard().returnSquare(point)).getRoom();
                    for (Point rsPoint : availableSquares) {
                        Square rs = cluedo.getBoard().getBoard()[(int) rsPoint.getX()][(int) rsPoint.getY()];
                        if (rs instanceof RoomSquare) {
                            if (((RoomSquare) rs).getRoom().equals(room)) {
                                player.setPreviousPoint(player.getPositionPoint());
                                player.setPositionPoint(point);
                                cluedo.getPaintBoard().repaint(player,cluedo);
                                System.out.println();
                                return true;
                            }
                        }
                    }
                    return false;
                }
                //Move players point to that position
                player.setPreviousPoint(player.getPositionPoint());
                player.setPositionPoint(point);
                cluedo.getPaintBoard().repaint(player,cluedo);
                System.out.println();
                return true;
            }
            //square cannot be moved too
            return false;


        }
        //Player is not in room square
        else {
            availableSquares = avaialableMoves(player.getPositionPoint(), roll, cluedo);
            //Remove Squares that Players are in
            for (Player p : cluedo.getPlayerList()) {
                Point checkPoint = p.getPositionPoint();
                if (availableSquares.contains(checkPoint)) {
                    availableSquares.remove(checkPoint);
                }
            }
            //CHeck if inteded movement is possible
            if (availableSquares.contains(point)) {
                System.out.println("Hi");
                //If movement is a room sqaure
                if (cluedo.getBoard().returnSquare(point) instanceof RoomSquare) {
                    System.out.println("Hi");
                    Room room = ((RoomSquare) cluedo.getBoard().returnSquare(point)).getRoom();
                    for (Point rsPoint : availableSquares) {
                        Square rs = cluedo.getBoard().getBoard()[(int) rsPoint.getX()][(int) rsPoint.getY()];
                        if (rs instanceof RoomSquare) {
                            if (((RoomSquare) rs).getRoom().equals(room)) {
                                player.setPreviousPoint(player.getPositionPoint());
                                player.setPositionPoint(point);
                                cluedo.getPaintBoard().repaint(player,cluedo);
                                System.out.println();
                                return true;
                            }
                        }
                    }
                    return false;
                }
                //Move players point to that position
                player.setPreviousPoint(player.getPositionPoint());
                player.setPositionPoint(point);
                cluedo.getPaintBoard().repaint(player,cluedo);
                System.out.println();
                return true;
            }
            //square cannot be moved too
            return false;
        }

    }

    Set<Point> avaialableMoves(Point p, int roll, Game cluedo){
        Point playerPos = p;
        Set<Point> visitable;
        visitable = getNeighbours(playerPos,roll,new HashSet<Point>(), cluedo);
        return visitable;
    }


    private Set<Point> availableMoves(Point p, int roll, Game cluedo){
        Point playerPos = p;
        Set<Point> visitable;
        visitable = getNeighbours(playerPos,roll,new HashSet<Point>(), cluedo);
        return visitable;
    }

    private Set<Point> getNeighbours(Point c, int remainingMoves, Set<Point> visited, Game cluedo) {
        if (remainingMoves != 0) {
            if(c.getX() < 0 || c.getX() > cluedo.getBoard().getWIDTH()-1 || c.getY() < 0 || c.getY() > cluedo.getBoard().getHEIGHT()){
                return null;
            }
            if(c.x + 1 < cluedo.getBoard().getWIDTH()) {
                if (!(cluedo.getBoard().getBoard()[c.x + 1][c.y] instanceof NullSquare) ) {
                    Point toAdd = new Point(c.x + 1, c.y);
                    visited.add(toAdd);
                    getNeighbours(toAdd, remainingMoves - 1, visited, cluedo);
                }
            }
            if(c.x - 1 >= 0) {
                if (!(cluedo.getBoard().getBoard()[c.x - 1][c.y] instanceof NullSquare)) {
                    Point toAdd = new Point(c.x - 1, c.y);
                    visited.add(toAdd);
                    getNeighbours(toAdd, remainingMoves - 1, visited, cluedo);
                }
            }
            if(c.y + 1 < cluedo.getBoard().getHEIGHT()){
                if (!(cluedo.getBoard().getBoard()[c.x][c.y + 1] instanceof NullSquare)) {
                    Point toAdd = new Point(c.x, c.y + 1);
                    visited.add(toAdd);
                    getNeighbours(toAdd, remainingMoves - 1, visited, cluedo);
                }
            }
            if(c.y - 1 >= 0){
                if (!(cluedo.getBoard().getBoard()[c.x][c.y - 1] instanceof NullSquare)) {
                    Point toAdd = new Point(c.x, c.y - 1);
                    visited.add(toAdd);
                    getNeighbours(toAdd, remainingMoves - 1, visited, cluedo);
                }
            }
        }
        return visited;

    }


    public void movePlayer(Player player, Game cluedo) {
        System.out.println("=============================");
        System.out.println(player.getName() + "'s turn");
        Random random = new Random();
        int diceRoll = random.nextInt(12 - 1) + 1;
        //int diceRoll = 6;
        System.out.println("You rolled a " + diceRoll);
        System.out.println("Your location: x="+ player.getPositionPoint().getX() + " y=" + player.getPositionPoint().getY());
        String commands = "";
        List<Move.Moves> moves = new ArrayList<>(Arrays.asList(Move.Moves.values()));
        boolean validMove = false;
        Point newPosition;
        while(!validMove) {
            commands = cluedo.getTextBaseCluedo().moving(player);
            String[] commandArray = commands.trim().split("\\s+");
             for(int i=0; i<commandArray.length; i++) { //error checking
                 while(!moves.contains(Move.Moves.fromString(commandArray[i]))) {
                     commands = cluedo.getTextBaseCluedo().invalidArrayInput();
                     commandArray = commands.trim().split("\\s+");
                 }
             }
//                    while(commandArray.length != diceRoll) { //error checking
//                        commands = Game.textBaseCluedo.invalidArrayInput();
//                        commandArray = commands.trim().split("\\s+");
//                    }
                    //convert commandArray to point coordinates
            newPosition = convertArrayToPoint(commandArray, player);
            validMove =  moveCheck(newPosition, player, diceRoll, cluedo); //check if valid move
            System.out.println("Your move is " + validMove);
        }
    }

    private Point convertArrayToPoint(String[] commandArray, Player player) {
        Point playerPosition = player.getPositionPoint();
        int x = (int)playerPosition.getX();
        int y = (int)playerPosition.getY();
        for (String aCommandArray : commandArray) {
            if (aCommandArray.equals(Move.Moves.W.getText()) || aCommandArray.equals(Move.Moves.w.getText())) {
                y--;
            } else if (aCommandArray.equals(Move.Moves.A.getText()) || aCommandArray.equals(Move.Moves.a.getText())) {
                x--;
            } else if (aCommandArray.equals(Move.Moves.S.getText()) || aCommandArray.equals(Move.Moves.s.getText())) {
                y++;
            } else if (aCommandArray.equals(Move.Moves.D.getText()) || aCommandArray.equals(Move.Moves.d.getText())) {
                x++;
            }
        }
        Point point = new Point(x,y);
        System.out.println(point.toString());
        return point;

    }
    public boolean playerAtPoint(Point p, Game cluedo){
        for (Player player : cluedo.getPlayerList()) {
            Point checkPoint = player.getPositionPoint();
            if (checkPoint.equals(p)) {
                return true;
            }
        }
        return false;
    }

}
