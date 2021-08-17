package br.com.loja_opensciense.testes;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.loja_opensciense.dao.CategoriaDAO;
import br.com.loja_opensciense.dao.ProdutoDAO;
import br.com.loja_opensciense.modelo.Categoria;
import br.com.loja_opensciense.modelo.CategoriaId;
import br.com.loja_opensciense.modelo.Produto;
import br.com.loja_opensciense.util.JPAUtil;

public class CadastroDeProdutos {

	public static void main(String[] args) {
		cadastrarProduto();
		EntityManager em = JPAUtil.getEntityManager();
		ProdutoDAO produtoDAO = new ProdutoDAO(em);

		Produto p = produtoDAO.buscarPorId(1l);
		System.out.println(p.getPreco());

		List<Produto> todos = produtoDAO.buscarPorNomeDaCategoria("celulares");
		todos.forEach(p2 -> System.out.println(p2.getNome()));
		
		BigDecimal precoDoProduto = produtoDAO.buscarPrecoDoProdutoComNome("iPhone");
		System.out.println("Preco do Produto: " + precoDoProduto);
	}

	private static void cadastrarProduto() {
		Categoria celulares = new Categoria("celulares");
		Produto celular = new Produto("iPhone", "iPhone 12 Pro Max", new BigDecimal("800"), celulares);

		EntityManager em = JPAUtil.getEntityManager();
		ProdutoDAO produtoDAO = new ProdutoDAO(em);
		CategoriaDAO categoriaDAO = new CategoriaDAO(em);

		em.getTransaction().begin();

		categoriaDAO.cadastrar(celulares);
		produtoDAO.cadastrar(celular);

		em.getTransaction().commit();
		
		em.find(Categoria.class, new CategoriaId("celulares", "xpto"));
		
		em.close();
	}
}
