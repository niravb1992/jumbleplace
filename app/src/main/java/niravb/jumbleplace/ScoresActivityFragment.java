package niravb.jumbleplace;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import niravb.jumbleplace.data.models.ScoreModel;
import niravb.jumbleplace.ui.Dialogs;
import niravb.jumbleplace.ui.ScoresListCursorAdapter;

/**
 * A placeholder fragment containing a simple view.
 */
public class ScoresActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private ScoresListCursorAdapter cursorAdapter;

    private ListView scoresListView;

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

        scoresListView = (ListView) rootView.findViewById(R.id.listView);
        scoresListView.setAdapter(cursorAdapter);

        registerForContextMenu(scoresListView);

        return rootView;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_score_item, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.action_share_score:
                View v = scoresListView.getAdapter().getView(info.position, null, null);
                TextView scoredOverTotalTextView = (TextView) v.findViewById(R.id.scored_over_total_textview);
                Intent intent = Utilities.
                        getScoreShareIntent(getContext(),
                                scoredOverTotalTextView.getText().toString());
                startActivity(Intent.createChooser(intent, "Share Score"));
                return true;
            default:
                Cursor cursor = (Cursor) scoresListView.getAdapter().getItem(info.position);
                ScoreModel.delete(getActivity(), cursor.getInt(Utilities.TABLE_ID_COLUMN_INDEX));
                return super.onContextItemSelected(item);
        }

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        int SCORES_LOADER = 0;
        getLoaderManager().initLoader(SCORES_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.action_clear_scores:

                Dialogs.showConfirmDialog(
                        getActivity(),
                        getString(R.string.clear_scores_dialog_title),
                        getString(R.string.clear_scores_dialog_message),
                        getString(R.string.standard_dialog_positive_button_text),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                ScoreModel.deleteAll(getActivity());

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
