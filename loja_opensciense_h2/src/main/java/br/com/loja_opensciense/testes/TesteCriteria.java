package br.com.loja_opensciense.testes;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.EntityManager;

import br.com.loja_opensciense.dao.CategoriaDAO;
import br.com.loja_opensciense.dao.ProdutoDAO;
import br.com.loja_opensciense.modelo.Categoria;
import br.com.loja_opensciense.modelo.Produto;
import br.com.loja_opensciense.util.JPAUtil;

public class TesteCriteria {
	
	public static void main(String[] args) {
            popularBancoDeDados();
		
		EntityManager em = JPAUtil.getEntityManager();
		ProdutoDAO produtoDAO = new ProdutoDAO(em);
		produtoDAO.buscarPorParametrosComCriteria("PS5", null, LocalDate.now());
		
	}
	
	private static void popularBancoDeDados() {
		Categoria celulares = new Categoria("CELULARES");
		Categoria videogames = new Categoria("VIDEOGAMES");
		Categoria informatica = new Categoria("INFORMATICA");
		
		Produto celular = new Produto("Smartphone", "Samsung S20", new BigDecimal("800"), celulares);
		Produto videogame = new Produto("PS5", "PlayStation 5 Pro", new BigDecimal("1800"), videogames);
		Produto macbook = new Produto("Macbook", "Macbook Pro", new BigDecimal("2800"), informatica);

		EntityManager em = JPAUtil.getEntityManager();
		ProdutoDAO produtoDAO = new ProdutoDAO(em);
		CategoriaDAO categoriaDAO = new CategoriaDAO(em);

		em.getTransaction().begin();

		categoriaDAO.cadastrar(celulares);
		categoriaDAO.cadastrar(videogames);
		categoriaDAO.cadastrar(informatica);
		
		produtoDAO.cadastrar(celular);
		produtoDAO.cadastrar(videogame);
		produtoDAO.cadastrar(macbook);

		em.getTransaction().commit();
		em.close();
	}
}
