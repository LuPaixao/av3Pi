package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.swing.JFrame;

import entities.Medico;
import util.JPAUtil;

public class MedicoDao {

	JFrame frame = new JFrame();

	public static void salvar(Medico medico) {
		EntityManager em = JPAUtil.criarEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(medico);
			em.getTransaction().commit();
		} catch (IllegalArgumentException e) {
			System.out.println("Erro ao salvar: " + e.getMessage());
			em.getTransaction().rollback();
		} finally {
			em.close();
		}
	}

	public static List<Medico> listarMedicos() {
		EntityManager em = JPAUtil.criarEntityManager();
		Query q = em.createQuery("SELECT a FROM Medico a");
		List<Medico> medico = q.getResultList();
		em.close();
		return medico;
	}

	public static Medico buscarPorId(Integer idMedico) {
		EntityManager em = JPAUtil.criarEntityManager();
		try {
			return em.find(Medico.class, idMedico);
		} finally {
			em.close();
		}
	}

}
