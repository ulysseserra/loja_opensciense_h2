package br.com.loja_opensciense.testes;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.loja_opensciense.dao.CategoriaDAO;
import br.com.loja_opensciense.dao.ClienteDAO;
import br.com.loja_opensciense.dao.PedidoDAO;
import br.com.loja_opensciense.dao.ProdutoDAO;
import br.com.loja_opensciense.modelo.Categoria;
import br.com.loja_opensciense.modelo.Cliente;
import br.com.loja_opensciense.modelo.ItemPedido;
import br.com.loja_opensciense.modelo.Pedido;
import br.com.loja_opensciense.modelo.Produto;
import br.com.loja_opensciense.util.JPAUtil;
import br.com.loja_opensciense.vo.RelatorioDeVendasVo;

public class CadastroDePedido {

	public static void main(String[] args) {
		popularBancoDeDados();
		
		EntityManager em = JPAUtil.getEntityManager();
		ProdutoDAO produtoDAO = new ProdutoDAO(em);
		ClienteDAO clienteDAO = new ClienteDAO(em);
		
		Produto produto = produtoDAO.buscarPorId(1l);
		Produto produto2 = produtoDAO.buscarPorId(2l);
		Produto produto3 = produtoDAO.buscarPorId(3l);
		Cliente cliente = clienteDAO.buscarPorId(1l);
		
		
		em.getTransaction().begin();
		
		Pedido pedido = new Pedido(cliente);
		pedido.adicionarItem(new ItemPedido(10, pedido, produto));
		pedido.adicionarItem(new ItemPedido(40, pedido, produto2));
		
		Pedido pedido2 = new Pedido(cliente);
		pedido.adicionarItem(new ItemPedido(2, pedido, produto3));
		
		PedidoDAO pedidoDAO = new PedidoDAO(em);
		pedidoDAO.cadastrar(pedido);
		pedidoDAO.cadastrar(pedido2);
		
		em.getTransaction().commit();
		
		BigDecimal totalVendido = pedidoDAO.valorTotalVendido();
		System.out.println("VALOR TOTAL: " + totalVendido);
		
		List<RelatorioDeVendasVo> relatorio = pedidoDAO.relatorioDeVendas();
		relatorio.forEach(System.out::println);
			
		}
	
	private static void popularBancoDeDados() {
		Categoria celulares = new Categoria("CELULARES");
		Categoria videogames = new Categoria("VIDEOGAMES");
		Categoria informatica = new Categoria("INFORMATICA");
		
		Produto celular = new Produto("Smartphone", "Samsung S20", new BigDecimal("800"), celulares);
		Produto videogame = new Produto("PS5", "PlayStation 5 Pro", new BigDecimal("1800"), videogames);
		Produto macbook = new Produto("Macbook", "Macbook Pro", new BigDecimal("2800"), informatica);
		
		Cliente cliente = new Cliente("Ulysses Serra", "66532383596");

		EntityManager em = JPAUtil.getEntityManager();
		ProdutoDAO produtoDAO = new ProdutoDAO(em);
		CategoriaDAO categoriaDAO = new CategoriaDAO(em);
		ClienteDAO clienteDAO = new ClienteDAO(em);

		em.getTransaction().begin();

		categoriaDAO.cadastrar(celulares);
		categoriaDAO.cadastrar(videogames);
		categoriaDAO.cadastrar(informatica);
		
		produtoDAO.cadastrar(celular);
		produtoDAO.cadastrar(videogame);
		produtoDAO.cadastrar(macbook);
		
		clienteDAO.cadastrar(cliente);

		em.getTransaction().commit();
		em.close();
	}

}
