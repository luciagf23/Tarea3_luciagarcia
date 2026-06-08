package com.luisdbb.tarea3AD2024base.log;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.db4o.ObjectContainer;

@Repository
public class LogOperacionRepository {

    @Autowired
    private DB4OManager manager;

    public void guardar(LogOperacion log) {
        ObjectContainer db = manager.open();
        try {
            db.store(log);
        } finally {
            db.close();
        }
    }

    public List<LogOperacion> buscarPorEjemplo(LogOperacion ejemplo) {
        ObjectContainer db = manager.open();
        try {
            return db.queryByExample(ejemplo);
        } finally {
            db.close();
        }
    }
}