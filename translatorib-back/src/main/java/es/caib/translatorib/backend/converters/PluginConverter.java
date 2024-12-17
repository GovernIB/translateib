package es.caib.translatorib.backend.converters;

import es.caib.translatorib.service.model.Plugin;
import es.caib.translatorib.service.service.PluginService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

//@Named
//@ManagedBean
//@ViewScoped
@FacesConverter("pluginConverter")
public class PluginConverter implements Converter, Serializable {
    private static final long serialVersionUID = -8972013394803135445L;

    @Inject
    private PluginService pluginService;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        // Workaround para que salte la validación de que hay que seleccionar un valor
        if (s != null && (s.contains("Seleccioni un") || s.contains("Seleccionar") ||s.contains("Seleccione un") || s.equals("Selecciona una opción") || s.equals("Tria una opció"))) {
            s = null;
        }

        if (s != null && s.trim().length() > 0) {
            // Obtener la lista de datos desde el componente
            List<Plugin> listaDatos = (List<Plugin>) uiComponent.getAttributes().get("listaDatos");
            if (listaDatos != null) {
                for (Plugin dato : listaDatos) {
                    if (dato.getCodigo().equals(Long.parseLong(s))) {
                        return dato;
                    }
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
            return String.valueOf(((Plugin) o).getCodigo());
        } else {
            return null;
        }
    }
}
