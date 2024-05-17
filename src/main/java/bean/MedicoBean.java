package bean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import dao.MedicoDao;
import entities.Medico;

@ManagedBean
@ViewScoped
public class MedicoBean {

	private Medico medico = new Medico();
	private List<Medico> listarMedicos;

	public void salvar() {
		MedicoDao.salvar(getMedico());
		medico = new Medico();
	}

	public List<Medico> listarMedicos() {
		if (listarMedicos == null) {
			listarMedicos = MedicoDao.listarMedicos();
		}
		return MedicoDao.listarMedicos();
	}

	public Medico getMedico() {
		return medico;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}

}
