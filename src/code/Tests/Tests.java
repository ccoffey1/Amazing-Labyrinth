package code.Tests;

import static org.junit.Assert.*;
import java.awt.Color;
import org.junit.Test;
import code.dataStructure.Board;
import code.dataStructure.Card;
import code.dataStructure.Pawn;
import code.dataStructure.Tile;

/**
 * A collection of Tests to verify functionality of the game.
 * 
 * @author Christian Coffey
 *
 */

public class Tests {

	/**
	 * This test checks that the board has a size of 49.
	 * 
	 * @author Christian Coffey
	 */
	@Test
	public void boardSizeTest() {
		Board board = new Board();
		int expected = 49;
		int actual = board.getSize();
		assertTrue("Expected " + expected + " got " + actual, expected == actual);
	}

	/**
	 * This test checks that players have been created at the correct locations.
	 * We'll add to this more later once we add more players.
	 * 
	 * @author Christian Coffey
	 */
	@Test
	public void playerCreationLocations() {
		String index1 = "";
		Board board = new Board();
		board.addPlayer("player 1");
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++) {
				if (board.hasPawn(i, j)) {
					index1 = i + " " + j;
				}
			}
		}
		String expected = "2 2";
		String actual = index1;
		assertTrue("Expected pawn to be at location " + expected + " but pawn was found at " + actual,
				actual.equals(expected));
	}

	/**
	 * This test checks that the card does in fact acquire 3 tokens.
	 * 
	 * @author Christian Coffey
	 */
	@Test
	public void cardReceivesTokens() {
		Card card = new Card();
		card.set_tokens();
		String expected = " 0 0 0";
		String actual = card.tokensOnCard();
		assertEquals(expected, actual);
	}

	/**
	 * This test checks that the player has received a card.
	 * 
	 * @author Christian Coffey
	 */

	// This Test is coded to seek out the token set "0 0 0", must be changed
	// once we actually give tokens value.
	@Test
	public void playerHasCard() {
		Pawn pawn = new Pawn(Color.BLUE, "Player 1");
		String expected = " 0 0 0";
		String actual = pawn.getCardRequirements();
		assertEquals(expected, actual);
	}

	/**
	 * Tests to see if player lost their wand after use.
	 * 
	 * @author Christian Coffey
	 */
	@Test
	public void playerLostWand() {
		Board board = new Board();
		board.addPlayer("player 1");
		board.useWand(board.player(1));
		int expected = 2;
		int actual = board.player(1).getWands();
		assertEquals(expected, actual);
	}

	@Test
	public void turnsGoesBack() {
		Board board = new Board();
		board.addPlayer("Player 1");
		board.useWand(board.player(1));

		int expected = 0;
		int actual = board.turn();

		assertEquals(expected, actual);
	}

	@Test
	public void isBoardPopulated() {
		int expected = 2;
		int actual = 2;
		Board board = new Board();
		board.populateBoard();
		for (int i = 0; i < 7; i++)
			for (int j = 0; j < 7; j++) {
				if (board.tileAt(i, j) != null) {
					assertEquals(expected, actual);
				}
			}
	}

	@Test
	public void getDirection() {
		int expected = 2;
		int actual = 2;
		Tile t = new Tile(true, true, false, false);
		for (int i = 0; i < 4; i++) {
			if (t.getDirection() != null)
				assertEquals(expected, actual);
		}
	}

	@Test
	public void moveUp() {
		Board board = new Board();
		board.addPlayer("Player 1");
		boolean expected = true;
		board.tileAt(2, 2).setDirection(true, true, true, true);
		board.tileAt(2, 1).setDirection(true, true, true, true);
		board.moveUp(1);
		boolean actual = board.hasPawn(2, 1);
		assertEquals(expected, actual);
		boolean expected2 = false;
		boolean actual2 = board.tileAt(2, 2).hasPawn();
		assertEquals(expected2, actual2);
	}

	@Test
	public void moveLeft() {
		Board board = new Board();
		board.addPlayer("Player 1");
		boolean expected = true;
		board.tileAt(2, 2).setDirection(true, true, true, true);
		board.tileAt(1, 2).setDirection(true, true, true, true);
		board.moveLeft(1);
		boolean actual = board.hasPawn(1, 2);
		assertEquals(expected, actual);
		boolean expected2 = false;
		boolean actual2 = board.tileAt(2, 2).hasPawn();
		assertEquals(expected2, actual2);
	}

	@Test
	public void moveRight() {
		Board board = new Board();
		board.addPlayer("Player 1");
		boolean expected = true;
		board.tileAt(2, 2).setDirection(true, true, true, true);
		board.tileAt(3, 2).setDirection(true, true, true, true);
		board.moveRight(1);
		boolean actual = board.hasPawn(3, 2);
		assertEquals(expected, actual);
		boolean expected2 = false;
		boolean actual2 = board.tileAt(2, 2).hasPawn();
		assertEquals(expected2, actual2);
	}

	@Test
	public void moveDown() {
		Board board = new Board();
		board.addPlayer("Player 1");
		boolean expected = true;
		board.tileAt(2, 2).setDirection(true, true, true, true);
		board.tileAt(2, 3).setDirection(true, true, true, true);
		board.moveDown(1);
		boolean actual = board.hasPawn(2, 3);
		assertEquals(expected, actual);
		boolean expected2 = false;
		boolean actual2 = board.tileAt(2, 2).hasPawn();
		assertEquals(expected2, actual2);
	}

	@Test
	public void nextTurn() {
		int expected = 2;
		Board board = new Board();
		board.finishTurn();
		int actual = board.turn();
		assertEquals(expected, actual);
	}

	@Test
	public void turnReturnsToOne() {
		int expected = 1;
		Board board = new Board();
		board.addPlayer("Player 1");
		board.addPlayer("Player 2");
		board.addPlayer("Player 3");
		board.addPlayer("Player 4");
		board.finishTurn();
		board.finishTurn();
		board.finishTurn();
		board.finishTurn();
		int actual = board.turn();
		assertEquals(expected, actual);
	}

	@Test
	public void cannotExceed4Players() {
		Board board = new Board();
		int expected = 4;
		int count = 0;
		board.addPlayer("player");
		board.addPlayer("player");
		board.addPlayer("player");
		board.addPlayer("player");
		board.addPlayer("player");
		board.addPlayer("player");
		board.addPlayer("player");
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++) {
				if (board.hasPawn(i, j)) {
					count++;
				}
			}
		}
		int actual = count;
		assertEquals(expected, actual);
	}

	@Test
	public void token() {
		Board board = new Board();
		boolean actual = board.tileAt(0, 0).hasToken();
		boolean expected = false;
		assertEquals(expected, actual);
	}

	@Test
	public void hasTokens() {
		int expected = 21;
		int actual = 0;
		Board b = new Board();
		for (int i = 0; i < 7; i++)
			for (int j = 0; j < 7; j++) {
				if (b.tileAt(i, j).hasToken())
					actual++;
			}
		assertEquals(expected, actual);
	}

	@Test
	public void isTokensInCorrectSpots() {
		int expected = 21;
		int actual = 0;
		Board b = new Board();
		for (int i = 1; i < 6; i = i + 2)
			for (int j = 1; j < 6; j++) {
				if (b.tileAt(i, j).hasToken())
					actual++;
			}
		for (int i = 2; i < 5; i = i + 2)
			for (int j = 1; j < 6; j = j + 2) {
				if (b.tileAt(i, j).hasToken())
					actual++;
			}
		assertEquals(expected, actual);
	}

}