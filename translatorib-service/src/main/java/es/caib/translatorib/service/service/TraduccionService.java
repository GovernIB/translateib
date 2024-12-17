package es.caib.translatorib.service.service;

import es.caib.translatorib.service.model.*;

public interface TraduccionService {

     public ResultadoTraduccionTexto realizarTraduccion(final String textoEntrada, final TipoEntrada tipoEntrada,
                                                        final Idioma idiomaEntrada, final Idioma idiomaSalidad, final String plugin, final Opciones opciones) ;

    public ResultadoTraduccionDocumento realizarTraduccionDocumento(final String contenidoDocumentoB64,
                                                                    final TipoDocumento tipoDocumento, final Idioma idiomaEntrada, final Idioma idiomaSalidad,
                                                                    final String plugin, final Opciones opciones) ;

}
