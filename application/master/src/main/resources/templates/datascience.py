
import pandas as pd
import matplotlib.pyplot as plt
from collections import Counter
from sklearn import model_selection
from sklearn.linear_model import LinearRegression
import numpy as np


def get_exercise_sessions():
    df = pd.read_csv('data/exercise-sessions.csv')
    df= df[pd.to_numeric(df['breaths'], errors='coerce').notnull()]
    df= df[pd.to_numeric(df['averageBreathLength'], errors='coerce').notnull()]
    df = df.apply(pd.to_numeric, errors='ignore')
    df = df[df['breaths'] < 20]
    return df

# Plots the Good Breaths versus Total Breaths for all patients
def plot_users_goodbreath_gameId():
    df = get_exercise_sessions()
    print(df.dtypes)
    users = list(Counter(df.patientRecordId))
    patientRecordId="patientRecordId"
    mean = df.groupby(["breaths", patientRecordId]).mean()
    plt.figure(figsize=(10,6))
    for userId in users:
        y=mean.unstack(patientRecordId)["goodBreaths"][userId]
        x = y.index
        plt.errorbar(x=x,y=y)

    plt.xlabel("Breaths")
    plt.ylabel("Good Breath")
    plt.title("Average good breath for total breaths for all patients")
    plt.legend(users, loc=2)
    plt.savefig('patientsbreaths.png')



# Plots the Good Breaths versus Total Breaths for all patients
def plot_games_good_bad_breaths():
    df = pd.read_csv('data/games-sessions.csv')

    users = list(Counter(df.gameId))
    gameId="gameId"
    mean = df.groupby(["breathCount", gameId]).mean()
    plt.figure(figsize=(10,6))
    for userId in users:
        y=mean.unstack(gameId)["goodBreathCount"][userId]
        x = y.index
        width = 1/1.5
        plt.bar(x,y, width)

    plt.xlabel("Breaths")
    plt.ylabel("Good Breath")
    plt.title("Average good breath for total breaths for all games")
    plt.legend(users, loc=2)
    plt.savefig('gamesbreaths.png')


def join_weather_exercise():
    df_exercise = get_exercise_sessions()
    df_exercise = df_exercise[pd.to_numeric(df_exercise['breaths'], errors='coerce').notnull()]
    df_weather = pd.read_csv('data/weather.csv')
    weather = []
    exercise_dates = df_exercise['startTime']
    for exercise in exercise_dates:
        date = exercise[:10]
        row = df_weather.loc[df_weather['date'] == date]
        try:
            temperature = int(row['maxtempC'])
        except:
            temperature = np.nan
        weather.append(temperature)
    df_exercise['weather'] = weather
    df_exercise = df_exercise[np.isfinite(df_exercise['weather'])]
    print(df_exercise)

    return df_exercise



def plot_heat_map():

    df = join_weather_exercise()
    usecols = ['weather', 'goodBreaths', 'averagePressure', 'averageBreathLength']
    df = df[usecols]
    corr = df.corr()

    plt.figure(figsize=(12, 9))
    plt.matshow(corr, fignum=3)
    plt.xticks(range(len(df.columns)), df.columns)
    plt.yticks(range(len(df.columns)), df.columns)
    plt.colorbar()
    #plt.show()
    plt.savefig('correlation.png')
    corr.to_csv('correlation.csv', sep=',')


def rmse(predictions, targets):
    differences = predictions - targets
    differences_squared = differences ** 2
    mean_of_differences_squared = differences_squared.mean()
    rmse_val = np.sqrt(mean_of_differences_squared)
    return rmse_val

def linear_regression():
    file = open("linear_regression_results.txt", "w")
    df = join_weather_exercise()
    usecols = ['weather', 'averagePressure', 'breaths']

    X = df[usecols]
    Y = df['averageBreathLength']
    X_train, X_test, y_train, y_test = model_selection.train_test_split(X, Y, test_size=0.2, random_state=7)

    # Initialize regressor
    regressor = LinearRegression()
    regressor.fit(X_train, y_train)

    print(regressor.coef_)
    file.write("Regression Coefficients:\nWeather AveragePressure Breaths\n")
    file.write(str(regressor.coef_))

    # Predicting the Test set results
    y_pred = regressor.predict(X_test)
    print(y_pred)
    file.write("\n\n Average Breath Predictions:\n\n")
    file.write(str(y_pred))

    rms = rmse(y_pred, y_test)
    file.write("\n\nRMSE= "+ str(rms))
    print(rms)



try:
  plot_users_goodbreath_gameId()
except Exception as e:
  print(e)


try:
    plot_games_good_bad_breaths()
except Exception as e:
    print(e)

try:
    linear_regression()
except Exception as e:
    print(e)

try:
    plot_heat_map()
except Exception as e:
    print(e)

