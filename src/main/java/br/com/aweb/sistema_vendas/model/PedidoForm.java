package br.com.aweb.sistema_vendas.model;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class PedidoForm {

    private Long id;

    @NotNull(message = "Cliente é obrigatório")
    private Long clienteId;

    @NotNull(message = "Valor total é obrigatório")
    @PositiveOrZero(message = "Valor total não pode ser negativo")
    private BigDecimal valorTotal;
}
