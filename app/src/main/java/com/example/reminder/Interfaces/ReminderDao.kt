package com.example.reminder.Interfaces

import androidx.room.Dao

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
}