package monRdv.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import monRdv.model.Lieu;

public interface ILieuDao extends JpaRepository<Lieu, Long> {
	Optional<Lieu> findById(Long id);
}
