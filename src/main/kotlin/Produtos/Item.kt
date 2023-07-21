package Produtos

class Item(val codigo: Int, val nome: String, var quantidade: Int, val valor: Double, var descricao: MutableList<String>, val tipoEnum: TipoEnum) {
    fun valorTotal(): Double {
        return (this.valor * this.quantidade)
    }
}
