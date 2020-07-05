package firbase.go.beruniy.view_setup.bottom;// 24.11.2016

import android.app.Activity;
import android.app.Dialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import firbase.go.beruniy.R;
import firbase.go.beruniy.variable.Setter;
import firbase.go.beruniy.view_setup.DialogApi;
import firbase.go.beruniy.view_setup.DialogError;
import firbase.go.beruniy.view_setup.listener.Callback;
import firbase.go.beruniy.view_setup.listener.Command;
import firbase.go.beruniy.view_setup.listener.CommandFacade;

public class BottomSheetDialog extends BottomSheetDialogFragment {

    private Object mTitle;
    private View mContent;
    private Callback callback;
    private RecyclerView.Adapter adapter;

    @NonNull
    protected View onCreateContentView(Dialog dialog, int style) {
        if (mContent != null) {
            return mContent;
        }
        View view = View.inflate(getActivity(), R.layout.bottom_sheet_dialog, null);

        CharSequence contentTitle = getContentTitle(this.mTitle);
        TextView title = view.findViewById(R.id.tv_bottom_sheet_title);
        boolean titleNonEmpty = !TextUtils.isEmpty(contentTitle);
        title.setVisibility(titleNonEmpty ? View.VISIBLE : View.GONE);
        view.findViewById(R.id.v_shadow).setVisibility(titleNonEmpty ? View.GONE : View.INVISIBLE);
        if (titleNonEmpty) {
            title.setText(contentTitle);
        }
        RecyclerView recyclerView = view.findViewById(R.id.bottom_sheet_recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        recyclerView.setAdapter(adapter);

        return view;
    }

    private CharSequence getContentTitle(Object val) {
        if (val == null) {
            return null;
        }
        if (val instanceof Integer) {
            return DialogApi.getString((Integer) val);
        }
        return (CharSequence) val;
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        View contentView = onCreateContentView(dialog, style);
        dialog.setContentView(contentView);

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();

        final Setter<View> shadow = new Setter<>();
        shadow.value = contentView.findViewById(R.id.v_shadow);
        if (shadow.value != null && shadow.value.getVisibility() == View.INVISIBLE) {
            shadow.value = null;
        }
        if (behavior instanceof BottomSheetBehavior) {
            BottomSheetBehavior.BottomSheetCallback bottomSheetCallback =
                    new BottomSheetBehavior.BottomSheetCallback() {

                        @Override
                        public void onStateChanged(@NonNull View bottomSheet, int newState) {
                            if (callback != null && callback.onStateChanged(bottomSheet, newState)) {
                                return;
                            }
                            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                                dismiss();
                            }

                        }

                        @Override
                        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                            if (callback != null && callback.onSlide(bottomSheet, slideOffset)) {
                                return;
                            }
                            if (shadow.value != null) {
                                shadow.value.setVisibility(slideOffset >= 1.0f ? View.VISIBLE : View.GONE);
                            }
                        }
                    };


            ((BottomSheetBehavior) behavior).setBottomSheetCallback(bottomSheetCallback);
        }
    }

    public static class Builder {

        private static final Object EMPTY_ICON = new Object();

        private Object mTitle;
        private View mContent;
        private List<Object> mOptionIcons;
        private List<Object> mOptionNames;
        private List<Command> mOptionCommands;
        private Callback callback;
        private RecyclerView.Adapter mAdapter;

        public Builder title(@NonNull CharSequence title) {
            if (this.mTitle != null) {
                throw new DialogError("Repeat the same");
            }
            this.mTitle = title;
            return this;
        }

        public Builder title(@StringRes int resId) {
            if (this.mTitle != null) {
                throw new DialogError("Repeat the same");
            }
            this.mTitle = resId;
            return this;
        }

        public Builder adapter(@NonNull RecyclerView.Adapter adapter) {
            if (this.mContent != null || this.mAdapter != null || this.mOptionNames != null) {
                throw DialogError.Unsupported();
            }
            this.mAdapter = adapter;
            return this;
        }


        public Builder contentView(@NonNull View contentView) {
            if (this.mOptionNames != null) {
                throw DialogError.Unsupported();
            }
            this.mContent = contentView;
            return this;
        }

        private Builder optionPrivate(Object icon, Object name, Command command) {
            if (this.mContent != null) {
                throw DialogError.Unsupported();
            }
            if (this.mOptionNames == null) {
                this.mOptionIcons = new ArrayList<>();
                this.mOptionNames = new ArrayList<>();
                this.mOptionCommands = new ArrayList<>();
            }
            this.mOptionIcons.add(icon);
            this.mOptionNames.add(name);
            this.mOptionCommands.add(command);
            return this;
        }

        public Builder option(Object icon, CharSequence name, Command command) {
            return optionPrivate(icon, name, command);
        }

        public Builder option(CharSequence name, Command command) {
            return optionPrivate(EMPTY_ICON, name, command);
        }

        public Builder option(Object icon, int stringId, Command command) {
            return optionPrivate(icon, stringId, command);
        }

        public Builder option(int stringId, Command command) {
            return optionPrivate(EMPTY_ICON, stringId, command);
        }

        public <T> Builder option(Collection<T> values, final CommandFacade<T> command) {
            for (final T val : values) {
                optionPrivate(command.getIcon(val), command.getName(val), new Command() {
                    @Override
                    public void apply() {
                        command.apply(val);
                    }
                });
            }
            return this;
        }

        public <T> Builder option(T[] values, final CommandFacade<T> command) {
            return option(Arrays.asList(values), command);
        }

        public Builder callback(Callback callback) {
            if (this.callback != null) {
                throw new DialogError("Repeat the same");
            }
            this.callback = callback;
            return this;
        }

        public void show(FragmentActivity activity) {
            BottomSheetDialog sheet = new BottomSheetDialog();

            sheet.mTitle = this.mTitle;
            sheet.mContent = this.mContent;
            sheet.callback = this.callback;

            if (mAdapter == null && mContent == null && mOptionNames != null) {
                sheet.adapter = new BottomSheetAdapter(activity, sheet, mOptionIcons, mOptionNames, mOptionCommands);
            }
            sheet.show(activity.getSupportFragmentManager(), sheet.getTag());
        }

    }


}
