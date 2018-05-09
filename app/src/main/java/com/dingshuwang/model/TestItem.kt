package com.dingshuwang.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by tx on 2017/7/26.
 */

class TestItem protected constructor(`in`: Parcel) : Parcelable {


    private val id: String
    private val name: String

    init {
        id = `in`.readString()
        name = `in`.readString()
    }

    override fun toString(): String {
        return "TestItem{" +
                "id='" + id + '\''.toString() +
                ", name='" + name + '\''.toString() +
                '}'.toString()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(id)
        dest.writeString(name)
    }

    companion object {

        val CREATOR: Parcelable.Creator<TestItem> = object : Parcelable.Creator<TestItem> {
            override fun createFromParcel(`in`: Parcel): TestItem {
                return TestItem(`in`)
            }

            override fun newArray(size: Int): Array<TestItem?> {
                return arrayOfNulls(size)
            }
        }
    }
}
