# Popular Movies ANdroid Application

Fully functional app for [Android Developer Nanodegree program](https://www.udacity.com/course/android-developer-nanodegree--nd801). This app uses responsive design for phones and tablets. It is reviewed by Udacity code reviewer. 

## Features of the App

With the app, you can:
* Discover the most popular or the highest rated movies
* Save favorite movies locally to view them even when offline
* Watch trailers
* Read reviews
* Share movies

## How to Work with the Source

This app uses [The Movie Database](https://www.themoviedb.org/documentation/api) API to retrieve movies.
You must provide your own API key in order to build the app. When you get it, just paste it to `network/MovieModule.java` file: 
```java    
public static final String API_KEY = "YOU_API_KEY";
```

## Libraries Used

* [ButterKnife](https://github.com/JakeWharton/butterknife)
* [Dagger 2](https://github.com/google/dagger)
* [Auto Dagger 2](https://github.com/lukaspili/Auto-Dagger2)
* [Retrofit](https://github.com/square/retrofit)
* [Sugar ORM](https://github.com/satyan/sugar)
* [RxJava](https://github.com/ReactiveX/RxJava)
* [RxAndroid](https://github.com/ReactiveX/RxAndroid)
* [Glide](https://github.com/bumptech/glide)
* [Gradle Retrolambda Plugin](https://github.com/evant/gradle-retrolambda)

## Android Developer Nanodegree
[![Udacity](https://cloud.githubusercontent.com/assets/3719141/14508774/c7f6cbce-01d1-11e6-9daf-02bcd10b6400.jpeg)](https://www.udacity.com/course/android-developer-nanodegree--nd801)

Readme is inspired by another project [Udacity popular movies](https://github.com/ewintory/udacity-popular-movies) check it out!
