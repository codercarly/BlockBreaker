import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class HW22_13 extends Application {
	
	public static boolean goLeft = false;
	public static boolean goRight = false;
	public static boolean start = false;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("BlockBreaker.fxml"));
		
		Scene scene = new Scene(root);
	
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				switch(event.getCode()) {
				case LEFT: goLeft = true;
					break;
				case RIGHT: goRight = true;
					break;
				case SPACE: start = true;
					break;
				default:
					break;
				}	
			}	
		});
		
		scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				switch(event.getCode()) {
				case LEFT: goLeft = false;
					break;
				case RIGHT: goRight = false;
					break;
				default:
					break;
				}
			}
		});
		
		primaryStage.setTitle("Block Breaker");
		primaryStage.setScene(scene);
		primaryStage.show();	
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
