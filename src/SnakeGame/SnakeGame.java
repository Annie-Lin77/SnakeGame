package SnakeGame;
import java.util.Random;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SnakeGame extends Application {
	
	//constants
	private static final int GAMEWIDTH = 600;
	private static final int GAMEHEIGHT = 500;
	private static final int RADIUS = 5;
	
	//pane
	private Pane root;
	
	//text componet to hold scene
	private Text score;
	
	//circle that act as food
	private Circle food;
	
	//rand obj
	Random rand = new Random();
	
	//snake
	private Snake snake;

	//this method creates new snake for game and add it to root component
	private void newSnake() {
		this.snake = new Snake(GAMEWIDTH / 2, GAMEHEIGHT / 2, RADIUS);
		root.getChildren().add(this.snake);	
		}
	
	//this method will create random food obj
	private void newFood() {
		this.food = new Circle(rand.nextInt(GAMEWIDTH), rand.nextInt(GAMEHEIGHT), RADIUS);
		this.food.setFill(Color.RED);
		this.root.getChildren().add(this.food);
	}
	
	//checks to see if snake touched food
	private boolean hit() {
		return this.food.intersects(this.snake.getBoundsInLocal());
	}
	
	//checks to seee if snake collided with iteslf
	private boolean gameOver() {
		return this.snake.eatSelf();
	}
	
	//primary functon that see movement of snake 
	//checks to see if snake has hit food
	//checks for gameOver();
	private void move() {
		Platform.runLater(() -> {
			//move the snake
			this.snake.step();
			adjustLocation();
			if(hit()) {
				this.snake.eat(this.food);
				this.score.setText("" + this.snake.getLength());
				newFood();
			}
			else if(gameOver()) {
				this.root.getChildren().clear();
				this.root.getChildren().add(this.score);
				this.score.setText("RESET" + this.snake.getLength());
				newSnake();
				newFood();
			}
		});
	}

	//redirects snake if it goes out of bounds
	private void adjustLocation() {
		if(this.snake.getCenterX() < 0) {
			this.snake.setCenterX(GAMEWIDTH);
		}else if(this.snake.getCenterX() > GAMEWIDTH) {
			this.snake.setCenterX(0);
		}
		
		if(this.snake.getCenterY() < 0) {
			this.snake.setCenterY(GAMEHEIGHT);
		}else if(this.snake.getCenterY() > GAMEHEIGHT) {
			this.snake.setCenterY(0);
		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.root = new Pane();
		this.root.setPrefSize(GAMEWIDTH, GAMEHEIGHT);
		
		this.score = new Text(0, 32, "0");
		this.root.getChildren().add(this.score);
		
		newFood();
		newSnake();
		
		//thread
		Runnable r = () -> {
			try {
				for(;;) {
					move();
					Thread.sleep(150);
				}
			}catch(InterruptedException e) {
				
			}
		};
		
		Scene scene = new Scene(this.root);
		
		//event filter- event handler object
		scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			KeyCode code = event.getCode();
			
			if(code == KeyCode.UP) {
				this.snake.setCurrDirection(Direction.UP);
			}else if(code == KeyCode.DOWN) {
				this.snake.setCurrDirection(Direction.DOWN);
			}else if(code == KeyCode.LEFT) {
				this.snake.setCurrDirection(Direction.LEFT);
			}else if(code == KeyCode.RIGHT) {
				this.snake.setCurrDirection(Direction.RIGHT);
			}
		});
		
		primaryStage.setTitle("Snake Game");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
		
		Thread th = new Thread(r);
		th.setDaemon(true);
		th.start();
	}
	
	public static void main(String[] args) {
		Application.launch(args);
	}

}

