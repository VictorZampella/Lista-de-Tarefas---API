package tech.buildrun.api.repository;
import tech.buildrun.api.model.Tarefa;
import tech.buildrun.api.model.StatusTarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
    Page<Tarefa> findByTituloContainingIgnoreCase(String titulo, Pageable pageable);
    Page<Tarefa> findByStatus(StatusTarefa status, Pageable pageable);
}
