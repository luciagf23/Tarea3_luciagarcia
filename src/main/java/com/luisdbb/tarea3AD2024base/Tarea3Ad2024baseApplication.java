package com.luisdbb.tarea3AD2024base;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import com.luisdbb.tarea3AD2024base.view.FxmlView;
import com.luisdbb.tarea3AD2024base.config.SpringFXMLLoader;
import com.luisdbb.tarea3AD2024base.config.StageManager;

import javafx.application.Application;
import javafx.stage.Stage;

@SpringBootApplication(scanBasePackages = "com.luisdbb.tarea3AD2024base")

public class Tarea3Ad2024baseApplication extends Application {

	protected ConfigurableApplicationContext springContext;
	protected StageManager stageManager;
	

	@Override
	public void init() throws Exception {
		 springContext = new SpringApplicationBuilder(Tarea3Ad2024baseApplication.class).run();
    }
	

	public static void main(final String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
	    SpringFXMLLoader springFXMLLoader = springContext.getBean(SpringFXMLLoader.class);

	    stageManager = new StageManager(springFXMLLoader, primaryStage);

	    // Mostrar la primera escena
	    stageManager.switchScene(FxmlView.LOGIN);

	}

	 @Override
	    public void stop() throws Exception {
	        springContext.close();
	    }

	
}
