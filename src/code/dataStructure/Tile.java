package code.dataStructure;

/**
 * The Tile object. This is added to the board at each of the 49 spaces. There
 * are 3 types of Tiles available to be added: A blank tile, a token tile, and a
 * pawn tile.
 * 
 * @author Christian Coffey
 *
 */
public class Tile {

	/**
	 * A pawn.
	 */
	private Pawn _pawn;

	/**
	 * A token.
	 */
	private Token _token;
	private boolean north;
	private boolean south;
	private boolean east;
	private boolean west;
	private boolean[] arr;
	private boolean previousLocation;

	/**
	 * Tile with a pawn.
	 * 
	 * @param pawn
	 *            Pawn to be added.
	 */
	public Tile(boolean north, boolean south, boolean east, boolean west, Pawn pawn) {
		set_Pawn(pawn);
	}

	public Tile(boolean north, boolean south, boolean east, boolean west) {
		this.north = north;
		this.south = south;
		this.east = east;
		this.west = west;
	}

	// *****EACH OF THE SET METHODS (set_Token and set_Pawn) ARE USED IN THE 3
	// DIFFERENT CONSTRUCTORS FOR THIS CLASS TO SPECIFY WHAT THE TILE
	// HOLDS!******

	/**
	 * Returns the pawn currently on this tile.
	 * 
	 * @return _pawn (The player).
	 * @author Christian Coffey
	 */
	public Pawn get_Pawn() {
		return _pawn;
	}

	public boolean hasPawn() {
		return _pawn != null;
	}

	/**
	 * Sets a specified pawn to the tile.
	 * 
	 * @param _pawn
	 * @author Christian Coffey
	 */
	public void set_Pawn(Pawn pawn) {
		_pawn = pawn;
	}

	/**
	 * Returns the array with boolean directions.
	 * 
	 * @author Jackie
	 */
	public boolean[] getDirection() {
		arr = new boolean[4];
		arr[0] = north;
		arr[1] = south;
		arr[2] = east;
		arr[3] = west;
		return arr;
	}

	/**
	 * Rotates the specified Tile randomly
	 * 
	 * @author Jackie
	 */
	public void randomRotate() {
		int randomInt = (int) (Math.random() * 4);
		for (int i = 0; i < randomInt; i++) {
			boolean temp = this.getDirection()[0];
			north = this.getDirection()[1];
			south = this.getDirection()[2];
			east = this.getDirection()[3];
			west = temp;
		}
	}

	/**
	 * Rotates the specified tile one time.
	 * 
	 * @author Christian Coffey
	 */
	public void rotate() {
		boolean temp = north;
		north = west;
		west = south;
		south = east;
		east = temp;
	}

	/**
	 * Gets the token on the specified Tile.
	 * 
	 * @return _token
	 * @author Christian Coffey
	 */
	public Token get_Token() {
		return _token;
	}

	/**
	 * Applies token to the specified Tile.
	 * 
	 * @param _token
	 *            the _token to set
	 * @author Christian Coffey
	 */
	public void set_Token(int n) {
		_token = new Token(n);
	}

	public void removeToken() {
		_token = null;
	}

	public boolean hasToken() {
		return _token != null;
	}

	/**
	 * Set direction of the Tile. Allows specification of directions manually
	 * when testing to verify functionality of both pathing and legal pawn
	 * movement.
	 * 
	 * @author Christian Coffey
	 */
	public void setDirection(boolean n, boolean s, boolean e, boolean w) {
		north = n;
		south = s;
		east = e;
		west = w;
		randomRotate();
	}

	/**
	 * Sets previous location of pawn
	 */
	public void setPrevLocation(boolean location) {
		previousLocation = location;
	}

	/**
	 * Gets previous location of pawn
	 */
	public boolean getPrevLocation() {
		return previousLocation;
	}

}
