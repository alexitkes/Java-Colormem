import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.JOptionPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.ButtonGroup;


public class ColormemComponent extends JPanel implements MouseListener, ActionListener {
    private enum State {
        INTRO,
        REMEMBER_ON,
        REMEMBER_OFF,
        REPEAT_ON,
        REPEAT_OFF,
        WIN,
        LOSE
    }

    private enum Speed {
        SLOW,
        MEDIUM,
        FAST
    }

    private int level;
    private int stage;
    private boolean all_ok;
    private int color;
    private int [] colors;
    private State state = State.INTRO;
    private Speed speed = Speed.SLOW;
    private Timer timer = null;

    private Color color_darkred;
    private Color color_lightred;
    private Color color_darkgreen;
    private Color color_lightgreen;
    private Color color_darkblue;
    private Color color_lightblue;
    private Color color_darkyellow;
    private Color color_lightyellow;
    private Color color_white;
    private Font font;

    private int getDelay() {
        if (speed == Speed.FAST) {
            return 110;
        } else if (speed == Speed.MEDIUM) {
            return 275;
        } else {
            return 550;
        }
    }

    private void startTimer() {
        if (timer == null) {
            timer = new Timer(getDelay(), this);
            timer.setActionCommand("Timer");
        } else if (timer.getDelay() != getDelay()) {
            timer.setDelay(getDelay());
        }
        timer.start();
    }

    private void initColormem () {
        setMinimumSize(new Dimension(512, 560));
        setMaximumSize(new Dimension(512, 560));
        setOpaque(true);
        setBackground(new Color (0, 0, 0));
        addMouseListener(this);

        level = 3;
        stage = -1;
        colors = null;

        color_lightred = new Color(255, 0, 0);
        color_darkred = new Color(127, 0, 0);
        color_lightgreen = new Color(0, 255, 0);
        color_darkgreen = new Color(0, 127, 0);
        color_lightblue = new Color(0, 0, 255);
        color_darkblue = new Color(0, 0, 127);
        color_lightyellow = new Color(255, 255, 0);
        color_darkyellow = new Color(127, 127, 0);
        color_white = new Color(255, 255, 255);

        font = new Font("Arial", Font.PLAIN, 24);

        state = State.INTRO;
    }

    public void drawCenteredText (Graphics g, String s, int x, int y, int w, int h) {
        Rectangle2D rect = g.getFontMetrics ().getStringBounds(s, g);

        g.drawString (s, x + (w - (int) rect.getWidth()) / 2, y + (h + (int) rect.getHeight()) / 2);
    }

