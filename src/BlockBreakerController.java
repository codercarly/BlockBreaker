import java.security.SecureRandom;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

public class BlockBreakerController {

	@FXML private Pane pane;
	@FXML private GridPane brickGrid;
	@FXML private Circle ball;
	@FXML private Rectangle paddle;
    @FXML private TextField livesTextField;
    @FXML private TextField pointsTextField;
    
    // Teal Bricks - 1st Row
    @FXML private Rectangle tb6;
    @FXML private Rectangle tb5;
    @FXML private Rectangle tb4;
    @FXML private Rectangle tb3;
    @FXML private Rectangle tb2;
    @FXML private Rectangle tb1;
    
    // Blue Bricks - 2nd Row
	@FXML private Rectangle bb1;
    @FXML private Rectangle bb2;
    @FXML private Rectangle bb3;
    @FXML private Rectangle bb4;
    @FXML private Rectangle bb5;
    @FXML private Rectangle bb6;
    
    // Purple Bricks - 3rd Row
    @FXML private Rectangle pb1;
    @FXML private Rectangle pb2;
    @FXML private Rectangle pb3;
    @FXML private Rectangle pb4;
    @FXML private Rectangle pb5;
    @FXML private Rectangle pb6;

    // Hot Pink Bricks - 4th Row
    @FXML private Rectangle hb1;
    @FXML private Rectangle hb2;
    @FXML private Rectangle hb3;
    @FXML private Rectangle hb4;
    @FXML private Rectangle hb5;
    @FXML private Rectangle hb6;
    
    // Red Bricks - 5th Row
    @FXML private Rectangle rb2;
    @FXML private Rectangle rb5;
    @FXML private Rectangle rb3;
    @FXML private Rectangle rb4;
    @FXML private Rectangle rb6;
    @FXML private Rectangle rb1;

	// Game variables
    Integer lives = 3;
    Integer points = 0;
    
	// Random for ball bouncing
	SecureRandom random = new SecureRandom();
	int dx = 1 + random.nextInt(5);
	int dy = 1 + random.nextInt(5);

	// Initialize - Game Launch
	public void initialize() {		
		// Define a TimeLine animation for the ball and paddle
    	Timeline timelineAnimation = new Timeline();
    	
    	// The ball
    	KeyFrame ballKeyFrame = new KeyFrame(Duration.millis(10), new EventHandler<ActionEvent>() {
			// Move the ball by the dx and dy amounts
    		@Override
    		public void handle(final ActionEvent e) {
    			ball.setLayoutX(ball.getLayoutX() + dx);
    			ball.setLayoutY(ball.getLayoutY() + dy);			
    			Bounds bounds = pane.getBoundsInLocal();
    			// If the ball hits the left or right edge, bounce away randomly
    			if (hitRightOrLeftEdge(bounds)) {
    				dx *= -1;
    			}
    			// If the ball hits the top or bottom, bounce away randomly
    			if (hitTopOrBottom(bounds)) {
    				dy *= -1;
    			}
    			// If the ball hits the paddle, bounce away
    			if (paddleCollision()) {
        			dy *= -1;
        		}
    			// If the ball hits a brick, bounce off the brick and remove the brick
    			if (tealBrickCollision()) {
					dy *= -1;
				}
    			
    			if (blueBrickCollision()) {
    				dy *= -1;
    			}
    			
    			if (purpleBrickCollision()) {
    				dy *= -1;
    			}
    			
    			if (hotPinkBrickCollision()) {
    				dy *= -1;
    			}
    			
    			if (redBrickCollision()) {
    				dy *= -1;
    			}
    		}
    	});
    	
    	// The paddle
    	KeyFrame paddleKeyFrame = new KeyFrame(Duration.millis(1), new EventHandler<ActionEvent>() {
    		// Use the left and right arrows to move the paddle
			@Override
			public void handle(ActionEvent event) {
				Bounds bounds = pane.getBoundsInLocal();
				// If the paddle hits the left edge
    			if (!paddleLeftEdge(bounds)) {
    				if (HW22_13.goLeft) {
        				paddle.setLayoutX(paddle.getLayoutX() - 3);
        			}
    			}
    			// If the paddle hits the right edge
        		if (!paddleRightEdge(bounds)) {
    				if (HW22_13.goRight) {
        				paddle.setLayoutX(paddle.getLayoutX() + 3);
        			}	
    			}
			}
    	});
    	
		// Indicate that the time line animation should run indefinitely
    	timelineAnimation.getKeyFrames().addAll(ballKeyFrame, paddleKeyFrame);
		timelineAnimation.setCycleCount(Timeline.INDEFINITE);
		timelineAnimation.play();
	}

