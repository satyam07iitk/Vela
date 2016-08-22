package com.vela.app.mobile.ui.items;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.vela.app.mobile.R;
import com.vela.app.mobile.models.response.Category;
import com.vela.app.mobile.ui.base.BaseFragment;
import com.vela.app.mobile.ui.items.dummy.DummyContent;
import com.vela.app.mobile.ui.items.dummy.DummyContent.DummyItem;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ItemsFragment extends BaseFragment {

    private OnListFragmentInteractionListener mListener;

    private Toolbar toolbarItems;
    private Spinner spinnerSelectCategory;
    private RecyclerView recyclerViewItems;
    private ArrayList<Category> categoryList;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemsFragment() {
    }

    public static ItemsFragment newInstance() {
        ItemsFragment fragment = new ItemsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        categoryList = new ArrayList<>();
        Category category = new Category("0", "All", "", "");
        categoryList.add(category);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_items, container, false);
        toolbarItems = (Toolbar) view.findViewById(R.id.toolbar_items);
        spinnerSelectCategory = (Spinner) toolbarItems.findViewById(R.id.spinner_select_category);
        recyclerViewItems = (RecyclerView) view.findViewById(R.id.recycler_view_items);
        // Set the adapter
        recyclerViewItems.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewItems.setAdapter(new ItemRecyclerViewAdapter(DummyContent.ITEMS, mListener));
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbarItems);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        this.setHasOptionsMenu(true);
        spinnerSelectCategory.setAdapter(new CategoryBaseAdapter(categoryList));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnListFragmentInteractionListener) {
//            mListener = (OnListFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnListFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_items, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class CategoryBaseAdapter extends BaseAdapter {

        private ArrayList<Category> categoryList;

        public CategoryBaseAdapter(ArrayList<Category> categoryList) {
            this.categoryList = categoryList;
        }

        @Override
        public int getCount() {
            return categoryList.size();
        }

        @Override
        public Object getItem(int position) {
            return categoryList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                view = View.inflate(getContext(), R.layout.view_category_dropdown, null);
            }
            TextView textView = (TextView) view.findViewById(R.id.text_view_title);
            textView.setText(categoryList.get(position).getName());
            return view;
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DummyItem item);
    }
}
