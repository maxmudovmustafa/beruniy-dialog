package firbase.go.beruniy.view_setup.bottom;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import firbase.go.beruniy.R;
import firbase.go.beruniy.view_setup.DialogApi;
import firbase.go.beruniy.view_setup.DialogError;
import firbase.go.beruniy.view_setup.listener.Command;

public class BottomSheetAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    public final Context context;
    private final LayoutInflater inflater;
    private final BottomSheetDialog bottomSheetDialog;
    private final List<Object> mOptionIcons;
    private final List<Object> mOptionNames;
    private final List<Command> mOptionCommands;

    public BottomSheetAdapter(Activity activity,
                              BottomSheetDialog bottomSheetDialog,
                              List<Object> mOptionIcons,
                              List<Object> mOptionNames,
                              List<Command> mOptionCommands) {
        this.context = activity;
        this.inflater = LayoutInflater.from(this.context);
        this.bottomSheetDialog = bottomSheetDialog;
        this.mOptionIcons = mOptionIcons;
        this.mOptionNames = mOptionNames;
        this.mOptionCommands = mOptionCommands;

        DialogError.checkNull(mOptionIcons, mOptionNames, mOptionCommands);
    }

    @Override
    public int getItemCount() {
        return this.mOptionNames.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.bottom_sheet_dialog_row, null);
        return new RecyclerView.ViewHolder(view) {
        };
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Object icon = this.mOptionIcons.get(position);
        Object text = this.mOptionNames.get(position);
        final Command command = this.mOptionCommands.get(position);

        ImageView ivIcon = holder.itemView.findViewById(R.id.iv_icon);
        TextView tvText = holder.itemView.findViewById(R.id.tv_text);
        tvText.setText(getText(text));
        ivIcon.setVisibility(icon != null ? View.VISIBLE : View.INVISIBLE);

        if (icon != null) {
            if (icon instanceof Bitmap) {
                ivIcon.setImageBitmap((Bitmap) icon);
            } else if (icon instanceof Drawable) {
                ivIcon.setImageDrawable((Drawable) icon);
            }
        }
        holder.itemView.setOnClickListener(this);
        holder.itemView.setTag(command);
    }

    private CharSequence getText(Object val) {
        if (val == null) {
            return null;
        }
        if (val instanceof Integer) {
            return DialogApi.getString((Integer) val);
        }
        return (CharSequence) val;
    }

    @Override
    public void onClick(View view) {
        Object tag = view.getTag();
        if (tag instanceof Command) ((Command) tag).apply();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Dialog dialog = bottomSheetDialog.getDialog();
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        }, 500);
    }
}