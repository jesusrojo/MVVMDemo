package com.jesusrojo.mvvmdemo.data.model

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity(tableName = "uidata_table")
data class UiData(
    val dataId: Int,
    val name: String,
    val avatarUrl: String,
    val title: String,
    val description: String,
    val fullDescription: String,
    val forksCount: Int,
    val startsCount: Int,
): Parcelable {

    @PrimaryKey(autoGenerate = true) var id: Long = 0

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readInt(),
    ) {
        id = parcel.readLong()
    }

    override fun toString(): String {
        return "*** FULL DESCRIPTION ***\n$fullDescription"
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(dataId)
        parcel.writeString(name)
        parcel.writeString(avatarUrl)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(fullDescription)
        parcel.writeInt(forksCount)
        parcel.writeInt(startsCount)
        parcel.writeLong(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UiData> {
        override fun createFromParcel(parcel: Parcel): UiData {
            return UiData(parcel)
        }

        override fun newArray(size: Int): Array<UiData?> {
            return arrayOfNulls(size)
        }
    }
}