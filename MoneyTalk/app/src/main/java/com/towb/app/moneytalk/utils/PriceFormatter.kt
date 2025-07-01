package com.towb.app.moneytalk.utils

object PriceFormatter {

    fun format(price: Int, suffix: String = "원"): String {
        return "%,d$suffix".format(price)
    }
}