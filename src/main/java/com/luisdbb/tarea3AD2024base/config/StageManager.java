package com.luisdbb.tarea3AD2024base.config;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.luisdbb.tarea3AD2024base.controller.ArtistaController;
import com.luisdbb.tarea3AD2024base.controller.FichaArtistaController;
import com.luisdbb.tarea3AD2024base.controller.LogController;
import com.luisdbb.tarea3AD2024base.controller.EspectaculoController;
import com.luisdbb.tarea3AD2024base.controller.LoginController;
import com.luisdbb.tarea3AD2024base.controller.NumeroController;
import com.luisdbb.tarea3AD2024base.controller.UserController;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Manages switching Scenes on the Primary Stage
 */

@Component
public class StageManager {

	private static final Logger LOG = getLogger(StageManager.class);
	private Stage primaryStage;
	private final SpringFXMLLoader springFXMLLoader;

	public void setPrimaryStage(Stage stage) {
		this.primaryStage = stage;
	}

	@Autowired
	public StageManager(SpringFXMLLoader springFXMLLoader) {
		this.springFXMLLoader = springFXMLLoader;
	}

	public void switchScene(final FxmlView view) {
		Parent viewRootNodeHierarchy = loadViewNodeHierarchy(view.getFxmlFile());
		show(viewRootNodeHierarchy, view.getTitle());
	}

	private void show(final Parent rootnode, String title) {
		Scene scene = prepareScene(rootnode);
		// scene.getStylesheets().add("/styles/Styles.css");

		// primaryStage.initStyle(StageStyle.TRANSPARENT);
		primaryStage.setTitle(title);
		primaryStage.setScene(scene);
		primaryStage.sizeToScene();
		primaryStage.centerOnScreen();

		try {
			primaryStage.show();
		} catch (Exception exception) {
			logAndExit("Unable to show scene for title" + title, exception);
		}
	}

	private Scene prepareScene(Parent rootnode) {
		Scene scene = primaryStage.getScene();

		if (scene == null) {
			scene = new Scene(rootnode);
		}
		scene.setRoot(rootnode);
		return scene;
	}

	/**
	 * Loads the object hierarchy from a FXML document and returns to root node of
	 * that hierarchy.
	 *
	 * @return Parent root node of the FXML document hierarchy
	 */

	/*
	 * private Parent loadViewNodeHierarchy(String fxmlFilePath) { Parent rootNode =
	 * null; try { rootNode = springFXMLLoader.load(fxmlFilePath);
	 * Objects.requireNonNull(rootNode, "A Root FXML node must not be null"); }
	 * catch (Exception exception) { logAndExit("Unable to load FXML view" +
	 * fxmlFilePath, exception); } return rootNode; }
	 */

	private Parent loadViewNodeHierarchy(String fxmlFilePath) {
		try {
			System.out.println(">>> loadViewNodeHierarchy: " + fxmlFilePath);
			FXMLLoader loader = new FXMLLoader();
			loader.setControllerFactory(springFXMLLoader.getContext()::getBean);
			loader.setResources(ResourceBundle.getBundle("Bundle"));
			var url = getClass().getResource(fxmlFilePath);
			System.out.println(">>> URL: " + url);
			loader.setLocation(url);
			Parent root = loader.load();

			Object controller = loader.getController();

			if (controller instanceof LoginController c) {
				c.setStageManager(this);
			}
			if (controller instanceof EspectaculoController c) {
				c.setStageManager(this);
			}
			if (controller instanceof UserController c) {
				c.setStageManager(this);
			}
			if (controller instanceof NumeroController c) {
				c.setStageManager(this);
			}
			if (controller instanceof ArtistaController c) {
				c.setStageManager(this);
			}
			if (controller instanceof FichaArtistaController c) {
				c.setStageManager(this);
			}
			if (controller instanceof LogController c) {
				c.setStageManager(this);
			}

			return root;

		} catch (Exception e) {
			e.printStackTrace();
			logAndExit("Unable to load FXML view " + fxmlFilePath, e);
			return null;
		}
	}

	private void logAndExit(String errorMsg, Exception exception) {
		LOG.error(errorMsg, exception, exception.getCause());
		Platform.exit();
	}

}