    public void paint (Graphics g) {
        super.paint(g);

        Color oldcolor = g.getColor();

        if (state == State.INTRO) {
            g.setColor(color_darkred);
            g.fillRect(3, 3, 250, 250);

            g.setColor(color_darkgreen);
            g.fillRect(259, 3, 250, 250);

            g.setColor(color_darkblue);
            g.fillRect(3, 259, 250, 250);

            g.setColor(color_darkyellow);
            g.fillRect(259, 259, 250, 250);

            g.setColor(color_white);
            g.setFont(font);
            drawCenteredText (g, "Click to start level " + level, 6, 516, 500, 40);
        } else if (state == State.REMEMBER_ON || state == State.REMEMBER_OFF) {
            if (state == State.REMEMBER_ON && stage >= 0 && colors [stage] == 0) {
                g.setColor(color_lightred);
            } else {
                g.setColor(color_darkred);
            }
            g.fillRect(3, 3, 250, 250);

            if (state == State.REMEMBER_ON && stage >= 0 && colors [stage] == 1) {
                g.setColor(color_lightgreen);
            } else {
                g.setColor(color_darkgreen);
            }
            g.fillRect(259, 3, 250, 250);

            if (state == State.REMEMBER_ON && stage >= 0 && colors [stage] == 2) {
                g.setColor(color_lightblue);
            } else {
                g.setColor(color_darkblue);
            }
            g.fillRect(3, 259, 250, 250);

            if (state == State.REMEMBER_ON && stage >= 0 && colors [stage] == 3) {
                g.setColor(color_lightyellow);
            } else {
                g.setColor(color_darkyellow);
            }
            g.fillRect(259, 259, 250, 250);

            g.setColor(color_white);
            g.setFont(font);
            drawCenteredText (g, "Try to remember the color sequence", 6, 516, 500, 40);
        } else if (state == State.REPEAT_ON || state == State.REPEAT_OFF) {
            if (state == State.REPEAT_ON && stage >= 0 && color == 0) {
                g.setColor(color_lightred);
            } else {
                g.setColor(color_darkred);
            }
            g.fillRect(3, 3, 250, 250);

            if (state == State.REPEAT_ON && stage >= 0 && color == 1) {
                g.setColor(color_lightgreen);
            } else {
                g.setColor(color_darkgreen);
            }
            g.fillRect(259, 3, 250, 250);

            if (state == State.REPEAT_ON && stage >= 0 && color == 2) {
                g.setColor(color_lightblue);
            } else {
                g.setColor(color_darkblue);
            }
            g.fillRect(3, 259, 250, 250);

            if (state == State.REPEAT_ON && stage >= 0 && color == 3) {
                g.setColor(color_lightyellow);
            } else {
                g.setColor(color_darkyellow);
            }
            g.fillRect(259, 259, 250, 250);

            g.setColor(color_white);
            g.setFont(font);
            drawCenteredText (g, "Try to repeat the color sequence", 6, 516, 500, 40);
        } else if (state == State.WIN) {
            g.setColor(color_darkred);
            g.fillRect(3, 3, 250, 250);

            g.setColor(color_darkgreen);
            g.fillRect(259, 3, 250, 250);

            g.setColor(color_darkblue);
            g.fillRect(3, 259, 250, 250);

            g.setColor(color_darkyellow);
            g.fillRect(259, 259, 250, 250);

            g.setColor(color_lightgreen);
            g.setFont(font);
            drawCenteredText (g, "You are right! Click to start level " + level, 6, 516, 500, 40);
        } else if (state == State.LOSE) {
            g.setColor(color_darkred);
            g.fillRect(3, 3, 250, 250);

            g.setColor(color_darkgreen);
            g.fillRect(259, 3, 250, 250);

            g.setColor(color_darkblue);
            g.fillRect(3, 259, 250, 250);

            g.setColor(color_darkyellow);
            g.fillRect(259, 259, 250, 250);

            g.setColor(color_lightred);
            g.setFont(font);
            drawCenteredText (g, "You are wrong. Click to start level " + level, 6, 516, 500, 40);
        }

        g.setColor(oldcolor);
    }

    public void actionPerformed (ActionEvent e) {
        if (e.getActionCommand() != null && e.getActionCommand().equals("Exit")) {
            System.exit(0);
        } else if (e.getActionCommand() != null && e.getActionCommand().equals("Slow")) {
            speed = Speed.SLOW;
        } else if (e.getActionCommand() != null && e.getActionCommand().equals("Medium")) {
            speed = Speed.MEDIUM;
        } else if (e.getActionCommand() != null && e.getActionCommand().equals("Fast")) {
            speed = Speed.FAST;
        } else if (e.getActionCommand() != null && e.getActionCommand().equals("About")) {
            JOptionPane.showMessageDialog(this,
                                          "This is Java Color Memory Demo v0.0.2 by Alex Itkes, 2019",
                                          "About JColorMemory",
                                          JOptionPane.PLAIN_MESSAGE);
        } else if (e.getActionCommand() != null && e.getActionCommand().equals ("Timer")) {
            if (state == State.REMEMBER_OFF) {
                stage++;

                if (stage < level) {
                    state = State.REMEMBER_ON;
                } else {
                    state = State.REPEAT_OFF;
                    timer.stop();
                    stage = 0;
                }
            } else if (state == State.REMEMBER_ON) {
                state = State.REMEMBER_OFF;
            } else if (state == State.REPEAT_ON) {
                state = State.REPEAT_OFF;
                timer.stop();

                if (stage >= level) {
                    if (all_ok) {
                        state = State.WIN;
                        level++;
                    } else {
                        state = State.LOSE;
                        level = Math.max(level - 1, 1);
                    }
                }
            }
            repaint ();
        }
    }

