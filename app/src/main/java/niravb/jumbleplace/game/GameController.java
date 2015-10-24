package niravb.jumbleplace.game;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import niravb.jumbleplace.R;
import niravb.jumbleplace.Utilities;
import niravb.jumbleplace.data.models.ScoreModel;
import niravb.jumbleplace.data.sql.tables.CachedCountriesTable;
import niravb.jumbleplace.ui.Dialogs;

public class GameController implements Loader.OnLoadCompleteListener<Cursor> {

    //region API

    public static final String KEY_GAME_DATA_RECEIVER = "gameDataReceiver";

    public Bundle saveState(Bundle outState) {
        return gameViewModel.populateBundleForStateSave(outState);
    }

    public void resumeOrStartNewGame() {
        if (!gameViewModel.gameInProgress) {
            startNewGame();
        } else {
            advanceGame();
            updateScoreAndRemaining(false, false);
        }
    }

    public static GameController buildFromSavedStateIfPossible(Bundle savedInstanceState,
                                                               Context context, View rootView) {

        GameViewModel viewModel;

        if (savedInstanceState != null) {
            viewModel = new GameViewModel(savedInstanceState);

        } else {
            viewModel = new GameViewModel(context);
        }

        return new GameController(context, rootView, viewModel);

    }

    public void finishCurrentGameAndStartNewOne() {


        String dialogMessage = String.
                format(context.getString(R.string.finish_game_dialog_message),
                        gameViewModel.score, gameViewModel.jumbledCountries.length);

        Dialogs.showConfirmDialog(
                context,
                context.getString(R.string.finish_game_dialog_title),
                dialogMessage,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        ScoreModel scoreModel = new ScoreModel(context, gameViewModel.score);

                        if (scoreModel.create()) {

                            startNewGame();

                        } else {

                            dialog.cancel();

                            Dialogs.showInfoDialog(
                                    context,
                                    context.getString(R.string.finish_game_error_dialog_title),
                                    context.getString(R.string.finish_game_dialog_error_message),
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            startNewGame();

                                        }
                                    }
                            );
                        }

                    }
                },
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        startNewGame();

                    }
                }
        );

    }
    //endregion

    //region Internals
    private final Context context;

    private final View rootView;

    private final GameViewModel gameViewModel;

    private final CountriesCache countriesCache;

    private GameController(Context context,
                           View rootView,
                           GameViewModel gameViewModel) {

        this.context = context;
        this.rootView = rootView;
        this.gameViewModel = gameViewModel;
        this.countriesCache = new CountriesCache(context);

        final EditText countryGuessEditText =
                (EditText) rootView.findViewById(R.id.country_guess_edittext);

        final Button nextButton = (Button) rootView.findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                updateScoreAndRemaining(false, true);
                advanceGame();
            }
        });

        final Button guessButton = (Button) rootView.findViewById(R.id.guess_button);
        guessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String guessText = countryGuessEditText.getText().toString();
                if (!TextUtils.isEmpty(guessText)) {
                    verifyGuess(guessText);
                    advanceGame();
                }
            }
        });

    }

    private void startNewGame() {

        GameDataReceiver gameDataReceiver = new GameDataReceiver(new Handler());
        Intent intent = new Intent(context, GameDataService.class);

        if (countriesCache.shouldReload()) {

            intent.putExtra(KEY_GAME_DATA_RECEIVER, gameDataReceiver);
            context.startService(intent);

        } else {

            Loader<Cursor> countriesLoader = countriesCache.fetchData();
            countriesLoader.registerListener(0, this);
            countriesLoader.startLoading();

        }
    }

    private void advanceGame() {

        if (gameViewModel.nextCountryIndex >= gameViewModel.jumbledCountries.length) {
            finishCurrentGameAndStartNewOne();
        } else {
            TextView jumbledCountryTextView = (TextView) rootView.
                    findViewById(R.id.jumbled_country_textview);
            jumbledCountryTextView.setText(gameViewModel
                    .jumbledCountries[gameViewModel.nextCountryIndex]);
            EditText countryGuessEditText = (EditText) rootView.
                    findViewById(R.id.country_guess_edittext);
            countryGuessEditText.setText("");
            gameViewModel.nextCountryIndex++;
        }

    }

    private void updateScoreAndRemaining(boolean shouldUpdateScore, boolean shouldUpdateRemaining) {
        if (shouldUpdateScore) {
            gameViewModel.score++;
        }

        if (shouldUpdateRemaining && gameViewModel.numCountriesRemaining > 0)
            gameViewModel.numCountriesRemaining--;


        ((TextView) rootView.findViewById(R.id.current_score_and_remaining)).
                setText(String.format(
                        context.getString(R.string.number_over_number),
                        gameViewModel.score,
                        gameViewModel.numCountriesRemaining));
    }

    private void verifyGuess(String guess) {

        String correctCountry = gameViewModel.jumbledToOriginalCountries.
                get(gameViewModel.jumbledCountries[gameViewModel.nextCountryIndex - 1]).
                trim().toLowerCase();

        String guessTrimmedAndLowercased = guess.trim().toLowerCase();

        updateScoreAndRemaining(guessTrimmedAndLowercased.equals(correctCountry), true);

    }

    private void initializeGameState(List<String> countries) {

        gameViewModel.populateCountriesDataFromAPI(countries);
        gameViewModel.nextCountryIndex = 0;
        gameViewModel.score = 0;
        gameViewModel.numCountriesRemaining = Utilities.getNumberOfCountriesPreferenceValue(context);
        advanceGame();
        updateScoreAndRemaining(false, true);
        gameViewModel.gameInProgress = true;

    }

    @Override
    public void onLoadComplete(Loader<Cursor> loader, Cursor data) {

        List<String> countries = new ArrayList<>();
        while (data.moveToNext()) {
            countries.add(data.getString(CachedCountriesTable.ColumnOriginalName.COLUMN_INDEX));
        }
        initializeGameState(countries);

        Intent intent = new Intent(context, GameDataService.class);
        context.startService(intent);

    }

    public class GameDataReceiver extends ResultReceiver {

        public GameDataReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {

            List<String> countriesList = resultData.
                    getStringArrayList(GameDataService.KEY_COUNTRIES_LIST);

            if (countriesList != null) {
                initializeGameState(countriesList);
            }

        }
    }


    //endregion

}
