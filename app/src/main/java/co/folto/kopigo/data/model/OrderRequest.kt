package co.folto.kopigo.data.model

/**
 * Created by Daniel on 7/13/2017 for Kopigo project.
 */
data class OrderRequest (
    val products: List<ProductOrder>
)

data class ProductOrder(
    val id: Int,
    val quantity: Int
)
