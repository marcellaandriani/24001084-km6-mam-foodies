import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.foodiesapp.R
import com.example.foodiesapp.base.ViewHolderBinder
import com.example.foodiesapp.data.model.Menu
import com.example.foodiesapp.databinding.ItemGridMenuBinding
import com.example.foodiesapp.presentation.home.adapter.MenuAdapter
import com.example.foodiesapp.utils.toIndonesianFormat

class MenuGridItemViewHolder(
    private val binding: ItemGridMenuBinding,
    private val listener: MenuAdapter.OnItemClickedListener<Menu>
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Menu> {

    override fun bind(item: Menu) {
        item.let {
            binding.ivListMenuImage.load(it.imgUrl){
                crossfade(true)
                error(R.mipmap.ic_launcher)
            }
            binding.tvListMenuName.text = it.name
            binding.tvListMenuPrice.text = it.price.toIndonesianFormat()
            itemView.setOnClickListener {
                listener.onItemClicked(item)
            }
        }
    }
}

