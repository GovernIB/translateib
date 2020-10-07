package es.caib.translatorib.api.services;

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

import es.caib.translatorib.api.model.ParametrosTraduccion;
import es.caib.translatorib.api.model.ParametrosTraduccionDocumento;
import es.caib.translatorib.commons.utils.Constants;
import es.caib.translatorib.ejb.TraduccionService;
import es.caib.translatorib.ejb.api.model.Idioma;
import es.caib.translatorib.ejb.api.model.Opciones;
import es.caib.translatorib.ejb.api.model.Resultado;
import es.caib.translatorib.ejb.api.model.TipoEntrada;
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
@Path("traduccion")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed({ Constants.TIB_ADMIN })
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class TraduccionResource {

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
	@APIResponse(responseCode = "200", description = "Procediment", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Resultado.class)))
	public Response get() {

		final String textoEntrada = "traducir";
		final TipoEntrada tipoEntrada = TipoEntrada.TEXTO_PLANTO;
		final Idioma idiomaEntrada = Idioma.CASTELLANO;
		final Idioma idiomaSalida = Idioma.CATALAN;
		final Opciones opciones = new Opciones();
		final Resultado resultado = traduccionService.realizarTraduccion(textoEntrada, tipoEntrada, idiomaEntrada,
				idiomaSalida, opciones);
		return Response.ok(resultado).build();
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
	@APIResponse(responseCode = "200", description = "Traduccion TEXTO", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Resultado.class)))
	public Response realizarTraduccion(
			@RequestBody(description = "Parametros para la traducción", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ParametrosTraduccion.class))) @Valid final ParametrosTraduccion parametros)
			throws TraduccionException {
		final Resultado resultado = traduccionService.realizarTraduccion(parametros.getTextoEntrada(),
				parametros.getTipoEntrada(), parametros.getIdiomaEntrada(), parametros.getIdiomaSalida(),
				parametros.getOpciones());

		if (resultado != null) {
			return Response.ok(resultado).build();
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
	@APIResponse(responseCode = "200", description = "Traduccion DOC", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Resultado.class)))
	public Response realizarTraduccionDocumento(
			@RequestBody(description = "Parametros para la traducción", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ParametrosTraduccionDocumento.class))) @Valid final ParametrosTraduccionDocumento parametros) {
		final Resultado resultado = traduccionService.realizarTraduccionDocumento(parametros.getContenidoDocumento(),
				parametros.getTipoDocumento(), parametros.getIdiomaEntrada(), parametros.getIdiomaSalida(),
				parametros.getOpciones());

		if (resultado != null) {
			return Response.ok(resultado).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}
}
