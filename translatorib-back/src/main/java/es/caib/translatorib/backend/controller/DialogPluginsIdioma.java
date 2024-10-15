package es.caib.translatorib.backend.controller;

import es.caib.translatorib.backend.model.DialogResult;
import es.caib.translatorib.backend.util.UtilJSF;
import es.caib.translatorib.core.api.model.Idioma;
import es.caib.translatorib.core.api.model.ParejaIdiomas;
import es.caib.translatorib.core.api.model.Plugin;
import es.caib.translatorib.core.api.model.comun.Propiedad;
import es.caib.translatorib.core.api.model.types.TypeModoAcceso;
import es.caib.translatorib.core.api.model.types.TypeNivelGravedad;
import es.caib.translatorib.core.api.model.types.TypeParametroVentana;
import es.caib.translatorib.core.api.service.PluginService;
import es.caib.translatorib.core.api.util.UtilJSON;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

@ManagedBean
@ViewScoped
public class DialogPluginsIdioma extends ViewControllerBase  {

    @Inject
    private PluginService pluginService;

    private static final Logger LOG = LoggerFactory.getLogger(DialogPluginsIdioma.class);

    private static final String TIPO_BOLETIN_PROPIEDAD = "tipoBoletin";

    private String id;

    private Plugin data;

    private Plugin dataOriginal;

    // Lista de todas las posibles combinaciones de idiomas (origen, destino)
    private List<ParejaIdiomas> todasLasCombinaciones;

    // Lista de combinaciones seleccionadas
    private List<ParejaIdiomas> combinacionesSeleccionadas;

    /**
     * Propiedad seleccionada.
     */
    private Propiedad propiedadSeleccionada;


    public void load() {
        LOG.debug("init");

        todasLasCombinaciones = new ArrayList<>();
        combinacionesSeleccionadas = new ArrayList<>();

        data = pluginService.getPluginByCodigo(Long.valueOf(id));
        if (data.getIdiomasPermitidos() != null && !data.getIdiomasPermitidos().isEmpty()) {
            cargarCombinacionesPlugin(data.getIdiomasPermitidos());
        }
        if (data.getIdiomasFrontal() != null && !data.getIdiomasFrontal().isEmpty()) {
            cargarSeleccionados(data.getIdiomasFrontal());
        }

    }


    public void guardar() {

        if (this.combinacionesSeleccionadas == null || this.combinacionesSeleccionadas.isEmpty()) {
            UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("dict.seleccioneUnElemento"));
            return;
        }

        pluginService.actualizarPlg(data.getCodigo(), guardarCombinacionesComoString());
        final DialogResult result = new DialogResult();
        if (this.getModoAcceso() != null) {
            result.setModoAcceso(TypeModoAcceso.valueOf(this.getModoAcceso()));
        } else {
            result.setModoAcceso(TypeModoAcceso.CONSULTA);
        }

