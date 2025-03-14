package com.banana.bananawhatsapp.persistencia;

import com.banana.bananawhatsapp.modelos.Mensaje;
import com.banana.bananawhatsapp.modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public interface IMensajeJPARepository extends JpaRepository<Mensaje, Long> {
    //public Mensaje save(Mensaje mensaje);

    //public List<Mensaje> getById(Usuario usuario);

    //public boolean borrarEntre(Usuario remitente, Usuario destinatario) throws Exception;

    //public boolean borrarTodos(Usuario usuario) throws SQLException;

}
