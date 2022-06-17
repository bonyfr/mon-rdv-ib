package monRdv.dao;

import monRdv.model.Specialite;

public interface ISpecialiteDao extends IDao<Specialite, Long> {
	Specialite findByIdWithPraticiens(Long id);
}
