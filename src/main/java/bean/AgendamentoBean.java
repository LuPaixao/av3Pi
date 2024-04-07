package bean;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import dao.AgendaDao;
import entities.Agenda;

@ManagedBean
public class AgendamentoBean {

	private Agenda agenda = new Agenda();
	private List<Agenda> lista;
	private Agenda agendaSelecionada;
	private Long quantidadeAgendas;
	private Date dataAtual;

	public Long getQuantidadeAgendas() {
		return quantidadeAgendas;
	}

	public void setQuantidadeAgendas(Long quantidadeAgendas) {
		this.quantidadeAgendas = quantidadeAgendas;
	}

	public void counterAgendas() {
		quantidadeAgendas = AgendaDao.counter();
		System.out.println("QNTD DE AGENDA" + quantidadeAgendas);
	}

	public String save() {
		try {
			if (!AgendaDao.verificarAgendamentoExistente(agenda)) {
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

	public String edit(Agenda agendaEdit) {
		try {
		
	        Agenda agendaOriginal = getAgendaById(agendaEdit.getId());
	        boolean nomeAlterado = !agendaOriginal.getPaciente().equals(agendaEdit.getPaciente());
	        System.out.println("NOME ORIGINAL" + agendaOriginal.getPaciente());
	        System.out.println("NOME EDITADO" + nomeAlterado);
	        System.out.println("DATA EDITADA" + nomeAlterado);
	        if (nomeAlterado || !AgendaDao.verificarAgendamentoExistente(agendaEdit)) {
	        	System.out.println(agendaEdit);
	            AgendaDao.edit(agendaEdit);
	            showMessage("Agenda editada com sucesso!", "");
	            System.out.println("FOI EDITADO");
	            return "agendamentos";
	        } else {
	            showMessage("Erro! Já existe um agendamento para a mesma data, hora e médico", "");
	            System.out.println("NAO FOI EDITADO PQ JA EXISTE UM AGENDAMENTO COM DATA/HORA/MEDICO IGUAL");
	            return "agendamentos";
	        }
	    } catch (Exception e) {
	        showMessage("Erro", e.getMessage());
	        return null;
	    }

	}

	public void showMessage(String title, String content) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, title, content));
	}

	public String delete(int id) {
		AgendaDao.delete(id);
		lista = AgendaDao.listar();
		System.out.println("agenda deletada" + id);
		return "agendamentos";
	}

	public Agenda getAgendaById(int id) {
		return AgendaDao.getAgendaById(id);
	}

	public Agenda getAgenda() {
		return agenda;
	}

	public void setAgenda(Agenda agenda) {
		this.agenda = agenda;
	}

	public List<Agenda> getLista() {
		if (lista == null) {
			lista = AgendaDao.listar();
			ordenarLista();
		}
		return lista;
	}

	public void setLista(List<Agenda> lista) {
		this.lista = lista;
	}

	public Agenda getAgendaSelecionada() {
		return agendaSelecionada;
	}

	public void setAgendaSelecionada(Agenda agendaSelecionada) {
		this.agendaSelecionada = agendaSelecionada;
	}

	public void ordenarLista() {
		Collections.sort(lista, Comparator.comparing(Agenda::getId));
	}

	@PostConstruct
	public void init() {
		dataAtual = new Date();
	}

	public Date getDataAtual() {
		return dataAtual;
	}

	public void setDataAtual(Date dataAtual) {
		this.dataAtual = dataAtual;
	}

}
