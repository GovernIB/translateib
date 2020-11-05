package es.caib.translatorib.plugin.api.mock;

import es.caib.translatorib.ejb.api.model.Idioma;
import es.caib.translatorib.ejb.api.model.Opciones;
import es.caib.translatorib.ejb.api.model.ResultadoTraduccionDocumento;
import es.caib.translatorib.ejb.api.model.ResultadoTraduccionTexto;
import es.caib.translatorib.ejb.api.model.TipoDocumento;
import es.caib.translatorib.ejb.api.model.TipoEntrada;
import es.caib.translatorib.plugin.api.ITraduccionPlugin;

/**
 * Interface pasarela pago.
 *
 * @author Indra
 *
 */
public class MockTradPlugin implements ITraduccionPlugin {

	@Override
	public ResultadoTraduccionTexto realizarTraduccion(final String textoEntrada, final TipoEntrada tipoEntrada,
			final Idioma idiomaEntrada, final Idioma idiomaSalida, final Opciones opciones) {
		final ResultadoTraduccionTexto resultado = new ResultadoTraduccionTexto();
		resultado.setError(false);
		resultado.setTextoTraducido("Hola");
		return resultado;
	}

	@Override
	public ResultadoTraduccionDocumento realizarTraduccionDocumento(final String documentoEntradaB64,
			final TipoDocumento tipoDocumento, final Idioma idiomaEntrada, final Idioma idiomaSalida,
			final Opciones opciones) {
		final ResultadoTraduccionDocumento resultado = new ResultadoTraduccionDocumento();
		resultado.setError(false);
		resultado.setTextoTraducido("Hola");
		return resultado;
	}
}
