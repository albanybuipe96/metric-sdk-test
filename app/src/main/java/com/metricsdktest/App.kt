package com.metricsdktest

import android.app.Application
import android.graphics.Color
import com.metric.sdk.BuildConfig
import com.metric.sdk.init.BasicMetricSettings
import com.metric.sdk.init.ClientAuthenticator
import com.metric.sdk.init.Environment
import com.metric.sdk.init.Metric
import com.metric.sdk.init.ThemeProvider
import com.metric.sdk.theme.AppLogo
import com.metric.sdk.theme.AppTheme

/**
 * @author Sam
 * Created 02/08/2023 at 12:24 am
 */

class App : Application() {

    private val metricSettings by lazy {
        BasicMetricSettings(
            applicationContext = this,
            themeProvider = ThemeProvider(
                appTheme = {
                    AppTheme(
                        appName = "",
                        logo = AppLogo.NetworkImage("https://upload.wikimedia.org/wikipedia/commons/thumb/2/2f/Google_2015_logo.svg/1280px-Google_2015_logo.svg.png"),
                        primaryColor = Color.RED,
                    )
                }
            ),
            authenticator = { getAuthenticator(BuildConfig.DEBUG) },
            environment = if (BuildConfig.DEBUG) Environment.Dev else Environment.Prod,
//            environment = Environment.Prod,
        )
    }

    companion object {
        private const val devClientKey = "Cgy3XA6YW0QV39NCcXA5"
        private const val devSecretKey = "aDwjQrAfSt2bbWyti37rRcbhPErq7XN0DT1Bgzx91KyUE"

        private const val prodClientKey = "Cgy3XA6YW0QV39NCcXA5"
        private const val prodSecretKey = "aDwjQrAfSt2bbWyti37rRcbhPErq7XN0DT1Bgzx91KyUE"

        fun getAuthenticator(isDev: Boolean): ClientAuthenticator {
            return ClientAuthenticator(
                clientKey = if (isDev) devClientKey else prodClientKey,
                secretKey = if (isDev) devSecretKey else prodSecretKey
            )
        }
    }

    override fun onCreate() {
        super.onCreate()
        initMetricSdk()
    }

    private fun initMetricSdk() {
        Metric.init(metricSettings)
    }
}
