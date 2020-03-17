package com.example.reminder.Interfaces

import androidx.room.*
import com.example.reminder.Models.Reminder

/*
To access your app's data using the Room persistence library, you work with data access objects, or DAOs.
This set of Dao objects forms the main component of Room, as each DAO includes methods that offer
abstract access to your app's database. By accessing a database using a DAO class instead of query
builders or direct queries, you can separate different components of your database architecture.

A Dao can either be an interface or an abstract class.

Room also provides some convenient annotations for CRUD (create, read, update, delete) operations which we used. You can also create queries using the Query annotation. We used this to retrieve all reminders from the database.

Room will take this interface and implement all the methods for you.
 */
@Dao
interface ReminderDao {

    //By adding the suspend keyword to the method we have specified that
    // this  method cannot be called without using Coroutines.

    @Query("SELECT * FROM reminderTable")
    suspend fun getAllReminders(): List<Reminder>

    @Insert
    suspend fun insertReminder(reminder: Reminder)

    @Delete
    suspend fun deleteReminder(reminder: Reminder)

    @Update
    suspend fun updateReminder(reminder: Reminder)
}