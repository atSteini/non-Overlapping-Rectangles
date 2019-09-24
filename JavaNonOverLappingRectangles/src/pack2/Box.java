package pack2;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

public class Box{
	int x, y;
	int width, height;
	int index;
	int fontHeight;
	
	Rectangle rect;
	
	boolean isHovered = false, isLeftClicked = false, isRightClicked = false, isFilled = false, isOverLap = false;
	boolean drawNumber = true;
	
	//Static	
	Color still = new Color(190, 190, 190);
	Color hover = new Color(150, 150, 220);
	Color left = new Color(255, 100, 10);
	Color right = new Color(10, 255, 255);
	Color numberLeft = new Color(180, 70, 0);
	Color numberRight = new Color(0, 180, 180);
	
	Color currentColor = still;
	Color currentHover = hover;
	Color currentNumber = numberLeft;
	
	public void draw(Graphics2D g2d) {
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2d.setColor(currentColor);
		g2d.fill(rect);
		
		g2d.setColor(currentNumber);
		g2d.setFont(new Font("Futura", Font.PLAIN, fontHeight));
		drawMidString(g2d, "" + index, x + width/2, y + height/2);
	}

	private void drawMidString(Graphics2D g2d, String string, int x, int y) {
		g2d.drawString(string, x - g2d.getFontMetrics().stringWidth(string)/2, y + g2d.getFontMetrics().getHeight()/3 - 1);
	}

	public Box(int x, int y, int width, int height, int index) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.index = index;
		
		fontHeight = height*2/3;
		
		rect = new Rectangle(this.x, this.y, this.width, this.height);
	}
	
	public void update() {
		currentHover = isLeftClicked ? left : isRightClicked ? right : hover;
		currentColor = isHovered ? currentHover : isLeftClicked ? left : isRightClicked ? right : isOverLap ? Color.RED : still;
		currentNumber = isLeftClicked ? numberLeft : isRightClicked ? numberRight : new Color(111, 111, 111);
		
		isFilled = isLeftClicked || isRightClicked;
	}
	
	public int getFontHeight() {
		return fontHeight;
	}

	public void setFontHeight(int fontHeight) {
		this.fontHeight = fontHeight;
	}

	public boolean isFilled() {
		return isFilled;
	}

	public void setFilled(boolean isFilled) {
		this.isFilled = isFilled;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public Rectangle getRect() {
		return rect;
	}

	public void setRect(Rectangle rect) {
		this.rect = rect;
	}

	public boolean isHovered() {
		return isHovered;
	}
	
	public void setHovered(boolean isHovered) {
		this.isHovered = isHovered;
	}
	
	public boolean isOverLap() {
		return isOverLap;
	}
	
	public void setOverLap(boolean isOverLap) {
		this.isOverLap = isOverLap;
	}
	
	public boolean isLeftClicked() {
		return isLeftClicked;
	}
	
	public void setLeftClicked(boolean isLeftClicked) {
		this.isLeftClicked = isLeftClicked;
	}
	
	public boolean isRightClicked() {
		return isRightClicked;
	}
	
	public void setRightClicked(boolean isRightClicked) {
		this.isRightClicked = isRightClicked;
	}
	
	public Color getCurrentColor() {
		return currentColor;
	}
	
	public void setCurrentColor(Color currentColor) {
		this.currentColor = currentColor;
	}
}
