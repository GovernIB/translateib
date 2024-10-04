package es.caib.translatorib.core.api.model;

import es.caib.translatorib.core.api.model.comun.Propiedad;

import java.util.ArrayList;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Plugin implements Cloneable {

    /** Codigo **/
    private Long codigo;

    /** Identificador */
    private String identificador;

    /** Descripción plugin */
    private String descripcion;

    /** Clase real */
    private String classname;

    /** User */
    private String prefijo;

    /** Idiomas permitidos **/
    private String idiomasPermitidos;

    /** Idiomas frontal **/
    private String idiomasFrontal;

    /** Lista serializada propiedades (codigo - valor) */
    private List<Propiedad> propiedades = new ArrayList<>();

    /** Por defecto */
    private boolean porDefecto;

    /** Seleccionado : Sólo se necesita en el viewConfiguracionFrontal **/
    private boolean seleccionado;

    public Plugin() {
        //Constructor vacio
    }

    public Plugin(Plugin otro) {
        if (otro != null) {
            this.codigo = otro.codigo;
            this.descripcion = otro.descripcion;
            this.classname = otro.classname;
            this.propiedades = otro.propiedades;
            this.prefijo = otro.prefijo;
            this.identificador = otro.identificador;
            if (this.propiedades == null) {
                this.propiedades = new ArrayList<>();
            }
            this.idiomasFrontal = otro.idiomasFrontal;
            this.idiomasPermitidos = otro.idiomasPermitidos;
        }
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getPrefijo() {
        return prefijo;
    }

    public void setPrefijo(String prefijo) {
        this.prefijo = prefijo;
    }

    public List<Propiedad> getPropiedades() {
        return propiedades;
    }

    public void setPropiedades(List<Propiedad> propiedades) {
        this.propiedades = propiedades;
    }

    public boolean isPorDefecto() {
        return porDefecto;
    }

    public void setPorDefecto(boolean porDefecto) {
        this.porDefecto = porDefecto;
    }

    public boolean isSeleccionado() {
        return seleccionado;
    }

    public void setSeleccionado(boolean seleccionado) {
        this.seleccionado = seleccionado;
    }

    public String getIdiomasPermitidos() {
        return idiomasPermitidos;
    }

    public void setIdiomasPermitidos(String idiomasPermitidos) {
        this.idiomasPermitidos = idiomasPermitidos;
    }

    public String getIdiomasFrontal() {
        return idiomasFrontal;
    }

    public void setIdiomasFrontal(String idiomasFrontal) {
        this.idiomasFrontal = idiomasFrontal;
    }

    @Override
    public Plugin clone() {
        return new Plugin(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Plugin pluginDTO = (Plugin) o;
        return Objects.equals(codigo, pluginDTO.codigo) && Objects.equals(descripcion, pluginDTO.descripcion) && Objects.equals(prefijo, pluginDTO.prefijo) && Objects.equals(classname, pluginDTO.classname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo, descripcion, classname, prefijo, propiedades);
    }

    @Override
    public String toString() {
        return "PluginDTO{" +
                "codigo=" + codigo +
                ", descripcion='" + descripcion + '\'' +
                ", classname='" + classname + '\'' +
                ", prefijo='" + prefijo + '\'' +
                ", propiedades=" + propiedades +
                '}';
    }
}
