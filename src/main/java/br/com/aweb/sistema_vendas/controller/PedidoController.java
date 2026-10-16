package br.com.aweb.sistema_vendas.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import br.com.aweb.sistema_vendas.model.Pedido;
import br.com.aweb.sistema_vendas.model.PedidoForm;
import br.com.aweb.sistema_vendas.service.ClienteService;
import br.com.aweb.sistema_vendas.service.PedidoService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;
    private final ClienteService clienteService;

    public PedidoController(PedidoService pedidoService, ClienteService clienteService) {
        this.pedidoService = pedidoService;
        this.clienteService = clienteService;
    }

    // Listar pedidos
    @GetMapping
    public ModelAndView list() {
        return new ModelAndView("pedido/list", Map.of("pedidos", pedidoService.listarTodos()));
    }

    // Formulário de cadastro
    @GetMapping("/novo")
    public String create(Model model) {
        model.addAttribute("pedidoForm", new PedidoForm());
        model.addAttribute("clientes", clienteService.listarTodos());
        return "pedido/form";
    }

    // Salvar pedido
    @PostMapping("/novo")
    public String create(@Valid PedidoForm pedidoForm, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("clientes", clienteService.listarTodos());
            return "pedido/form";
        }
        pedidoService.salvar(pedidoForm.getClienteId(), pedidoForm.getValorTotal());
        return "redirect:/pedidos";
    }

    // Formulário de edição
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Pedido pedido = pedidoService.buscarPorId(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        PedidoForm form = new PedidoForm();
        form.setId(pedido.getId());
        form.setClienteId(pedido.getCliente().getId());
        form.setValorTotal(pedido.getValorTotal());
        model.addAttribute("pedidoForm", form);
        model.addAttribute("clientes", clienteService.listarTodos());
        return "pedido/form";
    }

    // Atualizar pedido
    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id, @Valid PedidoForm pedidoForm, BindingResult result, Model model) {
        if (result.hasErrors()) {
            pedidoForm.setId(id);
            model.addAttribute("clientes", clienteService.listarTodos());
            return "pedido/form";
        }
        pedidoService.atualizar(id, pedidoForm.getClienteId(), pedidoForm.getValorTotal());
        return "redirect:/pedidos";
    }

    // Excluir pedido
    @GetMapping("/delete/{id}")
    public ModelAndView confirmarExclusao(@PathVariable Long id) {
        Pedido pedido = pedidoService.buscarPorId(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return new ModelAndView("pedido/delete", Map.of("pedido", pedido));
    }

    @PostMapping("/delete/{id}")
    public String excluir(@PathVariable Long id) {
        pedidoService.excluir(id);
        return "redirect:/pedidos";
    }
}
