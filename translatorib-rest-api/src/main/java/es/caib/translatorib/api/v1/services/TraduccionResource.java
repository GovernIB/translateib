package es.caib.translatorib.api.v1.services;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.translatorib.api.v1.model.ParametrosTraduccionDocumento;
import es.caib.translatorib.api.v1.model.ParametrosTraduccionTexto;
import es.caib.translatorib.api.v1.model.ResultadoTraduccionDocumento;
import es.caib.translatorib.api.v1.model.ResultadoTraduccionTexto;
import es.caib.translatorib.commons.utils.Constants;
import es.caib.translatorib.ejb.TraduccionService;
import es.caib.translatorib.ejb.api.model.Opciones;
import es.caib.translatorib.ejb.api.model.PropiedadValor;
import es.caib.translatorib.plugin.api.TraduccionException;

/**
 * Recurs REST per accedir a la traducció.
 *
 * La seguretat es pot establir a nivel de url-pattern/http-method a dins
 * web.xml, o amb l'etiqueta {@link RolesAllowed} a nivell de tota la classe o
 * de recurs. Per poder-la emprar cal que marquem el recurs com un bean
 * {@link Stateless}. Fixam també el {@link TransactionAttribute} al valor
 * {@link TransactionAttributeType#NOT_SUPPORTED} atès que no volem que demarqui
 * transaccions.
 *
 * @author areus
 */
