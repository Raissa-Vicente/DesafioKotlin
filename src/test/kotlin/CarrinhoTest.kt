
import Compra.Carrinho
import Compra.Carrinho.Companion.descriptorBebida
import Compra.Carrinho.Companion.descriptorXBurguer
import Compra.Carrinho.Companion.listaProdutos
import Produtos.Item
import Produtos.TipoEnum
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*

class CarrinhoTest {
    private var carrinho = Carrinho()
    var mockItem: Item = mock(Item::class.java)
    @BeforeEach
    fun setUp() {
        carrinho = Carrinho()
        mockItem = mock(Item::class.java)
    }
    @Test
    fun testComprarProdutoSemMock(){
        listaProdutos[123] = Item(123, "X-Burger", 1, 10.00, descriptorXBurguer, TipoEnum.COMIDA)
        listaProdutos[124] = Item(124, "Refrigerante", 1, 8.00, descriptorBebida, TipoEnum.BEBIDA)

        assertEquals(2, listaProdutos.size)
        assertEquals(18.00,carrinho.calcularValorTotalPedido())
    }
    @Test
    fun testRemoverItemDoCarrinho(){
        listaProdutos[123] = Item(123, "X-Burger", 1, 10.00, descriptorXBurguer, TipoEnum.COMIDA)
        listaProdutos[124] = Item(124, "Refrigerante", 1, 8.00, descriptorBebida, TipoEnum.BEBIDA)

        listaProdutos.remove(124)

        assertEquals(1, listaProdutos.size)
        assertEquals(10.00,carrinho.calcularValorTotalPedido())
    }
    @Test
    fun testComprarProduto() {
        // Configuração do mockItem
        `when`(mockItem.valorTotal()).thenReturn(10.00)

        // Compra de um produto
        carrinho.comprarProduto("X-Burger", 10.00, descriptorXBurguer, TipoEnum.COMIDA)

        // Verificação
        verify(mockItem, times(1)).valorTotal()
        assertEquals(1, Carrinho.listaProdutos.size)
        assertEquals(10.00, carrinho.calcularValorTotalPedido(), 0.001)
    }
    @Test
    fun testEditarItem_AdicionarIngredientes() {
        // Adicionar um item mock ao carrinho
        listaProdutos[1] = mockItem

        // Configuração do mockItem
        doReturn(TipoEnum.COMIDA).`when`(mockItem).tipoEnum
        doReturn(ArrayList<String>()).`when`(mockItem).descricao

        // Chamar o método editarItem() para adicionar ingredientes
        carrinho.editarItem()
        // Verificar se os ingredientes foram adicionados corretamente ao mockItem
    }
    @Test
    fun testEditarItem_RemoverIngredientes() {
        // Adicionar um item mock ao carrinho
        Carrinho.listaProdutos[1] = mockItem

        // Configuração do mockItem
        `when`(mockItem.tipoEnum).thenReturn(TipoEnum.COMIDA)
        `when`<List<String>>(mockItem.descricao).thenReturn(ArrayList())

        // Chamar o método editarItem() para remover ingredientes
        carrinho.editarItem()
        // Verificar se os ingredientes foram removidos corretamente do mockItem
    }
}