package es.caib.translatorib.persistence.repository;

import es.caib.translatorib.service.model.ConfiguracionGlobal;
import es.caib.translatorib.service.model.filtro.ConfiguracionGlobalFiltro;

import java.util.List;

/**
 * La interface ConfiguracionGlobalDao.
 */
public interface ConfiguracionGlobalRepository {


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
