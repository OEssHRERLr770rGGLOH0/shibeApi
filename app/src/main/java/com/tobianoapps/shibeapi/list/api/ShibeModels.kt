package com.tobianoapps.shibeapi.list.api

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

object ShibeModels {

    /**
     * Recycler view item captions
     */
    @Keep
    enum class DoggoLingo(val sentence: String) {
        GIB("Do a gib!"),
        FREN("Henlo, Fren!"),
        PUPGRADE("Pupgraded!"),
        WTF("What the Floof!?"),
        BLEP("Blep."),
        BORK("Bork, bork!"),
        SCHMAKKOS("Do u has schmakkos?"),
        HECKIN("Heckin good boy!"),
        MLEM("Mlem..."),
        WOOFER("Did u call me a woofer?"),
        PUPPLAUSE("Pupplause!!!!!");

        companion object {
            val randomSentence: String
                get() = "\"${values().random().sentence}\""
        }
    }


    /**
     * Bundles image and caption to represent one doggo.
     *
     * @param url String url returned by the [ShibeApi]
     * @param doggoLingo Caption for the image
     */
    @Keep
    @Parcelize
    data class Shibe(
        val url: String,
        val doggoLingo: String
    ) : Parcelable
}