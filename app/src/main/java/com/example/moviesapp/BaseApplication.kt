package com.example.moviesapp

import android.support.multidex.MultiDexApplication
import dagger.hilt.android.HiltAndroidApp
@HiltAndroidApp
class BaseApplication : MultiDexApplication() {}