	// Determines whether the ball hit the left or right of the window
	private boolean hitRightOrLeftEdge(Bounds bounds) {
		return (ball.getLayoutX() <= (bounds.getMinX() + ball.getRadius())) ||
				(ball.getLayoutX() >= (bounds.getMaxX() - ball.getRadius()));
	}

	// Determines whether the ball hit the top or bottom of the window
	private boolean hitTopOrBottom(Bounds bounds) {
		return (ball.getLayoutY() <= (bounds.getMinY() + ball.getRadius())) ||
				(ball.getLayoutY() >= (bounds.getMaxY() - ball.getRadius()));
	}
	
	// Determines whether the paddle hits the left or right of the window
	private boolean paddleRightEdge(Bounds bounds) {
		return (paddle.getLayoutX() >= (bounds.getMaxX() - paddle.getWidth()));
	}
	
	// Determines whether the paddle hits the left or right of the window
	private boolean paddleLeftEdge(Bounds bounds) {
		return (paddle.getLayoutX() <= (bounds.getMinX()));
	}
	
	// Check if the ball collides with the paddle
	private boolean paddleCollision() {
		Shape intersect = Shape.intersect(paddle, ball);
		if (intersect.getBoundsInLocal().getWidth() != -1) {
			return true;
		}
		return false;
	}
	
	// Add points to score
	private void score(int num) {
		points += num;
    	pointsTextField.setText(points.toString());;
		
	}
	
	// Check if the ball collides with a brick
	private boolean tealBrickCollision() {
		// Teal brick collisions
		Shape t1 = Shape.intersect(ball, tb1);
		Shape t2 = Shape.intersect(ball, tb2);
		Shape t3 = Shape.intersect(ball, tb3);
		Shape t4 = Shape.intersect(ball, tb4);
		Shape t5 = Shape.intersect(ball, tb5);
		Shape t6 = Shape.intersect(ball, tb6);
		
		// Remove brick from screen when it is hit
		if (t1.getBoundsInLocal().getWidth() != -1) { // Teal Bricks
			score(100); // Add points to the score
			brickGrid.getChildren().remove(tb1); // Get the brick from the parent and remove it.
			return true;
		} else if (t2.getBoundsInLocal().getWidth() != -1) {
			score(100);
			brickGrid.getChildren().remove(tb2);
			return true;
		} else if (t3.getBoundsInLocal().getWidth() != -1) {
			score(100);
			brickGrid.getChildren().remove(tb3);
			return true;
		} else if (t4.getBoundsInLocal().getWidth() != -1) {
			score(100);
			brickGrid.getChildren().remove(tb4);
			return true;
		} else if (t5.getBoundsInLocal().getWidth() != -1) {
			score(100);
			brickGrid.getChildren().remove(tb5);
			return true;
		} else if (t6.getBoundsInLocal().getWidth() != -1) {
			score(100);
			brickGrid.getChildren().remove(tb6);
			return true;
		} else {
			return false;
		}
	}
	private boolean blueBrickCollision() {
		// Blue brick collisions
		Shape b1 = Shape.intersect(ball, bb1);
		Shape b2 = Shape.intersect(ball, bb2);
		Shape b3 = Shape.intersect(ball, bb3);
		Shape b4 = Shape.intersect(ball, bb4);
		Shape b5 = Shape.intersect(ball, bb5);
		Shape b6 = Shape.intersect(ball, bb6);	
		
		if (b1.getBoundsInLocal().getWidth() != -1) { // Blue Bricks
			score(250); 
			brickGrid.getChildren().remove(bb1); 
			return true;
		} else if (b2.getBoundsInLocal().getWidth() != -1) {
			score(250);
			brickGrid.getChildren().remove(bb2);
			return true;
		} else if (b3.getBoundsInLocal().getWidth() != -1) {
			score(250);
			brickGrid.getChildren().remove(bb3);
			return true;
		} else if (b4.getBoundsInLocal().getWidth() != -1) {
			score(250);
			brickGrid.getChildren().remove(bb4);
			return true;
		} else if (b5.getBoundsInLocal().getWidth() != -1) {
			score(250);
			brickGrid.getChildren().remove(bb5);
			return true;
		} else if (b6.getBoundsInLocal().getWidth() != -1) {
			score(250);
			brickGrid.getChildren().remove(bb6);
			return true;
		} else {
			return false;
		}
	}
	
