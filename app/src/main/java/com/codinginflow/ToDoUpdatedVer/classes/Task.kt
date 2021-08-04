package com.codinginflow.ToDoUpdatedVer.classes

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.text.DateFormat
@Entity(tableName = "task_table")
@Parcelize // Serializable kinda
data class Task(
    val name:String,
    val important:Boolean=false,
    val completed:Boolean=false,
    val creation:Long= System.currentTimeMillis(),//milliseconds
    @PrimaryKey(autoGenerate = true) val id: Int=0




) :Parcelable{
    val creationDate:String
        get() = DateFormat.getDateTimeInstance().format(creation)

}

