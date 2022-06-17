package monRdv.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import monRdv.model.Specialite;

public interface ISpecialiteDao extends JpaRepository<Specialite, Long> {
	Specialite findByIdWithPraticiens(Long id);
}
