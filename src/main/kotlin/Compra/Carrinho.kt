package Compra

import Produtos.Item
import Produtos.TipoEnum

class Carrinho {
    companion object {
        val listaProdutos = mutableMapOf<Int, Item>()
        private var numero = 1
        val descriptorXSalada = mutableListOf("Alface", "Tomate", "Hamburguer", "Maionese")
        val descriptorXBurguer = mutableListOf("Alface", "Tomate", "Hamburguer", "Maionese", "Bacon", "Ovos")
    }

    private fun gerarCodigo(): Int {
        return numero++
    }

    private fun exibirProdutos() {
        println("======================== Carrinho de Compras ========================")
        if (listaProdutos.isEmpty()) {
            println("O carrinho está vazio.")
        } else {
            for ((codigo, item) in listaProdutos) {
                println("Código - $codigo | Nome - ${item.nome} | Valor - R$${item.valor}")
            }
        }
        println()
    }

    private fun exibirIngrediente(code: Int) {
        println("======================== Ingredientes ========================")
        if (listaProdutos.isEmpty()) {
            println("Nenhum produto encontrado.")
        } else {
            println("Produto -  ${listaProdutos[code]?.nome} | Ingredientes - ${listaProdutos[code]?.descricao}")
        }
        println()
    }

    fun comprarLanche() {
        println("======================== Lanches ========================")
        println("1. X-burger - R$10,00 | 2. X-salada - R$12,00 | 3. Nenhum")
        val option = readln().toIntOrNull() ?: 0

        when (option) {
            1 -> comprarProduto("X-Burger", 10.00, descriptorXBurguer, TipoEnum.COMIDA)
            2 -> comprarProduto("X-Salada", 12.00, descriptorXSalada, TipoEnum.COMIDA)
            3 -> println("Nenhum lanche selecionado!")
            else -> {
                println("Opção inválida, tente novamente!")
                comprarLanche()
            }
        }
        comprarBebida()
    }

    fun comprarBebida() {
        val descriptorBebida = mutableListOf("Bebida")
        println("======================== Bebidas ========================")
        println("1. Refrigerantes - R$ 8,00 | 2. Sucos - R$ 6,00 | 3. Nenhuma")
        val option = readln().toIntOrNull() ?: 0

        when (option) {
            1 -> comprarProduto("Refrigerante", 8.00, descriptorBebida, TipoEnum.BEBIDA)
            2 -> comprarProduto("Suco", 6.00, descriptorBebida, TipoEnum.BEBIDA)
            3 -> println("Nenhuma bebida selecionada!")
            else -> {
                println("Opção inválida, tente novamente!")
                comprarBebida()
            }
        }
        continuarComprando()
    }

    private fun comprarProduto(nome: String, valor: Double, descriptor: MutableList<String>, tipoEnum: TipoEnum) {
        print("Qual quantidade você deseja:")
        val quantidade = readln().toIntOrNull()

        if (quantidade != null && quantidade >= 0) {
            for (i in 0 until quantidade) {
                val codigo = gerarCodigo()
                listaProdutos[codigo] = Item(codigo, nome, 1, valor, descriptor, tipoEnum)
            }
            println("Produto adicionado ao carrinho!")
            exibirProdutos()
            print("Valor total do pedido até o momento - R$${calcularValorTotalPedido()}")
            println()
        } else {
            println("Quantidade inválida.")
            comprarProduto(nome, valor, descriptor, tipoEnum)
        }
    }

    private fun continuarComprando() {
        println()
        println("Qual opção você deseja:")
        println("1. Adicionar item  |  2. Editar | 3. remover item | 4. Finalizar")
        val value = readln().toIntOrNull()
        when (value) {
            1 -> comprarLanche()
            2 -> editarItem()
            3 -> removerItem()
            4 -> finalizarCompra()
            else -> {
                println("Opção inválida, tente novamente!")
                continuarComprando()
            }
        }
    }

