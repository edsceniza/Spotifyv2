package edna.ceniza.com.spotifyv20

import android.os.Parcel
import android.os.Parcelable
/**
 * Created by Edna Ceniza on 14/01/2018.
 */
data class SongList(
        val song_title: String = " ",
        val song_artist: String = " ",
        val song_album: String = " ",
        val song_path: String = " ",
        val stat: Int = 0
):Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt()
    ) {
    }


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(song_title)
        parcel.writeString(song_artist)
        parcel.writeString(song_album)
        parcel.writeString(song_path)
        parcel.writeInt(stat)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SongList> {
        override fun createFromParcel(parcel: Parcel): SongList {
            return SongList(parcel)
        }

        override fun newArray(size: Int): Array<SongList?> {
            return arrayOfNulls(size)
        }
    }
}

