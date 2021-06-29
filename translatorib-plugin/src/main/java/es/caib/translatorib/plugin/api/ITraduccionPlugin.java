package es.caib.translatorib.plugin.api;

import org.fundaciobit.pluginsib.core.IPlugin;

import es.caib.translatorib.ejb.api.model.Idioma;
import es.caib.translatorib.ejb.api.model.Opciones;
import es.caib.translatorib.ejb.api.model.ResultadoTraduccionDocumento;
import es.caib.translatorib.ejb.api.model.ResultadoTraduccionTexto;
import es.caib.translatorib.ejb.api.model.TipoDocumento;
import es.caib.translatorib.ejb.api.model.TipoEntrada;

/**
 * Interface pasarela traducción.
 *
 * @author Indra
 *
 */
public interface ITraduccionPlugin extends IPlugin {

	/** Prefix. */
	public static final String TRANSLATOR_BASE_PROPERTY = "es.caib.translatorib.";

	/** PLUGINS **/
	public static final String PLUGIN_OPENTRAD = "opentrad";

	public static final String PLUGIN_PLATA = "plata";

	/**
	 * Realizar traduccion de un texto.
	 *
	 * @param textoEntrada
	 * @param tipoEntrada
	 * @param idiomaEntrada
	 * @param idiomaSalida
	 * @param opciones
	 * @return
	 * @throws TraduccionException
	 */
	ResultadoTraduccionTexto realizarTraduccion(String textoEntrada, TipoEntrada tipoEntrada, Idioma idiomaEntrada,
			Idioma idiomaSalida, Opciones opciones) throws TraduccionException;

	/**
	 * Realizar traducción de un documento.
	 *
	 * @param documentoEntrada
	 * @param tipoDocumento
	 * @param idiomaEntrada
	 * @param idiomaSalida
	 * @param opciones
	 * @return
	 */
	ResultadoTraduccionDocumento realizarTraduccionDocumento(final String documentoEntradaB64,
			final TipoDocumento tipoDocumento, final Idioma idiomaEntrada, final Idioma idiomaSalida,
			final Opciones opciones) throws TraduccionException;

}