	private boolean purpleBrickCollision() {
		// Purple brick collisions
		Shape p1 = Shape.intersect(ball, pb1);
		Shape p2 = Shape.intersect(ball, pb2);
		Shape p3 = Shape.intersect(ball, pb3);
		Shape p4 = Shape.intersect(ball, pb4);
		Shape p5 = Shape.intersect(ball, pb5);
		Shape p6 = Shape.intersect(ball, pb6);
		
		if (p1.getBoundsInLocal().getWidth() != -1) { // Purple Bricks
			score(400);
			brickGrid.getChildren().remove(pb1);
			return true;
		} else if (p2.getBoundsInLocal().getWidth() != -1) {
			score(400);
			brickGrid.getChildren().remove(pb2);
			return true;
		} else if (p3.getBoundsInLocal().getWidth() != -1) {
			score(400);
			brickGrid.getChildren().remove(pb3);
			return true;
		} else if (p4.getBoundsInLocal().getWidth() != -1) {
			score(400);
			brickGrid.getChildren().remove(pb4);
			return true;
		} else if (p5.getBoundsInLocal().getWidth() != -1) {
			score(400);
			brickGrid.getChildren().remove(pb5);
			return true;
		} else if (p6.getBoundsInLocal().getWidth() != -1) {
			score(400);
			brickGrid.getChildren().remove(pb6);
			return true;
		} else {
			return false;
		}
	}
	
	private boolean hotPinkBrickCollision() {
		// Hot pink brick collisions
		Shape h1 = Shape.intersect(ball, hb1);
		Shape h2 = Shape.intersect(ball, hb2);
		Shape h3 = Shape.intersect(ball, hb3);
		Shape h4 = Shape.intersect(ball, hb4);
		Shape h5 = Shape.intersect(ball, hb5);
		Shape h6 = Shape.intersect(ball, hb6);
		
		if (h1.getBoundsInLocal().getWidth() != -1) {
			score(550);
			brickGrid.getChildren().remove(hb1);
			return true;
		} else if (h2.getBoundsInLocal().getWidth() != -1) {
			score(550);
			brickGrid.getChildren().remove(hb2);
			return true;
		} else if (h3.getBoundsInLocal().getWidth() != -1) {
			score(550);
			brickGrid.getChildren().remove(hb3);
			return true;
		} else if (h4.getBoundsInLocal().getWidth() != -1) {
			score(550);
			brickGrid.getChildren().remove(hb4);
			return true;
		} else if (h5.getBoundsInLocal().getWidth() != -1) {
			score(550);
			brickGrid.getChildren().remove(hb5);
			return true;
		} else if (h6.getBoundsInLocal().getWidth() != -1) {
			score(550);
			brickGrid.getChildren().remove(hb6);
			return true;
		} else {
			return false;
		}	
	}
	
	private boolean redBrickCollision() {
		// Red brick collisions
		Shape r1 = Shape.intersect(ball, rb1);
		Shape r2 = Shape.intersect(ball, rb2);
		Shape r3 = Shape.intersect(ball, rb3);
		Shape r4 = Shape.intersect(ball, rb4);
		Shape r5 = Shape.intersect(ball, rb5);
		Shape r6 = Shape.intersect(ball, rb6);
		
		if (r1.getBoundsInLocal().getWidth() != -1) {
			score(700);
			brickGrid.getChildren().remove(rb1);
			return true;
		} else if (r2.getBoundsInLocal().getWidth() != -1) {
			score(700);
			brickGrid.getChildren().remove(rb2);
			return true;
		} else if (r3.getBoundsInLocal().getWidth() != -1) {
			score(700);
			brickGrid.getChildren().remove(rb3);
			return true;
		} else if (r4.getBoundsInLocal().getWidth() != -1) {
			score(700);
			brickGrid.getChildren().remove(rb4);
			return true;
		} else if (r5.getBoundsInLocal().getWidth() != -1) {
			score(700);
			brickGrid.getChildren().remove(rb5);
			return true;
		} else if (r6.getBoundsInLocal().getWidth() != -1) {
			score(700);
			brickGrid.getChildren().remove(rb6);
			return true;
		} else {
			return false;
		}
	}
}
