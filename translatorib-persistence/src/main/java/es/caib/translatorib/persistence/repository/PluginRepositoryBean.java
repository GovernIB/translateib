package es.caib.translatorib.persistence.repository;

import es.caib.translatorib.persistence.model.JPlugin;
import es.caib.translatorib.service.model.Plugin;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Stateless
@Local(PluginRepository.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class PluginRepositoryBean extends AbstractCrudRepository<JPlugin, Long> implements PluginRepository {

	/** Constructor **/
	public PluginRepositoryBean() {
		super(JPlugin.class);
	}

	@Override
	public List<Plugin> lista(String filtro) {
		StringBuilder sql = new StringBuilder("SELECT J FROM JPlugin J");
		if (filtro != null && !filtro.isEmpty()) {
			sql.append(" WHERE LOWER(J.descripcion) like :filtro or LOWER(J.identificador) like :filtro or LOWER(J.classname) like :filtro or LOWER(J.url) like :filtro or LOWER(J.user) like :filtro or LOWER(J.pass) like :filtro ");
		}
		Query query = entityManager.createQuery(sql.toString(), JPlugin.class);
		if (filtro != null && !filtro.isEmpty()) {
			query.setParameter("filtro", "%" + filtro.toLowerCase() + "%");
		}
		List<JPlugin> jplugins = query.getResultList();
		List<Plugin> plugins = new ArrayList<Plugin>();
		if (jplugins != null) {
			for(JPlugin jPlugin : jplugins) {
				plugins.add(jPlugin.toModel());
			}
		}
		return plugins;
	}

	@Override
	public Plugin getPluginByCodigo(Long codigo) {
		JPlugin jplugin = entityManager.find(JPlugin.class, codigo);
		return jplugin.toModel();
	}

	@Override
	public void guardarPlugin(Plugin plugin) {
		if (plugin.isPorDefecto()) {
			JPlugin jpluginPorDefecto = getJPluginDefault();
			if (jpluginPorDefecto != null) {
				jpluginPorDefecto.setPorDefecto(false);
				entityManager.persist(jpluginPorDefecto);
			}
		}
		JPlugin jplugin = JPlugin.fromModel(plugin);
		entityManager.persist(jplugin);
	}

	@Override
	public void updatePlugin(Plugin plugin) {
		if (plugin.isPorDefecto()) {
			JPlugin jpluginPorDefecto = getJPluginDefault();
			if (jpluginPorDefecto != null) {
				jpluginPorDefecto.setPorDefecto(false);
				entityManager.persist(jpluginPorDefecto);
			}
		}
		JPlugin jplugin = entityManager.find(JPlugin.class, plugin.getCodigo());
		jplugin.merge(plugin);
		entityManager.persist(jplugin);
	}

	@Override
	public Plugin getPluginDefault() {
		Plugin plugin = null;
		JPlugin jplugin = getJPluginDefault();
		if (jplugin != null) {
			plugin = jplugin.toModel();
		}
		return plugin;
	}

	private JPlugin getJPluginDefault() {
		String sql ="SELECT J FROM JPlugin J WHERE J.porDefecto = 1";
		Query query = entityManager.createQuery(sql, JPlugin.class);
		List<JPlugin> jplugins = query.getResultList();
		JPlugin plugin = null;
		if (jplugins != null && !jplugins.isEmpty()) {
			plugin = jplugins.get(0);
		}
		return plugin;
	}

	@Override
	public Plugin getPluginByIdentificador(String identificador) {
		String sql ="SELECT J FROM JPlugin J WHERE J.identificador like :identificador";
		Query query = entityManager.createQuery(sql, JPlugin.class);
		query.setParameter("identificador",identificador);
		List<JPlugin> jplugins = query.getResultList();
		Plugin plugin = null;
		if (jplugins != null && !jplugins.isEmpty()) {
			plugin = jplugins.get(0).toModel();
		}
		return plugin;
	}

	@Override
	public Plugin getPluginByClassname(String classname) {
		String sql ="SELECT J FROM JPlugin J WHERE J.classname like 'es.caib.translatorib." + classname + "' ";
		Query query = entityManager.createQuery(sql, JPlugin.class);
		List<JPlugin> jplugins = query.getResultList();
		Plugin plugin = null;
		if (jplugins != null && !jplugins.isEmpty()) {
			plugin = jplugins.get(0).toModel();
		}
		return plugin;
	}

	@Override
	public void actualizarPlg(Long codigo, String idiomasFrontal) {
		JPlugin jplugin = entityManager.find(JPlugin.class, codigo);
		jplugin.setIdiomasFrontal(idiomasFrontal);
		entityManager.merge(jplugin);
	}

	@Override
	public void borrar(Plugin plugin) {
		JPlugin jplugin = entityManager.find(JPlugin.class, plugin.getCodigo());
		entityManager.remove(jplugin);
	}
}
