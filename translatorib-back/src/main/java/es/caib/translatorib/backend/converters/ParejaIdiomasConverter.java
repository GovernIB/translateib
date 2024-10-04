package es.caib.translatorib.backend.converters;

import es.caib.translatorib.core.api.model.Idioma;
import es.caib.translatorib.core.api.model.ParejaIdiomas;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("parejaIdiomasConverter")
public class ParejaIdiomasConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        String[] parts = value.split("##");
        if (parts.length == 2) {
            String[] origenParts = parts[0].split("#");
            String[] destinoParts = parts[1].split("#");
            if (origenParts.length == 2 && destinoParts.length == 2) {
                Idioma origen = Idioma.fromString(origenParts[0], origenParts[1]);
                Idioma destino = Idioma.fromString(destinoParts[0], destinoParts[1]);
                if (origen != null && destino != null) {
                    return new ParejaIdiomas(origen, destino);
                }
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return "";
        }
        ParejaIdiomas pareja = (ParejaIdiomas) value;
        return pareja.getOrigen().getIdioma() + "#" + pareja.getOrigen().getLocale() +
                "##" + pareja.getDestino().getIdioma() + "#" + pareja.getDestino().getLocale();
    }
}
