package es.caib.translatorib.backend.controller;


import es.caib.translatorib.backend.model.DialogResult;
import es.caib.translatorib.backend.model.types.TypeParametroVentana;
import es.caib.translatorib.backend.util.UtilJSF;
import es.caib.translatorib.core.api.model.ConfiguracionFrontal;
import es.caib.translatorib.core.api.model.ConfiguracionGlobal;
import es.caib.translatorib.core.api.model.Idioma;
import es.caib.translatorib.core.api.model.Plugin;
import es.caib.translatorib.core.api.model.filtro.ConfiguracionGlobalFiltro;
import es.caib.translatorib.core.api.model.types.TypeModoAcceso;
import es.caib.translatorib.core.api.model.types.TypeNivelGravedad;
import es.caib.translatorib.core.api.service.ConfiguracionFrontalService;
import es.caib.translatorib.core.api.service.ConfiguracionGlobalService;
import es.caib.translatorib.core.api.service.PluginService;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionListener;
import javax.inject.Inject;
import java.util.*;

/**
 * Controlador per configuracion frontal.
 *
 * @author Indra
 */
@ManagedBean
@ViewScoped
public class ViewConfiguracionFrontal extends ViewControllerBase {

    private static final long serialVersionUID = -7992474170848445700L;

    private static final Logger LOG = LoggerFactory.getLogger(ViewConfiguracionFrontal.class);

    /**
     * Model de dades emprat pel compoment dataTable de primefaces.
     */
    private ConfiguracionFrontal dato;

    @Inject
    private ConfiguracionFrontalService configuracionFrontalService;

    @Inject
    private SessionBean	sessionBean;

    @Inject
    private PluginService pluginService;

    private List<Plugin> plugins;
    private List<Plugin> selectedPlugins;


    /**
     * Carga datos.
     */
    public void init() {

        LOG.debug("load");
        // Control acceso
        UtilJSF.verificarAcceso();

        selectedPlugins = new ArrayList<>();
        plugins = pluginService.lista(null);
        dato = configuracionFrontalService.findConfFrontalByDefault();
        if (dato == null) {
            dato = new ConfiguracionFrontal();
        }
        if (dato.getPluginsSoportados() != null && !dato.getPluginsSoportados().isEmpty()) {
            for(String pluginSoportado : dato.getPluginsSoportados().split(",")) {
                for(Plugin plugin : plugins) {
                    if (plugin.getCodigo().equals(Long.valueOf(pluginSoportado))) {
                        plugin.setSeleccionado(true);
                        selectedPlugins.add(plugin);
                    }
                }
            }
        }
    }

    public void guardar() {
        dato.setPluginsSoportados("");
        StringBuilder sb = new StringBuilder();
        for(Plugin plugin : plugins) {
            if (plugin.isSeleccionado()) {
                sb.append(plugin.getCodigo()).append(",");
            }
        }
        if (sb.length() > 0) {
            dato.setPluginsSoportados(sb.substring(0, sb.length() - 1));
        }
        if (dato.getPlugin() != null && dato.getPluginsSoportados() != null) {
            boolean encontrado = false;
            for(String pluginSoportado : dato.getPluginsSoportados().split(",")) {
                if (dato.getPlugin().getCodigo().equals(Long.valueOf(pluginSoportado))) {
                    encontrado = true;
                    break;
                }
            }
            if (!encontrado) {
                UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("viewConfiguracionFrontal.pluginPorDefectoNoSorportado"));
                return;
            }
        }
        configuracionFrontalService.updateConfiguracionFrontal(dato);
        UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("dict.datosActualizados"));
    }

    public ConfiguracionFrontal getDato() {
        return dato;
    }

    public void setDato(ConfiguracionFrontal dato) {
        this.dato = dato;
    }

    private Idioma selectedIdioma;

    public Idioma[] getIdiomas() {
        return Idioma.values();
    }

    public List<Plugin> getPlugins() {
        return plugins;
    }

    public void setPlugins(List<Plugin> plugins) {
        this.plugins = plugins;
    }

    public List<Plugin> getSelectedPlugins() {
        return selectedPlugins;
    }

    public void setSelectedPlugins(List<Plugin> selectedPlugins) {
        this.selectedPlugins = selectedPlugins;
    }

    public Idioma getSelectedIdioma() {
        return selectedIdioma;
    }

    public void setSelectedIdioma(Idioma selectedIdioma) {
        this.selectedIdioma = selectedIdioma;
    }

    public void editarPlugin(Long codigo, boolean seleccionado) {
        if (seleccionado) {
            final Map<String, String> params = new HashMap<String, String>();
            params.put(TypeParametroVentana.ID.toString(), String.valueOf(codigo.toString()));
            UtilJSF.openDialog(DialogPluginsIdioma.class, TypeModoAcceso.EDICION, params, true, 900, 650);
        } else {
            UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("viewConfiguracionFrontal.editarIdiomasPlugin"));
        }
    }
}
