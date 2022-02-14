package com.joaopedroluz.cursomc;

import com.joaopedroluz.cursomc.domain.*;
import com.joaopedroluz.cursomc.domain.enums.EstadoPagamento;
import com.joaopedroluz.cursomc.domain.enums.TipoCliente;
import com.joaopedroluz.cursomc.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;


@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private EstadoRepository estadoRepository;
    @Autowired
    private CidadeRepository cidadeRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private PagamentoRepository pagamentoRepository;

    public static void main(String[] args) {
        SpringApplication.run(CursomcApplication.class, args);
    }
    @Transactional
    public void run(String... args) throws Exception {
        Categoria cat1 = new Categoria("Informática");
        Categoria cat2 = new Categoria("Escritório");

        Produto p1 = new Produto("Computador", 2000.00);
        Produto p2 = new Produto("Impressora", 800.00);
        Produto p3 = new Produto("Mouse", 80.00);

        categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
        produtoRepository.saveAll(Arrays.asList(p1, p2, p3));

        cat1.addProduto(p1, p3);
        cat2.addProduto(p2);

        categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
        produtoRepository.saveAll(Arrays.asList(p1, p2, p3));


        Estado est1 = new Estado("Minas Gerais");
        Estado est2 = new Estado("São Paulo");

        Cidade c1 = new Cidade("Uberlândia", est1);
        Cidade c2 = new Cidade("São Paulo", est2);
        Cidade c3 = new Cidade("Campinas", est2);

        estadoRepository.saveAll(Arrays.asList(est1, est2));
        cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));

        est1.addCidades(c1);
        est2.addCidades(c2, c3);

        estadoRepository.saveAll(Arrays.asList(est1, est2));
        cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));

        Cliente cli1 = new Cliente("Maria Silva", "maria@gmail.com", "36378912377", TipoCliente.PESSOAFISICA);
        cli1.getTelefones().addAll(Arrays.asList("27363323", "93838393"));

        clienteRepository.saveAll(List.of(cli1));

        Endereco e1 = new Endereco("Rua Flores", "300", "Apto 303", "Jardim", "38220834", cli1, c1);
        Endereco e2 = new Endereco("Avenida Matos", "105", "Sala 800", "Centro", "38777012", cli1, c2);

        enderecoRepository.saveAll(Arrays.asList(e1, e2));

        cli1.addEndereco(e1, e2);

        clienteRepository.saveAll(List.of(cli1));

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        Pedido ped1 = new Pedido(sdf.parse("30/09/2017 10:32"), cli1, e1);
        Pedido ped2 = new Pedido(sdf.parse("10/10/2017 19:35"), cli1, e2);

        pedidoRepository.saveAll(Arrays.asList(ped1, ped2));


        pedidoRepository.save(ped1);
        pedidoRepository.save(ped2);

        Pagamento pagto1 = new PagamentoComCartao(EstadoPagamento.QUITADO, ped1, 6);
        Pagamento pagto2 = new PagamentoComBoleto(EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"), null);

        pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));

        ped1.setPagamento(pagto1);
        ped2.setPagamento(pagto2);

        pedidoRepository.saveAll(Arrays.asList(ped1, ped2));

        cli1.addPedidos(ped1, ped2);
        clienteRepository.saveAll(List.of(cli1));

    }
}
