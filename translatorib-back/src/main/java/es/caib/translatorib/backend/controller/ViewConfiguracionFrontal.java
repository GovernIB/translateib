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
import javax.faces.event.AjaxBehaviorEvent;
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

    private List<Idioma> idiomasOrigen;
    private List<Idioma> idiomasDestino;

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

        if (dato.getPlugin() != null) {
            Idioma idiomaOr = dato.getIdiomaOrigen();
            Idioma idiomaDe = dato.getIdiomaDestino();
            setearIdiomas(dato.getPlugin());
            if (idiomaOr != null) {
                dato.setIdiomaOrigen(idiomaOr);
            }
            if (idiomaDe != null) {
                dato.setIdiomaDestino(idiomaDe);
            }
        } else {
            if (plugins != null && !plugins.isEmpty()) {
                setearIdiomas(plugins.get(0));
                dato.setPlugin(plugins.get(0));
            }
        }
    }

    /**
     * Cambiar plugin.
     */
    public void cambiarPlugin() {
        if (dato.getPlugin() == null) {
            return;
        }
        for(Plugin plg: plugins) {
            if (plg.getIdentificador().equals(dato.getPlugin().getIdentificador())) {
                setearIdiomas(plg);
                /*if (idiomaOrigen == null || idiomaOrigen.equals(Idioma.CASTELLANO)) {
                    idiomaOrigen = idiomasOrigen.get(0);
                }
                if (idiomaDestino == null || idiomaDestino.equals(Idioma.CATALAN_BALEAR)) {
                    idiomaDestino = idiomasDestino.get(0);
                }*/
                break;
            }
        }
    }

    /**
     * Cambiar idioma destino a partir del cambio de idioma origen. Para poner un valor por defecto.
     * @param event Evento de cambio de idioma origen.
     */
    public void cambiarIdiomaOrigenAux(AjaxBehaviorEvent event) {
        for(Idioma idioma : idiomasDestino) {
            if (!esDisabledDestino(idioma)) {
                dato.setIdiomaDestino(idioma);
                break;
            }
        }
    }

    /**
     * <p>Setear idiomas.</p>
     * <p>La idea es poner en idiomas origen e idiomas destino los idiomas posibles.</p>
     * <p>Setear un primer idioma origen y luego poner el posible idioma destino.</p>
     * @param plg Plugin
     */
    private void setearIdiomas(Plugin plg) {
        if (plg.getIdiomasFrontal() == null || plg.getIdiomasFrontal().isEmpty()) {
            idiomasDestino = new ArrayList<Idioma>();
            idiomasOrigen = new ArrayList<Idioma>();
            return;
        } else {
            setearIdiomasOrigen(plg.getIdiomasFrontal());
            setearIdiomasDestino(plg.getIdiomasFrontal());
        }
        if (idiomasOrigen != null && !idiomasOrigen.isEmpty()) {
            dato.setIdiomaOrigen(idiomasOrigen.get(0));
            for(Idioma idioma : idiomasDestino) {
                if (!esDisabledDestino(idioma)) {
                    dato.setIdiomaDestino(idioma);
                    break;
                }
            }
        }
    }

    /**
     * Setear idiomas destino.
     * @param idiomasFrontal Idiomas frontal
     */
    private void setearIdiomasDestino(String idiomasFrontal) {
        //Teniendo en cuenta que idiomasFronta tiene un valor asi : es es->ca ca_ES,ca ca_ES->es es
        //La separación entre pares es la coma (',')
        //Quiero extraer la segunda parte ants de la -> y convertirlo a una lista de idiomas y setearlo a idiomas
        idiomasDestino = new ArrayList<Idioma>();
        String[] idiomasFrontalArray = idiomasFrontal.split(",");
        for (String idioma : idiomasFrontalArray) {
            String[] idiomaArray = idioma.split("->");
            if(idiomasDestino.contains(Idioma.fromString(idiomaArray[1]))){
                continue;
            }
            idiomasDestino.add(Idioma.fromString(idiomaArray[1]));
        }
    }

    /**
     * Setear idiomas origen.
     * @param idiomasFrontal Idiomas frontal
     */
    private void setearIdiomasOrigen(String idiomasFrontal) {
        //Teniendo en cuenta que idiomasFronta tiene un valor asi : es es->ca ca_ES,ca ca_ES->es es
        //La separación entre pares es la coma (',')
        //Quiero extraer la primera parte ants de la -> y convertirlo a una lista de idiomas y setearlo a idiomas
        idiomasOrigen = new ArrayList<Idioma>();
        String[] idiomasFrontalArray = idiomasFrontal.split(",");
        for (String idioma : idiomasFrontalArray) {
            String[] idiomaArray = idioma.split("->");
            if(idiomasOrigen.contains(Idioma.fromString(idiomaArray[0]))){
                continue;
            }
            idiomasOrigen.add(Idioma.fromString(idiomaArray[0]));
        }
    }

    /**
     * Comprobar si el idioma destino está deshabilitado.
     * @param idiomaDestino Idioma destino
     * @return True si está deshabilitado, false en caso contrario.
     */
    public boolean esDisabledDestino(Idioma idiomaDestino) {
        if (dato.getIdiomaOrigen() == null || idiomaDestino == null || this.dato.getPlugin() == null || this.dato.getPlugin().getIdiomasFrontal() == null || this.dato.getPlugin().getIdiomasFrontal().isEmpty()) {
            //Si algun dato nulo, entonces no seleccionable
            return true;
        }
        if (dato.getIdiomaOrigen() == idiomaDestino) {
            //Si es mismo idioma, entonces no seleccionable
            return true;
        }

        //Buscamos los idiomas
        for(String idioma : this.dato.getPlugin().getIdiomasFrontal().split(",")) {
            String[] idiomaArray = idioma.split("->");
            if (Idioma.fromString(idiomaArray[0]) == dato.getIdiomaOrigen() && Idioma.fromString(idiomaArray[1]) == idiomaDestino) {
                return false;
            }
        }
        return true;
    }

    /**
     * Guardar datos de la configuración del frontal.
     */
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

    public void editarPlugin(Long codigo, boolean seleccionado) {
        if (seleccionado) {
            final Map<String, String> params = new HashMap<String, String>();
            params.put(TypeParametroVentana.ID.toString(), String.valueOf(codigo.toString()));
            UtilJSF.openDialog(DialogPluginsIdioma.class, TypeModoAcceso.EDICION, params, true, 900, 650);
        } else {
            UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("viewConfiguracionFrontal.editarIdiomasPlugin"));
        }
    }

    public List<Idioma> getIdiomasOrigen() {
        return idiomasOrigen;
    }

    public void setIdiomasOrigen(List<Idioma> idiomasOrigen) {
        this.idiomasOrigen = idiomasOrigen;
    }

    public List<Idioma> getIdiomasDestino() {
        return idiomasDestino;
    }

    public void setIdiomasDestino(List<Idioma> idiomasDestino) {
        this.idiomasDestino = idiomasDestino;
    }

}
