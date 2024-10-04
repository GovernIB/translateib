package es.caib.translatorib.backend.converters;

import es.caib.translatorib.core.api.model.Idioma;
import es.caib.translatorib.core.api.model.ParejaIdiomas;
import es.caib.translatorib.core.api.model.Plugin;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import java.util.List;

@FacesConverter("idiomaConverter")
public class IdiomaConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        // Workaround para que salte la validación de que hay que seleccionar un valor
        if (s != null && (s.contains("Seleccioni un") || s.contains("Seleccionar") ||s.contains("Seleccione un") || s.equals("Selecciona una opción") || s.equals("Tria una opció"))) {
            s = null;
        }

        if (s != null && s.trim().length() > 0) {
            // Obtener la lista de datos desde el componente
            for(Idioma idioma : Idioma.values()) {
                if (idioma.toString().equals(s)) {
                    return idioma;
                }
            }
            return null;
        } else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        if (o != null) {
            return ((Idioma) o).toString();
        } else {
            return null;
        }
    }
}
