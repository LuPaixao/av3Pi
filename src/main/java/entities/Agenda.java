package entities;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
public class Agenda {
	//Identity -> o banco de dados que lida com a geração dos valores das chaves primarias
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "nome_paciente", length = 50)
	private String paciente;
	@Column(name = "nome_medico", length = 50)
	private String medico;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_hora_consulta")
	private Date dataHoraConsulta;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPaciente() {
		return paciente;
	}
	public void setPaciente(String paciente) {
		this.paciente = paciente;
	}
	public String getMedico() {
		return medico;
	}
	public void setMedico(String medico) {
		this.medico = medico;
	}
	public Date getDataHoraConsulta() {
		return dataHoraConsulta;
	}
	public void setDataHoraConsulta(Date dataHoraConsulta) {
		this.dataHoraConsulta = dataHoraConsulta;
	}
}
