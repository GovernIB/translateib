package es.caib.translatorib.core.service.repository.model;

import es.caib.translatorib.core.api.model.Plugin;
import es.caib.translatorib.core.api.model.comun.Propiedad;
import es.caib.translatorib.core.api.util.UtilJSON;

import javax.persistence.*;
import java.util.List;

/**
 * JPlugin
 * SQL:
 * CREATE TABLE TIB_PLUGIN
 * (
 *  PLG_CODIGO NUMBER(18) NOT NULL,
 *  PLG_DESCR VARCHAR2(255) NOT NULL,
 *  PLG_CLASS VARCHAR2(500),
 *  PLG_PREFIJO VARCHAR2(50) NOT NULL,
 *  PLG_PROPS VARCHAR2(4000),
 *  PLG_PORDEF NUMBER(1) DEFAULT 0 NOT NULL,
 *  CONSTRAINT PK_TIB_PLUGIN PRIMARY KEY (PLG_CODIGO)
 *  );
 *
 */
@Entity
@Table(name = "TIB_PLUGIN")
public class JPlugin implements IModelApi {

    /** Serial Version UID. **/
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "TIB_PLUGIN_SEQ", allocationSize = 1, sequenceName = "TIB_PLUGIN_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TIB_PLUGIN_SEQ")
    @Column(name = "PLG_CODIGO", unique = true, nullable = false, precision = 18)
    private Long codigo;

    /** Descripción plugin */
    @Column(name = "PLG_IDENTI", nullable = false)
    private String identificador;

    /** Descripción plugin */
    @Column(name = "PLG_DESCR", nullable = false)
    private String descripcion;

    /** Clase real */
    @Column(name = "PLG_CLASS", nullable = true, length = 500)
    private String classname;

    /** Idiomas permitidos **/
    @Column(name = "PLG_IDIPER", nullable = true, length = 500)
    private String idiomasPermitidos;

    /** Idiomas permitidos **/
    @Column(name = "PLG_IDIFRO", nullable = true, length = 500)
    private String idiomasFrontal;

    /** User */
    @Column(name = "PLG_PREFIJO", nullable = false, length = 50)
    private String prefijo;

    /** Lista serializada propiedades (codigo - valor) */
    @Column(name = "PLG_PROPS", length = 4000)
    private String propiedades;

    /** Clase real */
    @Column(name = "PLG_PORDEF", nullable = false, precision = 1, scale = 0)
    private boolean porDefecto;

    /** Constructor. **/
    public JPlugin() {
        super();
    }

    /**
     * @return the codigo
     */
    public Long getCodigo() {
        return codigo;
    }

    /**
     * @param codigo
     *            the codigo to set
     */
    public void setCodigo(final Long codigo) {
        this.codigo = codigo;
    }

    /**
     * Get identificador.
     * @return identificador
     */
    public String getIdentificador() {
        return identificador;
    }

    /**
     * Set identificador.
     * @param identificador     Identificador
     */
    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion
     *            the descripcion to set
     */
    public void setDescripcion(final String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the propiedades
     */
    public String getPropiedades() {
        return propiedades;
    }

    /**
     * @param propiedades
     *            the propiedades to set
     */
    public void setPropiedades(final String propiedades) {
        this.propiedades = propiedades;
    }

    /**
     * @return the clase
     */
    public String getClassname() {
        return classname;
    }

    /**
     * @param classname the clase to set
     */
    public void setClassname(final String classname) {
        this.classname = classname;
    }


    public boolean isPorDefecto() {
        return porDefecto;
    }

    public void setPorDefecto(boolean porDefecto) {
        this.porDefecto = porDefecto;
    }

    public String getPrefijo() {
        return prefijo;
    }

    public void setPrefijo(String prefijo) {
        this.prefijo = prefijo;
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

    /**
     * toModel.
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public Plugin toModel() {
        final Plugin plugin = new Plugin();
        plugin.setCodigo(this.getCodigo());
        plugin.setDescripcion(this.getDescripcion());
        plugin.setPrefijo(this.getPrefijo());
        plugin.setDescripcion(this.getDescripcion());
        plugin.setPropiedades((List<Propiedad>) UtilJSON.fromListJSON(propiedades, Propiedad.class));
        plugin.setClassname(this.getClassname());
        plugin.setPorDefecto(this.isPorDefecto());
        plugin.setIdentificador(this.getIdentificador());
        plugin.setIdiomasPermitidos(this.getIdiomasPermitidos());
        plugin.setIdiomasFrontal(this.getIdiomasFrontal());
        return plugin;
    }

    /**
     * From model.
     *
     * @param plugin
     * @return
     */
    public static JPlugin fromModel(final Plugin plugin) {
        JPlugin jPlugin = null;
        if (plugin != null) {
            jPlugin = new JPlugin();
            jPlugin.setCodigo(plugin.getCodigo());
            jPlugin.setDescripcion(plugin.getDescripcion());
            jPlugin.setPrefijo(plugin.getPrefijo());
            jPlugin.setDescripcion(plugin.getDescripcion());
            jPlugin.setPropiedades(UtilJSON.toJSON(plugin.getPropiedades()));
            jPlugin.setClassname(plugin.getClassname());
            jPlugin.setPorDefecto(plugin.isPorDefecto());
            jPlugin.setIdentificador(plugin.getIdentificador());
            jPlugin.setIdiomasPermitidos(plugin.getIdiomasPermitidos());
            jPlugin.setIdiomasFrontal(plugin.getIdiomasFrontal());
        }
        return jPlugin;
    }

    /**
     * Mergear.
     * @param plugin Plugin
     */
    public void merge(Plugin plugin) {
        this.setDescripcion(plugin.getDescripcion());
        this.setPrefijo(plugin.getPrefijo());
        this.setDescripcion(plugin.getDescripcion());
        this.setPropiedades(UtilJSON.toJSON(plugin.getPropiedades()));
        this.setClassname(plugin.getClassname());
        this.setPorDefecto(plugin.isPorDefecto());
        this.setIdentificador(plugin.getIdentificador());
        this.setIdiomasPermitidos(plugin.getIdiomasPermitidos());
        this.setIdiomasFrontal(plugin.getIdiomasFrontal());
    }
}