    public void mouseClicked (MouseEvent e) {
        if (state == State.INTRO) {
            state = State.REMEMBER_OFF;
            stage = -1;
            colors = new int [level];

            for (int i = 0; i < level; i++) {
                colors [i] = ((int) (Math.random() * 4)) % 4;
            }

            all_ok = true;
        } else if (state == State.REPEAT_OFF) {
            if (e.getX() >= 3 && e.getY() >= 3 && e.getX() < 253 && e.getY() < 253) {
                color = 0;
            } else if (e.getX() >= 259 && e.getY() >= 3 && e.getX() < 509 && e.getY() < 253) {
                color = 1;
            } else if (e.getX() >= 3 && e.getY() >= 259 && e.getX() < 253 && e.getY() < 509) {
                color = 2;
            } else if (e.getX() >= 259 && e.getY() >= 259 && e.getX() < 509 && e.getY() < 509) {
                color = 3;
            } else {
                return;
            }

            if (color != colors [stage]) {
                all_ok = false;
            }

            state = State.REPEAT_ON;
            stage++;
        } else if (state == State.WIN || state == State.LOSE) {
            state = State.REMEMBER_OFF;
            stage = -1;
            colors = new int [level];

            for (int i = 0; i < level; i++) {
                colors [i] = ((int) (Math.random() * 4)) % 4;
            }

            all_ok = true;
        }

        repaint ();
        startTimer();
    }

    public void mousePressed (MouseEvent e) {}

    public void mouseReleased (MouseEvent e) {}

    public void mouseEntered (MouseEvent e) {}

    public void mouseExited (MouseEvent e) {}

    public void mouseMoved (MouseEvent e) {}

    public void mouseDragged (MouseEvent e) {}

    public void mouseWheelMoved (MouseEvent e) {}

    public ColormemComponent() {
        initColormem ();
    }

    public ColormemComponent(LayoutManager layout) {
        super(layout);
        initColormem ();
    }

    public ColormemComponent(boolean isDoubleBuffered) {
        super(isDoubleBuffered);
        initColormem ();
    }

    public ColormemComponent(LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
        initColormem ();
    }

    public static void main (String [] args) {
        JFrame frame = new JFrame ("Java Color Memory Demo");
        ColormemComponent cc = new ColormemComponent (true);

        JMenuBar mb = new JMenuBar ();
        JMenu file_menu = new JMenu ("File");
        JMenu speed_menu = new JMenu ("Speed");
        JMenu help_menu = new JMenu ("Help");

        JMenuItem exit_item = new JMenuItem ("Exit");

        exit_item.addActionListener(cc);
        exit_item.setMnemonic(KeyEvent.VK_X);

        JRadioButtonMenuItem slow_item = new JRadioButtonMenuItem ("Slow");

        slow_item.addActionListener(cc);
        slow_item.setMnemonic(KeyEvent.VK_S);

        JRadioButtonMenuItem medium_item = new JRadioButtonMenuItem ("Medium");

        medium_item.addActionListener(cc);
        medium_item.setMnemonic(KeyEvent.VK_M);

        medium_item.setSelected(true);

        JRadioButtonMenuItem fast_item = new JRadioButtonMenuItem ("Fast");

        fast_item.addActionListener(cc);
        fast_item.setMnemonic(KeyEvent.VK_F);

        JMenuItem about_item = new JMenuItem ("About");

        about_item.addActionListener(cc);
        about_item.setMnemonic(KeyEvent.VK_A);

        file_menu.add(exit_item);
        file_menu.setMnemonic(KeyEvent.VK_F);

        speed_menu.add(slow_item);
        speed_menu.add(medium_item);
        speed_menu.add(fast_item);

        help_menu.add(about_item);
        help_menu.setMnemonic(KeyEvent.VK_H);

        mb.add(file_menu);
        mb.add(speed_menu);
        mb.add(help_menu);

        ButtonGroup speed_bg = new ButtonGroup ();

        speed_bg.add(slow_item);
        speed_bg.add(medium_item);
        speed_bg.add(fast_item);

        frame.setLayout(new java.awt.BorderLayout());
        frame.add(cc, java.awt.BorderLayout.CENTER);
        frame.setMinimumSize(new Dimension (520, 620));
        frame.setJMenuBar (mb);
        frame.setVisible (true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
