package hu.uniobuda.nik.weathergame;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by gabo on 2016. 04. 29..
 */
public class MainFragment extends Fragment {

    private AppCompatButton newGameButton;
    private AppCompatButton optionsButton;
    private AppCompatButton highscoresButton;
    FragmentManager fm;
    FragmentTransaction ft;

    private View root;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.main_menu, container, false);
        return  root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        newGameButton = (AppCompatButton) root.findViewById(R.id.subOption1Button);
        optionsButton = (AppCompatButton) root.findViewById(R.id.suboption2Button);
        highscoresButton = (AppCompatButton) root.findViewById(R.id.highscoresButton);

        optionsButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                OptionsFragment fragment = OptionsFragment.newInstance();

                fm = getActivity().getSupportFragmentManager();
                ft = fm.beginTransaction();
                ft.addToBackStack(OptionsFragment.class.toString());
                ft.replace(R.id.fragment_container, fragment);
                ft.commit();

            }
        });

        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NewGameFragment fragment = NewGameFragment.newInstance();

                fm = getActivity().getSupportFragmentManager();
                ft = fm.beginTransaction();
                ft.addToBackStack(OptionsFragment.class.toString());
                ft.replace(R.id.fragment_container, fragment);
                ft.commit();
            }
        });

        highscoresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HighscoresFragment fragment = HighscoresFragment.newInstance();

                fm = getActivity().getSupportFragmentManager();
                ft = fm.beginTransaction();
                ft.addToBackStack(OptionsFragment.class.toString());
                ft.replace(R.id.fragment_container, fragment);
                ft.commit();
            }
        });
    }
}
