package firbase.go.beruniy.view_setup.choise;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import firbase.go.beruniy.R;
import firbase.go.beruniy.view_setup.MaterialDialog;
import firbase.go.beruniy.view_setup.choise.utils.SelectionModel;

public class CustomMultipleSelectionArrayAdapter extends ArrayAdapter<String> {

    private static final String TAG = "MultiSelectionAdapter";

    private LayoutInflater mInflater;
    private CharSequence[] mStrings;

    private List<SelectionModel> mSelectionList = new ArrayList<>();

    private MaterialDialog.ItemClickListener mItemClickListener;
    private MaterialDialog.ItemLongClickListener mItemLongClickListener;

    private Typeface mTypeface;


    public CustomMultipleSelectionArrayAdapter(Context context, String[] strings, Integer[] selectedPositions, MaterialDialog
            .ItemClickListener itemClickListener, MaterialDialog.ItemLongClickListener itemLongClickListener, Typeface typeface) {
        super(context, R.layout.custom_list_item_multiple_selection, strings);

        init(context, strings, selectedPositions, itemClickListener, typeface);

    }


    private void init(Context context, String[] strings, Integer[] selectedPositions, MaterialDialog.ItemClickListener
            itemClickListener, Typeface typeface) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mStrings = strings;
        List<Integer> selected = Arrays.asList(selectedPositions);
        for (int i = 0; i < strings.length; i++) {
            mSelectionList.add(new SelectionModel(i, selected.contains(i)));
        }

        for (SelectionModel sm : mSelectionList) {
            Log.i(TAG, "init " + sm.getPosition() + " == " + sm.isChecked());
        }
        mItemClickListener = itemClickListener;
        mTypeface = typeface;
    }


    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        View row = convertView;
        final ViewHolder viewHolder;

        if (row == null) {
            row = mInflater.inflate(R.layout.custom_list_item_multiple_selection, parent, false);

            final LinearLayout linearLayout = (LinearLayout) row.findViewById(R.id.ll_simple_list_item_multiple_selection);
            final CheckBox cbx = (CheckBox) row.findViewById(R.id.cbx_simple_list_item_multiple_selection);
            final TextView tv = (TextView) row.findViewById(R.id.tv_simple_list_item_multiple_selection);

            if (mTypeface != null) {
                cbx.setTypeface(mTypeface);
                tv.setTypeface(mTypeface);
            }

            viewHolder = new ViewHolder();
            viewHolder.linearLayout = linearLayout;
            viewHolder.cbx = cbx;
            viewHolder.tv = tv;
            row.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) row.getTag();
        }

        final View finalRow = row;

        viewHolder.cbx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mSelectionList.get(position).setChecked(isChecked);

                if (mItemClickListener != null) {
                    mItemClickListener.onClick(finalRow, position, finalRow.getId());
                }
                if (mItemLongClickListener != null) {
                    mItemLongClickListener.onLongClick(finalRow, position, finalRow.getId());
                }
            }
        });

        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.cbx.setChecked(!mSelectionList.get(position).isChecked());
                notifyDataSetChanged();
            }
        });

        viewHolder.tv.setText(mStrings[position]);
        viewHolder.cbx.setTag(position);
        viewHolder.tv.setTag(position);
        viewHolder.cbx.setChecked(mSelectionList.get(position).isChecked());

        return row;
    }


    static class ViewHolder {

        LinearLayout linearLayout;
        CheckBox cbx;
        TextView tv;
    }

}