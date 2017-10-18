package eu.biketrack.beta.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.Unbinder;
import eu.biketrack.beta.R;
import eu.biketrack.beta.settings.Language;


public class LanguageFragment extends Fragment {
    private static String TAG = "BIKETRACK - Lang";
    private Unbinder unbinder;
    private String[] langs;
    private ArrayList<String> langList;
    private ArrayAdapter<String> listAdapter ;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.listView_languages)
    ListView langs_lv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        langs = getResources().getStringArray(R.array.language_arrays);
        langList = new ArrayList<>();
        langList.addAll(Arrays.asList(langs));
        Log.d(TAG, langList.toString());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_language, container, false);
        unbinder = ButterKnife.bind(this, layout);
        if (toolbar != null) {
            toolbar.setNavigationIcon(R.drawable.ic_close_white_24px);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    closeFragment();
                }
            });
        }
        return layout;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listAdapter = new ArrayAdapter<String>(getContext(), R.layout.simplerow, langList);
        if (langs_lv != null)
            langs_lv.setAdapter(listAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnItemClick(R.id.listView_languages)
    public void itemClicked(int position){
        if (position == 0)
            Language.changeLanguage(getActivity().getApplicationContext(), new Locale("en"));
        else if (position == 1)
            Language.changeLanguage(getActivity().getApplicationContext(), new Locale("fr"));
        closeFragment();
    }

    @OnClick(R.id.button_language_clear)
    public void resetLanguage(){
        Language.clearLanguage(getActivity().getApplicationContext());
        closeFragment();
    }

    public void closeFragment(){
        getActivity().getSupportFragmentManager().popBackStack();
    }
}
