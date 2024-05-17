package bean;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;

import dao.AgendaDao;
import entities.Agenda;
import entities.Medico;
import enuns.StatusAgendamento;

@ManagedBean
public class MedicoBean {

	private Medico medico = new Medico();
	private List<Medico> lista;
	private Agenda agendaSelecionada;
	

	public String save() {
		try {
			if (!AgendaDao.verificarAgendamentoExistente(agenda)) {
				agenda.setStatus(StatusAgendamento.AGENDADO);
				AgendaDao.save(agenda);
				agenda = new Agenda();
				showMessage("Agendamento realizado com sucesso!", "");
				System.out.println("Agendamento criado!! ");
				return "agendamentos";
			}

			showMessage("Erro! Já existe um agendamento para a mesma data, hora e médico.", "");

			return "gerar_agendamento";
		} catch (IllegalArgumentException e) {
			showMessage("Erro", e.getMessage());
			return "gerar_agendamento";
		}

	}


	public List<Agenda> getLista() {
		if (lista == null) {
			lista = AgendaDao.listar();
			ordenarLista();
		}
		return lista;
	}

}
