package hu.uniobuda.nik.weathergame;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by gabo on 2016. 04. 29..
 */
public class NewGameFragment extends Fragment {

    private View root;

    public static NewGameFragment newInstance() {
        Bundle bundle = new Bundle();
        NewGameFragment fragment = new NewGameFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.newgame_fragment, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
}

