package eu.biketrack.android.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.Unbinder;
import eu.biketrack.android.R;
import eu.biketrack.android.models.BrandSelected;

public class BrandFragment extends Fragment {
    private static String TAG = "BIKETRACK - Brand";
    private Unbinder unbinder;
    private String[] brands;
    private ArrayList<String> brandList;
    private ArrayAdapter<String> listAdapter ;
//    private Disposable _disposable_search;
    private BrandSelected brandSelected;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.listView_brands)
    ListView brands_lv;
    @BindView(R.id.search_bar_brand)
    TextView search;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        brands = getResources().getStringArray(R.array.brands_bike_array);
        brandList = new ArrayList<>();
        brandList.addAll(Arrays.asList(brands));
        brandSelected = BrandSelected.getOurInstance();
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View layout = inflater.inflate(R.layout.fragment_brand, container, false);
//        unbinder = ButterKnife.bind(this, layout);
//        if (toolbar != null) {
//            toolbar.setNavigationIcon(R.drawable.ic_close_white_24px);
//            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    closeFragment();
//                }
//            });
//        }
//        return layout;
//    }
//
//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        listAdapter = new ArrayAdapter<String>(getContext(), R.layout.simplerow, brandList);
//        if (brands_lv != null)
//            brands_lv.setAdapter(listAdapter);
//
//        _disposable_search = RxJavaInterop.toV2Observable(RxTextView.textChangeEvents(search))
//                .debounce(200, TimeUnit.MILLISECONDS)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(updateList());
//    }
//
//    private DisposableObserver<TextViewTextChangeEvent> updateList() {
//        return new DisposableObserver<TextViewTextChangeEvent>() {
//            @Override
//            public void onComplete() {
//            }
//
//            @Override
//            public void onError(Throwable e){
//            }
//
//            @Override
//            public void onNext(TextViewTextChangeEvent onTextChangeEvent) {
//                listAdapter.getFilter().filter(onTextChangeEvent.text());
//            }
//        };
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        unbinder.unbind();
//    }
//
//    @OnItemClick(R.id.listView_brands)
//    public void itemClicked(int position){
//        String item = listAdapter.getItem(position);
//        new AlertDialog.Builder(this.getContext())
//                .setIcon(android.R.drawable.ic_dialog_info)
//                .setTitle(R.string.alert_confirmation_brand_title)
//                .setMessage(item)
//                .setPositiveButton(R.string.alert_confirmation_brand_yes,new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        returnValue(item);
//                    }
//                })
//                .setNegativeButton(R.string.alert_confirmation_brand_no, null)
//                .show();
//    }
//
//    private void returnValue(String value){
//        brandSelected.setBrand(value);
//        this.getFragmentManager().popBackStack();
//    }
//
//    public void closeFragment(){
//        getActivity().getSupportFragmentManager().popBackStack();
//    }

}
