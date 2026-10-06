package tech.buildrun.api.repository;
import tech.buildrun.api.model.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
public interface PerfilRepository extends JpaRepository<Perfil, Long> {
    Page<Perfil> findByTelefoneContaining(String telefone, Pageable pageable);
}
