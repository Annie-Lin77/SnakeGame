package SnakeGame;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Snake extends Circle{
	
	private List<Circle> tails;
	private int length = 0;
	private Direction currDirection;
	private static final int step = 8;
	
	public Snake(double x, double y, double radius) {
		super(x, y, radius);
		this.tails = new ArrayList<>();
		this.currDirection = Direction.UP;
	}
	
	public void step() {
		for(int i = this.length - 1; i >= 0; i--) {
			//check for head of snake
			if(i==0) {
				this.tails.get(i).setCenterX(getCenterX());
				this.tails.get(i).setCenterY(getCenterY());
			}
			else {//checks for body of snake
				this.tails.get(i).setCenterX(this.tails.get(i-1).getCenterX());
				this.tails.get(i).setCenterY(this.tails.get(i-1).getCenterY());
			}
	}
		if(this.currDirection == Direction.UP) {
			this.setCenterY(this.getCenterY() - step);
		}else if(this.currDirection == Direction.DOWN) {
			this.setCenterY(this.getCenterY() + step);
		}else if(this.currDirection == Direction.LEFT) {
			this.setCenterX(this.getCenterX() - step);
		}else if(this.currDirection == Direction.RIGHT) {
			this.setCenterX(this.getCenterX() + step);
		}
}

	//method that gets the last circle in the list of circle
	private Circle endTail() {
		//snake is just a head
		if(this.length == 0) {
			return this;
		}
		return this.tails.get(this.length - 1);
	}
	
	//method to eat food (circle obj) and make snake longer (add circle to list)
	public void eat(Circle food) {
		Circle tail = endTail();
		food.setCenterX(tail.getCenterX());
		food.setCenterY(tail.getCenterY());
		food.setFill(Color.BLACK);
		tails.add(this.length++, food);
	}
	
	public int getLength() {
		return length;
	}
	
	public void setLength(int length) {
		this.length = length;
	}
	
	public Direction getCurrDirection() {
		return currDirection;
	}
	
	public void setCurrDirection(Direction currDirection) {
		this.currDirection = currDirection;
	}
	
	//checks is snake collided with itself
	public boolean eatSelf() {
		for(int i = 0; i < this.length; i++) {
			if(this.getCenterX() == this.tails.get(i).getCenterX() && this.getCenterY() == this.tails.get(i).getCenterY()){
				return true;
			}
		}
		return false;
	}
	
}
	