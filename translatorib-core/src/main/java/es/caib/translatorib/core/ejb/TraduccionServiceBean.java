package es.caib.translatorib.core.ejb;

import es.caib.translatorib.core.api.model.*;
import es.caib.translatorib.core.api.service.TraduccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

@Stateless
@Interceptors(SpringBeanAutowiringInterceptor.class)
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TraduccionServiceBean implements TraduccionService {

	@Autowired
	private TraduccionService service;

	@Override
	//@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN })
	@PermitAll
	public ResultadoTraduccionTexto realizarTraduccion(String textoEntrada, TipoEntrada tipoEntrada, Idioma idiomaEntrada, Idioma idiomaSalidad, String plugin, Opciones opciones) {
		return service.realizarTraduccion(textoEntrada, tipoEntrada, idiomaEntrada, idiomaSalidad, plugin, opciones);
	}

	@Override
	@PermitAll
	public ResultadoTraduccionDocumento realizarTraduccionDocumento(String contenidoDocumentoB64, TipoDocumento tipoDocumento, Idioma idiomaEntrada, Idioma idiomaSalidad, String plugin, Opciones opciones) {
		return service.realizarTraduccionDocumento(contenidoDocumentoB64, tipoDocumento, idiomaEntrada, idiomaSalidad, plugin, opciones);
	}
}
