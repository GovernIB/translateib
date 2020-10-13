package es.caib.translatorib.ejb;

import javax.ejb.Local;

import es.caib.translatorib.ejb.api.model.Idioma;
import es.caib.translatorib.ejb.api.model.Opciones;
import es.caib.translatorib.ejb.api.model.ResultadoTraduccionDocumento;
import es.caib.translatorib.ejb.api.model.ResultadoTraduccionTexto;
import es.caib.translatorib.ejb.api.model.TipoDocumento;
import es.caib.translatorib.ejb.api.model.TipoEntrada;

/**
 * Interfície del servei per gestionar {@link Traduccion}
 *
 * @author Indra
 */
@Local
public interface TraduccionService {

	public ResultadoTraduccionTexto realizarTraduccion(String textoEntrada, TipoEntrada tipoEntrada,
			Idioma idiomaEntrada, Idioma idiomaSalidad, String plugin, Opciones opciones);

	public ResultadoTraduccionDocumento realizarTraduccionDocumento(byte[] contenidoDocumento,
			TipoDocumento tipoDocumento, Idioma idiomaEntrada, Idioma idiomaSalidad, String plugin, Opciones opciones);
}
