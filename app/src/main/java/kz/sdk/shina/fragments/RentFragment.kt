package kz.sdk.shina.fragments

import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.AndroidEntryPoint
import kz.sdk.shina.adapters.ProductAdapter
import kz.sdk.shina.base.BaseFragment
import kz.sdk.shina.databinding.FragmentRentBinding
import kz.sdk.shina.models.Product



@AndroidEntryPoint
class RentFragment:BaseFragment<FragmentRentBinding>(FragmentRentBinding::inflate) {
    private lateinit var adapter: ProductAdapter

    override fun onBindView() {
        super.onBindView()
        adapter = ProductAdapter()
        with(binding) {
            rentRecycler.adapter = adapter
            rentRecycler.layoutManager = LinearLayoutManager(requireContext())
        }
        loadEvents()

        adapter.itemClick =  {
            findNavController().navigate(
                RentFragmentDirections.actionRentFragmentToProductRentFragment(it)
            )
        }

    }
    private fun loadEvents() {
        val databaseReference = FirebaseDatabase.getInstance().getReference("rentCars")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val events = mutableListOf<Product>()
                snapshot.children.forEach {
                    val event = it.getValue(Product::class.java)
                    event?.let { events.add(it) }
                }
                adapter.submitList(events)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Failed to load events: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}