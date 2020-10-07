package es.caib.translatorib.api.filters;

import static es.caib.translatorib.api.config.ApiConstants.REQUEST_LOCALE;
import static es.caib.translatorib.api.config.ApiConstants.SUPPORTED_LOCALES;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.annotation.Priority;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;

/**
 * Filtre per garantir que la petició és retorna amb un dels idiomes soportats,
 * segons les preferències indicades a la petició.
 *
 * @author areus
 */
@Provider
@PreMatching
@Priority(300)
public class LocaleRequestFilter implements ContainerRequestFilter, ContainerResponseFilter {

	@Context
	private HttpServletRequest servletRequest;

	@Context
	private ServletContext servletContext;

	private List<Locale> supportedLocales;

	/**
	 * Inicialitza la llista de locales soportats.
	 */
	@PostConstruct
	private void init() {
		final String supportedLocalesParam = servletContext.getInitParameter(SUPPORTED_LOCALES);
		final String[] localeSplit = supportedLocalesParam.split(",");
		Arrays.sort(localeSplit);

		supportedLocales = new ArrayList<Locale>();
		for (int i = 0; i < localeSplit.length; i++) {
			if (localeSplit[i] != null) {
				final String supportedLocale = localeSplit[i].trim();
				supportedLocales.add(new Locale(supportedLocale));
			}
		}

//		JDK11
//		supportedLocales = Stream.of(supportedLocalesParam.split(",")).map(String::trim).map(Locale::forLanguageTag)
//				.collect(Collectors.toList());
	}

	/**
	 * Determina el language de la petició, en primer lloc agafant el paràmetre
	 * lang, i si no, de la capçalera Accept-language de la petició.
	 *
	 * @param request informació de context de la petició
	 */
	@Override
	public void filter(final ContainerRequestContext request) {

		Locale locale = supportedLocales.isEmpty() ? Locale.getDefault() : supportedLocales.get(0);

		final String lang = servletRequest.getParameter("lang");
		if (lang != null && !lang.isEmpty()) {
			final Locale langLocale = Locale.forLanguageTag(lang);
			if (supportedLocales.contains(langLocale)) {
				locale = langLocale;
			}
		} else {
			final List<Locale> acceptableLanguages = request.getAcceptableLanguages();
			for (final Locale acceptableLocale : acceptableLanguages) {
				if (supportedLocales.contains(acceptableLocale)) {
					locale = acceptableLocale;
					break;
				}
			}
		}
		request.getHeaders().putSingle(HttpHeaders.ACCEPT_LANGUAGE, locale.toLanguageTag());
		request.setProperty(REQUEST_LOCALE, locale);
	}

	/**
	 * Comprova si la resposta té el language fixat i si no el té afegeix la
	 * capçalera Content-Language amb el valor determinat al filtre d'entrada.
	 *
	 * @param request  informació de context de la petició
	 * @param response informació de context de la resposta
	 */
	@Override
	public void filter(final ContainerRequestContext request, final ContainerResponseContext response) {
		if (response.getLanguage() == null) {
			final Locale locale = (Locale) request.getProperty(REQUEST_LOCALE);
			response.getHeaders().putSingle(HttpHeaders.CONTENT_LANGUAGE, locale.toLanguageTag());
		}
	}
}