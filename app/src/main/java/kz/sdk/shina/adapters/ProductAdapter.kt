package kz.sdk.shina.adapters



import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import kz.sdk.shina.base.BaseProductViewHolder
import kz.sdk.shina.databinding.ItemProductBinding
import kz.sdk.shina.models.Product

class ProductAdapter: ListAdapter<Product, BaseProductViewHolder<*>>(CartAdapter.ProductDiffUtils()) {

    var itemClick:((Product) ->Unit)? = null

    inner class ProductViewHolder(binding:ItemProductBinding):
        BaseProductViewHolder<ItemProductBinding>(binding){
        override fun bindView(item: Product) {
            with(binding){
                item.img?.let { imageView.setImageResource(it) }
                title.text = item.title
                price.text = item.price.toString()+" KZT"

                var bonusAmount = item.price?.times(0.05)?.toInt()
            }
            itemView.setOnClickListener {
                itemClick?.invoke(item)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseProductViewHolder<*> {
        return ProductViewHolder(
            ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: BaseProductViewHolder<*>, position: Int) {
        holder.bindView(getItem(position))
    }
}
