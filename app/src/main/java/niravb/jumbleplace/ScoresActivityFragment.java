package niravb.jumbleplace;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import niravb.jumbleplace.data.models.ScoreModel;
import niravb.jumbleplace.ui.Dialogs;
import niravb.jumbleplace.ui.ScoresListCursorAdapter;

/**
 * A placeholder fragment containing a simple view.
 */
public class ScoresActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private ScoresListCursorAdapter cursorAdapter;
    private final int SCORES_LOADER = 0;

    public ScoresActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        setHasOptionsMenu(true);

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        cursorAdapter = new ScoresListCursorAdapter(getActivity());

        View rootView = inflater.inflate(R.layout.fragment_scores, container, false);

        ListView listView = (ListView) rootView.findViewById(R.id.listView);
        listView.setAdapter(cursorAdapter);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        getLoaderManager().initLoader(SCORES_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.action_clear_scores:

                final ScoresActivityFragment scoresActivityFragment = this;

                Dialogs.showConfirmDialog(
                        getActivity(),
                        getString(R.string.clear_scores_dialog_title),
                        getString(R.string.clear_scores_dialog_message),
                        getString(R.string.standard_dialog_positive_button_text),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                ScoreModel.deleteAll(getActivity());
                                getLoaderManager().restartLoader(SCORES_LOADER, null,
                                        scoresActivityFragment);

                            }
                        },
                        getString(R.string.standard_dialog_negative_button_text),
                        null
                );


                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return ScoreModel.getAll(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        cursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        cursorAdapter.swapCursor(null);
    }
}
