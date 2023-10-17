package com.autoparts.tashleeh;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.autoparts.tashleeh.Model.DataModel;
import com.autoparts.tashleeh.Model.Products;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment {

    private DatabaseReference ProductsRef;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private List<Products> dataList;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GalleryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GalleryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GalleryFragment newInstance(String param1, String param2) {
        GalleryFragment fragment = new GalleryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        // This callback is only called when MyFragment is at least started
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                replaceFragment(new GalleryFragment());
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
        // The callback can be enabled or disabled here or in handleOnBackPressed()

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        //dataList = generateData(); // You need to implement this method
        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");
        recyclerView = view.findViewById(R.id.recyclerView);

        FirebaseRecyclerOptions<Products> options =
                new FirebaseRecyclerOptions.Builder<Products>()
                        .setQuery(ProductsRef, Products.class)
                        .build();

        FirebaseRecyclerAdapter<Products, RecyclerViewAdapter.MyViewHolder> adapter =
                new FirebaseRecyclerAdapter<Products, RecyclerViewAdapter.MyViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull RecyclerViewAdapter.MyViewHolder holder, int position, @NonNull final Products model)
                    {
                        holder.cardTitle.setText(model.getPname());
                        Picasso.get().load(model.getImage()).into(holder.cardImage);
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ItemDetailFragment dataFragment = new ItemDetailFragment();
                                Bundle bundle = new Bundle();
                                bundle.putString("pid",model.getPid());
                                dataFragment.setArguments(bundle);

                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.frame_layout, dataFragment);
                                fragmentTransaction.commit();

                            }
                        });
                    }

                    @NonNull
                    @Override
                    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
                        return new RecyclerViewAdapter.MyViewHolder(view);
                    }
                };
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setAdapter(adapter);
        adapter.startListening();

        // Set up RecyclerView
//        adapter = new RecyclerViewAdapter(getActivity(),dataList);
//        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
//        recyclerView.setAdapter(adapter);


        return view;
    }

    private List<DataModel> generateData() {
         List<DataModel> dataList = new ArrayList<>();
         dataList.add(new DataModel("قطع غيار1","https://t3.ftcdn.net/jpg/01/59/42/26/240_F_159422695_LshFmeM9uQVJO73K7klE3zQRDk4QxlC8.jpg"));
         dataList.add(new DataModel("قطع غيار2","https://www.asm-autos.co.uk/image/fit-800/images/car-door-for-sale.jpg"));
         dataList.add(new DataModel("قطع غيار3","https://t3.ftcdn.net/jpg/01/59/42/26/240_F_159422695_LshFmeM9uQVJO73K7klE3zQRDk4QxlC8.jpg"));
         dataList.add(new DataModel("قطع غيار4","https://www.asm-autos.co.uk/image/fit-800/images/car-door-for-sale.jpg"));
         dataList.add(new DataModel("قطع غيار5","https://t3.ftcdn.net/jpg/01/59/42/26/240_F_159422695_LshFmeM9uQVJO73K7klE3zQRDk4QxlC8.jpg"));
         dataList.add(new DataModel("قطع غيار6","https://www.asm-autos.co.uk/image/fit-800/images/car-door-for-sale.jpg"));

         return dataList;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

}