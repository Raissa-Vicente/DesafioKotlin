package Compra
import java.lang.IllegalArgumentException
import java.util.concurrent.TimeUnit
fun main() {
    val carrinho = Carrinho()
    val entrada = false
    while (!entrada) {
        try {
            println()
            println("======= Bem-vindo ao SimCity FastFood =======")
            println("\t\t=========== Menu ===========")
            println("\t\t\t1. Lanche | 2. Bebida")
            val opcao = readlnOrNull()?.toIntOrNull() ?: 0

            when (opcao) {
                1 -> carrinho.comprarLanche()
                2 -> carrinho.comprarBebida()
                else -> println("Opção inválida, tente novamente!")
            }
            TimeUnit.SECONDS.sleep(3)
        }catch (exception: NumberFormatException){
            println("Formato inválido, para escolher o item, você deve informar o número dele!")
        }
    }
}