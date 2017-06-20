package co.folto.kopigo.ui.adapter.base

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

/**
 * Created by Daniel on 5/31/2017 for GitFInder project.
 */
abstract class BaseAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    protected var items: MutableList<ViewType> = ArrayList()

    abstract fun getAdapter(): SparseArrayCompat<ViewTypeDelegateAdapter>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
            = getAdapter().get(viewType).onCreateViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int)
            = getAdapter().get(getItemViewType(position)).onBindViewHolder(holder, this.items[position])

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int
            = items.get(position).getViewType()
}