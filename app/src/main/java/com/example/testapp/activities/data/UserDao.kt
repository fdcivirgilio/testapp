package com.example.testapp.activities.data

import androidx.room.*

@Dao
interface UserDao {
    @Insert
    suspend fun insert(user: User)

    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<User>

    @Query("SELECT * FROM users WHERE email_address = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): User?

    @Query("SELECT * FROM users WHERE token = :token LIMIT 1")
    suspend fun getUserByToken(token: String): User?

    @Query("UPDATE users SET token = :newToken WHERE id = :userId")
    suspend fun updateToken(userId: Int, newToken: String)

    @Query("UPDATE users SET token = :newToken WHERE email_address = :email")
    suspend fun updateTokenByEmail(email: String, newToken: String)

    @Query("UPDATE users SET email_address = :emailAddress, age = :age, name = :name WHERE id = :id")
    suspend fun updateUserDetails(emailAddress: String, id: Int, age: Int, name: String): Int

    @Delete
    suspend fun delete(user: User)
}