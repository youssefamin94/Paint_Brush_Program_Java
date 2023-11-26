import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class JavaProject extends Applet {

    protected int startpoint_x, startpoint_y, endpoint_x, endpoint_y, Width, Height, mode;

    private Color currentColor = Color.BLACK;

    protected boolean fill = false;

    protected boolean flag = false;

    ArrayList<Shape> arrayOfShapes = new ArrayList<Shape>();

    Shape shapeToBeDrew;

    public static final int RECTANGLE = 1;
    public static final int OVAL = 2;
    public static final int LINE = 3;
    public static final int ERASER = 4;
    public static final int DOODLE = 5;

    public void init() {

        MyMouseListener listener = new MyMouseListener();
        addMouseListener(listener);
        addMouseMotionListener(listener);

        
		/*Initialization of buttons of colors and shapes*/
		
		// Colors
		
        Button red_butt = new Button("Red");
		red_butt.setBackground(Color.RED);
        red_butt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                currentColor = Color.RED;
            }
        });
        add(red_butt);

        
        Button green_butt = new Button("Green");
		green_butt.setBackground(Color.GREEN);
        green_butt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                currentColor = Color.GREEN;
            }
        });
        add(green_butt);

        Button blue_butt = new Button("Blue");
		blue_butt.setBackground(Color.BLUE);
        blue_butt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                currentColor = Color.BLUE;
            }
        });
        add(blue_butt);
		
		Button yellow_butt = new Button("Yellow");
		yellow_butt.setBackground(Color.YELLOW);
        yellow_butt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                currentColor = Color.YELLOW;
            }
        });
        add(yellow_butt);
		
		Button cyan_butt = new Button("Cyan");
		cyan_butt.setBackground(Color.CYAN);
        cyan_butt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                currentColor = Color.CYAN;
            }
        });
        add(cyan_butt);

        // Shapes

        
        Button rect_butt = new Button("Rectangle");
        rect_butt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                mode = RECTANGLE;
            }
        });
        add(rect_butt);

        // Oval
        Button oval_butt = new Button("Oval");
        oval_butt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                mode = OVAL;
            }
        });
        add(oval_butt);

       
        Button line_butt = new Button("Line");
        line_butt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                mode = LINE;
            }
        });
        add(line_butt);

        
        Button doodle = new Button("Doodle");
        doodle.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                mode = DOODLE;
            }
        });
        add(doodle);

        Button eraser = new Button("Eraser");
        eraser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                mode = ERASER;
            }
        });
        add(eraser);
		
		
		Button clear = new Button("Clear All");
		clear.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ev){
				arrayOfShapes.clear();
				repaint();}});
		add(clear);
		

        Checkbox solid = new Checkbox("Solid");

        solid.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                fill = solid.getState();
            }
        });
        add(solid);
    }

    class MyMouseListener extends MouseAdapter {

        public void mousePressed(MouseEvent e) {
            startpoint_x = e.getX();
            startpoint_y = e.getY();
        }

        public void mouseDragged(MouseEvent e) {
            flag = true;
            endpoint_x = e.getX();
            endpoint_y = e.getY();
            Width = Math.abs(endpoint_x - startpoint_x);
            Height = Math.abs(endpoint_y - startpoint_y);

            if (mode == ERASER) {
                arrayOfShapes.add(new Oval(endpoint_x, endpoint_y, 15, 15, true, Color.WHITE));
            } else if (mode == DOODLE) {
                arrayOfShapes.add(new Oval(endpoint_x, endpoint_y, 7, 7, true, currentColor));
            }

            repaint();
        }

        public void mouseReleased(MouseEvent e) {
            if (flag && mode != DOODLE) {
                endpoint_x = e.getX();
                endpoint_y = e.getY();

                switch (mode) {
                    case RECTANGLE:
                        arrayOfShapes.add(new Rectangle(
                                Math.min(startpoint_x, endpoint_x),
                                Math.min(startpoint_y, endpoint_y),
                                Width,
                                Height,
                                fill,
                                currentColor));
                        break;

                    case OVAL:
                        arrayOfShapes.add(new Oval(Math.min(startpoint_x, endpoint_x),
                                Math.min(startpoint_y, endpoint_y),
                                Width,
                                Height,
                                fill,
                                currentColor));
                        break;

                    case LINE:
                        arrayOfShapes.add(new Line(startpoint_x,
                                startpoint_y,
                                endpoint_x,
                                endpoint_y,
                                fill,
                                currentColor)
                        );
                        break;
                }
                flag = false;
                repaint();
            }
        }
    }

    public void paint(Graphics g) {
        if (flag) {
            g.setColor(currentColor);
            switch (mode) {
                case RECTANGLE:
                    shapeToBeDrew = new Rectangle(
                            Math.min(startpoint_x, endpoint_x),
                            Math.min(startpoint_y, endpoint_y),
                            Width,
                            Height,
                            fill,
                            currentColor);
                    shapeToBeDrew.draw(g);
                    break;

                case OVAL:
                    shapeToBeDrew = new Oval(Math.min(startpoint_x, endpoint_x),
                            Math.min(startpoint_y, endpoint_y),
                            Width,
                            Height,
                            fill,
                            currentColor);
                    shapeToBeDrew.draw(g);
                    break;

                case LINE:
                    shapeToBeDrew = new Line(startpoint_x,
                            startpoint_y,
                            endpoint_x,
                            endpoint_y,
                            fill,
                            currentColor);
                    shapeToBeDrew.draw(g);
                    break;
            }
        }

        for (int shape = 0; shape < arrayOfShapes.size(); shape++) {
            arrayOfShapes.get(shape).draw(g);
        }
    }

    public abstract class Shape {

        int startpoint_x,
                startpoint_y,
                Width,
                Height;
        boolean fill;
        Color color;

        public Shape(int startpoint_x, int startpoint_y, int Width, int Height, boolean fill, Color color) {
            this.startpoint_x = startpoint_x;
            this.startpoint_y = startpoint_y;
            this.Width = Width;
            this.Height = Height;
            this.fill = fill;
            this.color = color;

        }

        public abstract void draw(Graphics g);

    }

    public class Rectangle extends Shape {

        public Rectangle(
                int startpoint_x,
                int startpoint_y,
                int Width,
                int Height,
                boolean fill,
                Color color) {

            super(startpoint_x,
                    startpoint_y,
                    Width,
                    Height,
                    fill,
                    color);
        }

        public boolean getSolid() {
            return fill;
        }

        public Color getColor() {
            return color;
        }

        public void draw(Graphics g) {
            g.setColor(getColor());
            if (getSolid()) {
                g.fillRect(
                        Math.min(startpoint_x, startpoint_x + Width),
                        Math.min(startpoint_y, startpoint_y + Height),
                        Width,
                        Height
                );
            } else {
                g.drawRect(
                        Math.min(startpoint_x, startpoint_x + Width),
                        Math.min(startpoint_y, startpoint_y + Height),
                        Width,
                        Height
                );
            }
        }
    }

    public class Line extends Shape {
        public Line(int startpoint_x,
                    int startpoint_y,
                    int Width,
                    int Height,
                    boolean fill,
                    Color color) {
            super(startpoint_x,
                    startpoint_y,
                    Width,
                    Height,
                    fill,
                    color);
        }

        public Color getColor() {
            return color;
        }

        public void draw(Graphics g) {
            g.setColor(getColor());
            g.drawLine(startpoint_x,
                    startpoint_y,
                    Width,
                    Height);
        }
    }

    public class Oval extends Shape {

        public Oval(int startpoint_x,
                    int startpoint_y,
                    int endpoint_x,
                    int endpoint_y,
                    boolean fill,
                    Color color) {
            super(startpoint_x,
                    startpoint_y,
                    endpoint_x,
                    endpoint_y,
                    fill,
                    color);
        }

        public boolean getSolid() {
            return fill;
        }

        public Color getColor() {
            return color;
        }

        public void draw(Graphics g) {
            g.setColor(getColor());
            if (getSolid()) {
                g.fillOval(
                        Math.min(startpoint_x, startpoint_x + Width),
                        Math.min(startpoint_y, startpoint_y + Height),
                        Width,
                        Height
                );
            } else {
                g.drawOval(
                        Math.min(startpoint_x, startpoint_x + Width),
                        Math.min(startpoint_y, startpoint_y + Height),
                        Width,
                        Height
                );
            }
        }
    }
}