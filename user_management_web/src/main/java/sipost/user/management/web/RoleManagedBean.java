package sipost.user.management.web;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import sipost.user.management.common.IRole;
import sipost.user.management.ejb.EjbExeption;
import sipost.user.management.jpa.Role;
import org.jboss.logging.Logger;

@Named("roleBean")
@ApplicationScoped
public class RoleManagedBean implements IRole {
	private IRole oRoleBean;
	private List<Role> roles = new ArrayList<>();
	private Role oRole;
	private int selectedRoleid = 0;
	private Logger oLogger = Logger.getLogger(RoleManagedBean.class);
	private String errorMessage;

	private IRole getRoleBean() {
		errorMessage = null;
		if (oRoleBean == null) {
			try {
				InitialContext jndi = new InitialContext();
				oRoleBean = (IRole) jndi.lookup(IRole.jndiNAME);
			} catch (NamingException e) {
				oLogger.error(e.getMessage());
				errorMessage = e.getMessage();
				throw new WebExeption(e.getMessage());
			}
		}
		return oRoleBean;
	}

	@Override
	public List<Role> getAllRoles() {
		errorMessage = null;
		try {
			roles = getRoleBean().getAllRoles();
			return roles;
		} catch (EjbExeption e) {
			errorMessage = e.getMessage();
			throw new WebExeption(e.getMessage());
		}
	}

	@Override
	public Role getRoleById(int id) {
		errorMessage = null;
		try {
			oRole = getRoleBean().getRoleById(id);
			return oRole;
		} catch (EjbExeption e) {
			errorMessage = e.getMessage();
			throw new WebExeption(e.getMessage());
		}
	}

	@Override
	public void insertRole(Role role) {
		errorMessage = null;
		try {
			getRoleBean().insertRole(role);
		} catch (EjbExeption e) {
			errorMessage = e.getMessage();
			throw new WebExeption(e.getMessage());
		}

	}

	@Override
	public void deleteRole(int id) {
		errorMessage = null;
		try {
			getRoleBean().deleteRole(id);
			oRole = null;
		} catch (EjbExeption e) {
			errorMessage = e.getMessage();
			throw new WebExeption(e.getMessage());
		}

	}

	@Override
	public void updateRole(Role role) {
		errorMessage = null;
		try {
			getRoleBean().updateRole(role);
			oRole = null;
		} catch (EjbExeption e) {
			errorMessage = e.getMessage();
			oLogger.error("++++++++++++"+errorMessage);
			throw new WebExeption(e.getMessage());
		}catch (Exception e) {
			errorMessage = e.getMessage();
			oLogger.error("++++++++++++"+errorMessage+"++++++++++++");
			throw new WebExeption(e.getMessage());
		}
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public Role getoRole() {
		return oRole;
	}

	public Role newRole() {
		oRole = new Role();
		return oRole;
	}

	public void setoRole(Role oRole) {
		this.oRole = oRole;
	}

	public int getSelectedRoleid() {
		return selectedRoleid;
	}

	public void setSelectedRoleid(int selectedRoleid) {
		this.selectedRoleid = selectedRoleid;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

}
