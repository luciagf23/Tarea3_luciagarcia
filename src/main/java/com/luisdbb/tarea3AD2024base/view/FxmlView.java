package com.luisdbb.tarea3AD2024base.view;

import java.util.ResourceBundle;

public enum FxmlView {
	USER {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("user.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/User.fxml";
		}
	},
	LOGIN {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("login.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/Login.fxml";
		}
	},

	ESPECTACULOS {
		@Override
		public String getTitle() {
			return "Gestión de Espectáculos";
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/espectaculo.fxml";
		}
	},

	DETALLE_ESPECTACULO {
		@Override
		public String getTitle() {
			return "Detalle del espectáculo";
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/detalle_espectaculo.fxml";
		}
	},

	NUMEROS {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("numeros.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/numero.fxml";
		}
	},

	INVITADO {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("espectaculos.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/invitado.fxml";
		}

	},
	ARTISTA {

		@Override
		public String getTitle() {
			return getStringFromResourceBundle("artistas.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/fichaArtista.fxml";
		}

	},

	FICHAARTISTA {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("artista_detalle.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/fichaArtista.fxml";
		}

	};

	public abstract String getTitle();

	{

	}

	public abstract String getFxmlFile();

	String getStringFromResourceBundle(String key) {
		return ResourceBundle.getBundle("Bundle").getString(key);
	}
}
