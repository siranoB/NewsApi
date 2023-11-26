package co.sirano.news.view.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import co.sirano.news.R
import co.sirano.news.common.util.DateUtil
import co.sirano.news.common.util.onClick
import co.sirano.news.data.local.entity.ArticleEntity
import co.sirano.news.databinding.ItemNewsBinding
import co.sirano.news.databinding.ItemNewsGridBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import java.io.File

class NewsListAdapter(
    private val isGrid: Boolean = false,
    private val clickListener: (ArticleEntity) -> Unit
) : PagingDataAdapter<ArticleEntity, NewsListAdapter.ViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == 0) {
            val binding = ItemNewsGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            GridViewHolder(binding)
        } else {
            val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            LinearViewHolder(binding)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (isGrid) 0 else 1
    }

    override fun onBindViewHolder(holder: NewsListAdapter.ViewHolder, position: Int) {
        holder.clickListener = clickListener
        holder.bind(position, getItem(position))
    }

    open class ViewHolder(open val binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
        var data: ArticleEntity? = null
        var clickListener: ((ArticleEntity) -> Unit)? = null

        open fun bind(position: Int, data: ArticleEntity?) {
        }
    }

    // 가로 600dp 미만인 경우 사용하는 ViewHolder
    class LinearViewHolder(
        override val binding: ItemNewsBinding
    ) : ViewHolder(binding) {
        init {
            binding.root.onClick {
                data?.let {
                    clickListener?.invoke(it)
                }
            }
        }

        override fun bind(position: Int, data: ArticleEntity?) {
            this.data = data
            with(binding) {
                data?.let { data ->
                    tvTitle.text = data.title
                    tvTitle.setTextColor(
                        ContextCompat.getColor(
                            root.context,
                            if (data.isShow) R.color.md_red_900 else R.color.black
                        )
                    )

                    tvDate.text = DateUtil.convertToString(data.publishedAt)
                    // urlToImage null 인 경우 로고 이미지 처리.
                    if (data.urlToImage.isEmpty()) {
                        ivThumb.setImageResource(R.drawable.news_api_logo)
                    } else {
                        // 로컬 이미지가 있는 경우 이를 우선 사용함.
                        if (data.imgLocalFilePath.isEmpty()) {
                            Glide.with(ivThumb).load(data.urlToImage).transform(CenterCrop(), RoundedCorners(16)).into(ivThumb)
                        } else {
                            val localFile = File(data.imgLocalFilePath)
                            Glide.with(ivThumb).load(localFile).transform(CenterCrop(), RoundedCorners(16)).into(ivThumb)
                        }
                    }
                }
            }
        }
    }

    // 가로 600dp 이상인 경우 사용하는 ViewHolder
    class GridViewHolder(
        override val binding: ItemNewsGridBinding
    ) : ViewHolder(binding) {
        init {
            binding.root.onClick {
                data?.let {
                    clickListener?.invoke(it)
                }
            }
        }

        override fun bind(position: Int, data: ArticleEntity?) {
            this.data = data
            with(binding) {
                data?.let { data ->
                    tvTitle.text = data.title
                    tvTitle.setTextColor(
                        ContextCompat.getColor(
                            root.context,
                            if (data.isShow) R.color.md_red_900 else R.color.black
                        )
                    )

                    tvDate.text = DateUtil.convertToString(data.publishedAt)
                    // urlToImage null 인 경우 로고 이미지 처리.
                    if (data.urlToImage.isEmpty()) {
                        ivThumb.setImageResource(R.drawable.news_api_logo)
                    } else {
                        // 로컬 이미지가 있는 경우 이를 우선 사용함.
                        if (data.imgLocalFilePath.isEmpty()) {
                            Glide.with(ivThumb).load(data.urlToImage).transform(CenterCrop(), RoundedCorners(16)).into(ivThumb)
                        } else {
                            val localFile = File(data.imgLocalFilePath)
                            Glide.with(ivThumb).load(localFile).transform(CenterCrop(), RoundedCorners(16)).into(ivThumb)
                        }
                    }
                }
            }
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<ArticleEntity>() {
            override fun areItemsTheSame(oldItem: ArticleEntity, newItem: ArticleEntity): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ArticleEntity, newItem: ArticleEntity): Boolean {
                return (oldItem.url == newItem.url) && (oldItem.publishedAt == newItem.publishedAt)
            }
        }
    }
}
