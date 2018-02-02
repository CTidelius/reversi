package gui;
/**
 * Created by carltidelius on 2018-01-19.
 */
import java.awt.Color;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
public class GUI extends JFrame{
    private final JPanel gui = new JPanel(new BorderLayout(3, 3));
    private JPanel reversiBoard;
    private JButton[][] boardspaces = new JButton[8][8];
    //private ReversiGame reversiGame;

    public GUI(){

        //reversiGame = new ReversiGame();

    }
    public final void initializeGui(boolean col) {
        gui.setBorder(new EmptyBorder(5, 5, 5, 5));

        reversiBoard = new JPanel(new GridLayout(0, 8));
        reversiBoard.setBorder(new LineBorder(Color.BLACK));
        reversiBoard.setBackground(Color.GREEN);
        gui.add(reversiBoard);
        setSize(650, 650);
        Insets buttonMargin = new Insets(0,0,0,0);
        for (int ii = 0; ii < boardspaces.length; ii++) {
            for (int jj = 0; jj < boardspaces[ii].length; jj++) {
                JButton b = new JButton();
                b.setOpaque(false);
                b.setContentAreaFilled(false);
                b.setBorderPainted(true);
                b.setMargin(buttonMargin);
                b.setBackground(Color.GREEN);
                // our chess pieces are 64x64 px in size, so we'll
                // 'fill this in' using a transparent icon..
                if ((ii == 3 && jj == 3) || (ii == 4 && jj == 4)){
                    BufferedImage image = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
                    paintComponent(image);
                    try {

                        image = ImageIO.read(new File("res/reversiblack.png"));
                    } catch (IOException ex) {
                        // handle exception...
                    }
                    ImageIcon icon = new ImageIcon();
                    icon.setImage(image);
                    b.setOpaque(false);
                    b.setContentAreaFilled(false);
                    b.setBorderPainted(false);
                    b.setIcon(icon);
                    boardspaces[jj][ii] = b;
                }else if ((ii == 3 && jj == 4) || (ii == 4 && jj == 3)){
                    BufferedImage image = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
                    paintComponent(image);
                    try {
                        image = ImageIO.read(new File("res/reversiwhite.png"));
                    } catch (IOException ex) {
                        // handle exception...
                    }
                    ImageIcon icon = new ImageIcon();
                    icon.setImage(image);
                    b.setOpaque(false);
                    b.setContentAreaFilled(false);
                    b.setBorderPainted(false);
                    b.setIcon(icon);
                    boardspaces[jj][ii] = b;
                }else {
                    BufferedImage image= new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
                    paintComponent(image);
                    ImageIcon icon = new ImageIcon(image);

                    b.setIcon(icon);

                    boardspaces[jj][ii] = b;
                }
            }
        }

        for (int ii = 0; ii < 8; ii++) {
            for (int jj = 0; jj < 8; jj++) {

                int x = ii;
                int y = jj;
                boardspaces[ii][jj].setBackground(Color.GREEN);
                reversiBoard.add(boardspaces[ii][jj]);
                boardspaces[ii][jj].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // reversiGame.getBoard().getPiece(ii, jj).flipPiece();
                        BufferedImage image = new BufferedImage(64, 64, BufferedImage.TYPE_4BYTE_ABGR);
                        paintComponent(image);
                        try {
                            if(col) {
                                image = ImageIO.read(new File("res/reversiblack.png"));
                            }else {
                                image = ImageIO.read(new File("res/reversiwhite.png"));
                            }
                        } catch (IOException ex) {
                            // handle exception...
                        }

                        ImageIcon icon = new ImageIcon();
                        icon.setImage(image);
                        boardspaces[x][y].setOpaque(false);
                        boardspaces[x][y].setContentAreaFilled(false);
                        boardspaces[x][y].setBorderPainted(false);
                        boardspaces[x][y].setIcon(icon);
                    }
                });
            }
        }


    }
    public final JComponent getReversiBoard(){
        return reversiBoard;
    }
    public final JComponent getGui(){
        return gui;
    }
    public static void main(String[] args) {
        Runnable r = new Runnable() {

            @Override
            public void run() {
                JOptionPane pane = new JOptionPane();
                JOptionPane pane2= new JOptionPane();
                boolean col = false;

                Object[] possibilities = {"Black", "White"};
                JFrame f = new JFrame("Reversi");
                f.getContentPane().setBackground(Color.GREEN);
                String color = (String)pane.showInputDialog(f, "What color do you want to play as?" , "Choose Color",
                        JOptionPane.PLAIN_MESSAGE, null , possibilities, "Black");
                String time = (String)pane2.showInputDialog(f, "How long, in milliseconds, should the AI take to make his move?" ,
                        null);
                if(color == "Black"){
                    col = true;
                }
                GUI rb = new GUI();
                rb.initializeGui(col);
                f.add(rb.getGui());
                f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                f.setLocationByPlatform(true);

                // ensures the frame is the minimum size it needs to be
                // in order display the components within it
                f.pack();
                // ensures the minimum size is enforced.
                f.setMinimumSize(f.getSize());
                f.setVisible(true);
            }
        };
        SwingUtilities.invokeLater(r);
    }
    public void paintComponent(BufferedImage image) {
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                image.setRGB(i, j, Color.green.getRGB());
            }
        }
    }

}
