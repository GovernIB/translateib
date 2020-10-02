package es.caib.translatorib.plugin.api.mock;

import es.caib.translatorib.ejb.api.model.Idioma;
import es.caib.translatorib.ejb.api.model.Opciones;
import es.caib.translatorib.ejb.api.model.Resultado;
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
	public Resultado realizarTraduccion(final String textoEntrada, final TipoEntrada tipoEntrada,
			final Idioma idiomaEntrada, final Idioma idiomaSalida, final Opciones opciones) {
		final Resultado resultado = new Resultado();
		resultado.setError(false);
		resultado.setTextoTraducido("Hola");
		return resultado;
	}

	@Override
	public Resultado realizarTraduccionDocumento(final byte[] documentoEntrada, final TipoDocumento tipoDocumento,
			final Idioma idiomaEntrada, final Idioma idiomaSalida, final Opciones opciones) {
		final Resultado resultado = new Resultado();
		resultado.setError(false);
		resultado.setTextoTraducido("Hola");
		return resultado;
	}
}
