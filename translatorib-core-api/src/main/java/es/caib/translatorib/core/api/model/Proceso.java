package es.caib.translatorib.core.api.model;
import es.caib.translatorib.core.api.model.comun.Propiedad;

import java.util.List;


/**
 * The type Proceso grid dto.
 */
public class Proceso  {


  /** Codigo. ***/
  private Long codigo;

  /**
   * Descripcion
   */
  private String descripcion;

  /**
   * Identificador del proceso
   */
  private String identificadorProceso;

  /**
   * Cron
   */
  private String cron;

  /**
   * Activo
   */
  private Boolean activo;

  /**
   * Parametros de invocacion
   */
  private List<Propiedad> parametrosInvocacion;

  public Proceso(){
    super();
  }

  public Proceso(Proceso otro) {
    this.identificadorProceso = otro.identificadorProceso;
    this.cron = otro.cron;
    this.activo = otro.activo;
  }

  /**
   * Obtiene codigo.
   *
   * @return  codigo
   */
  public Long getCodigo() {
    return codigo;
  }

  /**
   * Establece codigo.
   *
   * @param codigo  codigo to set
   */
  public void setCodigo(final Long codigo) {
    this.codigo = codigo;
  }

  /**
   * Obtiene descripcion.
   *
   * @return  descripcion
   */
  public String getDescripcion() {
    return descripcion;
  }

  /**
   * Establece descripcion.
   *
   * @param descripcion  descripcion
   */
  public void setDescripcion(final String descripcion) {
    this.descripcion = descripcion;
  }


  /**
   * Obtiene identificador proceso.
   *
   * @return  identificador proceso
   */
  public String getIdentificadorProceso() {
    return identificadorProceso;
  }

  /**
   * Establece identificador proceso.
   *
   * @param identificadorProceso  identificador proceso
   */
  public void setIdentificadorProceso(final String identificadorProceso) {
    this.identificadorProceso = identificadorProceso;
  }

  /**
   * Obtiene cron.
   *
   * @return  cron
   */
  public String getCron() {
    return cron;
  }

  /**
   * Establece cron.
   *
   * @param cron  cron
   */
  public void setCron(final String cron) {
    this.cron = cron;
  }

  /**
   * Obtiene activo.
   *
   * @return  activo
   */
  public Boolean getActivo() {
    return activo;
  }

  /**
   * Establece activo.
   *
   * @param activo  activo
   */
  public void setActivo(final Boolean activo) {
    this.activo = activo;
  }

  /**
   * Castea un object[] en ProcesoGrid
   *
   * @param resultado  resultado
   * @return proceso grid dto
   */
  public static Proceso cast(final Object[] resultado) {
    final Proceso proceso = new Proceso();
    proceso.setCodigo(Long.valueOf(resultado[0].toString()));
    proceso.setDescripcion(resultado[1].toString());
    proceso.setIdentificadorProceso(resultado[2].toString());
    proceso.setCron((resultado[3] == null) ? "" : resultado[3].toString());
    proceso.setActivo(Boolean.valueOf(resultado[4].toString()));
    return proceso;
  }

  /**
   * Obtiene parametrosInvocacion.
   *
   * @return parametrosInvocacion parametros invocacion
   */
  public List<Propiedad> getParametrosInvocacion() {
    return parametrosInvocacion;
  }

  /**
   * Establece parametrosInvocacion.
   *
   * @param parametrosInvocacion parametrosInvocacion a establecer
   */
  public void setParametrosInvocacion(final List<Propiedad> parametrosInvocacion) {
    this.parametrosInvocacion = parametrosInvocacion;
  }

  @Override
  public Proceso clone() {
    return new Proceso(this);
  }

}
