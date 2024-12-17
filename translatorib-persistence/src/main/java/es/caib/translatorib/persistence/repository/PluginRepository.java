package es.caib.translatorib.persistence.repository;

import es.caib.translatorib.service.model.Plugin;

import java.util.List;

/**
 * La interface PluginDao.
 */
public interface PluginRepository {

	/**
	 * Lista plugins.
	 *
	 */
	List<Plugin> lista(String filtro);

	/**
	 * Recupera plugin por Codigo.
	 *
	 * @param codigo codigo
	 * @return plugin
	 */
	Plugin getPluginByCodigo(Long codigo);

	/**
	 * Guardar plugin.
	 * @param plugin plugin
	 */
	void guardarPlugin(Plugin plugin);


	/**
	 * Actualizar plugin
	 * @param plugin Plugin
	 */
	 void updatePlugin(Plugin plugin);

	/**
	 * Obtiene el plugin por defecto.
	 * @return plugin por defecto
	 */
    Plugin getPluginDefault();

	/**
	 * Obtiene el plugin segun el identificador
	 * @param identificador identificador
	 * @return plugin
	 */
    Plugin getPluginByIdentificador(String identificador);

    /**
	 * Obtiene el plugin segun classname.
	 * @param classname classname
	 * @return plugin
	 */
	Plugin getPluginByClassname(String classname);

	/**
	 * Actualizar plugin sus idiomas frontales.
	 * @param codigo Codigo plugin
	 * @param idiomasFrontal Idiomas del frontal
	 */
	void actualizarPlg(Long codigo, String idiomasFrontal);

	/**
	 * Borrar plugin.
	 * @param plugin
	 */
    void borrar(Plugin plugin);
}
