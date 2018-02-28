package code.dataStructure;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * The main board. This is where all the components are added together
 * 
 * @author Christian Coffey
 *
 */
public class Board {
	// INDEXES ARE ROW THEN COLUMN.
	private Tile[][] board;
	private int _playerTurn;
	private int _activePlayers;
	private Pawn _player1;
	private Pawn _player2;
	private Pawn _player3;
	private Pawn _player4;
	private Tile _heldTile;
	public ArrayList<Integer> arrL;
	public ArrayList<Integer> tokensOnField;

	public Board() {

		// Determine whose turn it is, this value can be modified by the wands,
		// allowing for players to have more than one turn.
		_playerTurn = 1;

		// created the board
		board = new Tile[7][7];
		// populating the board with tiles.
		populateBoard();

		setHeldTile();
	}

	/**
	 * Returns the size of the board. Literally just for testing.
	 * 
	 * @author Christian Coffey
	 * @return count
	 */
	public int getSize() {
		int count = 0;
		for (int i = 0; i < board.length; i++) {
			count = count + board[i].length;
		}
		return count;
	}

	/**
	 * Checks to see whether or not a pawn exists on a given X and Y coordinate
	 * pair.
	 * 
	 * @author Christian Coffey
	 * @param x
	 *            (coordinate x)
	 * @param y
	 *            (coordinate y)
	 * @return boolean
	 */
	public boolean hasPawn(int x, int y) {

		if (board[x][y].get_Pawn() != null) {
			return true;
		}
		return false;

	}

