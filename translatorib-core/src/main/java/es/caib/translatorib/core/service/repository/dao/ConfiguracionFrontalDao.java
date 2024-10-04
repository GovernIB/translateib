package es.caib.translatorib.core.service.repository.dao;

import es.caib.translatorib.core.api.model.ConfiguracionFrontal;

import java.util.List;

/**
 * La interface ConfiguracionFrontalDao.
 */
public interface ConfiguracionFrontalDao {

	/**
	 * Update configuracion frontal
	 * @param dto DTO
	 */
	public void updateConfiguracionFrontal(ConfiguracionFrontal dto);

	/**
	 * Obtiene el configuracion frontal por defecto
	 * @return Conf. frontal por defecto
	 */
	public ConfiguracionFrontal findConfFrontalByDefault();

	/**
	 * Obtiene el configuracion frontal segun id
	 *
	 * @param id El id
	 * @return  Configuracion frontal
	 */
	public ConfiguracionFrontal findConfFrontalById(Long id);
}
