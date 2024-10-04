package es.caib.translatorib.core.service.repository.dao;

import es.caib.translatorib.core.api.model.ConfiguracionGlobal;
import es.caib.translatorib.core.api.model.filtro.ConfiguracionGlobalFiltro;

import java.util.List;

/**
 * La interface ConfiguracionGlobalDao.
 */
public interface ConfiguracionGlobalDao {


	/**
	 * Recupera ConfiguracionGlobal por Codigo.
	 *
	 * @param codigo codigo
	 * @return ConfiguracionGlobal
	 */
	ConfiguracionGlobal getConfiguracionGlobalByCodigo(Long codigo);

	/**
	 * Recupera ConfiguracionGlobal por propiedad.
	 * @param propiedad propiedad
	 * @return ConfiguracionGlobal
	 */
	ConfiguracionGlobal getConfiguracionGlobalByPropiedad(String propiedad);


	void guardarConfiguracionGlobal(ConfiguracionGlobal ConfiguracionGlobal);

	/**
	 * Actualizar ConfiguracionGlobal
	 * @param ConfiguracionGlobal ConfiguracionGlobal
	 */
	 void updateConfiguracionGlobal(ConfiguracionGlobal ConfiguracionGlobal);


	List<ConfiguracionGlobal> findPagedByFiltro(ConfiguracionGlobalFiltro filtro);

	int countByFiltro(ConfiguracionGlobalFiltro filtro);
}
