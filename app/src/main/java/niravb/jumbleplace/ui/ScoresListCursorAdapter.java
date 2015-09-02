package niravb.jumbleplace.ui;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import niravb.jumbleplace.R;
import niravb.jumbleplace.data.sql.tables.ScoresTable;

public class ScoresListCursorAdapter extends CursorAdapter {

    public ScoresListCursorAdapter(Context context) {
        super(context, null, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        return LayoutInflater.from(context).inflate(R.layout.list_item_score, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView createdAtTextView = (TextView) view.findViewById(R.id.created_at_textview);
        TextView scoredOverTotalTextView = (TextView) view.findViewById(R.id.scored_over_total_textview);

        createdAtTextView.
                setText(cursor.getString(ScoresTable.ColumnCreatedAt.COLUMN_INDEX));
        scoredOverTotalTextView.
                setText(
                        cursor.getInt(ScoresTable.ColumnScoredPoints.COLUMN_INDEX)
                                + "/"
                                + cursor.getInt(ScoresTable.ColumnTotalPoints.COLUMN_INDEX));

    }
}
