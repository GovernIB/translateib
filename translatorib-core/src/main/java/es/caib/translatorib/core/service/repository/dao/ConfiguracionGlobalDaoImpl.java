package es.caib.translatorib.core.service.repository.dao;

import es.caib.translatorib.core.api.model.ConfiguracionGlobal;
import es.caib.translatorib.core.api.model.filtro.ConfiguracionGlobalFiltro;
import es.caib.translatorib.core.service.component.ConfiguracionComponent;
import es.caib.translatorib.core.service.repository.model.JConfiguracionGlobal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Repository("configuracionGlobalDao")
public class ConfiguracionGlobalDaoImpl implements ConfiguracionGlobalDao {

	/** EntityManager. */
	@PersistenceContext
	private EntityManager entityManager;

	/** Configuracion. */
	@Autowired
	private ConfiguracionComponent config;

	public ConfiguracionGlobalDaoImpl() {
		super();
	}

	@Override
	public ConfiguracionGlobal getConfiguracionGlobalByCodigo(Long codigo) {
		JConfiguracionGlobal jConfiguracionGlobal = entityManager.find(JConfiguracionGlobal.class, codigo);
		return jConfiguracionGlobal.toModel();
	}

	@Override
	public ConfiguracionGlobal getConfiguracionGlobalByPropiedad(String propiedad) {
		Query query = entityManager.createQuery("SELECT J FROM JConfiguracionGlobal J WHERE J.propiedad = :propiedad", JConfiguracionGlobal.class);
		query.setParameter("propiedad", propiedad);
		List<JConfiguracionGlobal> resultado = query.getResultList();
		ConfiguracionGlobal configuracionGlobal = null;
		if (resultado != null && !resultado.isEmpty()) {
			configuracionGlobal = resultado.get(0).toModel();
		}
		return configuracionGlobal;
	}

	@Override
	public void guardarConfiguracionGlobal(ConfiguracionGlobal ConfiguracionGlobal) {
		JConfiguracionGlobal jConfiguracionGlobal = JConfiguracionGlobal.fromModel(ConfiguracionGlobal);
		entityManager.persist(jConfiguracionGlobal);
	}

	@Override
	public void updateConfiguracionGlobal(ConfiguracionGlobal ConfiguracionGlobal) {
		JConfiguracionGlobal jConfiguracionGlobal = entityManager.find(JConfiguracionGlobal.class, ConfiguracionGlobal.getCodigo());
		jConfiguracionGlobal.merge(ConfiguracionGlobal);
		entityManager.persist(jConfiguracionGlobal);
	}

	@Override
	public List<ConfiguracionGlobal> findPagedByFiltro(ConfiguracionGlobalFiltro filtro) {
		Query query = getQuery(false, filtro);

		List<Object[]> jConfiguracionesGlobal = query.getResultList();
		List<ConfiguracionGlobal> configuracionGlobal = new ArrayList<>();
		if (jConfiguracionesGlobal != null) {
			for (Object[] jConfiguracionGlobal : jConfiguracionesGlobal) {
				ConfiguracionGlobal configuracionGlobalGridDTO = new ConfiguracionGlobal();
				configuracionGlobalGridDTO.setCodigo((Long) jConfiguracionGlobal[0]);
				configuracionGlobalGridDTO.setPropiedad((String) jConfiguracionGlobal[1]);
				configuracionGlobalGridDTO.setValor((String) jConfiguracionGlobal[2]);
				configuracionGlobalGridDTO.setDescripcion((String) jConfiguracionGlobal[3]);
				configuracionGlobalGridDTO.setNoModificable((Boolean) jConfiguracionGlobal[4]);

				configuracionGlobal.add(configuracionGlobalGridDTO);
			}
		}
		return configuracionGlobal;
	}

	@Override
	public int countByFiltro(ConfiguracionGlobalFiltro filtro) {
		return ((Long) getQuery(true, filtro).getSingleResult()).intValue();
	}

	private Query getQuery(boolean isTotal, ConfiguracionGlobalFiltro filtro) {

		StringBuilder sql;
		if (isTotal) {
			sql = new StringBuilder("SELECT count(j) FROM JConfiguracionGlobal j where 1 = 1 ");
		} else {
			sql = new StringBuilder(
					"SELECT j.codigo, j.propiedad, j.valor, j.descripcion, j.noModificable FROM JConfiguracionGlobal j where 1 = 1 ");
		}
		if (filtro.isRellenoTexto()) {
			sql.append(" and ( cast(j.codigo as string) like :filtro OR LOWER(j.propiedad) LIKE :filtro "
					+ " OR LOWER(j.valor) LIKE :filtro OR LOWER(j.descripcion) LIKE :filtro "
					+ " OR LOWER(cast(j.noModificable as string)) LIKE :filtro )");
		}
		if (filtro.isRellenoPropiedad()) {
			sql.append(" and  j.propiedad like :propiedad ");
		}
		if (filtro.getOrderBy() != null) {
			sql.append(" order by ").append(getOrden(filtro.getOrderBy()));
			sql.append(filtro.isAscendente() ? " asc " : " desc ");
		}
		Query query = entityManager.createQuery(sql.toString());
		if (filtro.isRellenoTexto()) {
			query.setParameter("filtro", "%" + filtro.getTexto() + "%");
		}
		if (filtro.isRellenoPropiedad()) {
			query.setParameter("propiedad", filtro.getPropiedad());
		}
		return query;
	}

	private String getOrden(String order) {
		return "j." + order;
	}
}
