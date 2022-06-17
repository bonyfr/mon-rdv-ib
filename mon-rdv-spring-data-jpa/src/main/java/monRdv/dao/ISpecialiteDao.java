package monRdv.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import monRdv.model.Specialite;

public interface ISpecialiteDao extends JpaRepository<Specialite, Long> {
	@Query("select distinct s from Specialite s left join fetch s.praticiens where s.id = :id")
	Specialite findByIdWithPraticiens(@Param("id") Long id);
}
