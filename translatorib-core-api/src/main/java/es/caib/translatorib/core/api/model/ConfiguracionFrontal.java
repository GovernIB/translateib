package es.caib.translatorib.core.api.model;

import java.util.List;
import java.util.Objects;

/**
 * Datos de una Configuración Global
 *
 * @author Indra
 **/
public class ConfiguracionFrontal implements Cloneable {

    /**
     * Codigo
     */
    private Long codigo;

    /**
     * Codigo
     */
    private Plugin plugin;

    /**
     * Propiedad
     */
    private Idioma idiomaOrigen;

    /**
     * Valor
     */
    private Idioma idiomaDestino;

    /**
     * Descripcion
     */
    private String pluginsSoportados;

    /*
     * Instancia una nueva Configuracion global dto.
     */
    public ConfiguracionFrontal() {
        //Constructor vacio
    }


    public ConfiguracionFrontal(ConfiguracionFrontal otro) {
    	 if (otro != null) {
             this.codigo = otro.codigo;
             this.plugin = otro.plugin;
             this.idiomaDestino = otro.idiomaDestino;
             this.idiomaOrigen = otro.idiomaOrigen;
             this.pluginsSoportados = otro.pluginsSoportados;
         }
	}

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public void setPlugin(Plugin plugin) {
        this.plugin = plugin;
    }

    public Idioma getIdiomaOrigen() {
        return idiomaOrigen;
    }

    public void setIdiomaOrigen(Idioma idiomaOrigen) {
        this.idiomaOrigen = idiomaOrigen;
    }

    public Idioma getIdiomaDestino() {
        return idiomaDestino;
    }

    public void setIdiomaDestino(Idioma idiomaDestino) {
        this.idiomaDestino = idiomaDestino;
    }

    public String getPluginsSoportados() {
        return pluginsSoportados;
    }

    public void setPluginsSoportados(String pluginsSoportados) {
        this.pluginsSoportados = pluginsSoportados;
    }

    @Override
    public ConfiguracionFrontal clone() {
    	return new ConfiguracionFrontal(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ConfiguracionFrontal that = (ConfiguracionFrontal) o;
        return codigo.equals(that.codigo) && plugin.equals(that.plugin) && idiomaOrigen.equals(that.idiomaOrigen) && idiomaDestino.equals(that.idiomaDestino) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo, plugin, idiomaOrigen, idiomaDestino);
    }
}
