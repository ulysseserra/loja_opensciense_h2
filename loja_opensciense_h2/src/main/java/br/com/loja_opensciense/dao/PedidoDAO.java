package br.com.loja_opensciense.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.loja_opensciense.modelo.Pedido;
import br.com.loja_opensciense.vo.RelatorioDeVendasVo;

public class PedidoDAO {

	private EntityManager em;

	public PedidoDAO(EntityManager em) {
		this.em = em;
	}

	public void cadastrar(Pedido pedido) {
		this.em.persist(pedido);
	}
	
	public BigDecimal valorTotalVendido() {
		String jpql = "SELECT SUM(p.valorTotal) FROM Pedido p";
		return em.createQuery(jpql, BigDecimal.class)
				.getSingleResult();							
	}
	
	public List<RelatorioDeVendasVo> relatorioDeVendas() {
		String jpql = "SELECT new br.com.loja_opensciense.vo.RelatorioDeVendasVo("
				+ "produto.nome, "
	            + "SUM(item.quantidade), " 
	            + "MAX(pedido.data)) " 
				+ "FROM Pedido pedido "
	            + "JOIN pedido.itens item "
				+ "JOIN item.produto produto "
	            + "GROUP BY produto.nome "
				+ "ORDER BY item.quantidade DESC";
		return em.createQuery(jpql, RelatorioDeVendasVo.class)
				.getResultList();	
	}
	
}
