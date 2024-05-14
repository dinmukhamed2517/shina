package kz.sdk.shina.fragments


import android.util.Log
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kz.sdk.shina.R
import kz.sdk.shina.adapters.CartAdapter
import kz.sdk.shina.base.BaseFragment
import kz.sdk.shina.databinding.FragmentCartBinding
import kz.sdk.shina.firebase.UserDao
import kz.sdk.shina.models.Product
import javax.inject.Inject

@AndroidEntryPoint
class CartFragment: BaseFragment<FragmentCartBinding>(FragmentCartBinding::inflate) {
    var products: MutableList<Product> = mutableListOf()
    var productsRent: MutableList<Product> = mutableListOf()


    @Inject
    lateinit var firebaseAuth: FirebaseAuth
    @Inject
    lateinit var userDao: UserDao
    override fun onBindView() {
        userDao.getData()
        super.onBindView()

        val adapter = CartAdapter()
        val rentAdapter = CartAdapter()

        adapter.itemClick = {
            findNavController().navigate(
                CartFragmentDirections.actionCartFragmentToProductDetailsFragment(it)
            )
        }
        with(binding){
            startBtn.setOnClickListener {
                findNavController().navigate(R.id.action_cartFragment_to_homeFragment)
            }

            cartRecycler.adapter = adapter
            cartRecycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

            rentRecycler.adapter = rentAdapter
            rentRecycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)



            backBtn.setOnClickListener {
                findNavController().popBackStack()
            }
            adapter.deleteButtonClicked = { product ->
                val keyToDelete = userDao.getDataLiveData.value?.favorites?.filterValues { it.id == product.id }?.keys?.firstOrNull()
                keyToDelete?.let { key ->
                    userDao.deleteProductFromList(key)
                    val updatedProducts = ArrayList(products).apply {
                        remove(product)
                    }
                    adapter.submitList(updatedProducts)
                    products = updatedProducts
                } ?: run {
                    Log.e("CartFragment", "Failed to find key for product deletion")
                }
            }
            rentAdapter.deleteButtonClicked = { product ->
                val keyToDelete = userDao.getDataLiveData.value?.favorites?.filterValues { it.id == product.id }?.keys?.firstOrNull()
                keyToDelete?.let { key ->
                    userDao.deleteProductRentFromList(key)
                    val updatedProducts = ArrayList(products).apply {
                        remove(product)
                    }
                    adapter.submitList(updatedProducts)
                    products = updatedProducts
                } ?: run {
                    Log.e("CartFragment", "Failed to find key for product deletion")
                }
            }
        }
        userDao.getDataLiveData.observe(viewLifecycleOwner) { userData ->
            products.clear()
            productsRent.clear()
            userData?.favorites?.values?.let { productList ->
                products.addAll(productList)
            }
            userData?.favoritesRent?.values?.let { productList ->
                productsRent.addAll(productList)
            }
            adapter.submitList(products.toList())
            rentAdapter.submitList(productsRent.toList())



            val isCartEmpty = products.isEmpty() && productsRent.isEmpty()
            binding.emptyCartCv.isVisible = isCartEmpty
            binding.cartRecycler.isVisible = !isCartEmpty
        }
    }
}