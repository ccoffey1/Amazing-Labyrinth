package code;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import code.dataStructure.Board;

public class GUI {
	private Board board = new Board();
	private JButton[][] _tile;
	private JFrame _frame;
	private JPanel _mainPanel;
	private String _players[];
	private JTextPane playerBoxes[];
	private JTextPane nextToken;
	private JButton _heldTile;
	private JButton tokenButton;
	private JFrame controlFrame;
	private int lastPushedTile;
	private boolean tileHasBeenPushed;

	private JButton up;
	private JButton down;
	private JButton left;
	private JButton right;
	// private Clip bgm;
	private Clip lastTokenSong;

	/**
	 * The GUI class. How the lifeform interacts with our beautiful code.
	 * 
	 * @authors Christian Coffey
	 */
	public GUI(String[] names) {
		board = new Board();
		// Storing player names within an array for a more personal experience.
		_players = new String[names.length];
		for (int i = 0; i < names.length; i++) {
			board.addPlayer(names[i]);
			_players[i] = names[i];
		}

		// Main Frame Properties
		_mainPanel = new JPanel();
		_tile = new JButton[9][9];
		_frame = new JFrame("The Greatest Game");
		_frame.setVisible(true);
		populate();
		_frame.setSize(700, 700);

		// Creating Player Info board
		createPlayerInfoBoard();
		refreshStats();
		// testButton();
		pushTileButtons();

		// Why not a little music?
		// try {
		// AudioInputStream stream;
		// AudioFormat format;
		// DataLine.Info info;
		//
		// stream =
		// AudioSystem.getAudioInputStream(getClass().getResource("/undertale.wav"));
		// format = stream.getFormat();
		// info = new DataLine.Info(Clip.class, format);
		// bgm = (Clip) AudioSystem.getLine(info);
		// bgm.open(stream);
		// bgm.loop(2147483647);
		// } catch (Exception e) {
		// System.out.println("Sound file not found, mate.");
		// }

		try {
			AudioInputStream stream;
			AudioFormat format;
			DataLine.Info info;

			stream = AudioSystem.getAudioInputStream(getClass().getResource("/lasttoken.wav"));
			format = stream.getFormat();
			info = new DataLine.Info(Clip.class, format);
			lastTokenSong = (Clip) AudioSystem.getLine(info);
			lastTokenSong.open(stream);
			// lastTokenSong.loop(2147483647);
		} catch (Exception e) {
			System.out.println("Sound file not found, mate.");
		}

		_frame.setDefaultCloseOperation(_frame.EXIT_ON_CLOSE);

		JOptionPane.showMessageDialog(null,
				"In this current build, if you cross over another pawn that pawn will vanish! D: BUT DON'T WORRY! "
						+ "\nThat pawn is still there, it's just a sad little bug! Once it's that pawns turn again they will re-appear! :D");
	}

