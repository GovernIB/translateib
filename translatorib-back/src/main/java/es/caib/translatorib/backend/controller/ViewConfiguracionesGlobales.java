package es.caib.translatorib.backend.controller;


import es.caib.translatorib.backend.model.DialogResult;
import es.caib.translatorib.backend.util.UtilJSF;
import es.caib.translatorib.core.api.model.ConfiguracionGlobal;
import es.caib.translatorib.core.api.model.filtro.ConfiguracionGlobalFiltro;
import es.caib.translatorib.core.api.model.types.TypeModoAcceso;
import es.caib.translatorib.core.api.model.types.TypeNivelGravedad;
import es.caib.translatorib.core.api.model.types.TypeParametroVentana;
import es.caib.translatorib.core.api.service.ConfiguracionGlobalService;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

/**
 * Controlador Configuracion global.
 *
 * @author Indra
 */
@ManagedBean
@ViewScoped
public class ViewConfiguracionesGlobales extends ViewControllerBase {

    private static final long serialVersionUID = -7992474170848445700L;

    private static final Logger LOG = LoggerFactory.getLogger(ViewConfiguracionesGlobales.class);

    /**
     * Model de dades emprat pel compoment dataTable de primefaces.
     */
    private LazyDataModel<ConfiguracionGlobal> lazyModel;

    @Inject
    private ConfiguracionGlobalService configuracionGlobalService;

    @Inject
    private SessionBean	sessionBean;

    /**
     * Dato seleccionado
     */
    private ConfiguracionGlobal datoSeleccionado;

    /**
     * Filtro
     **/
    private ConfiguracionGlobalFiltro filtro;


    public LazyDataModel<ConfiguracionGlobal> getLazyModel() {
        return lazyModel;
    }


    // ACCIONS

    /**
     * Carrega la unitat orgànica i els procediments.
     */
    public void init() {
        LOG.debug("load");
        // Control acceso
        UtilJSF.verificarAcceso();

        // Inicializamos combos/desplegables/inputs/filtro
        filtro = new ConfiguracionGlobalFiltro();

        //filtro.setIdUA(sessionBean.getUnidadActiva().getCodigo());
        filtro.setIdioma(sessionBean.getLang());

        // Generamos una búsqueda
        buscar();
    }

    public void update() {
        buscar();
    }

    public void buscarAvanzada() {
        System.out.println();
    }

    public void buscar() {
        lazyModel = new LazyDataModel<>() {
            private static final long serialVersionUID = 1L;

            @Override
            public String getRowKey(ConfiguracionGlobal objeto) {
                return objeto.getCodigo().toString();
            }

            @Override
            public ConfiguracionGlobal getRowData(String rowKey) {
                for (ConfiguracionGlobal pers : getWrappedData()) {
                    if (pers.getCodigo().toString().equals(rowKey)) return pers;
                }
                return null;
            }


            public int count(Map<String, FilterMeta> filterBy) {
                try {
                    return configuracionGlobalService.countConfGlobalByFiltro(filtro);
                } catch (Exception e) {
                    LOG.error("Error llamando", e);
                    return 0;
                }
            }

            @Override
            public List<ConfiguracionGlobal> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
                try {
                    filtro.setIdioma(sessionBean.getLang());
                    if (sortBy != null && !sortBy.isEmpty()) {
                        SortMeta sortMeta = sortBy.values().iterator().next();
                        SortOrder sortOrder = sortMeta.getOrder();
                        if (sortOrder != null) {
                            filtro.setAscendente(sortOrder.equals(SortOrder.ASCENDING));
                        }
                        filtro.setOrderBy(sortMeta.getField());
                    }
                    return configuracionGlobalService.listConfGlobalByFiltro(filtro);
                } catch (Exception e) {
                    LOG.error("Error llamando", e);
                    return new ArrayList<>();
                }
            }
        };
    }

    public void editarConfiguracionGlobal() {
        if (datoSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("msg.seleccioneElemento"));
        } else {
            if (datoSeleccionado.getNoModificable()) {
                abrirVentana(TypeModoAcceso.CONSULTA);
            } else {
                abrirVentana(TypeModoAcceso.EDICION);
            }
        }
    }

    public void consultarConfiguracionGlobal() {
        if (datoSeleccionado != null) {
            abrirVentana(TypeModoAcceso.CONSULTA);
        }
    }


    public void returnDialogo(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();

        // Verificamos si se ha modificado
        if (!respuesta.isCanceled() && !TypeModoAcceso.CONSULTA.equals(respuesta.getModoAcceso())) {
            this.buscar();
        }
    }

    private void abrirVentana(TypeModoAcceso modoAcceso) {
        // Muestra dialogo
        final Map<String, String> params = new HashMap<>();
        if (this.datoSeleccionado != null && (modoAcceso == TypeModoAcceso.EDICION || modoAcceso == TypeModoAcceso.CONSULTA)) {
            params.put(TypeParametroVentana.ID.toString(), this.datoSeleccionado.getCodigo().toString());
        }
        UtilJSF.openDialog("dialogConfiguracionGlobal", modoAcceso, params, true, 780, 450);
    }

    public ConfiguracionGlobal getDatoSeleccionado() {
        return datoSeleccionado;
    }

    public void setDatoSeleccionado(ConfiguracionGlobal datoSeleccionado) {
        this.datoSeleccionado = datoSeleccionado;
    }

    public ConfiguracionGlobalFiltro getFiltro() {
        return filtro;
    }

    public void setFiltro(ConfiguracionGlobalFiltro filtro) {
        this.filtro = filtro;
    }

    public void setFiltroTexto(String texto) {
        if (Objects.nonNull(this.filtro)) {
            this.filtro.setTexto(texto);
        }
    }

    public String getFiltroTexto() {
        if (Objects.nonNull(this.filtro)) {
            return this.filtro.getTexto();
        }
        return "";
    }

}
