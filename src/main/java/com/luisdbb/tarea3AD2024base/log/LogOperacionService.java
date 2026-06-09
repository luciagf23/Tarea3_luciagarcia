package com.luisdbb.tarea3AD2024base.log;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogOperacionService {

    @Autowired
    private LogOperacionRepository repo;

    public void registrar(String usuario, TipoOperacion tipo, String resumen) {
        LogOperacion log = new LogOperacion();
        log.setId(System.currentTimeMillis()); 
        log.setFechaHora(LocalDateTime.now().toString());
        log.setUsuario(usuario);
        log.setTipoOperacion(tipo.name());
        log.setResumen(resumen);

        repo.guardar(log);
    }
    
    public List<LogOperacion> buscarTodos() {
        return repo.buscarPorEjemplo(new LogOperacion());
    }

}

