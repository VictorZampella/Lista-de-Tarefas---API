package tech.buildrun.api.repository;
import tech.buildrun.api.model.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    Page<Comentario> findByTextoContainingIgnoreCase(String texto, Pageable pageable);
}