        result.setResult(data);
        UtilJSF.closeDialog(result);
    }


    public void cerrar() {
        if (data != null && dataOriginal != null && comprobarModificacion()) {
            PrimeFaces.current().executeScript("PF('confirmCerrar').show();");
        } else {
            cerrarDefinitivo();
        }
    }

    public boolean comprobarModificacion() {
        return false;
    }

    public void cerrarDefinitivo() {
        final DialogResult result = new DialogResult();
        if (Objects.isNull(this.getModoAcceso())) {
            this.setModoAcceso(TypeModoAcceso.CONSULTA.name());
        } else {
            result.setModoAcceso(TypeModoAcceso.valueOf(this.getModoAcceso()));
        }
        result.setCanceled(true);
        UtilJSF.closeDialog(result);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Plugin getData() {
        return data;
    }

    public void setData(Plugin data) {
        this.data = data;
    }

    public Propiedad getPropiedadSeleccionada() {
        return propiedadSeleccionada;
    }

    public void setPropiedadSeleccionada(Propiedad propiedadSeleccionada) {
        this.propiedadSeleccionada = propiedadSeleccionada;
    }

    public List<ParejaIdiomas> getCombinacionesSeleccionadas() {
        return combinacionesSeleccionadas;
    }

    public void setCombinacionesSeleccionadas(List<ParejaIdiomas> combinacionesSeleccionadas) {
        this.combinacionesSeleccionadas = combinacionesSeleccionadas;
    }

    public List<ParejaIdiomas> getTodasLasCombinaciones() {
        return todasLasCombinaciones;
    }

    public void setTodasLasCombinaciones(List<ParejaIdiomas> todasLasCombinaciones) {
        this.todasLasCombinaciones = todasLasCombinaciones;
    }

    // Método para convertir las combinaciones seleccionadas a un String
    public String guardarCombinacionesComoString() {
        return combinacionesSeleccionadas.stream()
                .map(pareja -> pareja.getOrigen().getIdioma() + " " + pareja.getOrigen().getLocale()
                        + "->" + pareja.getDestino().getIdioma() + " " + pareja.getDestino().getLocale())
                .collect(Collectors.joining(","));
    }

    // Método para cargar las combinaciones seleccionadas a partir de un String de plugin
    public void cargarCombinacionesPlugin(String combinacionesString) {
        String[] combinacionesArray = combinacionesString.split(",");
        todasLasCombinaciones.clear(); // Limpiar la lista de seleccionados
        for (String combinacionStr : combinacionesArray) {
            String[] partes = combinacionStr.split("->");
            if (partes.length == 2) {
                String[] origenParts = partes[0].trim().split(" ");
                String[] destinoParts = partes[1].trim().split(" ");
                if (origenParts.length == 2 && destinoParts.length == 2) {
                    Idioma origen = Idioma.fromString(origenParts[0], origenParts[1]);
                    Idioma destino = Idioma.fromString(destinoParts[0], destinoParts[1]);
                    if (origen != null && destino != null) {
                        todasLasCombinaciones.add(new ParejaIdiomas(origen, destino));
                    }
                }
            }
        }

    }

    // Método para cargar las combinaciones seleccionadas a partir de un String de plugin
    public void cargarSeleccionados(String combinacionesString) {
        String[] combinacionesArray = combinacionesString.split(",");
        combinacionesSeleccionadas.clear(); // Limpiar la lista de seleccionados
        for (String combinacionStr : combinacionesArray) {
            String[] partes = combinacionStr.split("->");
            if (partes.length == 2) {
                String[] origenParts = partes[0].trim().split(" ");
                String[] destinoParts = partes[1].trim().split(" ");
                if (origenParts.length == 2 && destinoParts.length == 2) {
                    Idioma origen = Idioma.fromString(origenParts[0], origenParts[1]);
                    Idioma destino = Idioma.fromString(destinoParts[0], destinoParts[1]);
                    if (origen != null && destino != null) {
                        combinacionesSeleccionadas.add(new ParejaIdiomas(origen, destino));
                    }
                }
            }
        }
    }

    // Método para cargar las combinaciones seleccionadas a partir de un String
    public void cargarCombinacionesDesdeString(String combinacionesString) {
        String[] combinacionesArray = combinacionesString.split(",");
        combinacionesSeleccionadas.clear(); // Limpiar la lista de seleccionados
        for (String combinacionStr : combinacionesArray) {
            String[] partes = combinacionStr.split("->");
            if (partes.length == 2) {
                String[] origenParts = partes[0].trim().split(" ");
                String[] destinoParts = partes[1].trim().split(" ");
                if (origenParts.length == 2 && destinoParts.length == 2) {
                    Idioma origen = Idioma.fromString(origenParts[0], origenParts[1]);
                    Idioma destino = Idioma.fromString(destinoParts[0], destinoParts[1]);
                    if (origen != null && destino != null) {
                        combinacionesSeleccionadas.add(new ParejaIdiomas(origen, destino));
                    }
                }
            }
        }
    }
}
