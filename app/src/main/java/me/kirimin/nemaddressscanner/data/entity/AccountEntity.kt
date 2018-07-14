package me.kirimin.nemaddressscanner.data.entity

class AccountEntity {

    lateinit var address: String
    lateinit var balance: String

    val displayBalance get() = (balance.toDouble() / 1000000).toString() + " xem"
}