	/**
	 * Method that populates the board with JButton tiles.
	 * 
	 * @author Christian Coffey
	 */
	private void populate() {
		_mainPanel.setLayout(new GridLayout(9, 9));
		// Creating Tiles
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				_tile[j][i] = new JButton();
				_mainPanel.add(_tile[j][i]);
				_tile[j][i].setBackground(Color.WHITE);
				_tile[j][i].setOpaque(true);
				_frame.add(_mainPanel);
			}
		}

		// Disabling Certain Parameter tiles.
		_tile[0][0].setEnabled(false);
		_tile[0][0].setBackground(Color.GRAY);
		_tile[0][8].setEnabled(false);
		_tile[0][8].setBackground(Color.GRAY);
		_tile[8][0].setEnabled(false);
		_tile[8][0].setBackground(Color.GRAY);
		_tile[8][8].setEnabled(false);
		_tile[8][8].setBackground(Color.GRAY);

		for (int i = 1; i < 8; i += 2) {
			for (int j = 1; j < 8; j += 2) {
				_tile[i][0].setEnabled(false);
				_tile[i][0].setBackground(Color.GRAY);
				_tile[0][j].setEnabled(false);
				_tile[0][j].setBackground(Color.GRAY);

				_tile[8][i].setEnabled(false);
				_tile[8][j].setBackground(Color.GRAY);
				_tile[j][8].setEnabled(false);
				_tile[j][8].setBackground(Color.GRAY);
			}
		}
		// Calling all construction methods here.
		updateBoard();
		createControlPanel();
	}

	/**
	 * Colors the player's start positions.
	 * 
	 * @author Christian Coffey
	 */
	private void updateBoard() {

		ImageIcon LTile = new ImageIcon(getClass().getResource("/L Tile.jpg"));
		ImageIcon LTile2 = new ImageIcon(getClass().getResource("/L2 Tile.jpg"));
		ImageIcon LTile3 = new ImageIcon(getClass().getResource("/L3 Tile.jpg"));
		ImageIcon LTile4 = new ImageIcon(getClass().getResource("/L4 Tile.jpg"));
		ImageIcon TTile = new ImageIcon(getClass().getResource("/T Tile.jpg"));
		ImageIcon TTile2 = new ImageIcon(getClass().getResource("/T2 Tile.jpg"));
		ImageIcon TTile3 = new ImageIcon(getClass().getResource("/T3 Tile.jpg"));
		ImageIcon TTile4 = new ImageIcon(getClass().getResource("/T4 Tile.jpg"));
		ImageIcon ITile = new ImageIcon(getClass().getResource("/I Tile.jpg"));
		ImageIcon ITile2 = new ImageIcon(getClass().getResource("/I2 Tile.jpg"));

		for (int i = 1; i < 8; i++) {
			for (int j = 1; j < 8; j++) {
				_tile[i][j].setFont(new Font("Calibri", 1, 20));
				_tile[i][j].setForeground(Color.YELLOW);
				_tile[i][j].setText("");
				if (board.tileAt(i - 1, j - 1).getDirection()[0] == true
						&& board.tileAt(i - 1, j - 1).getDirection()[1] == false
						&& board.tileAt(i - 1, j - 1).getDirection()[2] == true
						&& board.tileAt(i - 1, j - 1).getDirection()[3] == true) {
					_tile[i][j].setIcon(TTile);
					_tile[i][j].setHorizontalTextPosition(JButton.CENTER);
					_tile[i][j].setVerticalTextPosition(JButton.CENTER);
					if (board.tileAt(i - 1, j - 1).hasToken()) {
						int n = board.tileAt(i - 1, j - 1).get_Token().tokenValue();
						_tile[i][j].setIcon(mergeImages("T Tile.jpg", n + ".png"));
						_tile[i][j].setText("");
					}
					if (board.tileAt(i - 1, j - 1).hasPawn()) {
						_tile[i][j].setText("");
						switch (board.tileAt(i - 1, j - 1).get_Pawn().getNumber()) {
						case 1:
							_tile[i][j].setIcon(mergeImages("T Tile.jpg", "red.png"));
							break;
						case 2:
							_tile[i][j].setIcon(mergeImages("T Tile.jpg", "cyan.png"));
							break;
						case 3:
							_tile[i][j].setIcon(mergeImages("T Tile.jpg", "green.png"));
							break;
						case 4:
							_tile[i][j].setIcon(mergeImages("T Tile.jpg", "magenta.png"));
							break;
						}
					}
					if (board.tileAt(i - 1, j - 1).hasToken() && board.tileAt(i - 1, j - 1).hasPawn()) {
						int n = board.tileAt(i - 1, j - 1).get_Token().tokenValue();
						_tile[i][j].setText("" + n);
					}
				} else if (board.tileAt(i - 1, j - 1).getDirection()[0] == true
						&& board.tileAt(i - 1, j - 1).getDirection()[1] == true
						&& board.tileAt(i - 1, j - 1).getDirection()[2] == false
						&& board.tileAt(i - 1, j - 1).getDirection()[3] == true) {
					_tile[i][j].setIcon(TTile2);
					_tile[i][j].setHorizontalTextPosition(JButton.CENTER);
					_tile[i][j].setVerticalTextPosition(JButton.CENTER);
					if (board.tileAt(i - 1, j - 1).hasToken()) {
						int n = board.tileAt(i - 1, j - 1).get_Token().tokenValue();
						_tile[i][j].setIcon(mergeImages("T2 Tile.jpg", n + ".png"));
						_tile[i][j].setText("");
					}
					if (board.tileAt(i - 1, j - 1).hasPawn()) {
						_tile[i][j].setText("");
						switch (board.tileAt(i - 1, j - 1).get_Pawn().getNumber()) {
						case 1:
							_tile[i][j].setIcon(mergeImages("T2 Tile.jpg", "red.png"));
							break;
						case 2:
							_tile[i][j].setIcon(mergeImages("T2 Tile.jpg", "cyan.png"));
							break;
						case 3:
							_tile[i][j].setIcon(mergeImages("T2 Tile.jpg", "green.png"));
							break;
						case 4:
							_tile[i][j].setIcon(mergeImages("T2 Tile.jpg", "magenta.png"));
							break;
						}
					}
					if (board.tileAt(i - 1, j - 1).hasToken() && board.tileAt(i - 1, j - 1).hasPawn()) {
						int n = board.tileAt(i - 1, j - 1).get_Token().tokenValue();
						_tile[i][j].setText("" + n);

					}
				} else if (board.tileAt(i - 1, j - 1).getDirection()[0] == false
						&& board.tileAt(i - 1, j - 1).getDirection()[1] == true
						&& board.tileAt(i - 1, j - 1).getDirection()[2] == true
						&& board.tileAt(i - 1, j - 1).getDirection()[3] == true) {
					_tile[i][j].setIcon(TTile3);
					_tile[i][j].setHorizontalTextPosition(JButton.CENTER);
					_tile[i][j].setVerticalTextPosition(JButton.CENTER);
					if (board.tileAt(i - 1, j - 1).hasToken()) {
						int n = board.tileAt(i - 1, j - 1).get_Token().tokenValue();
						_tile[i][j].setIcon(mergeImages("T3 Tile.jpg", n + ".png"));
						_tile[i][j].setText("");
					}
					if (board.tileAt(i - 1, j - 1).hasPawn()) {
						_tile[i][j].setText("");
						switch (board.tileAt(i - 1, j - 1).get_Pawn().getNumber()) {
						case 1:
							_tile[i][j].setIcon(mergeImages("T3 Tile.jpg", "red.png"));
							break;
						case 2:
							_tile[i][j].setIcon(mergeImages("T3 Tile.jpg", "cyan.png"));
							break;
						case 3:
							_tile[i][j].setIcon(mergeImages("T3 Tile.jpg", "green.png"));
							break;
						case 4:
							_tile[i][j].setIcon(mergeImages("T3 Tile.jpg", "magenta.png"));
							break;
						}
					}
					if (board.tileAt(i - 1, j - 1).hasToken() && board.tileAt(i - 1, j - 1).hasPawn()) {
						int n = board.tileAt(i - 1, j - 1).get_Token().tokenValue();
						_tile[i][j].setText("" + n);

					}
				} else if (board.tileAt(i - 1, j - 1).getDirection()[0] == true
						&& board.tileAt(i - 1, j - 1).getDirection()[1] == true
						&& board.tileAt(i - 1, j - 1).getDirection()[2] == true
						&& board.tileAt(i - 1, j - 1).getDirection()[3] == false) {
					_tile[i][j].setIcon(TTile4);
					_tile[i][j].setHorizontalTextPosition(JButton.CENTER);
					_tile[i][j].setVerticalTextPosition(JButton.CENTER);
					if (board.tileAt(i - 1, j - 1).hasToken()) {
						int n = board.tileAt(i - 1, j - 1).get_Token().tokenValue();
						_tile[i][j].setIcon(mergeImages("T4 Tile.jpg", n + ".png"));
						_tile[i][j].setText("");
					}
					if (board.tileAt(i - 1, j - 1).hasPawn()) {
						_tile[i][j].setText("");
						switch (board.tileAt(i - 1, j - 1).get_Pawn().getNumber()) {
						case 1:
							_tile[i][j].setIcon(mergeImages("T4 Tile.jpg", "red.png"));
							break;
						case 2:
							_tile[i][j].setIcon(mergeImages("T4 Tile.jpg", "cyan.png"));
							break;
						case 3:
							_tile[i][j].setIcon(mergeImages("T4 Tile.jpg", "green.png"));
							break;
						case 4:
							_tile[i][j].setIcon(mergeImages("T4 Tile.jpg", "magenta.png"));
							break;
						}
					}
					if (board.tileAt(i - 1, j - 1).hasToken() && board.tileAt(i - 1, j - 1).hasPawn()) {
						int n = board.tileAt(i - 1, j - 1).get_Token().tokenValue();
						_tile[i][j].setText("" + n);

					}
				} else if (board.tileAt(i - 1, j - 1).getDirection()[0] == true
						&& board.tileAt(i - 1, j - 1).getDirection()[1] == false
						&& board.tileAt(i - 1, j - 1).getDirection()[2] == false
						&& board.tileAt(i - 1, j - 1).getDirection()[3] == true) {
					_tile[i][j].setIcon(LTile);
					_tile[i][j].setHorizontalTextPosition(JButton.CENTER);
					_tile[i][j].setVerticalTextPosition(JButton.CENTER);
					if (board.tileAt(i - 1, j - 1).hasToken()) {
						int n = board.tileAt(i - 1, j - 1).get_Token().tokenValue();
						_tile[i][j].setIcon(mergeImages("L Tile.jpg", n + ".png"));
						_tile[i][j].setText("");
					}
					if (board.tileAt(i - 1, j - 1).hasPawn()) {
						_tile[i][j].setText("");
						switch (board.tileAt(i - 1, j - 1).get_Pawn().getNumber()) {
						case 1:
							_tile[i][j].setIcon(mergeImages("L Tile.jpg", "red.png"));
							break;
						case 2:
							_tile[i][j].setIcon(mergeImages("L Tile.jpg", "cyan.png"));
							break;
						case 3:
							_tile[i][j].setIcon(mergeImages("L Tile.jpg", "green.png"));
							break;
						case 4:
							_tile[i][j].setIcon(mergeImages("L Tile.jpg", "magenta.png"));
							break;
						}
					}
					if (board.tileAt(i - 1, j - 1).hasToken() && board.tileAt(i - 1, j - 1).hasPawn()) {
						int n = board.tileAt(i - 1, j - 1).get_Token().tokenValue();
						_tile[i][j].setText("" + n);

					}
				} else if (board.tileAt(i - 1, j - 1).getDirection()[0] == false
						&& board.tileAt(i - 1, j - 1).getDirection()[1] == true
						&& board.tileAt(i - 1, j - 1).getDirection()[2] == false
						&& board.tileAt(i - 1, j - 1).getDirection()[3] == true) {
					_tile[i][j].setIcon(LTile2);
					_tile[i][j].setHorizontalTextPosition(JButton.CENTER);
					_tile[i][j].setVerticalTextPosition(JButton.CENTER);
					if (board.tileAt(i - 1, j - 1).hasToken()) {
						int n = board.tileAt(i - 1, j - 1).get_Token().tokenValue();
						_tile[i][j].setIcon(mergeImages("L2 Tile.jpg", n + ".png"));
						_tile[i][j].setText("");
					}
					if (board.tileAt(i - 1, j - 1).hasPawn()) {
						_tile[i][j].setText("");
						switch (board.tileAt(i - 1, j - 1).get_Pawn().getNumber()) {
						case 1:
							_tile[i][j].setIcon(mergeImages("L2 Tile.jpg", "red.png"));
							break;
						case 2:
							_tile[i][j].setIcon(mergeImages("L2 Tile.jpg", "cyan.png"));
							break;
						case 3:
							_tile[i][j].setIcon(mergeImages("L2 Tile.jpg", "green.png"));
							break;
						case 4:
							_tile[i][j].setIcon(mergeImages("L2 Tile.jpg", "magenta.png"));
							break;
						}
					}
					if (board.tileAt(i - 1, j - 1).hasToken() && board.tileAt(i - 1, j - 1).hasPawn()) {
						int n = board.tileAt(i - 1, j - 1).get_Token().tokenValue();
						_tile[i][j].setText("" + n);

					}
				} else if (board.tileAt(i - 1, j - 1).getDirection()[0] == false
						&& board.tileAt(i - 1, j - 1).getDirection()[1] == true
						&& board.tileAt(i - 1, j - 1).getDirection()[2] == true
						&& board.tileAt(i - 1, j - 1).getDirection()[3] == false) {
					_tile[i][j].setIcon(LTile3);
					_tile[i][j].setHorizontalTextPosition(JButton.CENTER);
					_tile[i][j].setVerticalTextPosition(JButton.CENTER);
					if (board.tileAt(i - 1, j - 1).hasToken()) {
						int n = board.tileAt(i - 1, j - 1).get_Token().tokenValue();
						_tile[i][j].setIcon(mergeImages("L3 Tile.jpg", n + ".png"));
						_tile[i][j].setText("");
					}
					if (board.tileAt(i - 1, j - 1).hasPawn()) {
						_tile[i][j].setText("");
						switch (board.tileAt(i - 1, j - 1).get_Pawn().getNumber()) {
						case 1:
							_tile[i][j].setIcon(mergeImages("L3 Tile.jpg", "red.png"));
							break;
						case 2:
							_tile[i][j].setIcon(mergeImages("L3 Tile.jpg", "cyan.png"));
							break;
						case 3:
							_tile[i][j].setIcon(mergeImages("L3 Tile.jpg", "green.png"));
							break;
						case 4:
							_tile[i][j].setIcon(mergeImages("L3 Tile.jpg", "magenta.png"));
							break;
						}
					}
					if (board.tileAt(i - 1, j - 1).hasToken() && board.tileAt(i - 1, j - 1).hasPawn()) {
						int n = board.tileAt(i - 1, j - 1).get_Token().tokenValue();
						_tile[i][j].setText("" + n);

					}
				} else if (board.tileAt(i - 1, j - 1).getDirection()[0] == true
						&& board.tileAt(i - 1, j - 1).getDirection()[1] == false
						&& board.tileAt(i - 1, j - 1).getDirection()[2] == true
						&& board.tileAt(i - 1, j - 1).getDirection()[3] == false) {
					_tile[i][j].setIcon(LTile4);
					_tile[i][j].setHorizontalTextPosition(JButton.CENTER);
					_tile[i][j].setVerticalTextPosition(JButton.CENTER);
					if (board.tileAt(i - 1, j - 1).hasToken()) {
						int n = board.tileAt(i - 1, j - 1).get_Token().tokenValue();
						_tile[i][j].setIcon(mergeImages("L4 Tile.jpg", n + ".png"));
						_tile[i][j].setText("");
					}
					if (board.tileAt(i - 1, j - 1).hasPawn()) {
						_tile[i][j].setText("");
						switch (board.tileAt(i - 1, j - 1).get_Pawn().getNumber()) {
						case 1:
							_tile[i][j].setIcon(mergeImages("L4 Tile.jpg", "red.png"));
							break;
						case 2:
							_tile[i][j].setIcon(mergeImages("L4 Tile.jpg", "cyan.png"));
							break;
						case 3:
							_tile[i][j].setIcon(mergeImages("L4 Tile.jpg", "green.png"));
							break;
						case 4:
							_tile[i][j].setIcon(mergeImages("L4 Tile.jpg", "magenta.png"));
							break;
						}
					}
					if (board.tileAt(i - 1, j - 1).hasToken() && board.tileAt(i - 1, j - 1).hasPawn()) {
						int n = board.tileAt(i - 1, j - 1).get_Token().tokenValue();
						_tile[i][j].setText("" + n);

					}
				} else if (board.tileAt(i - 1, j - 1).getDirection()[0] == false
						&& board.tileAt(i - 1, j - 1).getDirection()[1] == false
						&& board.tileAt(i - 1, j - 1).getDirection()[2] == true
						&& board.tileAt(i - 1, j - 1).getDirection()[3] == true) {
					_tile[i][j].setIcon(ITile);
					_tile[i][j].setHorizontalTextPosition(JButton.CENTER);
					_tile[i][j].setVerticalTextPosition(JButton.CENTER);
					if (board.tileAt(i - 1, j - 1).hasToken()) {
						int n = board.tileAt(i - 1, j - 1).get_Token().tokenValue();
						_tile[i][j].setIcon(mergeImages("I Tile.jpg", n + ".png"));
						_tile[i][j].setText("");
					}
					if (board.tileAt(i - 1, j - 1).hasPawn()) {
						_tile[i][j].setText("");
						switch (board.tileAt(i - 1, j - 1).get_Pawn().getNumber()) {
						case 1:
							_tile[i][j].setIcon(mergeImages("I Tile.jpg", "red.png"));
							break;
						case 2:
							_tile[i][j].setIcon(mergeImages("I Tile.jpg", "cyan.png"));
							break;
						case 3:
							_tile[i][j].setIcon(mergeImages("I Tile.jpg", "green.png"));
							break;
						case 4:
							_tile[i][j].setIcon(mergeImages("I Tile.jpg", "magenta.png"));
							break;
						}
					}
					if (board.tileAt(i - 1, j - 1).hasToken() && board.tileAt(i - 1, j - 1).hasPawn()) {
						int n = board.tileAt(i - 1, j - 1).get_Token().tokenValue();
						_tile[i][j].setText("" + n);

					}
				} else if (board.tileAt(i - 1, j - 1).getDirection()[0] == true
						&& board.tileAt(i - 1, j - 1).getDirection()[1] == true
						&& board.tileAt(i - 1, j - 1).getDirection()[2] == false
						&& board.tileAt(i - 1, j - 1).getDirection()[3] == false) {
					_tile[i][j].setIcon(ITile2);
					_tile[i][j].setHorizontalTextPosition(JButton.CENTER);
					_tile[i][j].setVerticalTextPosition(JButton.CENTER);
					if (board.tileAt(i - 1, j - 1).hasToken()) {
						int n = board.tileAt(i - 1, j - 1).get_Token().tokenValue();
						_tile[i][j].setIcon(mergeImages("I2 Tile.jpg", n + ".png"));
						_tile[i][j].setText("");
					}
					if (board.tileAt(i - 1, j - 1).hasPawn()) {
						_tile[i][j].setText("");
						switch (board.tileAt(i - 1, j - 1).get_Pawn().getNumber()) {
						case 1:
							_tile[i][j].setIcon(mergeImages("I2 Tile.jpg", "red.png"));
							break;
						case 2:
							_tile[i][j].setIcon(mergeImages("I2 Tile.jpg", "cyan.png"));
							break;
						case 3:
							_tile[i][j].setIcon(mergeImages("I2 Tile.jpg", "green.png"));
							break;
						case 4:
							_tile[i][j].setIcon(mergeImages("I2 Tile.jpg", "magenta.png"));
							break;
						}
					}
					if (board.tileAt(i - 1, j - 1).hasToken() && board.tileAt(i - 1, j - 1).hasPawn()) {
						int n = board.tileAt(i - 1, j - 1).get_Token().tokenValue();
						_tile[i][j].setText("" + n);

					}
				}
			}
		}

	}

	/**
	 * Creates a small score GUI for each player present.
	 * 
	 * @author Christian Coffey
	 */
	private void createControlPanel() {

		board.tileAt(2, 2).setPrevLocation(true);

		// Control Panel frame
		controlFrame = new JFrame("Control Panel");
		controlFrame.setVisible(true);
		controlFrame.setLayout(new BorderLayout());
		controlFrame.setLocationRelativeTo(null);

		// Control Panel button placement
		up = new JButton("^");
		down = new JButton("v");
		left = new JButton("<");
		right = new JButton(">");
		up.setEnabled(false);
		down.setEnabled(false);
		left.setEnabled(false);
		right.setEnabled(false);

		tokenButton = new JButton("Pick up token!");
		tokenButton.setOpaque(true);
		tokenButton.setBackground(Color.YELLOW);
		tokenButton.setEnabled(false);

		// Creating Finish Turn
		JButton finishbtn = new JButton("Finish Turn.");

		// Adding all components to frame...
		JPanel topPanel = new JPanel();
		JPanel botPanel = new JPanel();
		JPanel leftPanel = new JPanel();
		JPanel rightPanel = new JPanel();

		topPanel.setBackground(board.player(board.turn()).getColor());
		botPanel.setBackground(board.player(board.turn()).getColor());
		leftPanel.setBackground(board.player(board.turn()).getColor());
		rightPanel.setBackground(board.player(board.turn()).getColor());

		topPanel.setLayout(new BorderLayout());
		botPanel.setLayout(new BorderLayout());

		topPanel.add(up, BorderLayout.SOUTH);
		botPanel.add(finishbtn, BorderLayout.SOUTH);
		botPanel.add(down, BorderLayout.NORTH);
		leftPanel.add(left, BorderLayout.CENTER);
		rightPanel.add(right, BorderLayout.CENTER);

		controlFrame.add(leftPanel, BorderLayout.WEST);
		controlFrame.add(rightPanel, BorderLayout.EAST);
		controlFrame.add(topPanel, BorderLayout.NORTH);
		controlFrame.add(botPanel, BorderLayout.SOUTH);
		controlFrame.add(tokenButton, BorderLayout.CENTER);
		controlFrame.pack();

		// Handlers for each of the buttons
		up.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (board.moveUp(board.turn())) {
					tokenButton.setEnabled(true);
				}
				updateBoard();
			}
		});

		down.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (board.moveDown(board.turn())) {
					tokenButton.setEnabled(true);
				}
				updateBoard();
			}
		});

		left.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (board.moveLeft(board.turn())) {
					tokenButton.setEnabled(true);
				}
				updateBoard();
			}
		});

		right.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (board.moveRight(board.turn())) {
					tokenButton.setEnabled(true);
				}
				updateBoard();
			}

		});

		tokenButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				board.grabToken(board.player(board.turn()));
				refreshStats();
				updateBoard();
			}

		});
		finishbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (tileHasBeenPushed == false) {
					JOptionPane.showMessageDialog(null, "You need to push a tile first!");

				} else {
					up.setEnabled(true);
					down.setEnabled(true);
					left.setEnabled(true);
					right.setEnabled(true);
					tokenButton.setEnabled(false);
					board.finishTurn();
					topPanel.setBackground(board.player(board.turn()).getColor());
					botPanel.setBackground(board.player(board.turn()).getColor());
					leftPanel.setBackground(board.player(board.turn()).getColor());
					rightPanel.setBackground(board.player(board.turn()).getColor());
					pushTileSetEnabled(true);
					disablePushTile(lastPushedTile);
					tileHasBeenPushed = false;

					up.setEnabled(false);
					down.setEnabled(false);
					left.setEnabled(false);
					right.setEnabled(false);

					for (int i = 0; i < 7; i++) {
						for (int j = 0; j < 7; j++) {
							board.tileAt(i, j).setPrevLocation(false);
						}
					}
					board.tileAt(board.player(board.turn()).xPosition(), board.player(board.turn()).yPosition())
							.setPrevLocation(true);
					updateBoard();
				}
			}
		});
		controlFrame.setDefaultCloseOperation(controlFrame.EXIT_ON_CLOSE);
	}

	/**
	 * A small interface that expresses each Player's stats.
	 * 
	 * @author Christian Coffey
	 */
	private void createPlayerInfoBoard() {
		JFrame frame = new JFrame();
		// frame.setAlwaysOnTop(true);
		JPanel playersPanel = new JPanel();
		JPanel heldTileTextPanel = new JPanel();
		JPanel heldTileImage = new JPanel();
		playerBoxes = new JTextPane[4];
		frame.setLayout(new BorderLayout());
		frame.add(playersPanel, BorderLayout.NORTH);
		frame.add(heldTileTextPanel, BorderLayout.WEST);
		frame.add(heldTileImage);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);

		// Creating text boxes for each player based on players playing.
		for (int i = 0; i < board.activePlayers(); i++) {
			playerBoxes[i] = new JTextPane();
			switch (i) {
			case 0:
				playersPanel.add(playerBoxes[i]);
				playerBoxes[i].setBackground(Color.RED);
				playerBoxes[i].setText("Wands: " + board.player(1).getWands());
				break;
			case 1:
				playersPanel.add(playerBoxes[i]);
				playerBoxes[i].setBackground(Color.CYAN);
				playerBoxes[i].setText("Wands: " + board.player(2).getWands());
				break;
			case 2:
				playersPanel.add(playerBoxes[i]);
				playerBoxes[i].setBackground(Color.GREEN);
				playerBoxes[i].setText("Wands: " + board.player(3).getWands());
				break;
			case 3:
				playersPanel.add(playerBoxes[i]);
				playerBoxes[i].setBackground(Color.MAGENTA);
				playerBoxes[i].setText("Wands: " + board.player(4).getWands());
			}
		}
		nextToken = new JTextPane();

		nextToken.setForeground(Color.YELLOW);
		nextToken.setBackground(Color.BLACK);
		nextToken.setText("Next Token: " + board.getNextToken());
		JTextPane heldTile = new JTextPane();
		heldTile.setText("Currently held tile:");
		_heldTile = new JButton();
		heldTileTextPanel.add(heldTile);
		heldTileImage.add(_heldTile);
		playersPanel.add(nextToken);
		frame.setSize(390, 200);
		frame.setLocationRelativeTo(null);

		JButton rotateTileButton = new JButton("Rotate tile.");
		JPanel rotateTileFrame = new JPanel();
		rotateTileFrame.add(rotateTileButton);
		frame.add(rotateTileFrame, BorderLayout.EAST);

		rotateTileButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				board.getHeldTile().rotate();
				refreshStats();
			}

		});
	}

	/**
	 * Refreshes the stats box.
	 * 
	 * @author Christian Coffey
	 */
	private void refreshStats() {
		try {
			if (board.getNextToken() == 25) {
				// bgm.stop();
				lastTokenSong.loop(2147483647);
			}
			nextToken.setText("Next Token: " + board.getNextToken());
		} catch (IndexOutOfBoundsException e) {
			nextToken.setText("All tokens collected!");
			endGame();
		}
		for (int i = 0; i < board.activePlayers(); i++) {
			switch (i) {
			case 0:
				playerBoxes[i].setText("Wands: " + board.player(1).getWands());
				break;
			case 1:
				playerBoxes[i].setText("Wands: " + board.player(2).getWands());
				break;
			case 2:
				playerBoxes[i].setText("Wands: " + board.player(3).getWands());
				break;
			case 3:
				playerBoxes[i].setText("Wands: " + board.player(4).getWands());
			}
			// copy-pasta all paths again.
			ImageIcon LTile = new ImageIcon(getClass().getResource("/L Tile.jpg"));
			ImageIcon LTile2 = new ImageIcon(getClass().getResource("/L2 Tile.jpg"));
			ImageIcon LTile3 = new ImageIcon(getClass().getResource("/L3 Tile.jpg"));
			ImageIcon LTile4 = new ImageIcon(getClass().getResource("/L4 Tile.jpg"));
			ImageIcon TTile = new ImageIcon(getClass().getResource("/T Tile.jpg"));
			ImageIcon TTile2 = new ImageIcon(getClass().getResource("/T2 Tile.jpg"));
			ImageIcon TTile3 = new ImageIcon(getClass().getResource("/T3 Tile.jpg"));
			ImageIcon TTile4 = new ImageIcon(getClass().getResource("/T4 Tile.jpg"));
			ImageIcon ITile = new ImageIcon(getClass().getResource("/I Tile.jpg"));
			ImageIcon ITile2 = new ImageIcon(getClass().getResource("/I2 Tile.jpg"));

			if (board.getHeldTile().getDirection()[0] == true && board.getHeldTile().getDirection()[1] == false
					&& board.getHeldTile().getDirection()[2] == true && board.getHeldTile().getDirection()[3] == true) {
				_heldTile.setIcon(TTile);
				_heldTile.setHorizontalTextPosition(JButton.CENTER);
				_heldTile.setVerticalTextPosition(JButton.CENTER);

			} else if (board.getHeldTile().getDirection()[0] == true && board.getHeldTile().getDirection()[1] == true
					&& board.getHeldTile().getDirection()[2] == false
					&& board.getHeldTile().getDirection()[3] == true) {
				_heldTile.setIcon(TTile2);
				_heldTile.setHorizontalTextPosition(JButton.CENTER);
				_heldTile.setVerticalTextPosition(JButton.CENTER);

			} else if (board.getHeldTile().getDirection()[0] == false && board.getHeldTile().getDirection()[1] == true
					&& board.getHeldTile().getDirection()[2] == true && board.getHeldTile().getDirection()[3] == true) {
				_heldTile.setIcon(TTile3);
				_heldTile.setHorizontalTextPosition(JButton.CENTER);
				_heldTile.setVerticalTextPosition(JButton.CENTER);

			} else if (board.getHeldTile().getDirection()[0] == true && board.getHeldTile().getDirection()[1] == true
					&& board.getHeldTile().getDirection()[2] == true
					&& board.getHeldTile().getDirection()[3] == false) {
				_heldTile.setIcon(TTile4);
				_heldTile.setHorizontalTextPosition(JButton.CENTER);
				_heldTile.setVerticalTextPosition(JButton.CENTER);

			} else if (board.getHeldTile().getDirection()[0] == true && board.getHeldTile().getDirection()[1] == false
					&& board.getHeldTile().getDirection()[2] == false
					&& board.getHeldTile().getDirection()[3] == true) {
				_heldTile.setIcon(LTile);
				_heldTile.setHorizontalTextPosition(JButton.CENTER);
				_heldTile.setVerticalTextPosition(JButton.CENTER);

			} else if (board.getHeldTile().getDirection()[0] == false && board.getHeldTile().getDirection()[1] == true
					&& board.getHeldTile().getDirection()[2] == false
					&& board.getHeldTile().getDirection()[3] == true) {
				_heldTile.setIcon(LTile2);
				_heldTile.setHorizontalTextPosition(JButton.CENTER);
				_heldTile.setVerticalTextPosition(JButton.CENTER);

			} else if (board.getHeldTile().getDirection()[0] == false && board.getHeldTile().getDirection()[1] == true
					&& board.getHeldTile().getDirection()[2] == true
					&& board.getHeldTile().getDirection()[3] == false) {
				_heldTile.setIcon(LTile3);
				_heldTile.setHorizontalTextPosition(JButton.CENTER);
				_heldTile.setVerticalTextPosition(JButton.CENTER);

			} else if (board.getHeldTile().getDirection()[0] == true && board.getHeldTile().getDirection()[1] == false
					&& board.getHeldTile().getDirection()[2] == true
					&& board.getHeldTile().getDirection()[3] == false) {
				_heldTile.setIcon(LTile4);
				_heldTile.setHorizontalTextPosition(JButton.CENTER);
				_heldTile.setVerticalTextPosition(JButton.CENTER);

			} else if (board.getHeldTile().getDirection()[0] == false && board.getHeldTile().getDirection()[1] == false
					&& board.getHeldTile().getDirection()[2] == true && board.getHeldTile().getDirection()[3] == true) {
				_heldTile.setIcon(ITile);
				_heldTile.setHorizontalTextPosition(JButton.CENTER);
				_heldTile.setVerticalTextPosition(JButton.CENTER);

			} else if (board.getHeldTile().getDirection()[0] == true && board.getHeldTile().getDirection()[1] == true
					&& board.getHeldTile().getDirection()[2] == false
					&& board.getHeldTile().getDirection()[3] == false) {
				_heldTile.setIcon(ITile2);
				_heldTile.setHorizontalTextPosition(JButton.CENTER);
				_heldTile.setVerticalTextPosition(JButton.CENTER);
			}

		}
	}

	/**
	 * Merges images 1 and 2 that were passed into the method.
	 * 
	 * @param imagePath
	 *            - Path to the resource folder
	 * @param image1Name
	 *            - Name of the first image within the resource folder
	 * @param image2Name
	 *            - Name of the second image within the resource folder
	 * @return New image with image2 as the overlay and image1 as the 'underlay'
	 */
	private ImageIcon mergeImages(String image1Name, String image2Name) {
		try {

			// load source images
			BufferedImage image = ImageIO.read(getClass().getResource("/" + image1Name));
			BufferedImage overlay = ImageIO.read(getClass().getResource("/" + image2Name));

			// create the new image, canvas size is the max. of both image sizes
			int w = Math.max(image.getWidth(), overlay.getWidth());
			int h = Math.max(image.getHeight(), overlay.getHeight());
			BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

			// paint both images, preserving the alpha channels
			Graphics g = combined.getGraphics();
			g.drawImage(image, 0, 0, null);
			g.drawImage(overlay, 0, 0, null);

			ImageIcon combinedImage = new ImageIcon(combined);
			return combinedImage;

		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * Configures the push tile buttons along the parameter
	 * 
	 * @author Christian Coffey
	 */
	private void pushTileButtons() {
		// Generating action handlers for each of the 12 buttons along the
		// outside via for loop.
		for (int i = 2; i < 8; i += 2) {
			int tile = i;
			Font font = new Font("Calibri", 0, 40);
			int rowOrColumn = i - 1;
			_tile[i][0].setText("v");
			_tile[i][0].setFont(font);
			_tile[i][0].addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					board.pushColumnTileUD(rowOrColumn);
					refreshStats();
					pushTileSetEnabled(false);
					lastPushedTile = tile;
					tileHasBeenPushed = true;

					up.setEnabled(true);
					down.setEnabled(true);
					left.setEnabled(true);
					right.setEnabled(true);

					updateBoard();

				}

			});
			_tile[0][i].setText(">");
			_tile[0][i].setFont(font);
			_tile[0][i].addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					board.pushRowTileLR(rowOrColumn);
					refreshStats();
					pushTileSetEnabled(false);
					lastPushedTile = tile + 18;
					tileHasBeenPushed = true;

					up.setEnabled(true);
					down.setEnabled(true);
					left.setEnabled(true);
					right.setEnabled(true);

					updateBoard();

				}

			});
			_tile[i][8].setText("^");
			_tile[i][8].setFont(font);
			_tile[i][8].addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					board.pushColumnTileDU(rowOrColumn);
					refreshStats();
					pushTileSetEnabled(false);
					lastPushedTile = tile + 12;
					tileHasBeenPushed = true;

					up.setEnabled(true);
					down.setEnabled(true);
					left.setEnabled(true);
					right.setEnabled(true);

					updateBoard();

				}

			});
			_tile[8][i].setText("<");
			_tile[8][i].setFont(font);
			_tile[8][i].addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					board.pushRowTileRL(rowOrColumn);
					refreshStats();
					pushTileSetEnabled(false);
					lastPushedTile = tile + 6;
					tileHasBeenPushed = true;

					up.setEnabled(true);
					down.setEnabled(true);
					left.setEnabled(true);
					right.setEnabled(true);

					updateBoard();

				}

			});

		}

	}

	/**
	 * Disable/Enable parameter buttons
	 * 
	 * @author Christian Coffey
	 */
	private void pushTileSetEnabled(boolean b) {
		for (int i = 2; i < 8; i += 2) {
			_tile[i][0].setEnabled(b);
			_tile[0][i].setEnabled(b);
			_tile[i][8].setEnabled(b);
			_tile[8][i].setEnabled(b);
		}
	}

	/**
	 * Disables a specific push tile along the parameter. Don't play with this
	 * method, it's kind of an abomination.
	 * 
	 * @author Christian Coffey
	 */
	private void disablePushTile(int i) {
		switch (i) {
		case 2:
			_tile[2][8].setEnabled(false);
			break;
		case 4:
			_tile[4][8].setEnabled(false);
			break;
		case 6:
			_tile[6][8].setEnabled(false);
			break;
		case 8:
			_tile[0][2].setEnabled(false);
			break;
		case 10:
			_tile[0][4].setEnabled(false);
			break;
		case 12:
			_tile[0][6].setEnabled(false);
			break;
		case 14:
			_tile[2][0].setEnabled(false);
			break;
		case 16:
			_tile[4][0].setEnabled(false);
			break;
		case 18:
			_tile[6][0].setEnabled(false);
			break;
		case 20:
			_tile[8][2].setEnabled(false);
			break;
		case 22:
			_tile[8][4].setEnabled(false);
			break;
		case 24:
			_tile[8][6].setEnabled(false);
			break;
		}
	}

	/**
	 * Finishes the bloody game.
	 * 
	 * @author Christian Coffey
	 */
	private void endGame() {
		try {
			AudioInputStream stream;
			AudioFormat format;
			DataLine.Info info;
			Clip endclip;

			stream = AudioSystem.getAudioInputStream(getClass().getResource("/gameover.wav"));
			format = stream.getFormat();
			info = new DataLine.Info(Clip.class, format);
			endclip = (Clip) AudioSystem.getLine(info);
			endclip.open(stream);
			lastTokenSong.stop();
			endclip.start();
		} catch (Exception e) {
			System.out.println("Sound file not found, mate.");
		}
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				_tile[i][j].setBackground(Color.BLACK);
				_tile[i][j].setForeground(Color.BLACK);
				_tile[i][j].setIcon(null);
				_tile[i][j].setEnabled(false);
			}
		}
		controlFrame.setVisible(false);

		int[] scores = new int[_players.length];

		JFrame endFrame = new JFrame("Game over!");
		endFrame.setDefaultCloseOperation(endFrame.EXIT_ON_CLOSE);
		JPanel scorePanel = new JPanel();
		endFrame.setLayout(new BorderLayout());
		for (int i = 0; i < _players.length; i++) {
			JLabel score = new JLabel();
			score.setFont(new Font("Impact", 0, 30));
			switch (i) {
			case 0:
				score.setText(_players[i] + ": " + board.player(1).getTotalPoints() + " total points!");
				score.setForeground(Color.RED);
				scores[i] = board.player(1).getTotalPoints();
				break;
			case 1:
				score.setText(_players[i] + ": " + board.player(2).getTotalPoints() + " total points!");
				score.setForeground(Color.CYAN);
				scores[i] = board.player(2).getTotalPoints();
				break;
			case 2:
				score.setText(_players[i] + ": " + board.player(3).getTotalPoints() + " total points!");
				score.setForeground(Color.GREEN);
				scores[i] = board.player(3).getTotalPoints();
				break;
			case 3:
				score.setText(_players[i] + ": " + board.player(4).getTotalPoints() + " total points!");
				score.setForeground(Color.MAGENTA);
				scores[i] = board.player(4).getTotalPoints();
				break;
			}
			scorePanel.add(score);
		}
		endFrame.add(scorePanel, BorderLayout.NORTH);

		endFrame.setVisible(true);
		endFrame.setLocationRelativeTo(_frame);
		endFrame.pack();
	}
}
