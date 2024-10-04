package es.caib.translatorib.backend.controller;


import es.caib.translatorib.backend.model.DialogResult;
import es.caib.translatorib.backend.util.UtilJSF;
import es.caib.translatorib.core.api.model.ConfiguracionGlobal;
import es.caib.translatorib.core.api.model.types.TypeModoAcceso;
import es.caib.translatorib.core.api.service.ConfiguracionGlobalService;
import es.caib.translatorib.core.api.model.types.TypeNivelGravedad;
import org.primefaces.PrimeFaces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import java.util.Objects;

/**
 * Controlador para editar un DialogConfiguracionGlobal.
 *
 * @author Indra
 */
@ManagedBean
@ViewScoped
public class DialogConfiguracionGlobal extends ViewControllerBase {
    
    private static final Logger LOG = LoggerFactory.getLogger(DialogConfiguracionGlobal.class);

    private String id;

    private ConfiguracionGlobal data;

    private ConfiguracionGlobal dataOriginal;

    @Inject
    private ConfiguracionGlobalService configuracionGlobalService;

    public void load() {
        LOG.debug("init");

        data = new ConfiguracionGlobal();
        if (this.isEdicion() || this.isConsulta()) {
            data = configuracionGlobalService.findConfGlobalById(Long.valueOf(id));
            dataOriginal = data.clone();
        }
    }

    public void guardar() {

        if (this.data.getCodigo() != null) {
            configuracionGlobalService.updateConfGlobal(this.data);
        }

        // Retornamos resultado
        final DialogResult result = new DialogResult();
        if (Objects.isNull(this.getModoAcceso())) {
            this.setModoAcceso(TypeModoAcceso.CONSULTA.name());
        } else {
            result.setModoAcceso(TypeModoAcceso.valueOf(this.getModoAcceso()));
        }
        result.setResult(data);
        UtilJSF.closeDialog(result);
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

    public void cerrar() {
        if (data != null && dataOriginal != null && comprobarModificacion()) {
            PrimeFaces.current().executeScript("PF('confirmCerrar').show();");
        } else {
            cerrarDefinitivo();
        }
    }

    private boolean comprobarModificacion() {
        return false;
       // return UtilComparador.compareTo(data.getCodigo(), dataOriginal.getCodigo()) != 0 || UtilComparador.compareTo(data.getValor(), dataOriginal.getValor()) != 0 || UtilComparador.compareTo(data.getDescripcion(), dataOriginal.getDescripcion()) != 0;
    }

    public void traducir() {
        UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, "No está implementado la traduccion", true);
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ConfiguracionGlobal getData() {
        return data;
    }

    public void setData(ConfiguracionGlobal data) {
        this.data = data;
    }

    public ConfiguracionGlobal getDataOriginal() {
        return dataOriginal;
    }

    public void setDataOriginal(ConfiguracionGlobal dataOriginal) {
        this.dataOriginal = dataOriginal;
    }

}
