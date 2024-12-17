package es.caib.translatorib.persistence.repository;

import es.caib.translatorib.service.model.ConfiguracionFrontal;

/**
 * La interface ConfiguracionFrontalDao.
 */
public interface ConfiguracionFrontalRepository {

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
