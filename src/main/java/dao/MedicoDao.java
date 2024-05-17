package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.swing.JFrame;

import entities.Agenda;
import entities.Medico;
import enuns.StatusAgendamento;
import util.JPAUtil;

public class MedicoDao {

	JFrame frame = new JFrame();

	public static void save(Medico medico) {
		EntityManager em = JPAUtil.criarEntityManager();
		try {
			em.getTransaction().begin();

			if (agendamentoExistente) {
				throw new IllegalArgumentException("Já existe um agendamento para a mesma data, hora e médico.");

			}

			em.persist(agenda);
			em.getTransaction().commit();
		} catch (IllegalArgumentException e) {
			System.out.println("Erro ao salvar: " + e.getMessage());
			// rollback mantem a integridade dos dados caso ocorra alguma exceção,
			// desfazendo todas as operações de persistencia
			em.getTransaction().rollback();

		} finally {
			em.close();
		}
	}

	public static List<Agenda> listar() {
		EntityManager em = JPAUtil.criarEntityManager();
		Query q = em.createQuery("SELECT a FROM Agenda a");
		List<Agenda> agendas = q.getResultList();
		em.close();
		return agendas;
	}

}
