/**
 * Clase ModuleConfig.java
 */
package es.caib.translatorib.backend.model.comun;

/**
 * @author Indra
 *
 */
import org.springframework.stereotype.Component;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.bean.ManagedBean;

//@ApplicationScoped
@ManagedBean(name = "negocioModuleConfig")
@ApplicationScoped
public final class ModuleConfig {

	/** Entorno. **/
	private String entorno;

	/** Version. **/
	private String version;

	/** Commit svn. **/
	private String commitSvn;

	/** Commit git. **/
	private String commitGit;

	/**
	 * @return the entorno
	 */
	public String getEntorno() {
		return entorno;
	}

	/**
	 * @param entorno the entorno to set
	 */
	public void setEntorno(final String entorno) {
		this.entorno = entorno;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(final String version) {
		this.version = version;
	}

	public String getCommitSvn() {
		return commitSvn;
	}

	public void setCommitSvn(final String commitSvn) {
		this.commitSvn = commitSvn;
	}

	public String getCommitGit() {
		return commitGit;
	}

	public void setCommitGit(final String commitGit) {
		this.commitGit = commitGit;
	}

}
