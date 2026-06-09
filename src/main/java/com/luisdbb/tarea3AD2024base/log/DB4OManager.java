package com.luisdbb.tarea3AD2024base.log;

import org.springframework.stereotype.Component;
import com.db4o.ObjectContainer;

import jakarta.annotation.PreDestroy;

import com.db4o.Db4oEmbedded;

@Component
public class DB4OManager {

	private static final String DB_PATH = "ficheros/log.db4o";
	private ObjectContainer db;

	public ObjectContainer open() {
		return Db4oEmbedded.openFile(DB_PATH);
	}

	public ObjectContainer getDb() {
		return db;
	}

	@PreDestroy
	public void close() {
		db.close();
	}
}