    private fun editarItem() {
        if (listaProdutos.isEmpty()) {
            println("O carrinho está vazio, adicione um produto para editar.")
            continuarComprando()
        }
        var contador = 0
        for (item in listaProdutos) {
            if (item.value.tipoEnum.equals(TipoEnum.BEBIDA)) {
                contador++
            }
        }
        if (contador == listaProdutos.size) {
            println("No carrinho só tem Bebida, não será possivel editar nenhum item")
            continuarComprando()
        }
        println("Produtos disponiveis para editar!")
        for (item in listaProdutos) {
            if (item.value.tipoEnum.equals(TipoEnum.COMIDA)) {
                println("Código - ${item.key} | Nome - ${item.value.nome}")
            }
        }
        print("Digite o código do produto que você deseja editar:")
        val code = readlnOrNull()?.toIntOrNull() ?: 0
        if (listaProdutos.containsKey(code)) {
            println("1. Adicionar ingredientes | 2. Remover ingredientes")
            val option = readlnOrNull()?.toIntOrNull()
            when {
                (option != null && option == 1) -> adicionarIngredientes(code)
                (option != null && option == 2) -> removerIngredientes(code)
                else -> {
                    println("Opção inválida, tente novamente!")
                    editarItem()
                }
            }
        } else {
            println("Nenhum produto com o código $code encontrado, tente novamente!")
            editarItem()
        }
    }

    private fun adicionarIngredientes(code: Int) {
        println()
        println("======================== Adicionais Disponíveis ========================")
        println("1. Cheddar | 2. Calabresa")

        val value = readlnOrNull()?.toIntOrNull() ?: 0
        when (value) {
            1 -> listaProdutos[code]?.descricao?.add("Cheddar")
            2 -> listaProdutos[code]?.descricao?.add("Calabresa")
            else -> {
                println("Opção inválida, tente novamente!")
                adicionarIngredientes(code)
            }
        }
        exibirIngrediente(code)
        continuarComprando()
    }

    private fun removerIngredientes(code: Int) {
        println()
        println("======================== Ingredientes Disponíveis para Remoção ========================")
        for ((index, ingrediente) in listaProdutos[code]?.descricao?.withIndex() ?: emptyList<String>().withIndex()) {
            println("${index + 1}. $ingrediente")
        }
        println("Digite o número do ingrediente que deseja remover:")
        val value = readlnOrNull()?.toIntOrNull() ?: 0
        if (value in 1 until (listaProdutos[code]?.descricao?.size ?: 0)) {
            listaProdutos[code]?.descricao?.removeAt(value - 1)
            exibirIngrediente(code)
        } else {
            println("Opção inválida, tente novamente!")
            removerIngredientes(code)
        }
        continuarComprando()
    }

    private fun removerItem() {
        exibirProdutos()
        println()
        print("Digite o código do produto que deseja remover:")
        val codigo = readlnOrNull()?.toIntOrNull()

        if (codigo != null) {
            if (listaProdutos.containsKey(codigo)) {
                listaProdutos.remove(codigo)
                println("Produto removido com sucesso!")
                exibirProdutos()
                continuarComprando()
            } else {
                println("Código de produto inválido.")
                removerItem()
            }
        } else {
            println("Código inválido.")
            removerItem()
        }
    }

    fun finalizarCompra() {
        if (listaProdutos.isEmpty()) {
            println("O carrinho está vazio. Para finalizar o pedido é necessario adicionar um produto!.")
            main()
        }
        exibirProdutos()
        println("Valor total do pedido: R$${calcularValorTotalPedido()}")
        println()
        println("Selecione a forma de pagamento:")
        println("1. Cartão de crédito | 2. Cartão de débito | 3. Vale refeição | 4. Dinheiro")
        val formaPagamento = readln().toIntOrNull()

        when (formaPagamento) {
            1, 2, 3 -> {
                println("Compra finalizada com sucesso!\nObrigada pela preferência, quando bater a fome, já sabe onde procurar!")
                listaProdutos.clear()
            }
            4 -> {
                val valorPago = readIntInput("Digite o valor em dinheiro que irá pagar:")
                if (valorPago >= calcularValorTotalPedido()) {
                    val troco = valorPago - calcularValorTotalPedido()
                    println("Compra finalizada com sucesso! Troco: R$$troco.\nObrigada pela preferência, quando bater a fome, já sabe onde procurar!")
                    listaProdutos.clear()
                    main()
                } else {
                    println("Valor em dinheiro insuficiente. Digite novamente:")
                    finalizarCompra()
                }
            }
            else -> {
                println("Opção de pagamento inválida, tente novamente.")
                finalizarCompra()
            }
        }
    }

    fun readIntInput(prompt: String): Double {
        while (true) {
            print(prompt)
            val input = readlnOrNull()

            try {
                if (input != null) {
                    return input.toDouble()
                } else {
                    throw NumberFormatException()
                }
            } catch (e: NumberFormatException) {
                println("Entrada inválida. Por favor, digite um número inteiro válido.")
            }
        }
    }

    fun calcularValorTotalPedido(): Double {
        return listaProdutos.values.sumByDouble { it.valorTotal() }
    }
}
