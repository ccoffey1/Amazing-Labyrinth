package code.dataStructure;

/**
 * Card object. If the players collects tokens that this card contains,
 * additional points will be rewarded at the conclusion of the game.
 * 
 * @author Christian Coffey
 *
 */
public class Card {
	/**
	 * Array of type Token.
	 */
	private Token[] _tokens;

	public Card() {
		_tokens = new Token[3];
		set_tokens();
	}

	/**
	 * Returns tokens in the player's inventory.
	 * 
	 * @author Christian Coffey
	 * @return _tokens
	 */
	public String tokensOnCard() {
		String values = "";
		for (int i = 0; i < 3; i++) {
			values = values + " " + _tokens[i].tokenValue();
		}
		return values;
	}

	/**
	 * Sets 3 Tokens to the card.
	 * 
	 * @author Christian Coffey
	 */

	// this method currently only gives the card tokens of value '0', we will
	// have to change this later.
	public void set_tokens() {
		for (int i = 0; i < 3; i++) {
			_tokens[i] = new Token(0);
		}
	}

}