@Stateless
@Path("traduccion/v1")
@Produces(MediaType.APPLICATION_JSON+ ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed({ Constants.TIB_API })
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class TraduccionResource {

	private static final Logger LOG = LoggerFactory.getLogger(TraduccionResource.class);

	@EJB
	private TraduccionService traduccionService;

	/**
	 * Obté un procediment
	 *
	 * @param id identificador
	 * @return Resposta amb status 200 i la informació del procediment o un resposta
	 *         amb estatus 404 si l'identificador no existeix.
	 */
	@GET
	@Path("/test")
	@Operation(operationId = "test", summary = "Test de prueba que devuelve el texto traducir traducido : traduir")
	@APIResponse(responseCode = "200", description = "Procediment", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultadoTraduccionTexto.class)))
	public Response get() {
		LOG.error("TraduccionResources.TEST");
		final String textoEntrada = "traducir";
		final es.caib.translatorib.ejb.api.model.TipoEntrada tipoEntrada = es.caib.translatorib.ejb.api.model.TipoEntrada.TEXTO_PLANO;
		final es.caib.translatorib.ejb.api.model.Idioma idiomaEntrada = es.caib.translatorib.ejb.api.model.Idioma.CASTELLANO;
		final es.caib.translatorib.ejb.api.model.Idioma idiomaSalida = es.caib.translatorib.ejb.api.model.Idioma.CATALAN;
		final es.caib.translatorib.ejb.api.model.Opciones opciones = new es.caib.translatorib.ejb.api.model.Opciones();
		final es.caib.translatorib.ejb.api.model.ResultadoTraduccionTexto resultado = traduccionService
				.realizarTraduccion(textoEntrada, tipoEntrada, idiomaEntrada, idiomaSalida, null, opciones);
		return Response.ok(cast(resultado)).build();
	}

	/**
	 * Realiza una traducción de un texto.
	 *
	 * @param parametros Los parametros de consulta.
	 * @return Un codi 201 amb la traducción del texto.
	 * @throws TraduccionException
	 */
	@POST
	@Path("/texto")
	@Operation(operationId = "texto", summary = "Realiza una traducción de un texto")
	@APIResponse(responseCode = "200", description = "Traduccion TEXTO", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultadoTraduccionTexto.class)))
	public Response realizarTraduccion(
			@RequestBody(description = "Parametros para la traducción", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ParametrosTraduccionTexto.class))) @Valid final ParametrosTraduccionTexto parametros) {
		LOG.error("TraduccionResources.traduccion.text");
		final es.caib.translatorib.ejb.api.model.TipoEntrada tipoEntrada = es.caib.translatorib.ejb.api.model.TipoEntrada
				.fromString(parametros.getTipoEntrada().toString());
		final es.caib.translatorib.ejb.api.model.Idioma idiomaEntrada = es.caib.translatorib.ejb.api.model.Idioma
				.fromString(parametros.getIdiomaEntrada().getIdioma(), parametros.getIdiomaEntrada().getLocale());
		final es.caib.translatorib.ejb.api.model.Idioma idiomaSalida = es.caib.translatorib.ejb.api.model.Idioma
				.fromString(parametros.getIdiomaSalida().getIdioma(), parametros.getIdiomaSalida().getLocale());
		final es.caib.translatorib.ejb.api.model.Opciones opciones = crearOpciones(parametros.getOpciones());

		final es.caib.translatorib.ejb.api.model.ResultadoTraduccionTexto resultado = traduccionService
				.realizarTraduccion(parametros.getTextoEntrada(), tipoEntrada, idiomaEntrada, idiomaSalida,
						parametros.getPlugin(), opciones);

		if (resultado != null) {
			return Response.ok(cast(resultado)).build();
		} else {

			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}

	/**
	 * Realiza una traducción de un texto.
	 *
	 * @param textoEntrada Texto de enrrada
	 * @return Un codi 201 amb el text traduit.
	 * @throws TraduccionException
	 */
	@POST
	@Path("/documento")
	@Operation(operationId = "documento", summary = "Realiza una traducción de un documento")
	@APIResponse(responseCode = "200", description = "Traduccion DOC", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultadoTraduccionDocumento.class)))
	public Response realizarTraduccionDocumento(
			@RequestBody(description = "Parametros para la traducción", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ParametrosTraduccionDocumento.class))) @Valid final ParametrosTraduccionDocumento parametros) {
		LOG.error("TraduccionResources.traduccion.documento");
		final es.caib.translatorib.ejb.api.model.TipoDocumento tipoDocumento = es.caib.translatorib.ejb.api.model.TipoDocumento
				.fromString(parametros.getTipoDocumento().toString());
		final es.caib.translatorib.ejb.api.model.Idioma idiomaEntrada = es.caib.translatorib.ejb.api.model.Idioma
				.fromString(parametros.getIdiomaEntrada().getIdioma(), parametros.getIdiomaEntrada().getLocale());
		final es.caib.translatorib.ejb.api.model.Idioma idiomaSalida = es.caib.translatorib.ejb.api.model.Idioma
				.fromString(parametros.getIdiomaSalida().getIdioma(), parametros.getIdiomaSalida().getLocale());
		final es.caib.translatorib.ejb.api.model.Opciones opciones = crearOpciones(parametros.getOpciones());

		final es.caib.translatorib.ejb.api.model.ResultadoTraduccionDocumento resultado = traduccionService
				.realizarTraduccionDocumento(parametros.getContenidoDocumento(), tipoDocumento, idiomaEntrada,
						idiomaSalida, parametros.getPlugin(), opciones);

		if (resultado != null) {
			return Response.ok(cast(resultado)).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}

	/** Cast de Resultado ejb.abi a rest.api.model **/
	private ResultadoTraduccionTexto cast(final es.caib.translatorib.ejb.api.model.ResultadoTraduccionTexto resultado) {
		ResultadoTraduccionTexto res = null;
		if (resultado != null) {
			res = new ResultadoTraduccionTexto();
			res.setError(resultado.isError());
			res.setDescripcionError(resultado.getDescripcionError());
			res.setTextoTraducido(resultado.getTextoTraducido());
		}
		return res;
	}

	/** Cast de Resultado ejb.abi a rest.api.model **/
	private ResultadoTraduccionDocumento cast(
			final es.caib.translatorib.ejb.api.model.ResultadoTraduccionDocumento resultado) {
		ResultadoTraduccionDocumento res = null;
		if (resultado != null) {
			res = new ResultadoTraduccionDocumento();
			res.setError(resultado.isError());
			res.setDescripcionError(resultado.getDescripcionError());
			res.setTextoTraducido(resultado.getTextoTraducido());
			res.setChecksum(resultado.getChecksum());
			res.setTipo(resultado.getTipo());
			res.setDireccion(resultado.getDireccion());
		}
		return res;
	}

	private Opciones crearOpciones(final es.caib.translatorib.api.v1.model.Opciones opciones) {
		final Opciones resultado = new Opciones();
		if (opciones != null && opciones.getPropiedades() != null && opciones.getPropiedades().isEmpty()) {
			final List<PropiedadValor> propiedades = new ArrayList();
			for (final es.caib.translatorib.api.v1.model.PropiedadValor prop : opciones.getPropiedades()) {
				propiedades.add(new PropiedadValor(prop.getPropiedad(), prop.getValor()));
			}
		}
		return resultado;
	}
}
