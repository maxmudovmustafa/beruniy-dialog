package firbase.go.beruniy.collection;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import firbase.go.beruniy.utils.L_Log;

public abstract class MyAdapter<E, H> extends BaseAdapter implements Filterable {

    protected final Context context;
    protected final LayoutInflater inflater;
    protected MyArray<E> items = MyArray.emptyArray();
    protected MyArray<E> filteredItems = MyArray.emptyArray();

    private ItemFilter itemFilter;
    public MyPredicate<E> predicateSearch;
    public MyPredicate<E> predicateOthers;

    public MyAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    private MyArray<E> filterItems() {
        MyPredicate<E> predicate = MyPredicate.True();
        return items.filter(predicate.and(predicateSearch).and(predicateOthers));
    }

    public void setItems(@NonNull MyArray<E> items) {
        if (items == null) {
            new L_Log().log(MyAdapter.class.getName());
        }
        this.items = items;
        this.filteredItems = filterItems();
        notifyDataSetChanged();
    }

    public boolean isEmpty() {
        return this.filteredItems == null || this.filteredItems.isEmpty();
    }

    public MyArray<E> getItems() {
        return this.items;
    }

    public MyArray<E> getFilteredItems() {
        return this.filteredItems;
    }

    @Override
    public int getCount() {
        return filteredItems.size();
    }

    @Override
    public E getItem(int position) {
        return filteredItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @SuppressWarnings("unchecked")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            H holder;
            if (convertView == null) {
                convertView = inflater.inflate(getLayoutResource(), parent, false);
                holder = makeHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (H) convertView.getTag();
            }

            populate(holder, getItem(position));

            return convertView;
        } catch (Exception ex) {
            new L_Log().log(MyAdapter.class.getName());
        }
        return null;
    }

    public void filter() {
        getFilter().filter(null);
    }

    public abstract int getLayoutResource();

    public abstract H makeHolder(View view);

    public abstract void populate(H holder, E item);

    @Override
    public Filter getFilter() {
        if (itemFilter == null) {
            itemFilter = new ItemFilter();
        }
        return itemFilter;
    }

    private class ItemFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults result = new FilterResults();
            try {
                MyArray<E> filteredItems = filterItems();
                result.values = filteredItems;
                result.count = filteredItems.size();
            } catch (Exception ex) {
                result.values = ex;
            }
            return result;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.values instanceof Exception) {
                new L_Log().log(MyAdapter.class.getName()+ ":" + results.values);
            } else {
                filteredItems = (MyArray<E>) results.values;
                notifyDataSetChanged();
            }
        }
    }
}
