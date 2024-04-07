package dao;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.swing.JFrame;

import entities.Agenda;
import util.JPAUtil;

public class AgendaDao {

	JFrame frame = new JFrame();

	public static void save(Agenda agenda) {
		EntityManager em = JPAUtil.criarEntityManager();
		try {
			em.getTransaction().begin();

			boolean agendamentoExistente = verificarAgendamentoExistente(agenda);
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

	public static void edit(Agenda agenda) {
		EntityManager em = JPAUtil.criarEntityManager();

		 try {
		        em.getTransaction().begin();

		        // Verifica se apenas o nome foi alterado
		        Agenda agendaOriginal = getAgendaById(agenda.getId());
		        boolean nomeAlterado = !agendaOriginal.getPaciente().equals(agenda.getPaciente());

		        if (nomeAlterado) {
		            agenda = em.merge(agenda);
		            em.getTransaction().commit();
		        } else {
		            // Se apenas o nome foi alterado, não é necessário verificar a duplicidade completa
		            em.getTransaction().commit();
		        }

		    } catch (Exception e) {
		        System.out.println("Erro ao editar: " + e.getMessage());
		        em.getTransaction().rollback();

		    } finally {
		        em.close();
		    }
	}

	public static void delete(int id) {
		EntityManager em = JPAUtil.criarEntityManager();
		em.getTransaction().begin();

		Agenda agendaDeletada = em.find(Agenda.class, id);

		if (agendaDeletada != null) {
			em.remove(agendaDeletada);
		}

		em.getTransaction().commit();
		em.close();
	}

	public static List<Agenda> listar() {
		EntityManager em = JPAUtil.criarEntityManager();
		Query q = em.createQuery("SELECT a FROM Agenda a");
		List<Agenda> agendas = q.getResultList();
		em.close();
		return agendas;
	}

	public static long counter() {
		EntityManager em = JPAUtil.criarEntityManager();
		Query q = em.createQuery("SELECT a FROM Agenda a");
		List<Agenda> agendas = q.getResultList();
		em.close();
		return agendas.size();
	}

	public static boolean verificarAgendamentoExistente(Agenda agenda) {
		EntityManager em = JPAUtil.criarEntityManager();
		try {
			Query query = em.createQuery(
					"SELECT COUNT(a) FROM Agenda a WHERE a.dataHoraConsulta = :dataHora AND a.medico = :medico");
			query.setParameter("dataHora", agenda.getDataHoraConsulta());
			query.setParameter("medico", agenda.getMedico());

			Long count = (Long) query.getSingleResult();
			// Se maior que 0, já existe um agendamento c as mesmas infos.
			return count > 0;

		} finally {
			em.close();
		}
	}

	public static Agenda getAgendaById(Integer id) {
		EntityManager em = JPAUtil.criarEntityManager();

		try {
			return em.find(Agenda.class, id);
		} catch (Exception e) {
			System.out.println("Erro ao buscar por ID: " + e.getMessage());
		} finally {
			em.close();
		}
		return null;

	}

}
