package pack2;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

public class Panel2 extends JPanel implements MouseMotionListener, MouseListener{

	boolean init = false;
	int width, height;
	int mouseX, mouseY;

	Random rng = new Random();

	Box temp, next;
	int iterationCount = 0;
	boolean isOverLap = false, isUnderUI = false;
	
	boolean finished = true;
	boolean clear = false;
	
	Font font = new Font("Futura", Font.PLAIN, 25);
	
	public Panel2() {
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	/** Ist das eine akzeptable Lösung ? Ich weiß dass sie nicht gut ist aber was soll man machen...
	 */
	public void clear() {
		clear = true;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(
		        RenderingHints.KEY_TEXT_ANTIALIASING,
		        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		if(!init) {
			initialize();
			repaint();
		}
		width = getWidth();
		height = getHeight();
		
		setButton();		//Habe vergessen das Layout zu ändern, dann war das Frame kaputt und ich habe es so gemacht.
		
		g2d.clearRect(0, 0, width, height);
		
		g2d.drawLine(0, 0, width, 0);

		if(var.correctInput && !var.exOnce) {
			var.box = new Box[0];
			
			iterationCount = 0;
			clear = false;
			
			finished = true; 
			isUnderUI = false; 
			isOverLap = false; 

			while(var.box.length < var.inputCount) {
				temp = new Box(rng.nextInt(width - var.inputSize), rng.nextInt(height - var.inputSize)+1, var.inputSize, var.inputSize, var.box.length+1);
				iterationCount++;
				
				for(int j = 0; j < var.box.length; j++) {
					next = var.box[j];
					
					isOverLap = temp.getRect().intersects(next.getRect());
					isUnderUI = temp.getRect().intersects(new Rectangle(0, height - 65, 300, 65));
					if(!var.excludeUI) {
						isUnderUI = false;
					}
					
					if(isOverLap || isUnderUI) break;
				}
				
				if(!isOverLap && !isUnderUI) {
					var.box = addToArray(var.box, temp);
				}

				if(iterationCount >= var.maxIterations) {
					finished = false;
					
					break;
				}else {
					finished = true;
				}
			}

			var.exOnce = true;
		}
		
		if(!clear) {
			if(!finished) {
				g2d.setColor(Color.RED);
	
				drawMidString(g2d, "Couldn't find perfect Alignment", width/2, height/2 - 65/2, font);
			}else {
				var.actualFilled = 0;
				for(int i = 0; var.correctInput && i < var.box.length; i++) {
					var.box[i].update();
					var.box[i].draw(g2d);
					
					if(var.box[i].isFilled()) {
						var.actualFilled ++;
					}
				}
			}
		}
		
		g2d.setColor(new Color(50, 150, 255, 180));
		g2d.fillRect(0, height - 65, 150, 65);
			
		g2d.setColor(new Color(255, 150, 0, 180));
		g2d.fillRect(150, height - 65, 150, 65);
			
		g2d.setColor(Color.BLACK);
			
		drawMidString(g2d, "Filled", 75, height - 65*3/4, new Font("Futura", Font.PLAIN, 18));
		drawMidString(g2d, Integer.toString(var.actualFilled), 75, height - 65/3, font);
			
		drawMidString(g2d, "Iterations", 225, height - 65*3/4, new Font("Futura", Font.PLAIN, 18));
		drawMidString(g2d, Integer.toString(iterationCount), 225, height - 65/3, font);
	}

	/** Initialisiert wichtige Variablen bevor dem ersten Durchlauf.
	 */
	private void initialize() {
		width = getWidth();
		height = getHeight();
		
		init = true;
	}
	
	private void setButton() {
		var.buttonX = width - 90 - 8;
		Frame2.btnRun.setBounds(var.buttonX, 8, 90, 33);
		Frame2.topPanel.add(Frame2.btnRun);
	}
	
	/** Fügt einem Array ein Element hinzu.
	 * @param a das Array.
	 * @param b das Element.
	 * @return
	 */
	private Box[] addToArray(Box[] a, Box b) {
		a = extendArray(a, 1);
		a[a.length - 1] = b;
		
		return a;
	}
	
	/**Verlängert ein Array
	 * @param array das Array
	 * @param toExtend um wie viel muss es extended werden
	 * @return neues Array
	 */
	public static Box[] extendArray(Box[] array, int toExtend){
		Box[] newArray;
		try {
			newArray = new Box[array.length + toExtend];
		}catch(Exception e) {
			newArray = new Box[toExtend];
			array = new Box[toExtend];
		}

		for (int i = 0; i < array.length; i++)
		{
			newArray[i] = array[i];
		}

		return newArray;
	}
	
	/** Überprüft ob ein Punkt ein Rechteck schneidet.
	 * @param x X-Koordinate.
	 * @param y Y-Koordinate.
	 * @param r das Rechteck.
	 * @return
	 */
	private boolean pointInRect(int x, int y, Rectangle r) {
		return new Rectangle(x, y, 1, 1).intersects(r);
	}
	
	/** Zeichnet einen String mit Mittelpunkt als Anker.
	 * @param g2d die Grafik.
	 * @param s der String.
	 * @param x X-Koordinate.
	 * @param y Y-Koordinate.
	 */
	private void drawMidString(Graphics2D g2d, String s, int x, int y, Font font) {
		g2d.setFont(font);
		g2d.drawString(s, x - g2d.getFontMetrics().stringWidth(s)/2, y + g2d.getFontMetrics().getHeight()/3 - 2);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		
		for(int i = 0; var.correctInput && i < var.inputCount; i++) {
			if(pointInRect(mouseX, mouseY, var.box[i].getRect())) {
				if(e.getButton() == MouseEvent.BUTTON1) {
					if(var.box[i].isRightClicked) {
						var.box[i].setLeftClicked(false);
						var.box[i].setRightClicked(false);
					}else {
						var.box[i].setLeftClicked(!var.box[i].isLeftClicked);
					}

				}else if(e.getButton() == MouseEvent.BUTTON3) {
					if(var.box[i].isLeftClicked) {
						var.box[i].setLeftClicked(false);
						var.box[i].setRightClicked(false);
					}else {
						var.box[i].setRightClicked(!var.box[i].isRightClicked);
					}
				}
			}
		}
		
		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		
		for(int i = 0; var.correctInput && var.exOnce && i < var.box.length; i++) {
			if(pointInRect(mouseX, mouseY, var.box[i].getRect())) {
				var.box[i].setHovered(true);
			}else {
				var.box[i].setHovered(false);
			}
		}
		
		repaint();
	}
}
