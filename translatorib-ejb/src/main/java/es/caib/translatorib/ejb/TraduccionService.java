package es.caib.translatorib.ejb;

import javax.ejb.Local;

import es.caib.translatorib.ejb.api.model.Idioma;
import es.caib.translatorib.ejb.api.model.Opciones;
import es.caib.translatorib.ejb.api.model.Resultado;
import es.caib.translatorib.ejb.api.model.TipoDocumento;
import es.caib.translatorib.ejb.api.model.TipoEntrada;

/**
 * Interfície del servei per gestionar {@link Traduccion}
 *
 * @author Indra
 */
@Local
public interface TraduccionService {

	public Resultado realizarTraduccion(String textoEntrada, TipoEntrada tipoEntrada, Idioma idiomaEntrada,
			Idioma idiomaSalidad, Opciones opciones);

	public Resultado realizarTraduccionDocumento(byte[] contenidoDocumento, TipoDocumento tipoDocumento,
			Idioma idiomaEntrada, Idioma idiomaSalidad, Opciones opciones);
}
