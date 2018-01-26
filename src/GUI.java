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

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
public class GUI extends JFrame{
    private final JPanel gui = new JPanel(new BorderLayout(3, 3));
    private JPanel reversiBoard;
    private JButton[][] boardspaces = new JButton[8][8];


    public GUI(){
        initializeGui();

    }
    public final void initializeGui() {
        gui.setBorder(new EmptyBorder(5, 5, 5, 5));

        reversiBoard = new JPanel(new GridLayout(0, 8));
        reversiBoard.setBorder(new LineBorder(Color.BLACK));
        gui.add(reversiBoard);
        setSize(650, 650);
        Insets buttonMargin = new Insets(0,0,0,0);
        for (int ii = 0; ii < boardspaces.length; ii++) {
            for (int jj = 0; jj < boardspaces[ii].length; jj++) {
                JButton b = new JButton();
                b.setMargin(buttonMargin);
                // our chess pieces are 64x64 px in size, so we'll
                // 'fill this in' using a transparent icon..
                if ((ii == 3 && jj == 3) || (ii == 4 && jj == 4)){
                    BufferedImage image = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
                    try {
                        image = ImageIO.read(new File("/Users/carltidelius/AndroidStudioProjects/Reversi/src/reversiblack.png"));
                    } catch (IOException ex) {
                        // handle exception...
                    }
                    ImageIcon icon = new ImageIcon(image);
                    b.setIcon(icon);
                    boardspaces[jj][ii] = b;
                }else if ((ii == 3 && jj == 4) || (ii == 4 && jj == 3)){
                    BufferedImage image = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
                    try {
                        image = ImageIO.read(new File("/Users/carltidelius/AndroidStudioProjects/Reversi/src/reversiwhite.png"));
                    } catch (IOException ex) {
                        // handle exception...
                    }
                    ImageIcon icon = new ImageIcon(image);
                    b.setIcon(icon);
                    boardspaces[jj][ii] = b;
                }else {
                    ImageIcon icon = new ImageIcon(
                            new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
                    
                    b.setIcon(icon);
                    boardspaces[jj][ii] = b;
                }
            }
        }
        for (int ii = 0; ii < 8; ii++) {
            for (int jj = 0; jj < 8; jj++) {
                        reversiBoard.add(boardspaces[jj][ii]);
                        boardspaces[jj][ii].addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {

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
                GUI rb = new GUI();

                JFrame f = new JFrame("Reversi");
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

}
