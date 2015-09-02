package niravb.jumbleplace;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import niravb.jumbleplace.game.GameController;

public class GameFragment extends Fragment {

    private View rootView;

    private GameController gameController;

    public GameFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        setHasOptionsMenu(true);

        super.onCreate(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(gameController.saveState(outState));
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        gameController =
                GameController.
                        buildFromSavedStateIfPossible(
                                savedInstanceState,
                                getActivity(),
                                rootView
                        );
        gameController.resumeOrStartNewGame();

        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_game_options, menu);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_game, container, false);

        return rootView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.action_newgame:
                gameController.finishCurrentGameAndStartNewOne();
                return true;
            case R.id.action_view_scores:
                startActivity(new Intent(getActivity(), ScoresActivity.class));
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}
