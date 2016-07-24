import controller.Game;
import model.*;
import model.Character;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CluedoTests {
    private Game game = new Game();

    @Test
    public void testSolution() {
        //Solution solutions = game.initGame();
    }

    @Test
    public void testingSolutionEqualsAccusation() {
        Solution solution = new Solution(new Weapon(Weapon.Weapons.Revolver), new Room(Room.Rooms.BallRoom), new Character(Character.Characters.ColonelMustard));
        Accusation accusation = new Accusation(new Weapon(Weapon.Weapons.Revolver), new Room(Room.Rooms.BallRoom), new Character(Character.Characters.ColonelMustard));
        assertTrue("solution equals accusation", solution.equals(accusation));
    }
}