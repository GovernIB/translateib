package es.caib.translatorib.core.api.service;

import es.caib.translatorib.core.api.model.*;

public interface TraduccionService {

     public ResultadoTraduccionTexto realizarTraduccion(final String textoEntrada, final TipoEntrada tipoEntrada,
                                                        final Idioma idiomaEntrada, final Idioma idiomaSalidad, final String plugin, final Opciones opciones) ;

    public ResultadoTraduccionDocumento realizarTraduccionDocumento(final String contenidoDocumentoB64,
                                                                    final TipoDocumento tipoDocumento, final Idioma idiomaEntrada, final Idioma idiomaSalidad,
                                                                    final String plugin, final Opciones opciones) ;

}
