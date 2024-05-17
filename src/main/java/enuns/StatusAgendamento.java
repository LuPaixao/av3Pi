package enuns;

public enum StatusAgendamento {
	AGENDADO("Agendado"), CANCELADO("Cancelado"), REALIZADO("Realizado");

	private String descricao;

	StatusAgendamento(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
