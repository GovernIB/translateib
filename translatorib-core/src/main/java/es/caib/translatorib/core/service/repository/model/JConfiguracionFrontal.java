package es.caib.translatorib.core.service.repository.model;

import es.caib.translatorib.core.api.model.ConfiguracionFrontal;
import es.caib.translatorib.core.api.model.ConfiguracionGlobal;
import es.caib.translatorib.core.api.model.Idioma;
import es.caib.translatorib.core.api.model.Plugin;

import javax.persistence.*;

/**
 * La Clase J configuracion global.
 */
@Entity
@SequenceGenerator(name = "configuracion-sequence", sequenceName = "TIB_CNFFRO_SEQ", allocationSize = 1)
@Table(name = "TIB_CNFFRO", indexes = {@Index(name = "PK_TIB_CNFFRO", columnList = "CFF_CODIGO")})

@NamedQueries({@NamedQuery(name = JConfiguracionFrontal.FIND_BY_ID, query = "select p from JConfiguracionFrontal p where p.codigo = :id")})

public class JConfiguracionFrontal {

    /**
     * Consulta FIND_BY_ID.
     */
    public static final String FIND_BY_ID = "ConfiguracionFrontal.FIND_BY_ID";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "configuracion-sequence")
    @Column(name = "CFF_CODIGO", nullable = false)
    private Long codigo;

    /**
     * JPLugin
     **/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CFF_PLUGIN")
    private JPlugin jplugin;

    /**
     * Valor
     **/
    @Column(name = "CFF_IDIORI", length = 20)
    private String idiomaOrigen;

    /**
     * Descripcion
     **/
    @Column(name = "CFF_IDIDES", length = 20)
    private String idiomaDestino;

    /**
     * Indica si es no modificable.
     **/
    @Column(name = "CFF_PLGSOP", length = 1000)
    private String pluginsSorportados;

    /**
     * Obtiene codigo.
     *
     * @return codigo
     */
    public Long getCodigo() {
        return codigo;
    }

    /**
     * Establece codigo.
     *
     * @param id id
     */
    public void setCodigo(Long id) {
        this.codigo = id;
    }

    public JPlugin getJplugin() {
        return jplugin;
    }

    public void setJplugin(JPlugin jplugin) {
        this.jplugin = jplugin;
    }

    public String getIdiomaOrigen() {
        return idiomaOrigen;
    }

    public void setIdiomaOrigen(String idiomaOrigen) {
        this.idiomaOrigen = idiomaOrigen;
    }

    public String getIdiomaDestino() {
        return idiomaDestino;
    }

    public void setIdiomaDestino(String idiomaDestino) {
        this.idiomaDestino = idiomaDestino;
    }

    public String getPluginsSorportados() {
        return pluginsSorportados;
    }

    public void setPluginsSorportados(String pluginsSorportados) {
        this.pluginsSorportados = pluginsSorportados;
    }

    /**
     * toModel
     * @return El modelo
     */
    public ConfiguracionFrontal toModel() {
        ConfiguracionFrontal configuracionFrontal = new ConfiguracionFrontal();
        configuracionFrontal.setCodigo(codigo);
        if (idiomaOrigen != null) {
            configuracionFrontal.setIdiomaOrigen(Idioma.fromString(idiomaOrigen));
        }
        if (idiomaDestino != null) {
            configuracionFrontal.setIdiomaDestino(Idioma.fromString(idiomaDestino));
        }
        configuracionFrontal.setPluginsSoportados(this.pluginsSorportados);
        if (this.jplugin != null) {
            configuracionFrontal.setPlugin(this.jplugin.toModel());
        }
        return configuracionFrontal;
    }

    /**
     * From model
     * @param configuracionFrontal El modelo
     * @return el JConfiguracionGlobal
     */
    public static JConfiguracionFrontal fromModel(ConfiguracionFrontal configuracionFrontal) {
        JConfiguracionFrontal jconfiguracionFrontal = new JConfiguracionFrontal();
        jconfiguracionFrontal.setCodigo(configuracionFrontal.getCodigo());
        jconfiguracionFrontal.setIdiomaOrigen(configuracionFrontal.getIdiomaOrigen().toString());
        jconfiguracionFrontal.setIdiomaDestino(configuracionFrontal.getIdiomaDestino().toString());
        jconfiguracionFrontal.setJplugin(null);
        jconfiguracionFrontal.setPluginsSorportados(configuracionFrontal.getPluginsSoportados());
        if (configuracionFrontal.getPlugin() != null) {
            jconfiguracionFrontal.setJplugin(JPlugin.fromModel(configuracionFrontal.getPlugin()));
        }
        return jconfiguracionFrontal;
    }

    /**
     * From configuracionFrontal
     * @param configuracionFrontal El modelo
     *
     */
    public void merge(ConfiguracionFrontal configuracionFrontal) {
        this.setCodigo(configuracionFrontal.getCodigo());
        if (configuracionFrontal.getIdiomaOrigen() != null) {
            this.setIdiomaOrigen(configuracionFrontal.getIdiomaOrigen().toString());
        } else {
            this.setIdiomaOrigen(null);
        }
        if (configuracionFrontal.getIdiomaDestino() != null) {
            this.setIdiomaDestino(configuracionFrontal.getIdiomaDestino().toString());
        } else {
            this.setIdiomaDestino(null);
        }
        this.setJplugin(null);
        this.setPluginsSorportados(configuracionFrontal.getPluginsSoportados());
        if (configuracionFrontal.getPlugin() != null) {
            this.setJplugin(JPlugin.fromModel(configuracionFrontal.getPlugin()));
        }
    }
}
