About
=======

JumblePlace is a simple [Android](https://www.android.com/) game to test your knowledge of the world's countries. This app was my final project for the [Developing Android Apps course on Udacity](https://www.udacity.com/course/developing-android-apps--ud853).

Each game shows you some jumbled country names. You're supposed to guess the country from a given jumbled country name. If your guess is correct, you are awarded a point. If you guess incorrectly or you skip to the next country, you are not given any point. 

![](https://github.com/niravb1992/jumbleplace/blob/master/screenshots/game.png)

Features
===========

#### Scores
* Save your score at the end of the game and view all your past scores in one place
* Share your scores

![](https://github.com/niravb1992/jumbleplace/blob/master/screenshots/view_scores.png)
![](https://github.com/niravb1992/jumbleplace/blob/master/screenshots/share_score.png)

#### Settings
* Set how many countries you'd like to guess per game.

![](https://github.com/niravb1992/jumbleplace/blob/master/screenshots/num_countries_per_game_setting.png)

#### Optimized for tablets

# Internals

## Backend
When a user starts the app for the first time or changes his/her preference for "Number of Countries Per Game", the app does the following in the background:
* Makes an HTTP GET request to a web service endpoint requesting 2*n country names, with n depending on the user's current preference for "Number of Countries Per Game". The endpoint returns a shuffled list of country names.
* Empties the cache (a SQLite database table).
* Caches n of those country names (Stores them in that SQLite database table) and uses the remaining n country names for the current game. 

When the current game is over and a new game is started, the app does the following in the background:
* Loads the existing n items from the cache.
* Makes an HTTP GET request to the same web service endpoint, this time requesting n country names. When those n country names are returned, the cache is emptied and new country names are stored for the next game. 

The caching mechanism enables a new game to start instantly after finishing the current one. This is because the data for a new game is loaded from the local SQLite database and the fetching of the data for the next game happens in the background, without the user knowing. 

## Jumbling

Each country name is jumbled via the [Fisher Yates Shuffle](https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle).

