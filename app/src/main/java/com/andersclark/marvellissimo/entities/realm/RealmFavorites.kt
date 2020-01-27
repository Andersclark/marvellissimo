package com.andersclark.marvellissimo.entities.realm

import io.realm.RealmList
import io.realm.RealmObject

open class RealmFavorites (
    var favorites: RealmList<RealmMarvelEntity> = RealmList()
) : RealmObject()
