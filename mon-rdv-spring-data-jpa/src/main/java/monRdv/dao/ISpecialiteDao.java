package monRdv.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import monRdv.model.Specialite;

public interface ISpecialiteDao extends JpaRepository<Specialite, Long> {
	@Query("select s from Specialite s left join fetch s.praticiens where s.id = :id")
	Optional<Specialite> findByIdWithPraticiens(@Param("id") Long id);
	Optional<Specialite> findById(Long id);
	
}