	/**
	 * Populates the board with 49 blank Tile() objects from board[0][0] to
	 * board[7][7].
	 * 
	 * @author Jackie
	 */
	public void populateBoard() {
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++) {
				int x = (int) (Math.random() * 3);
				if (x == 0)
					board[i][j] = new Tile(true, true, false, false);
				if (x == 1)
					board[i][j] = new Tile(true, false, false, true);
				if (x == 2)
					board[i][j] = new Tile(true, false, true, true);
				board[i][j].randomRotate();
			}
		}

		tokensOnField = new ArrayList<Integer>();
		arrL = new ArrayList<Integer>();
		for (int h = 1; h < 21; h++) {
			arrL.add(h);
			tokensOnField.add(h);
		}
		arrL.add(25);
		Collections.shuffle(arrL);
		tokensOnField.add(25);

		// Hard coding the 16 fixed tiles... I know, ugly. Also made sure player
		// start points don't have tokens.
		board[0][0] = new Tile(false, true, true, false);
		board[2][0] = new Tile(false, true, true, true);
		board[4][0] = new Tile(false, true, true, true);
		board[6][0] = new Tile(false, true, false, true);
		board[0][2] = new Tile(true, true, true, false);
		board[2][2] = new Tile(true, true, true, false);
		board[4][2] = new Tile(false, true, true, true);
		board[6][2] = new Tile(true, true, false, true);
		board[0][4] = new Tile(true, true, true, false);
		board[2][4] = new Tile(true, false, true, true);
		board[4][4] = new Tile(true, true, false, true);
		board[6][4] = new Tile(true, true, false, true);
		board[0][6] = new Tile(true, false, true, false);
		board[2][6] = new Tile(true, false, true, true);
		board[4][6] = new Tile(true, false, true, true);
		board[6][6] = new Tile(true, false, false, true);

		// Hard coded the tokens.

		this.tileAt(1, 1).set_Token(arrL.get(0));
		this.tileAt(1, 2).set_Token(arrL.get(1));
		this.tileAt(1, 3).set_Token(arrL.get(2));
		this.tileAt(1, 4).set_Token(arrL.get(3));
		this.tileAt(1, 5).set_Token(arrL.get(4));
		this.tileAt(2, 1).set_Token(arrL.get(5));
		this.tileAt(2, 3).set_Token(arrL.get(6));
		this.tileAt(2, 5).set_Token(arrL.get(7));
		this.tileAt(3, 1).set_Token(arrL.get(8));
		this.tileAt(3, 2).set_Token(arrL.get(9));
		this.tileAt(3, 3).set_Token(arrL.get(10));
		this.tileAt(3, 4).set_Token(arrL.get(11));
		this.tileAt(3, 5).set_Token(arrL.get(12));
		this.tileAt(4, 1).set_Token(arrL.get(13));
		this.tileAt(4, 3).set_Token(arrL.get(14));
		this.tileAt(4, 5).set_Token(arrL.get(15));
		this.tileAt(5, 1).set_Token(arrL.get(16));
		this.tileAt(5, 2).set_Token(arrL.get(17));
		this.tileAt(5, 3).set_Token(arrL.get(18));
		this.tileAt(5, 4).set_Token(arrL.get(19));
		this.tileAt(5, 5).set_Token(arrL.get(20));
	}

	/**
	 * Goes back one turn after using a wand, allowing for players to have more
	 * than one turn. Also decrements their wands by 1.
	 * 
	 * @author Christian Coffey
	 */
	public void useWand(Pawn pawn) {
		pawn.decrementWand();
		_playerTurn--;
	}

	/**
	 * Adds a player to a position on the board, position changes with number of
	 * players active.
	 * 
	 * @author Christian Coffey
	 * @param pawn
	 *            The player.
	 * @param playerName
	 *            Player's name.
	 * @param color
	 *            The color of the player.
	 */
	public void addPlayer(String playerName) {
		// created the player, as well as the tile that houses the player's
		// starting position
		switch (_activePlayers) {

		case 0:
			_player1 = new Pawn(Color.RED, playerName);
			_player1.setXY(2, 2);
			_player1.setNumber(1);
			// added the player to position X=2, Y=2.
			board[2][2].set_Pawn(_player1);
			_activePlayers++;
			break;

		case 1:
			_player2 = new Pawn(Color.CYAN, playerName);
			_player2.setXY(2, 4);
			_player2.setNumber(2);
			board[2][4].set_Pawn(_player2);
			;
			_activePlayers++;
			break;

		case 2:
			_player3 = new Pawn(Color.GREEN, playerName);
			_player3.setXY(4, 2);
			_player3.setNumber(3);
			board[4][2].set_Pawn(_player3);
			;
			_activePlayers++;
			break;

		case 3:
			_player4 = new Pawn(Color.MAGENTA, playerName);
			_player4.setXY(4, 4);
			_player4.setNumber(4);
			board[4][4].set_Pawn(_player4);
			;
			_activePlayers++;
			break;
		}

	}

	/**
	 * Returns Player specified in arguement list.
	 * 
	 * @author Christian Coffey
	 * @param player
	 *            A number (1-4) of the player to return.
	 */
	public Pawn player(int player) {
		switch (player) {
		case 1:
			return _player1;
		case 2:
			return _player2;
		case 3:
			return _player3;
		case 4:
			return _player4;
		}
		return null;
	}

	/**
	 * Returns whose turn it is.
	 * 
	 * @return _playerTurn
	 * @author Christian Coffey
	 */
	public int turn() {
		return _playerTurn;
	}

	/**
	 * Returns number of players currently in the game.
	 * 
	 * @author Christian Coffey
	 * @return _activePlayers
	 */
	public int activePlayers() {
		return _activePlayers;
	}

	/**
	 *
	 * Returns tile at coordinate pair.
	 * 
	 * @author Jackie
	 */
	public Tile tileAt(int x, int y) {
		return board[x][y];
	}

	/**
	 * Forwards the turn count one or back to one if it's at 4
	 * 
	 * @author Christian Coffey
	 */
	public void finishTurn() {
		if (_playerTurn == _activePlayers)
			_playerTurn = 1;
		else
			_playerTurn++;
	}

	/**
	 * Moves the specified pawn up one space
	 * 
	 * @param int
	 * @author Christian Coffey
	 */
	public boolean moveUp(int pawn) {
		int x = player(pawn).xPosition();
		int y = player(pawn).yPosition();
		try {
			if (board[x][y].getDirection()[0] == true && board[x][y - 1].getDirection()[1] == true
					&& board[x][y - 1].getPrevLocation() == false) {
				player(pawn).setXY(x, y - 1);
				board[x][y - 1].set_Pawn(player(pawn));
				board[x][y].set_Pawn(null);
				return true;
			}
			if (board[x][y - 1].getPrevLocation()) {
				JOptionPane.showMessageDialog(null, "You may not proceed to the tile you began on!");
			}
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		return false;
	}

	/**
	 * Moves the specified pawn down one space.
	 * 
	 * @param int
	 * @author Christian Coffey
	 */
	public boolean moveDown(int pawn) {
		int x = player(pawn).xPosition();
		int y = player(pawn).yPosition();

		try {
			if (board[x][y].getDirection()[1] == true && board[x][y + 1].getDirection()[0] == true
					&& board[x][y + 1].getPrevLocation() == false) {
				board[x][y].get_Pawn().setXY(x, y + 1);
				board[x][y + 1].set_Pawn(player(pawn));
				board[x][y].set_Pawn(null);
				return true;
			}
			if (board[x][y + 1].getPrevLocation()) {
				JOptionPane.showMessageDialog(null, "You may not proceed to the tile you began on!");
			}
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		return false;
	}

	/**
	 * Moves the specified pawn left one space.
	 * 
	 * @param int
	 * @author Christian Coffey
	 */
	public boolean moveLeft(int pawn) {
		int x = player(pawn).xPosition();
		int y = player(pawn).yPosition();

		try {
			if (board[x][y].getDirection()[3] == true && board[x - 1][y].getDirection()[2] == true
					&& board[x - 1][y].getPrevLocation() == false) {
				board[x][y].get_Pawn().setXY(x - 1, y);
				board[x - 1][y].set_Pawn(player(pawn));
				board[x][y].set_Pawn(null);
				return true;
			}
			if (board[x - 1][y].getPrevLocation()) {
				JOptionPane.showMessageDialog(null, "You may not proceed to the tile you began on!");
			}
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		return false;
	}

	/**
	 * Moves the specified pawn right one space.
	 * 
	 * @param int
	 * @author Christian Coffey
	 */
	public boolean moveRight(int pawn) {
		int x = player(pawn).xPosition();
		int y = player(pawn).yPosition();
		try {
			if (board[x][y].getDirection()[2] == true && board[x + 1][y].getDirection()[3] == true
					&& board[x + 1][y].getPrevLocation() == false) {
				board[x][y].get_Pawn().setXY(x + 1, y);
				board[x + 1][y].set_Pawn(player(pawn));
				board[x][y].set_Pawn(null);
				return true;
			}
			if (board[x + 1][y - 1].getPrevLocation()) {
				JOptionPane.showMessageDialog(null, "You may not proceed to the tile you began on!");
			}
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		return false;
	}

	/**
	 * Pushes tile by row from right to left.
	 * 
	 * @author Jackie
	 */
	public void pushRowTileRL(int row) {
		if (!(this.tileAt(this.player(this.turn()).xPosition(), this.player(this.turn()).yPosition()).hasPawn()))
			this.tileAt(this.player(this.turn()).xPosition(), this.player(this.turn()).yPosition())
					.set_Pawn(this.player(this.turn()));

		for (int i = 0; i < 7; i++) {
			if (board[i][row].hasPawn()) {
				board[i][row].get_Pawn().setXY(i - 1, board[i][row].get_Pawn().yPosition());
			}
		}

		if (row == 1 || row == 3 || row == 5) {
			Tile temp = board[0][row];
			for (int i = 0; i < 6; i++) {
				board[i][row] = board[i + 1][row];
			}
			board[6][row] = _heldTile;
			_heldTile = temp;
		}

		if (_heldTile.hasPawn()) {
			_heldTile.get_Pawn().setXY(6, _heldTile.get_Pawn().yPosition());
			board[6][_heldTile.get_Pawn().yPosition()].set_Pawn(player(_heldTile.get_Pawn().getNumber()));
			_heldTile.set_Pawn(null);
		}

		if (_heldTile.hasToken()) {
			board[6][row].set_Token(_heldTile.get_Token().tokenValue());
			_heldTile.removeToken();
		}
	}

	/**
	 * Pushes tile by row from left to right.
	 * 
	 * @author Jackie
	 */
	public void pushRowTileLR(int row) {
		if (!(this.tileAt(this.player(this.turn()).xPosition(), this.player(this.turn()).yPosition()).hasPawn()))
			this.tileAt(this.player(this.turn()).xPosition(), this.player(this.turn()).yPosition())
					.set_Pawn(this.player(this.turn()));

		for (int i = 0; i < 7; i++) {
			if (board[i][row].hasPawn()) {
				board[i][row].get_Pawn().setXY(i + 1, board[i][row].get_Pawn().yPosition());
			}
		}
		if (row == 1 || row == 3 || row == 5) {
			Tile temp = board[6][row];
			for (int i = 6; i > 0; i--) {
				board[i][row] = board[i - 1][row];
			}
			board[0][row] = _heldTile;
			_heldTile = temp;
		}

		if (_heldTile.hasPawn()) {
			_heldTile.get_Pawn().setXY(0, _heldTile.get_Pawn().yPosition());
			board[0][_heldTile.get_Pawn().yPosition()].set_Pawn(player(_heldTile.get_Pawn().getNumber()));
			_heldTile.set_Pawn(null);
		}

		if (_heldTile.hasToken()) {
			board[0][row].set_Token(_heldTile.get_Token().tokenValue());
			_heldTile.removeToken();
		}
	}

	/**
	 * Pushes tile by column from upside to down.
	 * 
	 * @author Jackie
	 */
	public void pushColumnTileUD(int column) {
		if (!(this.tileAt(this.player(this.turn()).xPosition(), this.player(this.turn()).yPosition()).hasPawn()))
			this.tileAt(this.player(this.turn()).xPosition(), this.player(this.turn()).yPosition())
					.set_Pawn(this.player(this.turn()));

		for (int i = 0; i < 7; i++) {
			if (board[column][i].hasPawn()) {
				board[column][i].get_Pawn().setXY(board[column][i].get_Pawn().xPosition(), i + 1);
			}
		}
		if (column == 1 || column == 3 || column == 5) {
			Tile temp = board[column][6];
			for (int i = 6; i > 0; i--) {
				board[column][i] = board[column][i - 1];
			}
			board[column][0] = _heldTile;
			_heldTile = temp;
		}

		if (_heldTile.hasPawn()) {
			_heldTile.get_Pawn().setXY(_heldTile.get_Pawn().xPosition(), 0);
			board[_heldTile.get_Pawn().xPosition()][0].set_Pawn(player(_heldTile.get_Pawn().getNumber()));
			_heldTile.set_Pawn(null);
		}

		if (_heldTile.hasToken()) {
			board[column][0].set_Token(_heldTile.get_Token().tokenValue());
			_heldTile.removeToken();
		}
	}

	/**
	 * Pushes tile by column from downside to up.
	 * 
	 * @author Jackie
	 */
	public void pushColumnTileDU(int column) {
		if (!(this.tileAt(this.player(this.turn()).xPosition(), this.player(this.turn()).yPosition()).hasPawn()))
			this.tileAt(this.player(this.turn()).xPosition(), this.player(this.turn()).yPosition())
					.set_Pawn(this.player(this.turn()));

		for (int i = 0; i < 7; i++) {
			if (board[column][i].hasPawn()) {
				board[column][i].get_Pawn().setXY(board[column][i].get_Pawn().xPosition(), i - 1);
			}
		}
		if (column == 1 || column == 3 || column == 5) {
			Tile temp = board[column][0];
			for (int i = 0; i < 6; i++) {
				board[column][i] = board[column][i + 1];
			}
			board[column][6] = _heldTile;
			_heldTile = temp;
		}

		if (_heldTile.hasPawn()) {
			_heldTile.get_Pawn().setXY(_heldTile.get_Pawn().xPosition(), 6);
			board[_heldTile.get_Pawn().xPosition()][6].set_Pawn(player(_heldTile.get_Pawn().getNumber()));
			_heldTile.set_Pawn(null);
		}

		if (_heldTile.hasToken()) {
			board[column][6].set_Token(_heldTile.get_Token().tokenValue());
			_heldTile.removeToken();
		}
	}

	/**
	 * Grabs Token if its allowed.
	 * 
	 * @author Jackie
	 */
	public void grabToken(Pawn player) {
		if (board[player.xPosition()][player.yPosition()].hasToken() == false) {
			JFrame frame = new JFrame();
			JOptionPane.showMessageDialog(frame, "There's no token to pick up!");
			frame.dispose();
		}

		else if (board[player.xPosition()][player.yPosition()].get_Token().tokenValue() == tokensOnField.get(0)) {
			player.takeToken(this.tileAt(player.xPosition(), player.yPosition()).get_Token());
			this.tileAt(player.xPosition(), player.yPosition()).removeToken();
			tokensOnField.remove(0);
		} else if (board[player.xPosition()][player.yPosition()].get_Token().tokenValue() != tokensOnField.get(0)) {
			JFrame frame = new JFrame();
			JOptionPane.showMessageDialog(frame, "You have yet to collect the previous token!");
			frame.dispose();
		}
	}

	/**
	 * Returns the next token to be grabbed by the players.
	 * 
	 * @return tokensOnField.get(0)
	 * @author Christian Coffey
	 */
	public int getNextToken() {
		return tokensOnField.get(0);
	}

	/**
	 * Returns the held tile.
	 * 
	 * @return _heldTile
	 * @author Christian Coffey
	 */
	public Tile getHeldTile() {
		return _heldTile;
	}

	/**
	 * Gives the held tile an initial, random orientation. Only to be called
	 * within the Board constructor
	 * 
	 * @author Christian Coffey
	 */
	public void setHeldTile() {
		int x = (int) (Math.random() * 3);
		if (x == 0)
			_heldTile = new Tile(true, true, false, false);
		if (x == 1)
			_heldTile = new Tile(true, false, false, true);
		if (x == 2)
			_heldTile = new Tile(true, false, true, true);
	}

	public void tokenDebugging() {
		System.out.println("Taking token " + tokensOnField.get(0));
		tokensOnField.remove(0);
	}
}
