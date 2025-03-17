package com.banana.bananawhatsapp.persistencia;

import com.banana.bananawhatsapp.modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUsuarioJPARepository extends JpaRepository<Usuario, Long> {
}