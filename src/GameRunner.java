import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.LineBorder;

import board.Board;
import board.BoardGui;
import board.Cell;
import board.CellGui;
import board.TickListener;
import shapes.Shape;
import util.Sound; /** Fatma */

/**
 */
public class GameRunner {

    private final BoardGui board;
    private JFrame app;
    private int Score;  			/** Secil */
    private JLabel scoreLabel;		/** Secil */
    private Sound sound; 		/** Fatma */
    private boolean wait = true;  /** Secil */
    
    private GameRunner() throws InterruptedException {
        board = new BoardGui(30, 10);
        app = new JFrame();
        newApp();
        app.add(board);
        app.add(new NextShapeGui(board));
        setScore(0);							 		/** Secil */
        scoreLabel= new JLabel("Score : " +Score);		/** Secil */
        app.add(scoreLabel,0); 						 	/** Secil */   
        sound=new Sound();      /** Fatma */
        start();
    }

    void start() throws InterruptedException {
        startBackgroundSound();         /** Fatma */
        board.addNewShapeAtRandom();
        board.repaintBoard();
        while (!board.gameOver()) {
            board.tick();
            setScore(board.getBoard().Score);			/** Secil */
            scoreLabel.setText("Score : " +Score);   	/** Secil */        
            Thread.sleep(250);
        }
	sound.play("game_over.wav"); /** Fatma */
        stop();
    }
    /**Fatma*/
    public void startBackgroundSound(){
    	
    }
    
    /** Secil */
    public void stop(){
        app.dispose();
    	ImageIcon image = new ImageIcon("gameover.png");
    	JLabel label = new JLabel(image);
    	JButton buton1 = new JButton("Tekrar Oyna");
    	JButton buton2 = new JButton("Cikis");
    	buton1.setPreferredSize(new Dimension(350, 50));
    	buton2.setPreferredSize(new Dimension(350, 50));

    	Font font=new Font("Calibri",Font.BOLD,16);
     	buton1.setFont(font);
     	buton2.setFont(font);
     	
     	JFrame frame = new JFrame("GAME-OVER");
    	frame.setLayout(new BorderLayout());
    	JPanel buttons = new JPanel();
    	buttons.add(buton1);
    	buttons.add(buton2);
    	buttons.setBackground(Color.BLACK);
    	
    	frame.add(label,BorderLayout.NORTH);
    	frame.add(buttons,BorderLayout.SOUTH);
   
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
     	frame.setVisible(true);
     	
     	buton2.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
     	
     	buton1.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				wait=false;
			}
		});
     	
    	try {
    		while(wait)
    			Thread.sleep(100);
    		frame.dispose();
    		new GameRunner();
    		wait=true;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }
    
    private void newApp() {
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setLayout(new FlowLayout());
        app.setSize(500, 600);
        app.setLocation(200, 200);
        app.setVisible(true);
        app.setTitle("Tetris");
        app.addKeyListener(new ShapeMover());
    }

    private class ShapeMover implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT){
                board.getBoard().moveShapeToLeft();
                sound.play("move.wav");  /** Fatma */
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT){
                board.getBoard().moveShapeToRight();
                sound.play("move.wav");  /** Fatma */
            }
            if (e.getKeyCode() == KeyEvent.VK_UP){
                board.getBoard().rotateShapeAntiClockwise();
                sound.play("rotate.wav");  /** Fatma */
            }
            if(e.getKeyCode()==KeyEvent.VK_U)board.getBoard().undoMove(); /** Elif */
            if (e.getKeyCode() == KeyEvent.VK_DOWN){
                board.getBoard().rotateShapeClockwise();
                sound.play("rotate.wav");  /** Fatma */
            }
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                sound.play("space.wav");  /** Fatma */
                while (board.getBoard().movingShapeCanMoveDown()) {
                    board.tick();
                }
            }

            board.repaintBoard();
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }
    }

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, IllegalAccessException, InstantiationException, InterruptedException {
        UIManager.setLookAndFeel(
                UIManager.getSystemLookAndFeelClassName());
        new GameRunner();
    }

    
    /** Secil */
    public void setScore(int i){
    	this.Score = i;
    }
    
    private class NextShapeGui extends JPanel implements TickListener {
        Board board;
        List<CellGui> cells;

        public NextShapeGui(BoardGui board) {
            this.board = board.getBoard();
            this.board.addTickListener(this);
            setLayout(new GridLayout(4, 4));

            setBorder(new LineBorder(Color.black));
            cells = new ArrayList<CellGui>();

            for (int row = 0;row<4;row++){
                for (int col = 0;col<4;col++){
                    CellGui cell = new CellGui(new Cell(row, col));
                    add(cell);
                    cells.add(cell);
                }
            }
        }


        @Override
        public void boardHasTicked() {
            Shape nextShape = board.getNextShape();
            for (int row = 0;row<nextShape.getLayoutArray().length;row++){
                for (int col = 0;col<nextShape.getLayoutArray()[0].length;col++){
                    cellGuiAt(row, col).underlying().setPopulated(
                            nextShape.getLayoutArray()[row][col] == 1, nextShape);
                    cellGuiAt(row, col).recolour();
                }
            }
        }

        private CellGui cellGuiAt(int row, int col){
            for (CellGui cell : cells){
                if (cell.underlying().row == row && cell.underlying().column == col) return cell;
            }
            throw new RuntimeException("unable to find cell at "+row+", "+col+" in next shape gui");
        }
    }

}
