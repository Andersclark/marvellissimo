package com.andersclark.marvellissimo.entities.realm

import io.realm.RealmObject

open class RealmUser (
        var uid: String = "",
        var username: String = "",
        var email: String = ""
    ): RealmObject()