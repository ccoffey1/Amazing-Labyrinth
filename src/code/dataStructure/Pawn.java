package code.dataStructure;

import java.awt.Color;
import java.util.ArrayList;

/**
 * The Pawn object. This is what the player manipulates in order to generate
 * entertainment for them. Pawn's colors can be specified through its parameter
 * list, where Color.SOMETHING can be added.
 * 
 * @param color
 * @author Christian Coffey
 */
public class Pawn {

	private Color _color;
	private String _name;
	private ArrayList<Token> _tokenBackpack;
	private int _wands;
	private Card _card;
	private int _xPos;
	private int _yPos;
	private int _playerNumber;

	public Pawn(Color color, String name) {

		// Gives the player a wand.
		_wands = 3;

		// Gives the player a card.
		_card = new Card();

		// Container that will store all the tokens the player picks up.
		_tokenBackpack = new ArrayList<Token>();

		// Hard coding the name, going to change this in later stages
		_name = name;

		// Applies the color of the pawn.
		_color = color;

	}

	/**
	 * Returns the specified pawn's color.
	 * 
	 * @return _color
	 * @author Christian Coffey
	 */
	public Color getColor() {
		return _color;
	}

	/**
	 * Returns the specified pawn's name.
	 * 
	 * @return _name
	 * @author Christian Coffey
	 */
	public String getName() {
		return _name;
	}

	/**
	 * Takes specified token and adds it to the pawn's "Token Backpack". The
	 * "Token Backpack" is iterated through at the end of the game, and the
	 * points are added up!
	 * 
	 * @param token
	 * @author Christian Coffey
	 */
	public void takeToken(Token token) {
		_tokenBackpack.add(token);
	}

	/**
	 * Returns the wands of the specified player.
	 * 
	 * @return wands
	 */
	public int getWands() {
		return _wands;
	}

	/**
	 * Decrements the wands of the specified player.
	 */
	public void decrementWand() {
		if (_wands > 0)
			_wands = _wands - 1;
	}

	/**
	 * Displays as a string the token values on the player's card as "X X X",
	 * where 'X' is the token value.
	 * 
	 * @return tokensOnCard
	 * @author Christian Coffey
	 */
	public String getCardRequirements() {
		return _card.tokensOnCard();
	}

	/**
	 * Sets the pawn's X and Y positions for later reference. NOTE: This does
	 * NOT move the pawn to these coordinates.
	 * 
	 * @author Christian Coffey
	 */
	public void setXY(int x, int y) {
		_xPos = x;
		_yPos = y;
	}

	/**
	 * Returns Pawn's X position
	 * 
	 * @author Christian Coffey
	 */
	public int xPosition() {
		return _xPos;
	}

	/**
	 * Returns the Pawn's Y position
	 * 
	 * @author Christian Coffey
	 */

	public int yPosition() {
		return _yPos;
	}

	/**
	 * Computes player points.
	 * 
	 * @author Jackie
	 */
	public int getTotalPoints() {
		int x = 0;
		for (int i = 0; i < _tokenBackpack.size(); i++) {
			x = x + _tokenBackpack.get(i).tokenValue();
		}
		return x;
	}

	/**
	 * Sets the player's order of being introduced into the game.
	 * 
	 * @author Christian Coffey
	 * @param int
	 */
	public void setNumber(int i) {
		_playerNumber = i;
	}

	/**
	 * Returns the player's order of being introduced into the game. This method
	 * is for other methods to reference pawns indirectly.
	 * 
	 * @return _playerNumber
	 */
	public int getNumber() {
		return _playerNumber;
	}
}